import { getHTML } from "../controller/querys.js";
import { URL_BAR, URL_REGISTER, URL_HOME } from "../data/const.js";
import { userRegister } from "../service/auth/register/register.js";
import { cargarLoginAndEventSend } from "../service/auth/login/login.js";

export function containderDiv(){
    return {
        header: document.getElementById('header'),
        container: document.getElementById('container'),
        options: document.getElementById('options')
    }
}

export function barMenus(){
    return {
        bar: 'bar',
        barNote: 'barNote',
        listNote: 'listNote'
    }
}

function barIncial(){
    return {
        loginLi: document.getElementById('login'),
        registerLi: document.getElementById('register'),
        homeLi: document.getElementById('home')
    }
}

function homeData(){
    return {
        tituloHome: 'tituloHome',
        funcionalidades: 'funcionalidades'
    }
}


export async function cargarHTML(container, id, URL, drop = true) {
    
    const parser = new DOMParser(); // Crea un parse de DOM
    try{
        const html = await getHTML(URL);
        const doc = parser.parseFromString(html, 'text/html'); // Convierte el texto HTML en un documento
        const insertElement = doc.getElementById(id); // Obtiene el elemento a insertar
        if (insertElement){
            if(drop){
                // Limpiar el contenedor antes de agregar nuevas notas
                container.innerHTML = "";
            }
            container.appendChild(insertElement);
        }
        else{
            console.error(`Elemento con id: ${id}, no encontrado en: ${URL} `);
        }
    }catch(err){
        console.log('Hubo un problema con la peticion Fetch:', err);
    }
}

async function load() {
    
    const { header, container} = containderDiv();
    const { bar } = barMenus();
    const { tituloHome, funcionalidades } = homeData();

    // Carga el menu de opciones principal
    await cargarHTML(header, bar, URL_BAR);
    
    // Carga el menu de opciones principal
    await cargarHTML(container, tituloHome, URL_HOME);
    
    // Carga el menu de opciones principal
    await cargarHTML(container, funcionalidades, URL_HOME, false);
}

export async function startPage() {
    
    await load();

    const { registerLi, loginLi, homeLi} = barIncial();

    if(registerLi){
        registerLi.addEventListener('click', async () => {
    
            try{
                
                await cargarHTML(container,`registerFrom`, URL_REGISTER);
                // Espera a que el formulario se inyecte para añadir el envento
                
                // Manejador del formulario
                const fromResgister = document.getElementById('registerFrom');
                
                if(fromResgister){
                    // Evento para enviar formulario para logearse
                    fromResgister.addEventListener('submit', userRegister);
                }else{
                    console.error("El formulario no se encontro despues de cargar");
                }  
    
            }catch(error){
                console.error("Error al cargar el formulario de Register:", error);
            }
        });
    }
    
    if(loginLi){
        // Evento que carga el formulario para logearse
        loginLi.addEventListener('click', async () => {
            try{
                await cargarLoginAndEventSend();     
            }catch(error){
                console.error("Error al cargar el formulario de Login:", error);
            }
        });
    }else{
        console.error("Error al cargar el menu principal")
    }
    
    if(homeLi){
        // Evento que carga el home 
        homeLi.addEventListener('click', async () => {
            
            try{
                const {tituloHome, funcionalidades} = homeData();
                
                // Carga el menu de opciones principal
                await cargarHTML(container, tituloHome, URL_HOME);
    
                // Carga el menu de opciones principal
                await cargarHTML(container, funcionalidades, URL_HOME, false);

            }catch(error){
                console.error("Error al cargar la pagina de inicio", error);
            }
        });
    }
}


try{
    await startPage();
}catch(error){
    console.error("Error al cargar la pagina principal", error);
}

