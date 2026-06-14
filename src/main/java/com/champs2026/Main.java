package com.champs2026;

import com.champs2026.gui.VistaPrincipal;
import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        // Iniciar la interfaz visual (Frontend) en el hilo de despacho de eventos de Swing
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                VistaPrincipal vista = new VistaPrincipal();
                vista.setVisible(true);
            }
        });
        
        /* 
        ========================================================================
        CÓDIGO DE CONSOLA ORIGINAL (DESACTIVADO PARA MODO GRÁFICO)
        ========================================================================
        
        // PASO 1: Carga dinámica desde el archivo .txt
        java.util.ArrayList<com.champs2026.logica.Deportista> bancoComun = 
            com.champs2026.logica.LectorDatos.cargarMercadoDesdeArchivo("/home/daniel/NetBeansProjects/Champs2026/futbolistas.txt");
        com.champs2026.logica.MercadoFichajes mercado = new com.champs2026.logica.MercadoFichajes(bancoComun);

        // PASO 2: Registro interactivo de Directores Técnicos
        com.champs2026.logica.Jugador[] entrenadores = com.champs2026.logica.ConfiguracionInicial.registrarJugadores();
        com.champs2026.logica.Jugador j1 = entrenadores[0];
        com.champs2026.logica.Jugador j2 = entrenadores[1];

        // FASE DE PRUEBA DE COMPRAS EN CONSOLA
        java.util.Scanner scanner = new java.util.Scanner(System.in);
        
        // Muestra los jugadores disponibles
        mercado.mostrarMercadoDisponible();

        // Simulación interactiva de una ronda de fichajes rápida
        System.out.println("--- TURNO DE FICHAJE DE " + j1.getNombre().toUpperCase() + " ---");
        System.out.print("Escriba el nombre del futbolista que desea fichar para " + j1.getSeleccionElegida().getNombrePais() + ": ");
        String fichaje1 = scanner.nextLine();
        mercado.comprarFutbolista(j1, fichaje1);

        System.out.println("\n--- TURNO DE FICHAJE DE " + j2.getNombre().toUpperCase() + " ---");
        System.out.print("Escriba el nombre del futbolista que desea fichar para " + j2.getSeleccionElegida().getNombrePais() + ": ");
        String fichaje2 = scanner.nextLine();
        mercado.comprarFutbolista(j2, fichaje2);

        // Resumen final de entrega del Sprint 1
        System.out.println("\n=========================================");
        System.out.println("       ESTADO FINAL DE LOS EQUIPOS       ");
        System.out.println("=========================================");
        j1.getSeleccionElegida().mostrarPlantilla();
        System.out.println("Presupuesto de " + j1.getNombre() + ": " + j1.getPresupuesto() + " monedas.\n");
        
        j2.getSeleccionElegida().mostrarPlantilla();
        System.out.println("Presupuesto de " + j2.getNombre() + ": " + j2.getPresupuesto() + " monedas.");
        System.out.println("=========================================");
        */
    }
}