import { RegisterDTO } from "../../../dto/registerDTO.js";
import { sendAuth } from "../../../controller/querys.js"; 
import { URL_AUTH } from "../../../data/const.js";
import { cargarLoginAndEventSend } from "../login/login.js";


export async function userRegister(event){
    // Previene que el formulario se envie de forma tradicional
    event.preventDefault();

    // Capturando los valores ingresado por el usuario
    const nombre = document.getElementById('nombre').value;
    const apellido = document.getElementById('apellido').value;
    const email = document.getElementById('email').value;
    const date = document.getElementById('date').value;
    const nickname = document.getElementById('nickname').value;
    const password = document.getElementById('password').value;
    

    // Crear DTO
    const user = new RegisterDTO(nombre, email, date, nickname, password);

    // Agregar el apellido
    if( apellido.trim() !== ""){
        user.setApellido(apellido);
    }

    try {
        // Funcion para enviar datos al backend
        const data = await sendAuth(user, `${URL_AUTH}/register`);

        console.log("Usuario registrado: ",data);
        
        try{

            await cargarLoginAndEventSend();     
                
        }catch(error){
            console.error("Error al cargar el formulario de Login:", error);
        }
    }catch(error){
        console.log("Error al registrar: ", error);
    }
}


