import { getHTML } from "../controller/querys.js";

export async function cargarHTML(elementSet, elementGet, URL) {
    
    const parser = new DOMParser(); // Crea un parse de DOM
    try{
        const html = await getHTML(URL);
        const doc = parser.parseFromString(html, 'text/html'); // Convierte el texto HTML en un documento
        const insertElement = doc.getElementById(elementGet); // Obtiene el elemento a insertar
        if (insertElement){
            // Limpiar el contenedor antes de agregar nuevas notas
            elementSet.innerHTML = "";
            elementSet.appendChild(insertElement);
        }
        else{
            console.error(`Elemento con id: ${elementGet}, no encontrado en: ${URL} `);
        }
    }catch(err){
        console.log('Hubo un problema con la peticion Fetch:', err);
    }
}
