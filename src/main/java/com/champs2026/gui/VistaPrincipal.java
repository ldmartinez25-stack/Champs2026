package com.champs2026.gui;

import com.champs2026.logica.*;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Ventana principal que administra el flujo del juego mediante un CardLayout.
 */
public class VistaPrincipal extends JFrame {

    // Paleta de colores globales
    public static final Color COLOR_FONDO = new Color(18, 24, 36);
    public static final Color COLOR_TARJETA = new Color(30, 41, 59);
    public static final Color COLOR_ACENTO = new Color(59, 130, 246);
    public static final Color COLOR_EXITO = new Color(16, 185, 129);
    public static final Color COLOR_ALERTA = new Color(239, 68, 68);
    public static final Color COLOR_TEXTO = new Color(248, 250, 252);
    public static final Color COLOR_TEXTO_MUTED = new Color(148, 163, 184);

    // Tipografías
    public static final Font FUENTE_TITULO = new Font("Segoe UI", Font.BOLD, 26);
    public static final Font FUENTE_SUBTITULO = new Font("Segoe UI", Font.BOLD, 18);
    public static final Font FUENTE_NORMAL = new Font("Segoe UI", Font.PLAIN, 14);
    public static final Font FUENTE_NEGRITA = new Font("Segoe UI", Font.BOLD, 14);
    public static final Font FUENTE_BOTON = new Font("Segoe UI", Font.BOLD, 15);

    private CardLayout cardLayout;
    private JPanel contenedorPrincipal;

    private Jugador jugador1;
    private Jugador jugador2;
    private MercadoFichajes mercado;

    public VistaPrincipal() {
        setTitle("Champs 2026 - Gestor de Fichajes");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 800);
        setMinimumSize(new Dimension(1000, 700));
        setLocationRelativeTo(null);

        // Configurar UI Manager para una apariencia moderna y limpia
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            // Continuar con Look and Feel por defecto si falla
        }

        cardLayout = new CardLayout();
        contenedorPrincipal = new JPanel(cardLayout);
        contenedorPrincipal.setBackground(COLOR_FONDO);

        // Inicializar y agregar la primera pantalla (Registro)
        PanelRegistro panelRegistro = new PanelRegistro(this);
        contenedorPrincipal.add(panelRegistro, "REGISTRO");

        add(contenedorPrincipal);
        cardLayout.show(contenedorPrincipal, "REGISTRO");
    }

    /**
     * Inicia la fase de mercado con los jugadores registrados.
     */
    public void iniciarMercado(Jugador j1, Jugador j2, ArrayList<Deportista> bancoComun) {
        this.jugador1 = j1;
        this.jugador2 = j2;
        this.mercado = new MercadoFichajes(bancoComun);

        // Crear e ir a la pantalla del mercado
        PanelMercado panelMercado = new PanelMercado(this, jugador1, jugador2, mercado);
        contenedorPrincipal.add(panelMercado, "MERCADO");
        cardLayout.show(contenedorPrincipal, "MERCADO");
    }

    /**
     * Muestra la pantalla final de resumen del draft.
     */
    public void finalizarPartida() {
        PanelResumen panelResumen = new PanelResumen(this, jugador1, jugador2);
        contenedorPrincipal.add(panelResumen, "RESUMEN");
        cardLayout.show(contenedorPrincipal, "RESUMEN");
    }

    /**
     * Reinicia el flujo del juego desde la pantalla de registro.
     */
    public void reiniciarJuego() {
        jugador1 = null;
        jugador2 = null;
        mercado = null;

        PanelRegistro panelRegistro = new PanelRegistro(this);
        contenedorPrincipal.add(panelRegistro, "REGISTRO");
        cardLayout.show(contenedorPrincipal, "REGISTRO");
    }
}
