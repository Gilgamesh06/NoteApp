import { InfoNoteDTO } from "../../dto/infoNoteDTO.js";
import { CreateNoteDTO } from "../../dto/createNoteDTO.js";
import { sendNote } from "../../controller/querys.js";
import { getElements } from "../../controller/querys.js";
import { getToken } from "../../data/saveToken.js";
import { URL_NOTES, URL_BARNOTE, URL_LISTOPTIONS, URL_CREATENOTE, URL_BUTTONS } from "../../data/const.js";
import { handleClickCargarHTML } from "../../main/methodMain.js";


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

export function handleClickCargarInterfazInterna(header,container) {
  return cargarInterfazInterna(header,container);
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
    try{
        // Obtener token desde localStorage
        const token = getToken();
        if(!token){
            alert("No estas autenticado, Por favor inicia sesion");
            return;
        }

        // Traer las notas desde el backend

        const data = await getElements(token,`${routeBase}?page=${page}&size=4&orderBy=true`);
        totalPages = data.totalPages;  // Guardamos total de páginas
        currentPage = data.number;     // Página actual que viene del backend

        // Limpiar el contenedor antes de agregar nuevas notas
        container.innerHTML = "";

        // Convertir cada nota del backend a un objeto NoteDTO
        const noteList = convertNotes(data);

        // traigo el frag 
        const frag = fragmentNotes(noteList);
        container.appendChild(frag);

        await cargarBotonesPrevAndNext(routeBase);
        
    }catch(error){
        console.log("Error al otener notes: ", error);
    }
}

// Funcion que envia los datos de crear una nota al Backend
async function sendFromNote(event) {
    // Previene que el formulario se envie de forma tradicional
    event.preventDefault();

    // Capture los valores ingresado por el usuario
    const title = document.getElementById('title').value;
    const descripcion = document.getElementById('description').value;
    const status = document.getElementById('status').checked;

    const note = new CreateNoteDTO(title,status);

    if(descripcion.trim() !== ""){
        note.setDescripcion(descripcion);
    }
    
    try{
        const token = getToken();
        // Funcion para enviar datos al backend
        const data = await sendNote(token, note, `${URL_NOTES}/create`);
        
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

// Funcion que cargar el formulario para crear una nota y crea un evento para enviar el formulario
async function createNewNote(container, createNote, URL_CREATENOTE){
                  
    try{
        await handleClickCargarHTML(container, createNote, URL_CREATENOTE);
                    
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
async function cargarInterfazInterna(header,container) {
    
    // Lista de opciones
    const listOptions = document.getElementById('list-options');

    await handleClickCargarHTML(listOptions,`listNote`, URL_LISTOPTIONS);
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

    await handleClickCargarHTML(header,`barNote`, URL_BARNOTE);
    // Espera a que el formulario se inyecte para añadir el evento
                    
    //  Lista para crear nota
    const createNoteLi = document.getElementById('create');
    
    // id del elemento a traer
    const createNote = 'createNote';                
    
    createNoteLi.addEventListener('click', handleClickCreateNote(container,createNote,URL_CREATENOTE));
}

async function cargarBotonesPrevAndNext(routeBase) {
        
        await handleClickCargarHTML( container,`navNote`, URL_BUTTONS, false);
        // Espera a que el formulario se inyecte para añadir el evento

        const prevButton = document.getElementById('prev');
        const nextButton = document.getElementById('next');

        if (prevButton && nextButton){
            prevButton.addEventListener('click', handleClickPrevPage(container, routeBase, currentPage ));
            nextButton.addEventListener('click', handleClickNextPage(container,routeBase, currentPage, totalPages));
        }
}