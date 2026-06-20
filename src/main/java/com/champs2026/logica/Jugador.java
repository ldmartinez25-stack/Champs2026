package com.champs2026.logica;

/**
 * Entidad que modela al Director Técnico humano temporal en la partida.
 */
public class Jugador {
    private String nombre;
    private double presupuesto;
    private SeleccionNacional seleccion;

    public Jugador(String nombre, com.champs2026.logica.SeleccionNacional seleccionElegida) {
        this.nombre = nombre;
        this.seleccion = seleccionElegida;
        this.presupuesto = 1000.0; // Presupuesto inicial idéntico fijado por requerimiento
    }

    // Getters y Setters indispensables para la transacción del mercado
    public String getNombre() { return nombre; }
    public double getPresupuesto() { return presupuesto; }
    public void setPresupuesto(double presupuesto) { this.presupuesto = presupuesto; }
    public SeleccionNacional getSeleccionNacional() {
    return this.seleccion; // O como hayas nombrado tu atributo de selección
}
}
