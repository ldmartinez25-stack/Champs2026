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
import java.util.Scanner;
import java.util.Random;

public class SimuladorJugadas {
    private static Scanner scanner = new Scanner(System.in);
    private static Random random = new Random();

    public static void resolverJugadaVersus(com.champs2026.motor.Partido partido, com.champs2026.logica.Jugador atacanteDT, com.champs2026.logica.Jugador defensaDT, int equipoAtacante) {
    
    // 1. OBTENER LOS ROLES MEDIANTE LOS MÉTODOS DE TU SELECCIÓN (Polimorfismo estricto)
    com.champs2026.logica.Deportista atacante = atacanteDT.getSeleccionNacional().getDelantero();
    com.champs2026.logica.Deportista porteroRival = defensaDT.getSeleccionNacional().getPortero();

    // 2. VALIDACIÓN DE SEGURIDAD EXIGIDA POR EL PROYECTO
    // Si un DT no fue inteligente en el mercado y no fichó la posición requerida, la jugada se cancela por falta de alineación.
    if (atacante == null || porteroRival == null) {
        System.out.println("⚠️ La jugada se detiene: Uno de los equipos no cuenta con los roles requeridos en cancha (Delantero o Portero).");
        return;
    }

    // 3. ENTRADA A LA ACCIÓN REALISTA
    System.out.println("\n🏃‍♂️ ¡El peligro aumenta! " + atacante.getNombre() + " se escapa solo en ataque y busca fusilar a " + porteroRival.getNombre() + "!");

    // 4. INTERACCIÓN CON EL USUARIO (Toma de decisiones en Consola)
    java.util.Scanner scan = new java.util.Scanner(System.in);
    System.out.println("[SISTEMA TÁCTICO] Elige tu tipo de disparo, DT " + atacanteDT.getNombre() + ":");
    System.out.println("1. Disparo Potente (Fuerza)");
    System.out.println("2. Disparo Colocado (Habilidad)");
    System.out.print("Selección (1 o 2): ");
    
    int opcion = 1;
    if (scan.hasNextInt()) {
        opcion = scan.nextInt();
    }

    // 5. RESOLUCIÓN DEL DUELO (Simulación probabilística)
    java.util.Random rand = new java.util.Random();
    boolean esGol = false;

    if (opcion == 1) {
        System.out.println("💥 ¡" + atacante.getNombre() + " saca un misil directo al arco!");
        esGol = (rand.nextInt(40) + 60) > 75; 
    } else {
        System.out.println("🎯 ¡" + atacante.getNombre() + " intenta definir con clase al segundo palo!");
        esGol = (rand.nextInt(40) + 60) > 78;
    }

    // 6. REGISTRAR EL GOL USANDO TU MÉTODO REAL DE PARTIDO
    if (esGol) {
        System.out.println("⚽ ¡GOOOOOOOOOOOOOL DE " + atacante.getNombre().toUpperCase() + "!");
        partido.registrarGol(equipoAtacante); 
    } else {
        System.out.println("🧤 ¡ATAJADÓN! " + porteroRival.getNombre() + " se viste de héroe y desvía el balón al tiro de esquina.");
    }
}
    

    private static int obtenerModificadorFlecha() {
        int r = random.nextInt(3);
        if (r == 0) return 10;  // ⬆️ Excelente
        if (r == 1) return 0;   // ➡️ Normal
        return -10;             // ⬇️ Bajo Rendimiento
    }
}

