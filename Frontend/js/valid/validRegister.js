
export function validType(text) {
    if(typeof(text) === "string"){
        if(text.trim() !== ""){
            return true;
        }
        else{
            throw new Error("No puede ser vacio.");
        }
    }
    else{
        throw new Error(`Type isn't correct: ${typeof(text)}`);
    }
}

export function validPassword(text){
    if(validType(text)){
        if(text.length >= 8 ){
            return true;
        }
        else{
            throw new Error(`La longitud debe ser igual o mayor a 8: ${text.length}`);
        }
    }
}

export function validEmail(text){
   if(validType(text)){
        if(text.includes('@')){
            if(text.endsWith('.com')){
                return true;
            }
            else{
                throw new Error("El email debe terminar en .com");
            }
        }
        else{
            throw new Error("El email debe incluir @");
        }
   } 
}