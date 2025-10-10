export class RegisterDTO {
    constructor(nombre, correoElectronico, fechaNacimiento, nickname, password){
        this.nombre = nombre;
        this.correoElectronico = correoElectronico;
        this.fechaNacimiento = fechaNacimiento;
        this.nickname = nickname;
        this.password = password;
    }
    
    getNombre(){
        return this.nombre;
    }

    getApellido(){
        return this.apellido;
    }

    getCorreoElectronico(){
        return this.correoElectronico;
    }

    getFechaNacimiento(){
        return this.fechaNacimiento;
    }

    setNombre(nombre){
        this.nombre = nombre;
    }

    setApellido(apellido){
        this.apellido = apellido;
    }

    setCorreoElectronico(email){
        this.correoElectronico = email;
    }

    setFechaNacimiento(date){
        this.fechaNacimiento = date;
    }


    getNickname(){
        return this.nickname;
    }

    setNickname(nickname){
        this.nickname = nickname;
    }

    setPassword(password){
        this.password = password;
    }
}