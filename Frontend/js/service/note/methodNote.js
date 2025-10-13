import { InfoNoteDTO } from "../../dto/infoNoteDTO.js";
import { CreateNoteDTO } from "../../dto/createNoteDTO.js";
import { sendNote } from "../../controller/querys.js";
import { getElements } from "../../controller/querys.js";
import { getToken } from "../../data/saveToken.js";
import { URL_NOTES } from "../../data/const.js";
import { cargarHTML } from "../../main/methodMain.js";


// funcion que convierte la data en InfoNoteDTO
function convertNotes(data){
    return data.content.map( note => new InfoNoteDTO(
        note.id,
        note.titulo,
        note.descripcion
    ));
}

// funcion que almacena los div (notas) en un fragment
function fragmentNotes(noteList){
    // Creo un fragmento para no usar innerHTML
    const frag = document.createDocumentFragment();
    // recorro la lista de NoteDTO
    for (const note of noteList){

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

        // Agrego el div al frag
        frag.appendChild(noteDiv);

    }
    return frag;
}

// Funcion que obtiene la lista de notas y las convierte en objetos DOM
async function getNotes(container, URL) {
    try{
        // Obtener token desde localStorage
        const token = getToken();
        if(!token){
            alert("No estas autenticado, Por favor inicia sesion");
            return;
        }

        // Traer las notas desde el backend

        const data = await getElements(token, URL);

        // Limpiar el contenedor antes de agregar nuevas notas
        container.innerHTML = "";

        // Convertir cada nota del backend a un objeto NoteDTO
        const noteList = convertNotes(data);

        // traigo el frag 
        const frag = fragmentNotes(noteList);
        container.appendChild(frag);
        
    }catch(error){
        console.log("Error al otener notes: ", error);
    }
}

/**
 * Metodo que obtiene la lista de Notas
 * @param {*} type 
 * @returns 
 */
export function handleClickNoteList(container, type) {
  return () => getNotes(container,type);
}



/**
 * Metodo para cargar El formulario para crear una Nota
 * @param {*} container 
 * @param {*} elementGet 
 * @param {*} URL 
 * @returns 
 */
export function handleClickCreateNote(container, elementGet, URL) {
  return cargarHTML(container, elementGet, URL);
}



/**
 * Metodo para enviar el formulario que crea una Nota
 * @param {*} event 
 */
export async function sendFromNote(event) {
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
        console.log("Se creo la nota:", data);

    }catch(error){
        console.log("Error al crear Nota:", error);
    }
}
