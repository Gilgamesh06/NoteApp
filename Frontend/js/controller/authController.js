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