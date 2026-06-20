package com.champs2026.logica;

import java.util.ArrayList;

/**
 * Clase que gestiona el Mercado de Fichajes global del videojuego.
 * Actúa como un banco común de futbolistas disponibles de donde ambos
 * Directores Técnicos (Jugadores) adquirirán sus atletas utilizando su presupuesto.
 * * @author TuNombre / Integrante 3
 * @version 1.0
 */
public class MercadoFichajes {

    /** Lista global de futbolistas que aún no han sido comprados por ningún jugador. */
    private ArrayList<com.champs2026.logica.Deportista> bancoComun;

    /**
     * Constructor que inicializa el mercado de fichajes con una lista previa de atletas.
     * * @param bancoComun El ArrayList inicial de deportistas cargados desde el archivo de datos.
     */
    public MercadoFichajes(ArrayList<com.champs2026.logica.Deportista> bancoComun) {
        this.bancoComun = bancoComun;
    }

    /**
     * Procesa la compra de un futbolista. Verifica la existencia del atleta,
     * valida el presupuesto del comprador, descuenta las monedas, añade el futbolista
     * a la selección del jugador y lo remueve del mercado común.
     * * @param comprador El objeto Jugador (DT) que intenta realizar la compra.
     * @param nombreFutbolista El nombre del futbolista que se desea adquirir.
     * @return true si la transacción fue exitosa; false en caso de error o fondos insuficientes.
     */
    public boolean comprarFutbolista(com.champs2026.logica.Jugador comprador, String nombreFutbolista) {
        com.champs2026.logica.Deportista futbolistaEncontrado = null;

        // 1. Buscar al futbolista por su nombre en el bancoComun (ignorando mayúsculas/minúsculas)
        for (com.champs2026.logica.Deportista d : this.bancoComun) {
            if (d.getNombre().equalsIgnoreCase(nombreFutbolista)) {
                futbolistaEncontrado = d;
                break; // Se encontró el futbolista, salimos del bucle
            }
        }

        // Validación: Si el futbolista no existe en el mercado
        if (futbolistaEncontrado == null) {
            System.out.println("[ERROR MERCADO]: El futbolista '" + nombreFutbolista + "' no está disponible o ya fue comprado.");
            return false;
        }

        // 2. Verificar si el Jugador comprador tiene suficiente presupuesto
        if (comprador.getPresupuesto() < futbolistaEncontrado.getPrecio()) {
            System.out.println("[ERROR MERCADO]: " + comprador.getNombre() + " no tiene suficientes monedas virtuales. "
                    + "Costo: " + futbolistaEncontrado.getPrecio() + " | Saldo: " + comprador.getPresupuesto());
            return false;
        }

        // 3. Si pasa las validaciones, se ejecuta la transacción económica
        double nuevoPresupuesto = comprador.getPresupuesto() - futbolistaEncontrado.getPrecio();
        comprador.setPresupuesto(nuevoPresupuesto);

        // 4. Agregar el futbolista a la plantilla de la SeleccionNacional de ese jugador
        comprador.getSeleccionNacional().agregarFutbolista(futbolistaEncontrado);

        // 5. Eliminar al futbolista del bancoComun (garantiza el principio de mercado exclusivo)
        this.bancoComun.remove(futbolistaEncontrado);

        System.out.println("[ÉXITO]: " + comprador.getNombre() + " ha fichado a " + futbolistaEncontrado.getNombre()
                + " por " + futbolistaEncontrado.getPrecio() + " monedas.");
        return true;
    }

    /**
     * Método utilitario para listar en consola los jugadores disponibles en el mercado.
     */
    public void mostrarMercadoDisponible() {
        System.out.println("\n--- JUGADORES DISPONIBLES EN EL MERCADO ---");
        if (bancoComun.isEmpty()) {
            System.out.println("El mercado está vacío. Todos los jugadores han sido fichados.");
        } else {
            for (com.champs2026.logica.Deportista d : bancoComun) {
                // Llama al método abstracto implementado en las subclases
                d.mostrarDetalles();
            }
        }
        System.out.println("-------------------------------------------\n");
    }

    // Getters y Setters
    public ArrayList<com.champs2026.logica.Deportista> getBancoComun() {
        return bancoComun;
    }

    public void setBancoComun(ArrayList<com.champs2026.logica.Deportista> bancoComun) {
        this.bancoComun = bancoComun;
    }
}