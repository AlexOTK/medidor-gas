package com.example.gasmonitor;

public class Datos {
    private String mensaje;

    public Datos() {
        // Constructor vacío requerido por Firebase
    }

    public Datos(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}

