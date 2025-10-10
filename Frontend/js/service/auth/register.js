import { RegisterDTO } from "../../dto/registerDTO.js";
import { sendAuth } from "../../controller/authController.js"; 
import { URL } from "../../const/const.js";
import { validType , validPassword, validEmail } from "../../valid/validRegister.js";

// Manejador del formulario
const fromResgister = document.getElementById('register');

fromResgister.addEventListener('submit', async function (event){
    // Previene que el formulario se envie de forma tradicional
    event.preventDefault();

    // Capturando los valores ingresado por el usuario
    const nombre = document.getElementById('nombre').value;
    const apellido = document.getElementById('apellido').value;
    const email = document.getElementById('email').value;
    const date = document.getElementById('date').value;
    const nickname = document.getElementById('nickname').value;
    const password = document.getElementById('password').value;
    
    // Validation
    try {
        validType(nombre);
        validEmail(email);
        validType(nickname);
        validPassword(password);
        
    }catch(error){
        console.log(error);
    }

    // Crear DTO
    const user = new RegisterDTO(nombre, email, date, nickname, password);

    // Agregar el apellido
    if( apellido.trim() !== ""){
        user.getApellido(apellido);
    }

    try {
        // Funcion para enviar datos al backend
        const result = await sendAuth(user, `${URL}/register`);
        console.log("Usuario registrado: ",result);
    }catch(error){
        console.log("Error al registrar: ", error);
    }
});


