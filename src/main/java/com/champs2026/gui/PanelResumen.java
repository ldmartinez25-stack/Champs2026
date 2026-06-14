package com.champs2026.gui;

import com.champs2026.logica.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Pantalla final de resumen que muestra las plantillas completas de ambos directores técnicos,
 * calcula el puntaje promedio de fortaleza de cada selección nacional y declara al ganador.
 */
public class PanelResumen extends JPanel {

    private VistaPrincipal framePrincipal;
    private Jugador jugador1;
    private Jugador jugador2;

    public PanelResumen(VistaPrincipal framePrincipal, Jugador j1, Jugador j2) {
        this.framePrincipal = framePrincipal;
        this.jugador1 = j1;
        this.jugador2 = j2;

        setLayout(new BorderLayout());
        setBackground(VistaPrincipal.COLOR_FONDO);
        setBorder(new EmptyBorder(30, 30, 30, 30));

        // 1. Cabecera
        JPanel panelCabecera = new JPanel(new GridLayout(2, 1, 5, 5));
        panelCabecera.setBackground(VistaPrincipal.COLOR_FONDO);
        panelCabecera.setBorder(new EmptyBorder(0, 0, 20, 0));

        JLabel lblTitulo = new JLabel("MERCADO DE FICHAJES FINALIZADO", JLabel.CENTER);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 28));
        lblTitulo.setForeground(VistaPrincipal.COLOR_EXITO);

        JLabel lblSubtitulo = new JLabel("Resumen de las plantillas nacionales registradas", JLabel.CENTER);
        lblSubtitulo.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        lblSubtitulo.setForeground(VistaPrincipal.COLOR_TEXTO_MUTED);

        panelCabecera.add(lblTitulo);
        panelCabecera.add(lblSubtitulo);
        add(panelCabecera, BorderLayout.NORTH);

        // 2. Contenedor central (Comparativa de los 2 equipos)
        JPanel panelCentro = new JPanel(new GridLayout(1, 2, 30, 0));
        panelCentro.setBackground(VistaPrincipal.COLOR_FONDO);

        JPanel panelEquipo1 = crearPanelEquipo(jugador1, VistaPrincipal.COLOR_ACENTO);
        JPanel panelEquipo2 = crearPanelEquipo(jugador2, VistaPrincipal.COLOR_EXITO);

        panelCentro.add(panelEquipo1);
        panelCentro.add(panelEquipo2);
        add(panelCentro, BorderLayout.CENTER);

        // 4. Panel Inferior (Controles: Jugar de nuevo / Salir)
        JPanel panelInferior = new JPanel(new FlowLayout(FlowLayout.CENTER, 25, 20));
        panelInferior.setBackground(VistaPrincipal.COLOR_FONDO);

        JButton btnReiniciar = new JButton("NUEVA PARTIDA");
        btnReiniciar.setUI(new javax.swing.plaf.basic.BasicButtonUI());
        btnReiniciar.setFont(VistaPrincipal.FUENTE_BOTON);
        btnReiniciar.setBackground(VistaPrincipal.COLOR_ACENTO);
        btnReiniciar.setForeground(VistaPrincipal.COLOR_TEXTO);
        btnReiniciar.setPreferredSize(new Dimension(200, 45));
        btnReiniciar.setFocusPainted(false);
        btnReiniciar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnReiniciar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                framePrincipal.reiniciarJuego();
            }
        });

        JButton btnSalir = new JButton("SALIR DEL JUEGO");
        btnSalir.setUI(new javax.swing.plaf.basic.BasicButtonUI());
        btnSalir.setFont(VistaPrincipal.FUENTE_BOTON);
        btnSalir.setBackground(VistaPrincipal.COLOR_TARJETA);
        btnSalir.setForeground(VistaPrincipal.COLOR_TEXTO);
        btnSalir.setPreferredSize(new Dimension(200, 45));
        btnSalir.setFocusPainted(false);
        btnSalir.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnSalir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        panelInferior.add(btnReiniciar);
        panelInferior.add(btnSalir);
        add(panelInferior, BorderLayout.SOUTH);
    }

    private double calcularRatingEquipo(SeleccionNacional seleccion) {
        ArrayList<Deportista> plantilla = seleccion.getPlantilla();
        if (plantilla.isEmpty()) return 0.0;

        double suma = 0;
        for (Deportista d : plantilla) {
            double promedioAtleta;
            if (d instanceof Portero) {
                promedioAtleta = (d.getVelocidad() + d.getDisparo() + d.getPase() + d.getDefensa() + ((Portero) d).getAtajada()) / 5.0;
            } else {
                promedioAtleta = (d.getVelocidad() + d.getDisparo() + d.getPase() + d.getDefensa()) / 4.0;
            }
            suma += promedioAtleta;
        }
        return Math.round((suma / plantilla.size()) * 10.0) / 10.0;
    }

    private JPanel crearPanelEquipo(Jugador jugador, Color colorAcento) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(VistaPrincipal.COLOR_TARJETA);
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(VistaPrincipal.COLOR_TEXTO_MUTED, 1, true),
            new EmptyBorder(20, 20, 20, 20)
        ));

        // Info de cabecera del equipo
        JPanel infoHeader = new JPanel(new GridLayout(2, 1, 2, 2));
        infoHeader.setBackground(VistaPrincipal.COLOR_TARJETA);
        infoHeader.setBorder(new EmptyBorder(0, 0, 15, 0));

        JLabel lblNombre = new JLabel(jugador.getNombre().toUpperCase());
        lblNombre.setFont(VistaPrincipal.FUENTE_SUBTITULO);
        lblNombre.setForeground(colorAcento);

        JLabel lblPais = new JLabel("Selección: " + jugador.getSeleccionElegida().getNombrePais());
        lblPais.setFont(VistaPrincipal.FUENTE_NORMAL);
        lblPais.setForeground(VistaPrincipal.COLOR_TEXTO);

        infoHeader.add(lblNombre);
        infoHeader.add(lblPais);
        panel.add(infoHeader, BorderLayout.NORTH);

        // Tabla de plantilla
        JPanel listPanel = new JPanel();
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));
        listPanel.setBackground(VistaPrincipal.COLOR_TARJETA);

        ArrayList<Deportista> plantilla = jugador.getSeleccionElegida().getPlantilla();
        if (plantilla.isEmpty()) {
            JLabel lblVacio = new JLabel("No se fichó a ningún futbolista.");
            lblVacio.setFont(VistaPrincipal.FUENTE_NORMAL);
            lblVacio.setForeground(VistaPrincipal.COLOR_TEXTO_MUTED);
            listPanel.add(lblVacio);
        } else {
            for (Deportista d : plantilla) {
                JPanel fila = crearFilaFutbolista(d);
                listPanel.add(fila);
                listPanel.add(Box.createRigidArea(new Dimension(0, 5)));
            }
        }

        JScrollPane scroll = new JScrollPane(listPanel);
        scroll.setBorder(null);
        scroll.setBackground(VistaPrincipal.COLOR_TARJETA);
        scroll.getViewport().setBackground(VistaPrincipal.COLOR_TARJETA);
        panel.add(scroll, BorderLayout.CENTER);

        // Footer: Monedas restantes
        JLabel lblRestante = new JLabel("Presupuesto restante: " + jugador.getPresupuesto() + " monedas", JLabel.RIGHT);
        lblRestante.setFont(VistaPrincipal.FUENTE_NEGRITA);
        lblRestante.setForeground(VistaPrincipal.COLOR_TEXTO_MUTED);
        lblRestante.setBorder(new EmptyBorder(15, 0, 0, 0));
        panel.add(lblRestante, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel crearFilaFutbolista(Deportista d) {
        JPanel fila = new JPanel(new GridBagLayout());
        fila.setBackground(new Color(24, 32, 48));
        fila.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(40, 50, 70), 1, true),
            new EmptyBorder(8, 12, 8, 12)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        gbc.gridx = 0;
        gbc.gridy = 0;

        // Nombre
        JLabel lblNombre = new JLabel(d.getNombre());
        lblNombre.setFont(VistaPrincipal.FUENTE_NEGRITA);
        lblNombre.setForeground(VistaPrincipal.COLOR_TEXTO);
        fila.add(lblNombre, gbc);

        // Posición
        gbc.gridx++;
        gbc.weightx = 0.5;
        String pos = "DEL";
        Color posColor = VistaPrincipal.COLOR_EXITO;
        if (d instanceof Mediocampista) {
            pos = "MED";
            posColor = VistaPrincipal.COLOR_ACENTO;
        } else if (d instanceof Portero) {
            pos = "POR";
            posColor = Color.ORANGE;
        }
        JLabel lblPos = new JLabel(pos, JLabel.CENTER);
        lblPos.setFont(new Font("Segoe UI", Font.BOLD, 11));
        lblPos.setForeground(posColor);
        fila.add(lblPos, gbc);

        // Stats resumen
        gbc.gridx++;
        gbc.weightx = 0.8;
        String statsText = String.format("V:%d D:%d P:%d D:%d", d.getVelocidad(), d.getDisparo(), d.getPase(), d.getDefensa());
        if (d instanceof Portero) {
            statsText += " A:" + ((Portero) d).getAtajada();
        }
        JLabel lblStats = new JLabel(statsText, JLabel.CENTER);
        lblStats.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        lblStats.setForeground(VistaPrincipal.COLOR_TEXTO_MUTED);
        fila.add(lblStats, gbc);

        // Precio
        gbc.gridx++;
        gbc.weightx = 0.4;
        JLabel lblPrecio = new JLabel(d.getPrecio() + " M", JLabel.RIGHT);
        lblPrecio.setFont(VistaPrincipal.FUENTE_NEGRITA);
        lblPrecio.setForeground(VistaPrincipal.COLOR_EXITO);
        fila.add(lblPrecio, gbc);

        return fila;
    }
}
