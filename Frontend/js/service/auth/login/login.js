import { UserDTO } from "../../../dto/userDTO.js";
import { sendAuth } from "../../../controller/querys.js"; 
import { URL_AUTH , URL_LOGIN, URL_BAR} from "../../../data/const.js";
import { saveToken , isTokenValid} from "../../../valid/token/token.js";
import { cargarInterfazInterna } from "../../note/note.js";
import { cargarHTML,containerDiv, barMenus, startPage } from "../../../main/main.js";


// Funcion que me envia los datos del Login From
async function userLogin(event){

    const { header, container } = containerDiv();
    
    // Previene que el formulario se envie de forma tradicional
    event.preventDefault(); 
    
    // Capturando los valores ingresado por el usuario
    const nickname = document.getElementById('nickname').value;
    const password = document.getElementById('password').value;

    // Crea el DTO
    const user = new UserDTO(nickname,password);

    try{
        // Funcion para enviar datos al backend
        const data = await sendAuth(user, `${URL_AUTH}/login`);

        // Guarda el token
        saveToken(data.token);
        console.log(data);
        // Espera a que el token esté guardado y verifica validez
        if (isTokenValid()) {
            
            container.innerHTML="";
            await cargarInterfazInterna(header,container);
        
        } else {
            console.error("El token recibido no es válido o está expirado");
        }
    }catch(error){
        console.error("Error al logearse", error);
    }
}

// Funcion que carga el Formulario de Login y su evento de envio
export async function cargarLoginAndEventSend(){

    const { container } = containerDiv();

    await cargarHTML(container,`loginFrom`, URL_LOGIN);
    // Espera a que el formulario se inyecte para añadir el envento
            
    // Manejador del login
    const fromLogin =  document.getElementById('loginFrom');
            
    if(fromLogin){            
        // Evento para enviar formulario para logearse
        fromLogin.addEventListener('submit', userLogin);  
    }else{
        console.error("El formulario no se encontro despues de cargar");
    }
}

// Funcion que limpia los contenedores y carga el Formulario de Login y Menu Inicial
export async function tokenNotValidLoadLoginFrom(){
    // Carga los div que se van a modificar 
    const { options , header } = containerDiv();
    // borra la lista de opciones de notas
    options.innerHTML = "";
    // contiene el id de la barra de menu de inicio
    const { bar } = barMenus();
    // carga la barra de menu de inicio            
    await cargarHTML(header,bar, URL_BAR);
                
    // Carga el Login y su envento de envio
    await cargarLoginAndEventSend();     
}

export async function unLogin() {
    try{
        // Eliminar token
        localStorage.removeItem('authToken');
        // Limpie el contenedor de Options y Cambie el Header y Container
        const {options} = containerDiv();
        // Limpiamos las opciones
        options.innerHTML = "";

        await startPage();

    }catch(error){
        console.error("Error al cargar la pagina de inicio", error);
    }
}