import { InfoNoteDTO } from "../../dto/infoNoteDTO.js";
import { CreateNoteDTO } from "../../dto/createNoteDTO.js";
import { changeStatusNote, deleteNote, sendNote } from "../../controller/querys.js";
import { getElements } from "../../controller/querys.js";
import { getToken, isTokenValid } from "../../valid/token/token.js";
import { URL_NOTES, URL_BARNOTE, URL_OPTIONS, URL_CREATENOTE, URL_BUTTONS, URL_BAROPTIONS, URL_UPDATENOTE } from "../../data/const.js";
import { cargarHTML, cargarHTMLConValue, containerDiv } from "../../main/main.js";
import { tokenNotValidLoadLoginFrom, unLogin } from "../auth/login/login.js";

let currentPage = 0; // Página inicial
let totalPages = 0;  // Se actualizará con la respuesta del backend

function barOptionsNoteData(){
    return {
        updateLi: document.getElementById('update'),
        changeLi: document.getElementById('change'),
        deleteLi: document.getElementById('delete')
    }
}

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

function handleClickUpdateNote(id, container, dataValueForm,URL_UPDATENOTE) {
  return () => LoadFormUpdateNote(id, container, dataValueForm, URL_UPDATENOTE);
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
    noteDiv.id = `${note.id}`; 

    // Asinga la clase "note"
    noteDiv.classList.add("note");
        
    // Creo el elemento h3
    const titleHThree = document.createElement('h3');
    titleHThree.id = 'title';        
    // Le asigno el titulo
    titleHThree.textContent = note.titulo;
   
    // Creo el elemento p
    const descriptionP = document.createElement('p');
    descriptionP.id = 'description'    
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

function changeStatus(data){
    // Creo un elmento div
    const infoDiv = document.createElement('div');
    // Asigna el id al div
    infoDiv.id = `noteStatus-${data.id}`;

    // Asinga la clase "note"
    infoDiv.classList.add("info");

    const titleResponseH = document.createElement('h3');
    titleResponseH.textContent =`Nota: ${data.title}`;
    titleResponseH.id = "title"
    const infoP = document.createElement('p');
    infoP.id = "status"
    infoP.textContent = data.status;
    infoDiv.appendChild(titleResponseH);
    infoDiv.appendChild(infoP);
    return infoDiv;
}

// Obtener y remover la barra de opciones
function cleanBarOptions(){
    const barOptions = document.getElementById('barOption');
    barOptions.remove();
}

// Funcion que captura los datos del Formulario de actualizar nota y los convierte en un objeto
function getUpdateNote(id){

    // Capture los valores ingresado por el usuario
    const title = document.getElementById('title').value;
    const descripcion = document.getElementById('description').value;

    const note = {
        id: id,
        titulo: title,
        descripcion: ""
    }
    // Valida si ingresaron descripcion
    if(descripcion.trim() !== ""){
        note.descripcion = descripcion;
    }
    return note;
}

async function sendFormUpdateNote(event, id){
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
        const note = getUpdateNote(id);

        const token = getToken();
        // Funcion para enviar datos al backend
        const data = await sendNote(token, note, `${URL_NOTES}/update`);
        if( data === 401){
            // Si el token no es valido ingresa aqui
            try{
                // cargar el menu de inicio y el formulario y su respectivo evento de envio
                await tokenNotValidLoadLoginFrom();

            }catch(error){
           
            }
        }else if(data === 400){
            // Limpiar el contenedor antes de agregar nuevas notas
            container.innerHTML = "";
            // recive la nota creada
            const infoDiv = errorNoteIsExist(note, "Ya Existe Nota Con Ese Titulo, No Se puede Actualizar.");
            // La agrega al contenedor
            container.appendChild(infoDiv);
        }else{
            try{
                const { container } = containerDiv();
                // Limpiar el contenedor antes de agregar nuevas notas
                container.innerHTML = "";
                // recive la nota creada
                const infoNote = converNote(data);
                // la convierte a div
                
                const noteDiv = createNoteDiv(infoNote);
                // La agrega al contenedor
                container.appendChild(noteDiv);

            }catch(error){
                console.log("Error al actualizar Nota:", error);
            }
        }
    }
}

// Funcion que cargar el formulario para actualizar una nota y crea un evento para enviar el formulario
async function LoadFormUpdateNote(id, container, dataValueForm, URL_UPDATENOTE){
    try{
        await cargarHTMLConValue(container, 'updateNote', URL_UPDATENOTE, dataValueForm);

        // Esperar a que el formulariuo se inyecte para añadir el evento
        const formUpdateNote = document.getElementById('updateNote');
                    
        if(formUpdateNote){
            // Evento para enviar formulario para crear una nueva nota
            formUpdateNote.addEventListener('submit' , async (event) => sendFormUpdateNote(event,id));
        }else{
            console.error("El formulario no se encotrno despues de cargar");
        }
    }catch(error){
        console.error("Error al cargar el formulario:", error);
    }
}

async function actualizarEstadoNota(note){
    const token = getToken();
    // Elimina el menu
    cleanBarOptions();
    // Envia la solicitud al enpoint y obtiene el retorno
    const data = await changeStatusNote(token,`${URL_NOTES}/change-status/${note.id}`);
                            
    if(data === false){
        // Si el token no es valido ingresa aqui
        try{
            // cargar el menu de inicio y el formulario y su respectivo evento de envio
            await tokenNotValidLoadLoginFrom();

        }catch(error){
            console.error("Error al cargar el formulario de Login:", error);
        }
    }else{
        const { container } = containerDiv();
        container.innerHTML = "";
        const infoDiv = changeStatus(data);
        container.appendChild(infoDiv);                            
    }
}

function createInfoDeleteDiv(data){
        const infoDiv = document.createElement('div');
        infoDiv.id = "NotaDeleteDiv";
        // Asinga la clase "note"
        infoDiv.classList.add("info");
        const titleResponseH = document.createElement('h3');
        titleResponseH.id = "NotaEliminada";
        titleResponseH.textContent = `Nota: ${data.title}`;
        const infoP = document.createElement('p');
        infoP.id = "request" ;
        infoP.textContent = "Eliminada Exitosamente";
        
        infoDiv.appendChild(titleResponseH); 
        infoDiv.appendChild(infoP);
        return infoDiv;
}

function errorNoteIsExist(note,message){
        const infoDiv = document.createElement('div');
        infoDiv.id = "NotaIsExistDiv";
        // Asinga la clase "note"
        infoDiv.classList.add("info");
        const titleResponseH = document.createElement('h3');
        titleResponseH.id = "notaExistente";
        titleResponseH.textContent = `Nota: ${note.titulo}`;
        const infoP = document.createElement('p');
        infoP.id = "request" ;
        infoP.textContent = message;
        
        infoDiv.appendChild(titleResponseH); 
        infoDiv.appendChild(infoP);
        return infoDiv;
}

async function eliminarNota(note){
    const token = getToken();
    // Elimina el menu
    cleanBarOptions();
    // Envia la solicitud al enpoint y obtiene el retorno
    const data = await deleteNote(token,`${URL_NOTES}/delete/${note.id}`);
                            
    if(data === false){
        // Si el token no es valido ingresa aqui
        try{
            // cargar el menu de inicio y el formulario y su respectivo evento de envio
            await tokenNotValidLoadLoginFrom();

        }catch(error){
            console.error("Error al cargar el formulario de Login:", error);
        }
    }else{
        const { container } = containerDiv();
        container.innerHTML = "";
        
        const infoDiv = createInfoDeleteDiv(data);
        container.appendChild(infoDiv);                            
    }
}

// Funcion que obtiene la lista de notas y las convierte en objetos DOM
async function getNotes(container,routeBase, page = 0) {
    const { crud } = containerDiv();
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

                // Limpiar crud options si se da click
                crud.innerHTML = "";
                // Limpiar el contenedor antes de agregar nuevas notas
                container.innerHTML = "";
                
                // Convertir cada nota del backend a un objeto NoteDTO
                const noteList = convertNotes(data);

                // traigo el frag 
                const frag = fragmentNotes(noteList);
                container.appendChild(frag);
                // Carga los botones de paginado
                await cargarBotonesPrevAndNext(routeBase);
                const notes = document.querySelectorAll('.note');
                for ( const note of notes){
                    note.addEventListener( 'click', async () => {
                        // Limpia el contenedor 
                        container.innerHTML="";
                        // Carga la nota selecionada
                        container.appendChild(note);
                        // Carga el menu de opciones (update,change,delete)
                        await cargarHTML(crud, 'barOption', URL_BAROPTIONS);
                        const { updateLi, changeLi, deleteLi } = barOptionsNoteData();
                        
                        const titleElement = document.getElementById('title');
                        const descriptionElement = document.getElementById('description');
                        
                        if(updateLi){
                            const dataValueForm = {
                            title: ['title', titleElement ? titleElement.textContent.trim() : ''],
                            descripcion: ['description', descriptionElement ? descriptionElement.textContent.trim() : '']
                            };
                            updateLi.addEventListener('click', handleClickUpdateNote(note.id, container, dataValueForm, URL_UPDATENOTE));
                        }
                        if(changeLi){
                            changeLi.addEventListener('click', async () => actualizarEstadoNota(note));
                        }
                        if(deleteLi){
                            deleteLi.addEventListener('click', async () => eliminarNota(note));
                        }else{
                            console.error('Error al cargar el menu de opciones de nota');
                        }

                    })
                } 
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
async function sendFormNote(event) {
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
        if( data === 401){
            // Si el token no es valido ingresa aqui
            try{
                // cargar el menu de inicio y el formulario y su respectivo evento de envio
                await tokenNotValidLoadLoginFrom();

            }catch(error){
                
            }
        }else if (data === 400){
            // Limpiar el contenedor antes de agregar nuevas notas
            container.innerHTML = "";
            // recive la nota creada
            const infoDiv = errorNoteIsExist(note, "Nota A Crear Ya Existe");
            // La agrega al contenedor
            container.appendChild(infoDiv);
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
    try{
        const { crud } = containerDiv()
        crud.innerHTML = "";
        await cargarHTML(container, createNote, URL_CREATENOTE);
                    
        // Esperar a que el formulariuo se inyecte para añadir el evento
        const fromNote = document.getElementById('createNote');
                    
        if(fromNote){
            // Evento para enviar formulario para crear una nueva nota
            fromNote.addEventListener('submit' , sendFormNote);
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
    const { options } = containerDiv();

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