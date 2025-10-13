import { URL_NOTES } from "../../data/const.js";
import { handleClickCreateNote, handleClickNoteList, sendFromNote } from "./methodNote.js";

// Elmentos del DOM

// Listas para listar notas
const activeLi = document.getElementById("active");
const archiveLi = document.getElementById("archive");

// contenedor de nota
const container = document.getElementById("container-note");

const noteRoutes = {
  active: `${URL_NOTES}/all/active?page=0&size=4&orderBy=true`,
  archive: `${URL_NOTES}/all/archive?page=0&size=4&orderBy=true`,
};

// Eventos para listar notas [active, archive]
activeLi.addEventListener('click', handleClickNoteList(container, noteRoutes.active));
archiveLi.addEventListener('click', handleClickNoteList(container, noteRoutes.archive));

//  Lista para crear nota
const newNoteLi = document.getElementById('create-note');
// id del elemento a traer
const elementGet = 'new-note';
// direccion del archivo
const URL = '../../../html/componets/create-note.html';

// Evento que carga el fromulario para crear nota cuando dan clic en: Crear Nota
newNoteLi.addEventListener('click', async () => {
  
  try{
    
    await handleClickCreateNote(container, elementGet, URL);
    
    // Esperar a que el formulariuo se inyecte para añadir el evento
    const fromNote = document.getElementById('new-note');
    
    if(fromNote){
      // Evento para enviar formulario para crear una nueva nota
      fromNote.addEventListener('submit' , sendFromNote);
    }
    else{
      console.error("El formulario no se encotrno despues de cargar");
    }
  }catch(error){
    console.error("Error al cargar el formulario:", error);
  }
});



