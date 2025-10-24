export class CreateNoteDTO {
    constructor(titulo, estado = true){
        this.titulo = titulo;
        this.estado = estado;
    }
    setTitulo(titulo){
        this.titulo = titulo;
    }

    setDescripcion(descripcion){
        this.descripcion = descripcion;
    }


    setId(estado){
        this.estado = estado;
    }

    getTitulo(){
        return this.titulo;
    }

    getDescripcion(){
        return this.descripcion;
    }

    getEstado(){
        return this.estado;
    }
}