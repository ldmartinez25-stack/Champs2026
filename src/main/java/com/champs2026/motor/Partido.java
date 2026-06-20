/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.champs2026.motor;

/**
 *
 * @author danielmartinez
 */


import com.champs2026.logica.Jugador;
import com.champs2026.logica.Deportista;
import java.util.Random;

public class Partido {
    private Jugador jugador1; // DT 1
    private Jugador jugador2; // DT 2
    private int golesJ1;
    private int golesJ2;
    private int minutoActual;
    private Random random;

    public Partido(Jugador jugador1, Jugador jugador2) {
        this.jugador1 = jugador1;
        this.jugador2 = jugador2;
        this.golesJ1 = 0;
        this.golesJ2 = 0;
        this.minutoActual = 0;
        this.random = new Random();
    }

public void iniciarSimulacion() {
        System.out.println("\n==================================================");
        System.out.println(" ⚽ ¡LA GRAN FINAL DEL MUNDIAL DE JULIO DE 2026! ⚽");
        System.out.println("   DT 1: " + jugador1.getNombre() + " vs DT 2: " + jugador2.getNombre());
        System.out.println("==================================================\n");

        while (minutoActual < 90) { // <-- Cambiado a menor estricto
            minutoActual += random.nextInt(6) + 4; // Avanza entre 4 y 10 minutos
            
            if (minutoActual >= 90) {
                minutoActual = 90;
            }

            System.out.println("[" + minutoActual + "'] El balón sigue en disputa...");

            // Probabilidad de peligro
            double umbralPeligro = (minutoActual >= 85) ? 0.50 : 0.25;

            if (random.nextDouble() < umbralPeligro && minutoActual < 90) {
                dispararMomentoClave();
            }

            try { Thread.sleep(600); } catch (InterruptedException e) {}
        }

        finalizarPartido();
    }

    private void dispararMomentoClave() {
        System.out.println("\n🔥 --------------------------------------------------");
        
        // 1 vs 1 Local: Al azar se decide quién ataca y quién defiende [cite: 9, 19]
        if (random.nextBoolean()) {
            System.out.println(" ¡MOMENTO CLAVE! Ataque para: " + jugador1.getNombre());
            SimuladorJugadas.resolverJugadaVersus(this, jugador1, jugador2, 1);
        } else {
            System.out.println(" ¡MOMENTO CLAVE! Ataque para: " + jugador2.getNombre());
            SimuladorJugadas.resolverJugadaVersus(this, jugador2, jugador1, 2);
        }
        
        System.out.println("-------------------------------------------------- 🔥\n");
    }

    public void registrarGol(int idJugador) {
        if (idJugador == 1) this.golesJ1++;
        else this.golesJ2++;
    }

    private void finalizarPartido() {
        System.out.println("==================================================");
        System.out.println(" 🏁 ¡FINAL DE LA GRAN FINAL!");
        System.out.println(" Marcador Final: " + jugador1.getNombre() + " [" + golesJ1 + "] - [" + golesJ2 + "] " + jugador2.getNombre());
        System.out.println("==================================================\n");
    }
}
