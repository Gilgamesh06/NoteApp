export class PersonDTO {
    constructor(nombre, correoElectronico, fechaNacimiento){
        this.nombre = nombre;
        this.correoElectronico = correoElectronico;
        this.fechaNacimiento = fechaNacimiento;
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

    setApellido(appellido){
        this.apellido = appellido;
    }

    setCorreoElectronico(email){
        this.correoElectronico = email;
    }

    setFechaNacimiento(date){
        this.fechaNacimiento = date;
    }
}