package com.champs2026;

import com.champs2026.gui.VistaPrincipal;
import com.champs2026.motor.Partido; // Importamos el motor del partido
import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        // NOTA: Dejamos la GUI comentada temporalmente para que no se abra la ventana 
        // y puedas interactuar directamente con el partido en la consola.
        /*
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                VistaPrincipal vista = new VistaPrincipal();
                vista.setVisible(true);
            }
        });
        */
        
        // ========================================================================
        // CÓDIGO DE CONSOLA ACTIVADO Y CONFIGURADO PARA EL SPRINT 2
        // ========================================================================
        
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
        System.out.println("\n--- TURNO DE FICHAJE DE " + j1.getNombre().toUpperCase() + " ---");
        System.out.print("Escriba el nombre del futbolista que desea fichar para " + j1.getSeleccionNacional().getNombrePais() + ": ");
        String fichaje1 = scanner.nextLine();
        mercado.comprarFutbolista(j1, fichaje1);

        System.out.println("\n--- TURNO DE FICHAJE DE " + j2.getNombre().toUpperCase() + " ---");
        System.out.print("Escriba el nombre del futbolista que desea fichar para " + j2.getSeleccionNacional().getNombrePais() + ": ");
        String fichaje2 = scanner.nextLine();
        mercado.comprarFutbolista(j2, fichaje2);
        
        
        
        // ========================================================================
        // RONDA DE FICHAJES INTERACTIVA CON REINTENTOS DE SEGURIDAD
        // ========================================================================
         // ========================================================================
        // MERCADO DE FICHAJES ELITE (4 RONDAS INTERCALADAS CON SALVAVIDAS)
        // ========================================================================
        System.out.println("\n🔥 ¡INICIA EL MERCADO DE FICHAJES! 🔥");
        System.out.println("💡 (Si te quedas sin dinero o no hay jugadores que puedas pagar, escribe 'PASAR' para saltar tu turno)");
        
        for (int ronda = 1; ronda <= 4; ronda++) {
            System.out.println("\n=========================================");
            System.out.println("          🌟 RONDA DE FICHAJES " + ronda + " / 4 🌟");
            System.out.println("=========================================");

            // --- TURNO JUGADOR 1 ---
            boolean fichajeJ1 = false;
            while (!fichajeJ1) {
                System.out.println("\n--- TURNO DE " + j1.getNombre().toUpperCase() + " ---");
                System.out.println("💰 Saldo actual: " + j1.getPresupuesto() + " monedas.");
                System.out.print("Escriba el nombre del futbolista para " + j1.getSeleccionNacional().getNombrePais() + " (o 'PASAR'): ");
                String nombreFichaje = scanner.nextLine();
                
                // 🛑 SALVAVIDAS: Si escribe pasar, rompe el bucle de inmediato
                if (nombreFichaje.equalsIgnoreCase("pasar")) {
                    System.out.println("⏭️ " + j1.getNombre() + " ha decidido PASAR en esta ronda.");
                    fichajeJ1 = true; 
                } else {
                    fichajeJ1 = mercado.comprarFutbolista(j1, nombreFichaje);
                }
            }

            // --- TURNO JUGADOR 2 ---
            boolean fichajeJ2 = false;
            while (!fichajeJ2) {
                System.out.println("\n--- TURNO DE " + j2.getNombre().toUpperCase() + " ---");
                System.out.println("💰 Saldo actual: " + j2.getPresupuesto() + " monedas.");
                System.out.print("Escriba el nombre del futbolista para " + j2.getSeleccionNacional().getNombrePais() + " (o 'PASAR'): ");
                String nombreFichaje = scanner.nextLine();
                
                // 🛑 SALVAVIDAS: Si escribe pasar, rompe el bucle de inmediato
                if (nombreFichaje.equalsIgnoreCase("pasar")) {
                    System.out.println("⏭️ " + j2.getNombre() + " ha decidido PASAR en esta ronda.");
                    fichajeJ2 = true;
                } else {
                    fichajeJ2 = mercado.comprarFutbolista(j2, nombreFichaje);
                }
            }
        }
        
        
        
        
        
        
        
        
        
        

        // Resumen final de entrega del Sprint 1
        System.out.println("\n=========================================");
        System.out.println("       ESTADO FINAL DE LOS EQUIPOS       ");
        System.out.println("=========================================");
        j1.getSeleccionNacional().mostrarPlantilla();
        System.out.println("Presupuesto de " + j1.getNombre() + ": " + j1.getPresupuesto() + " monedas.\n");
        
        j2.getSeleccionNacional().mostrarPlantilla();
        System.out.println("Presupuesto de " + j2.getNombre() + ": " + j2.getPresupuesto() + " monedas.");
        System.out.println("=========================================\n");
        
        // ========================================================================
        // 🚀 INICIO DEL SPRINT 2: EL PARTIDO EN CONSOLA
        // ========================================================================
        System.out.println("⚽ Todo listo. ¡Los equipos saltan a la cancha! ⚽");
        
        // Creamos el partido pasando tus variables reales (j1 y j2)
        Partido partido = new Partido(j1, j2);
        
        // Arranca la simulación con el reloj y los momentos clave
        partido.iniciarSimulacion();
    }
}