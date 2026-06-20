// Delantero.java
package com.champs2026.logica;

public class Delantero extends Deportista {
    public Delantero(String nombre, String pais, int velocidad, int disparo, int pase, int defensa, double precio) {
        super(nombre, pais, velocidad, disparo, pase, defensa, precio);
    }

    @Override
    public void mostrarDetalles() {
        System.out.println("[DELANTERO] " + getNombre() + " | VEL: " + getVelocidad() + " | SHO: " + getDisparo() + " | PRECIO: " + getPrecio());
    }
}