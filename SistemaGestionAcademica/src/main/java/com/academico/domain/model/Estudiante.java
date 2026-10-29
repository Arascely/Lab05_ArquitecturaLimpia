package com.academico.domain.model;

public class Estudiante {
    private int id;
    private String nombre;
    private String correo;

    public Estudiante() {

    }
    public Estudiante(int id, String nombre, String correo) {
        this.id = id;
        this.nombre = nombre;
        this.correo = correo;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getCorreo() {
        return correo;
    }
}
