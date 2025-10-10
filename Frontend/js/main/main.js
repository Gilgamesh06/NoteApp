
function cargarHeader(header) {
    const frag = document.createDocumentFragment()
    fetch('/html/header.html').then(res => {
        // Verifica la respuesta de la query
        if (!res.ok) throw new Error('Error al cargar el arhivo: '+ res.statusText );
        return res.text(); // Convierte la respuesta en texto
    })
    .then(html => {
        const parser = new DOMParser(); // Crea un parser de DOM
        const doc = parser.parseFromString(html, 'text/html'); // Convierte el texto HTML en un documento
        const bar = doc.getElementById('bar'); // Obtienen el elemtno con id 'bar'
        if(bar){
            header.appendChild(bar); // Agrega el elemento encontrado al contendor
        }else{
            console.error('Elemento con id "bar" no encontrado en header.html');
        }
    })
    .catch(error => {
        console.log('Hubo un problema con la peticion Fetch:', error);
    });
}

const header = document.getElementById('header');

cargarHeader(header);