import User from "../js/model/user";

const URL = "backend/auth"

const user = document.getElementById('login').addEventListener('submit', function (event){
    event.preventDefault(); // Previene que el formulario se envie de forma tradicional
    // Capturando los valores ingresado por el usuario
    const nickname = document.getElementById('nickname').value;
    const password = document.getElementById('password').value;
    return new User(nickname,password)
});

async function login(user, URL){
    const rest = fetch((URL + '/login/'), {
        method: 'POST',
        headers: {'Content-Type': 'application/json', 'Accept':'applicacition/json'},
        body: JSON.stringify(user)
    });
    if (!res.ok) throw new Error(res.status);
    const result = await res.json();
    console.log(result);
}