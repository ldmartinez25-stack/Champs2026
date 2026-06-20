// Portero.java
package com.champs2026.logica;

public class Portero extends Deportista {
    private int atajada; // Atributo exclusivo

    public Portero(String nombre, String pais, int velocidad, int disparo, int pase, int defensa, double precio, int atajada) {
        super(nombre, pais, velocidad, disparo, pase, defensa, precio);
        this.atajada = atajada;
    }

    public int getAtajada() { return atajada; }

    @Override
    public void mostrarDetalles() {
        System.out.println("[PORTERO] " + getNombre() + " | ATK (Atajada): " + atajada + " | DEF: " + getDefensa() + " | PRECIO: " + getPrecio());
    }
}