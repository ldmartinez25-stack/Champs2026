/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.champs2026.logica;

/**
 *
 * @author danielmartinez
 */

/**
 * Representa a un jugador con rol defensivo.
 */
public class Defensa extends Deportista {

    public Defensa(String nombre, String pais, int velocidad, int disparo, int pase, int defensa, double precio) {
        super(nombre, pais, velocidad, disparo, pase, defensa, precio);
    }

    @Override
    public void mostrarDetalles() {
        System.out.println("[Defensa] " + getNombre() + " (" + getPais() + ") | DEF: " + getDefensa() + " | VEL: " + getVelocidad());
    }
}