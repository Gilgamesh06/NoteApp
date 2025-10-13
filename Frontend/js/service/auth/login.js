import { UserDTO } from "../../dto/userDTO.js";
import { sendAuth } from "../../controller/querys.js"; 
import { URL_AUTH } from "../../data/const.js";
import { saveToken } from "../../data/saveToken.js";
import { validType , validPassword } from "../../valid/validRegister.js";

// Manejador del login
const fromLogin =  document.getElementById('login');

fromLogin.addEventListener('submit', async function (event){
    // Previene que el formulario se envie de forma tradicional
    event.preventDefault(); 
    
    // Capturando los valores ingresado por el usuario
    const nickname = document.getElementById('nickname').value;
    const password = document.getElementById('password').value;

    // Validation
    try {
        validType(nickname);
        validPassword(password);
        
    }catch(error){
        console.log(error);
    }
    
    // Crea el DTO
    const user = new UserDTO(nickname,password);

    try{
        // Funcion para enviar datos al backend
        const data = await sendAuth(user, `${URL_AUTH}/login`);
        
        // Guarda el token
        saveToken(data.token);

        console.log(data);
    }catch(error){
        console.log("Error al logearse: ", error);
    }
});

