export class InfoNoteDTO {
    constructor(id, titulo, descripcion){
        this.id = id;
        this.titulo = titulo;
        this.descripcion = descripcion;
    }

    setId(id){
        this.id = id;
    }

    setTitulo(titulo){
        this.titulo = titulo;
    }

    setDescripcion(descripcion){
        this.descripcion = descripcion;
    }

    getId(){
        return this.id;
    }

    getTitulo(){
        return this.titulo;
    }

    getDescripcion(){
        return this.descripcion;
    }
}