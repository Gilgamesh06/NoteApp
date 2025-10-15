import { UserDTO } from "../../../dto/userDTO.js";
import { sendAuth } from "../../../controller/querys.js"; 
import { URL_AUTH , URL_LOGIN} from "../../../data/const.js";
import { saveToken , isTokenValid} from "../../../data/saveToken.js";
import { handleClickCargarInterfazInterna } from "../../note/note.js";
import { handleClickCargarHTML } from "../../../main/methodMain.js";

function containerForData(){
    return {
        header: document.getElementById('header'),
        container: document.getElementById('container')
    }
}

async function userLogin(event){

    const { header, container } = containerForData();
    
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
            await handleClickCargarInterfazInterna(header,container);
        
        } else {
            console.error("El token recibido no es válido o está expirado");
        }
    }catch(error){
        console.error("Error al logearse", error);
    }
}

export async function cargarLoginAndEventSend(){

    const { container } = containerForData();

    await handleClickCargarHTML(container,`loginFrom`, URL_LOGIN);
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