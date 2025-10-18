import { InfoNoteDTO } from "../../dto/infoNoteDTO.js";
import { CreateNoteDTO } from "../../dto/createNoteDTO.js";
import { sendNote } from "../../controller/querys.js";
import { getElements } from "../../controller/querys.js";
import { getToken, isTokenValid } from "../../valid/token/token.js";
import { URL_NOTES, URL_BARNOTE, URL_OPTIONS, URL_CREATENOTE, URL_BUTTONS } from "../../data/const.js";
import { cargarHTML, containderDiv } from "../../main/main.js";
import { tokenNotValidLoadLoginFrom, unLogin } from "../auth/login/login.js";

let currentPage = 0; // Página inicial
let totalPages = 0;  // Se actualizará con la respuesta del backend

function handleClickNoteList(container, type, page) {
  return () => getNotes(container,type, page);
}

function handleClickPrevPage(container, type, currentPage){
    return () => {
        if(currentPage > 0){
            getNotes(container,type,currentPage -1);
        }
    }
}

function handleClickNextPage(container, type, currentPage, totalPages){
    return () => {
        if(currentPage < totalPages -1){
            getNotes(container,type,currentPage + 1);
        }
    }
}

function handleClickCreateNote(container, createNote, URL_CREATENOTE) {
  return () => createNewNote(container, createNote, URL_CREATENOTE);
}


function converNote(note){
    return new InfoNoteDTO(
        note.id,
        note.titulo,
        note.descripcion
    );
}

function createNoteDiv(note){
    // Creo un elmento div
    const noteDiv = document.createElement('div');
    
    // Asigna el id al div
    noteDiv.id = `note-${note.id}`; 

    // Asinga la clase "note"
    noteDiv.classList.add("note");
        
    // Creo el elemento h3
    const titleHThree = document.createElement('h3');
        
    // Le asigno el titulo
    titleHThree.textContent = note.titulo;
        
    // Creo el elemento p
    const descriptionP = document.createElement('p');
        
    // Le asigno la descripcion
    descriptionP.textContent = note.descripcion;
    
    // Agrego el titulo y la descripcion al div
    noteDiv.appendChild(titleHThree);
    noteDiv.appendChild(descriptionP);
    
    return noteDiv;
}

// Funcion que convierte la data en InfoNoteDTO
function convertNotes(data){
    return data.content.map( note => converNote(note));
}

// Funcion que almacena los div (notas) en un fragment
function fragmentNotes(noteList){
    // Creo un fragmento para no usar innerHTML
    const frag = document.createDocumentFragment();
    // recorro la lista de NoteDTO
    for (const note of noteList){

        // Div que contiene el titulo, descripcion de la nota
        const noteDiv = createNoteDiv(note);

        // Agrego el div al frag
        frag.appendChild(noteDiv);

    }
    return frag;
}

// Funcion que obtiene la lista de notas y las convierte en objetos DOM
async function getNotes(container,routeBase, page = 0) {
    
    // Obtener token desde localStorage
    const token = getToken();
    // El token es valido ?
    if(!isTokenValid()){
        // Si el token no es valido ingresa aqui
        alert("No estas autenticado, Por favor inicia sesion");
        try{
            // cargar el menu de inicio y el formulario y su respectivo evento de envio
            await tokenNotValidLoadLoginFrom();

        }catch(error){
            console.error("Error al cargar el formulario de Login:", error);
        }  
    }else{
        // Traer las notas desde el backend        
        const data = await getElements(token,`${routeBase}?page=${page}&size=4&orderBy=true`);
        // Verifica si retorna false
        if(data === false){
            // Si el token no es valido ingresa aqui
            try{
                // cargar el menu de inicio y el formulario y su respectivo evento de envio
                await tokenNotValidLoadLoginFrom();

            }catch(error){
                console.error("Error al cargar el formulario de Login:", error);
            }           
        }else{

            try{
                totalPages = data.totalPages;  // Guardamos total de páginas
                currentPage = data.number;     // Página actual que viene del backend

                // Limpiar el contenedor antes de agregar nuevas notas
                container.innerHTML = "";

                // Convertir cada nota del backend a un objeto NoteDTO
                const noteList = convertNotes(data);

                // traigo el frag 
                const frag = fragmentNotes(noteList);
                container.appendChild(frag);
                // Carga los botones de paginado
                await cargarBotonesPrevAndNext(routeBase);
        
            }catch(error){
            console.log("Error al otener notes: ", error);
            }
        }
    }
}

// Funcion que captura los datos del Formulario de crear nota y los convierte en un objeto CreateNoteDTO
function createNote(){
    // Capture los valores ingresado por el usuario
    const title = document.getElementById('title').value;
    const descripcion = document.getElementById('description').value;
    const status = document.getElementById('status').checked;
    // Objecto CreateNoteDTO
    const note = new CreateNoteDTO(title,status);

    // Valida si ingresaron descripcion
    if(descripcion.trim() !== ""){
        note.setDescripcion(descripcion);
    }
    return note;
}

// Funcion que envia los datos de crear una nota al Backend
async function sendFromNote(event) {
    // Previene que el formulario se envie de forma tradicional
    event.preventDefault();

    if(!isTokenValid()){
        // Si el token no es valido ingresa aqui
        alert("No estas autenticado, Por favor inicia sesion");
        try{
            // cargar el menu de inicio y el formulario y su respectivo evento de envio
            await tokenNotValidLoadLoginFrom();

        }catch(error){
            console.error("Error al cargar el formulario de Login:", error);
        }  
    }else{
        // Crea un Objeto CreateNoteDTO apartir de los datos del formulario de createNote
        const note = createNote();

        const token = getToken();
        // Funcion para enviar datos al backend
        const data = await sendNote(token, note, `${URL_NOTES}/create`);
        if( data === false){
            // Si el token no es valido ingresa aqui
            try{
                // cargar el menu de inicio y el formulario y su respectivo evento de envio
                await tokenNotValidLoadLoginFrom();

            }catch(error){
           
            }
        }else{
            try{
                // Limpiar el contenedor antes de agregar nuevas notas
                container.innerHTML = "";
                // recive la nota creada
                const infoNote = converNote(data);
                // la convierte a div
                
                const noteDiv = createNoteDiv(infoNote);
                // La agrega al contenedor
                container.appendChild(noteDiv);

            }catch(error){
                console.log("Error al crear Nota:", error);
            }
        }
    }
}

// Funcion que cargar el formulario para crear una nota y crea un evento para enviar el formulario
async function createNewNote(container, createNote, URL_CREATENOTE){
    const { options } = containderDiv();
    try{
        await cargarHTML(container, createNote, URL_CREATENOTE);
                    
        // Esperar a que el formulariuo se inyecte para añadir el evento
        const fromNote = document.getElementById('createNote');
                    
        if(fromNote){
            // Evento para enviar formulario para crear una nueva nota
            fromNote.addEventListener('submit' , sendFromNote);
        }else{
            console.error("El formulario no se encotrno despues de cargar");
        }
    }catch(error){
        console.error("Error al cargar el formulario:", error);
    }
}

function datosList(){
    return {
        active: `${URL_NOTES}/all/active`,
        archive: `${URL_NOTES}/all/archive`,
    };
}

// Funcion que  carga la interfaz lista de notas y opcion de crear nota y carga los eventos de estas listas
export async function cargarInterfazInterna(header,container) {
    
    // contenedor de mi lista de opciones
    const { options } = containderDiv();

    await cargarHTML(options,`listNote`, URL_OPTIONS);
    // Espera a que el formulario se inyecte para añadir el evento
                    
    // Listas para listar notas
    const activeLi = document.getElementById("active");
    const archiveLi = document.getElementById("archive");

    const noteRoutes = datosList();

    
    if (activeLi && archiveLi) {
        
        activeLi.addEventListener('click', handleClickNoteList(container, noteRoutes.active));
        archiveLi.addEventListener('click', handleClickNoteList(container, noteRoutes.archive));
        
    }else{
        console.error("Lista de Opciones no cargada");
    }

    await cargarHTML(header,`barNote`, URL_BARNOTE);
    // Espera a que el formulario se inyecte para añadir el evento
                    
    //  Lista que contiene las opciones de: crear Nota y Salir
    const createNoteLi = document.getElementById('create');
    const salirLi = document.getElementById('salir');

    // Verifica que los elmentos se cargaron
    if( createNoteLi && salirLi){
        // id del elemento a traer
        const createNote = 'createNote';    
        // Evento para enviar la nota creada            
        createNoteLi.addEventListener('click', handleClickCreateNote(container,createNote,URL_CREATENOTE));
        // Evento para desplogearse
        salirLi.addEventListener('click', unLogin);
    }else{
        console.error("Error al cargar el Menu de Notas");
    }

}

async function cargarBotonesPrevAndNext(routeBase) {
        
        await cargarHTML( container,`navNote`, URL_BUTTONS, false);
        // Espera a que el formulario se inyecte para añadir el evento

        const prevButton = document.getElementById('prev');
        const nextButton = document.getElementById('next');

        if (prevButton && nextButton){
            prevButton.addEventListener('click', handleClickPrevPage(container, routeBase, currentPage ));
            nextButton.addEventListener('click', handleClickNextPage(container,routeBase, currentPage, totalPages));
        }
}