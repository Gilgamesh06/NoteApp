export function saveToken(token) {
    // Guarda el token de autenticacion
    localStorage.setItem('authToken', token);
}

export function getToken() {
    // retorna el token de autenticacion
    return localStorage.getItem("authToken");
}