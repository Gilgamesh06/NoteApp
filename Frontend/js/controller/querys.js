
export async function getElements(token, URL){
    const res = await fetch(`${URL}`, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${token}` 
        }
    });
    if (!res.ok){
        if (res.status === 401) {
        return false;
        }
     throw new Error(`HTTP: ${res.status}`);
    }

    return await res.json();
}

export async function sendNote(token ,body, URL){
    const res = await fetch( `${URL}`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${token}`, 
            'Accept': 'application/json'
        },
        body: JSON.stringify(body)
    });
    if (!res.ok){
        if (res.status === 401) {
            return res.status;
        }
        if(res.status === 400){
            return res.status
        }
     throw new Error(`HTTP: ${res.status}`);
    }

    return await res.json();
}


export async function deleteNote(token, URL){
    const res = await fetch(`${URL}`, {
        method: 'DELETE',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${token}` 
        }
    });
    if (!res.ok){
        if (res.status === 401) {
        return false;
        }
     throw new Error(`HTTP: ${res.status}`);
    }

    return await res.json();
}

export async function changeStatusNote(token, URL){
    const res = await fetch(`${URL}`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${token}` 
        }
    });
    if (!res.ok){
        if (res.status === 401) {
        return false;
        }
     throw new Error(`HTTP: ${res.status}`);
    }

    return await res.json();
}

export async function sendAuth(body, URL){
    const res = await fetch( `${URL}`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Accept': 'application/json'
        },
        body: JSON.stringify(body)
    });
    if (!res.ok) throw new Error(`HTTP: ${res.status}`);
    
    return await res.json();
}


export async function getHTML(URL){
    const res = await fetch(`${URL}`);
    
    if (!res.ok) throw new Error('Error al cargar el arhivo: '+ res.statusText );
    
    return res.text(); // Convierte la respuesta en texto

}

