import { URL_BAR, URL_REGISTER, URL_HOME } from "../data/const.js";
import { userRegister } from "../service/auth/register/register.js";
import { cargarHTML, handleClickCargarHTML } from "./methodMain.js";
import { cargarLoginAndEventSend } from "../service/auth/login/login.js";

const header = document.getElementById('header');
const bar = 'bar';

// Contenedor 
const container = document.getElementById('container');
const tituloHome = 'tituloHome';
const funcionalidades = 'funcionalidades';

// Carga el menu de opciones principal
await cargarHTML(header, bar, URL_BAR);

// Carga el menu de opciones principal
await cargarHTML(container, tituloHome, URL_HOME);

// Carga el menu de opciones principal
await cargarHTML(container, funcionalidades, URL_HOME, false);

// Elementos de la lista que son cargados en el header 
const loginLi = document.getElementById('login'); 
const registerLi = document.getElementById('register');
const homeLi = document.getElementById('home');

if(registerLi){
    registerLi.addEventListener('click', async () => {

        try{
            
            await handleClickCargarHTML(container,`registerFrom`, URL_REGISTER);
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
        
        // Carga el menu de opciones principal
        await cargarHTML(container, tituloHome, URL_HOME);

        // Carga el menu de opciones principal
        await cargarHTML(container, funcionalidades, URL_HOME, false);
    })
}