package com.example.proyectofinal.Entities;

import java.io.Serializable;
import java.util.ArrayList;

public class Usuario implements Serializable {
    Integer id;
    String nombre;
    String nombre_usuario;
    String correo;
    String clave;
    String amistades;
    String resultados;

    public Usuario(Integer id, String nombre, String nombre_usuario, String correo, String clave) {
        this.id = id;
        this.nombre = nombre;
        this.nombre_usuario = nombre_usuario;
        this.correo = correo;
        this.clave = clave;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre_usuario() {
        return nombre_usuario;
    }

    public void setNombre_usuario(String nombre_usuario) {
        this.nombre_usuario = nombre_usuario;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getAmistades() {
        return amistades;
    }

    public void setAmistades(String amistades) {
        this.amistades = amistades;
    }

    public String getResultados() {
        return resultados;
    }

    public void setResultados(String resultados) {
        this.resultados = resultados;
    }
}
