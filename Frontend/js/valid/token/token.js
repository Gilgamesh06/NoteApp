export function getToken() {
    // retorna el token de autenticacion
    return localStorage.getItem("authToken");
}

export function saveToken(token) {
    // Guarda el token de autenticacion
    localStorage.setItem('authToken', token);
}

export function isTokenValid() {
    const token = getToken();
    if (!token) return false;

    try {
        const payload = JSON.parse(atob(token.split('.')[1])); // decodifica el payload
        const currentTime = Date.now() / 1000; // tiempo actual en segundos
        return payload.exp > currentTime; // true si no ha expirado
    } catch (error) {
        localStorage.removeItem('authToken');
        console.error("Token inválido:", error);
        return false;
    }
}
