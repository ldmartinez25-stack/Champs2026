package com.champs2026.gui;

import com.champs2026.logica.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Panel del mercado de fichajes donde los jugadores eligen por turnos a sus futbolistas.
 */
public class PanelMercado extends JPanel {

    private VistaPrincipal framePrincipal;
    private Jugador jugador1;
    private Jugador jugador2;
    private MercadoFichajes mercado;

    private Jugador jugadorActivo;

    // Componentes de interfaz
    private JLabel lblTurnoDT;
    private JLabel lblTurnoPais;
    private JLabel lblPresupuesto;
    private JLabel lblCantPlantilla;
    
    private JPanel panelCartasContenedor;
    private JScrollPane scrollCartas;

    private JPanel panelPlantilla1;
    private JPanel panelPlantilla2;
    private JLabel lblTotalPlantilla1;
    private JLabel lblTotalPlantilla2;

    public PanelMercado(VistaPrincipal framePrincipal, Jugador j1, Jugador j2, MercadoFichajes mercado) {
        this.framePrincipal = framePrincipal;
        this.jugador1 = j1;
        this.jugador2 = j2;
        this.mercado = mercado;

        // Jugador 1 empieza fichando
        this.jugadorActivo = jugador1;

        setLayout(new BorderLayout());
        setBackground(VistaPrincipal.COLOR_FONDO);
        setBorder(new EmptyBorder(15, 15, 15, 15));

        // 1. Panel Superior: Información de Turno
        JPanel panelSuperior = crearPanelSuperior();
        add(panelSuperior, BorderLayout.NORTH);

        // 2. Panel Central: Mercado de Futbolistas (Scrollable Grid)
        panelCartasContenedor = new ScrollableJPanel();
        panelCartasContenedor.setBackground(VistaPrincipal.COLOR_FONDO);
        // Usar Grid de columnas variables adaptables
        panelCartasContenedor.setLayout(new WrapLayout(FlowLayout.LEFT, 15, 15));

        scrollCartas = new JScrollPane(panelCartasContenedor);
        scrollCartas.setBorder(null);
        scrollCartas.getVerticalScrollBar().setUnitIncrement(16);
        scrollCartas.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollCartas.setBackground(VistaPrincipal.COLOR_FONDO);
        scrollCartas.getViewport().setBackground(VistaPrincipal.COLOR_FONDO);
        add(scrollCartas, BorderLayout.CENTER);

        // 3. Panel Lateral: Plantillas en tiempo real (Este/Derecha)
        JPanel panelLateral = crearPanelLateralPlantillas();
        add(panelLateral, BorderLayout.EAST);

        // 4. Panel Inferior: Controles de finalización o pase
        JPanel panelInferior = crearPanelInferior();
        add(panelInferior, BorderLayout.SOUTH);

        // Cargar las cartas iniciales
        actualizarInterfaz();
    }

    private JPanel crearPanelSuperior() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(VistaPrincipal.COLOR_TARJETA);
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(VistaPrincipal.COLOR_TEXTO_MUTED, 1, true),
            new EmptyBorder(15, 20, 15, 20)
        ));

        // Izquierda: Turno de
        JPanel panelDT = new JPanel(new GridLayout(2, 1, 2, 2));
        panelDT.setBackground(VistaPrincipal.COLOR_TARJETA);
        
        lblTurnoDT = new JLabel("TURNO DE FICHAJE DE: " + jugadorActivo.getNombre().toUpperCase());
        lblTurnoDT.setFont(VistaPrincipal.FUENTE_SUBTITULO);
        lblTurnoDT.setForeground(VistaPrincipal.COLOR_ACENTO);

        lblTurnoPais = new JLabel("Dirigiendo a: " + jugadorActivo.getSeleccionElegida().getNombrePais());
        lblTurnoPais.setFont(VistaPrincipal.FUENTE_NORMAL);
        lblTurnoPais.setForeground(VistaPrincipal.COLOR_TEXTO);

        panelDT.add(lblTurnoDT);
        panelDT.add(lblTurnoPais);
        panel.add(panelDT, BorderLayout.WEST);

        // Derecha: Presupuesto y Plantilla
        JPanel panelPresupuesto = new JPanel(new GridLayout(2, 1, 2, 2));
        panelPresupuesto.setBackground(VistaPrincipal.COLOR_TARJETA);

        lblPresupuesto = new JLabel("Presupuesto: " + jugadorActivo.getPresupuesto() + " monedas", JLabel.RIGHT);
        lblPresupuesto.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblPresupuesto.setForeground(VistaPrincipal.COLOR_EXITO);

        lblCantPlantilla = new JLabel("Futbolistas fichados: " + jugadorActivo.getSeleccionElegida().getPlantilla().size(), JLabel.RIGHT);
        lblCantPlantilla.setFont(VistaPrincipal.FUENTE_NORMAL);
        lblCantPlantilla.setForeground(VistaPrincipal.COLOR_TEXTO_MUTED);

        panelPresupuesto.add(lblPresupuesto);
        panelPresupuesto.add(lblCantPlantilla);
        panel.add(panelPresupuesto, BorderLayout.EAST);

        return panel;
    }

    private JPanel crearPanelLateralPlantillas() {
        JPanel panel = new JPanel(new GridLayout(2, 1, 0, 15));
        panel.setPreferredSize(new Dimension(280, 0));
        panel.setBackground(VistaPrincipal.COLOR_FONDO);
        panel.setBorder(new EmptyBorder(0, 15, 0, 0));

        // Roster de Jugador 1
        JPanel panelJ1 = new JPanel(new BorderLayout());
        panelJ1.setBackground(VistaPrincipal.COLOR_TARJETA);
        panelJ1.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(VistaPrincipal.COLOR_TEXTO_MUTED, 1, true),
            new EmptyBorder(10, 10, 10, 10)
        ));
        
        JLabel lblTitulo1 = new JLabel(jugador1.getNombre() + " (" + jugador1.getSeleccionElegida().getNombrePais() + ")");
        lblTitulo1.setFont(VistaPrincipal.FUENTE_NEGRITA);
        lblTitulo1.setForeground(VistaPrincipal.COLOR_ACENTO);
        panelJ1.add(lblTitulo1, BorderLayout.NORTH);

        panelPlantilla1 = new JPanel();
        panelPlantilla1.setLayout(new BoxLayout(panelPlantilla1, BoxLayout.Y_AXIS));
        panelPlantilla1.setBackground(VistaPrincipal.COLOR_TARJETA);
        JScrollPane scroll1 = new JScrollPane(panelPlantilla1);
        scroll1.setBorder(null);
        scroll1.setBackground(VistaPrincipal.COLOR_TARJETA);
        scroll1.getViewport().setBackground(VistaPrincipal.COLOR_TARJETA);
        panelJ1.add(scroll1, BorderLayout.CENTER);

        lblTotalPlantilla1 = new JLabel("Monedas: " + jugador1.getPresupuesto());
        lblTotalPlantilla1.setFont(VistaPrincipal.FUENTE_NORMAL);
        lblTotalPlantilla1.setForeground(VistaPrincipal.COLOR_TEXTO_MUTED);
        panelJ1.add(lblTotalPlantilla1, BorderLayout.SOUTH);

        // Roster de Jugador 2
        JPanel panelJ2 = new JPanel(new BorderLayout());
        panelJ2.setBackground(VistaPrincipal.COLOR_TARJETA);
        panelJ2.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(VistaPrincipal.COLOR_TEXTO_MUTED, 1, true),
            new EmptyBorder(10, 10, 10, 10)
        ));

        JLabel lblTitulo2 = new JLabel(jugador2.getNombre() + " (" + jugador2.getSeleccionElegida().getNombrePais() + ")");
        lblTitulo2.setFont(VistaPrincipal.FUENTE_NEGRITA);
        lblTitulo2.setForeground(VistaPrincipal.COLOR_EXITO);
        panelJ2.add(lblTitulo2, BorderLayout.NORTH);

        panelPlantilla2 = new JPanel();
        panelPlantilla2.setLayout(new BoxLayout(panelPlantilla2, BoxLayout.Y_AXIS));
        panelPlantilla2.setBackground(VistaPrincipal.COLOR_TARJETA);
        JScrollPane scroll2 = new JScrollPane(panelPlantilla2);
        scroll2.setBorder(null);
        scroll2.setBackground(VistaPrincipal.COLOR_TARJETA);
        scroll2.getViewport().setBackground(VistaPrincipal.COLOR_TARJETA);
        panelJ2.add(scroll2, BorderLayout.CENTER);

        lblTotalPlantilla2 = new JLabel("Monedas: " + jugador2.getPresupuesto());
        lblTotalPlantilla2.setFont(VistaPrincipal.FUENTE_NORMAL);
        lblTotalPlantilla2.setForeground(VistaPrincipal.COLOR_TEXTO_MUTED);
        panelJ2.add(lblTotalPlantilla2, BorderLayout.SOUTH);

        panel.add(panelJ1);
        panel.add(panelJ2);

        return panel;
    }

    private JPanel crearPanelInferior() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        panel.setBackground(VistaPrincipal.COLOR_FONDO);

        JButton btnPasar = new JButton("PASAR TURNO");
        btnPasar.setUI(new javax.swing.plaf.basic.BasicButtonUI());
        btnPasar.setFont(VistaPrincipal.FUENTE_NEGRITA);
        btnPasar.setBackground(VistaPrincipal.COLOR_TARJETA);
        btnPasar.setForeground(VistaPrincipal.COLOR_TEXTO);
        btnPasar.setPreferredSize(new Dimension(160, 40));
        btnPasar.setFocusPainted(false);
        btnPasar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnPasar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pasarTurno();
            }
        });

        JButton btnFinalizar = new JButton("FINALIZAR DRAFT");
        btnFinalizar.setUI(new javax.swing.plaf.basic.BasicButtonUI());
        btnFinalizar.setFont(VistaPrincipal.FUENTE_NEGRITA);
        btnFinalizar.setBackground(VistaPrincipal.COLOR_ALERTA);
        btnFinalizar.setForeground(VistaPrincipal.COLOR_TEXTO);
        btnFinalizar.setPreferredSize(new Dimension(180, 40));
        btnFinalizar.setFocusPainted(false);
        btnFinalizar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnFinalizar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int confirm = JOptionPane.showConfirmDialog(
                    PanelMercado.this,
                    "¿Estás seguro de que deseas finalizar la ronda de fichajes y ver los resultados?",
                    "Finalizar Draft",
                    JOptionPane.YES_NO_OPTION
                );
                if (confirm == JOptionPane.YES_OPTION) {
                    framePrincipal.finalizarPartida();
                }
            }
        });

        panel.add(btnPasar);
        panel.add(btnFinalizar);

        return panel;
    }

    private void pasarTurno() {
        // Cambiar turno al otro jugador
        jugadorActivo = (jugadorActivo == jugador1) ? jugador2 : jugador1;
        actualizarInterfaz();
    }

    private void actualizarInterfaz() {
        // 1. Actualizar textos de cabecera
        lblTurnoDT.setText("TURNO DE FICHAJE DE: " + jugadorActivo.getNombre().toUpperCase());
        lblTurnoPais.setText("Dirigiendo a: " + jugadorActivo.getSeleccionElegida().getNombrePais());
        lblPresupuesto.setText("Presupuesto: " + jugadorActivo.getPresupuesto() + " monedas");
        lblCantPlantilla.setText("Futbolistas fichados: " + jugadorActivo.getSeleccionElegida().getPlantilla().size());

        // Cambiar color del título según el jugador activo para retroalimentación visual
        if (jugadorActivo == jugador1) {
            lblTurnoDT.setForeground(VistaPrincipal.COLOR_ACENTO);
        } else {
            lblTurnoDT.setForeground(VistaPrincipal.COLOR_EXITO);
        }

        // 2. Actualizar plantillas laterales
        rellenarPlantillaVisual(panelPlantilla1, jugador1);
        rellenarPlantillaVisual(panelPlantilla2, jugador2);
        lblTotalPlantilla1.setText("Fichajes: " + jugador1.getSeleccionElegida().getPlantilla().size() + " | Monedas: " + jugador1.getPresupuesto());
        lblTotalPlantilla2.setText("Fichajes: " + jugador2.getSeleccionElegida().getPlantilla().size() + " | Monedas: " + jugador2.getPresupuesto());

        // 3. Volver a pintar las tarjetas del mercado
        panelCartasContenedor.removeAll();
        ArrayList<Deportista> disponibles = mercado.getBancoComun();
        
        if (disponibles.isEmpty()) {
            JLabel lblVacio = new JLabel("¡El mercado de fichajes está vacío! Todos los deportistas han sido seleccionados.", JLabel.CENTER);
            lblVacio.setFont(VistaPrincipal.FUENTE_SUBTITULO);
            lblVacio.setForeground(VistaPrincipal.COLOR_TEXTO_MUTED);
            panelCartasContenedor.add(lblVacio);
            
            // Si el mercado se vacía, pasar automáticamente a pantalla final tras un breve aviso
            Timer timer = new Timer(1500, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    framePrincipal.finalizarPartida();
                }
            });
            timer.setRepeats(false);
            timer.start();
        } else {
            for (Deportista d : disponibles) {
                JPanel card = crearTarjetaDeportista(d);
                panelCartasContenedor.add(card);
            }
        }

        panelCartasContenedor.revalidate();
        panelCartasContenedor.repaint();
    }

    private void rellenarPlantillaVisual(JPanel panel, Jugador jugador) {
        panel.removeAll();
        for (Deportista d : jugador.getSeleccionElegida().getPlantilla()) {
            JPanel fila = new JPanel(new BorderLayout());
            fila.setBackground(VistaPrincipal.COLOR_TARJETA);
            fila.setBorder(new EmptyBorder(5, 5, 5, 5));

            String posText = "[DEL]";
            Color posColor = VistaPrincipal.COLOR_EXITO;
            if (d instanceof Mediocampista) {
                posText = "[MED]";
                posColor = VistaPrincipal.COLOR_ACENTO;
            } else if (d instanceof Portero) {
                posText = "[POR]";
                posColor = Color.ORANGE;
            }

            JLabel lblName = new JLabel("<html><font color='" + hex(posColor) + "'>" + posText + "</font> " + d.getNombre() + "</html>");
            lblName.setFont(VistaPrincipal.FUENTE_NORMAL);
            lblName.setForeground(VistaPrincipal.COLOR_TEXTO);

            JLabel lblPrice = new JLabel(d.getPrecio() + " M", JLabel.RIGHT);
            lblPrice.setFont(VistaPrincipal.FUENTE_NEGRITA);
            lblPrice.setForeground(VistaPrincipal.COLOR_TEXTO_MUTED);

            fila.add(lblName, BorderLayout.WEST);
            fila.add(lblPrice, BorderLayout.EAST);
            
            panel.add(fila);
        }
        panel.revalidate();
        panel.repaint();
    }

    private String hex(Color color) {
        return String.format("#%02x%02x%02x", color.getRed(), color.getGreen(), color.getBlue());
    }

    private JPanel crearTarjetaDeportista(Deportista d) {
        JPanel card = new JPanel();
        card.setPreferredSize(new Dimension(210, 290));
        card.setBackground(VistaPrincipal.COLOR_TARJETA);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(VistaPrincipal.COLOR_TEXTO_MUTED, 1, true),
            new EmptyBorder(12, 12, 12, 12)
        ));
        card.setLayout(new BorderLayout());

        // Header de la tarjeta: Nombre y Posición
        JPanel header = new JPanel(new GridLayout(2, 1));
        header.setBackground(VistaPrincipal.COLOR_TARJETA);

        JLabel lblNombre = new JLabel(d.getNombre());
        lblNombre.setFont(new Font("Segoe UI", Font.BOLD, 17));
        lblNombre.setForeground(VistaPrincipal.COLOR_TEXTO);
        lblNombre.setHorizontalAlignment(JLabel.CENTER);

        String posicion = "DELANTERO";
        Color colorPos = VistaPrincipal.COLOR_EXITO;
        if (d instanceof Mediocampista) {
            posicion = "MEDIOCAMPISTA";
            colorPos = VistaPrincipal.COLOR_ACENTO;
        } else if (d instanceof Portero) {
            posicion = "PORTERO";
            colorPos = Color.ORANGE;
        }

        JLabel lblPos = new JLabel(posicion);
        lblPos.setFont(new Font("Segoe UI", Font.BOLD, 11));
        lblPos.setForeground(colorPos);
        lblPos.setHorizontalAlignment(JLabel.CENTER);

        header.add(lblNombre);
        header.add(lblPos);
        card.add(header, BorderLayout.NORTH);

        // Centro: Estadísticas
        JPanel statsPanel = new JPanel(new GridLayout(5, 1, 2, 2));
        statsPanel.setBackground(VistaPrincipal.COLOR_TARJETA);
        statsPanel.setBorder(new EmptyBorder(10, 5, 10, 5));

        statsPanel.add(crearBarraStat("Velocidad (VEL)", d.getVelocidad(), VistaPrincipal.COLOR_ACENTO));
        statsPanel.add(crearBarraStat("Disparo (SHO)", d.getDisparo(), VistaPrincipal.COLOR_ACENTO));
        statsPanel.add(crearBarraStat("Pase (PAS)", d.getPase(), VistaPrincipal.COLOR_ACENTO));
        statsPanel.add(crearBarraStat("Defensa (DEF)", d.getDefensa(), VistaPrincipal.COLOR_ACENTO));

        if (d instanceof Portero) {
            int atajadaVal = ((Portero) d).getAtajada();
            statsPanel.add(crearBarraStat("Atajada (ATAJ)", atajadaVal, Color.ORANGE));
        } else {
            statsPanel.add(Box.createGlue()); // Espaciador para mantener dimensiones
        }

        card.add(statsPanel, BorderLayout.CENTER);

        // Footer: Precio y Botón de Fichaje
        JPanel footer = new JPanel(new BorderLayout(5, 5));
        footer.setBackground(VistaPrincipal.COLOR_TARJETA);

        JLabel lblPrecio = new JLabel(d.getPrecio() + " Monedas");
        lblPrecio.setFont(VistaPrincipal.FUENTE_NEGRITA);
        lblPrecio.setForeground(VistaPrincipal.COLOR_EXITO);
        lblPrecio.setHorizontalAlignment(JLabel.CENTER);
        footer.add(lblPrecio, BorderLayout.NORTH);

        JButton btnFichar = new JButton("FICHAR");
        btnFichar.setUI(new javax.swing.plaf.basic.BasicButtonUI());
        btnFichar.setFont(VistaPrincipal.FUENTE_NEGRITA);
        btnFichar.setBackground(VistaPrincipal.COLOR_ACENTO);
        btnFichar.setForeground(VistaPrincipal.COLOR_TEXTO);
        btnFichar.setFocusPainted(false);
        btnFichar.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Cambiar apariencia si no pertenece a la selección del jugador activo
        boolean esDeSuPais = jugadorActivo.getSeleccionElegida().getNombrePais().equalsIgnoreCase(d.getPais());
        
        if (!esDeSuPais) {
            btnFichar.setText("PAIS DIFERENTE");
            btnFichar.setBackground(new Color(90, 40, 40));
            btnFichar.setForeground(VistaPrincipal.COLOR_TEXTO_MUTED);
        } else if (jugadorActivo.getPresupuesto() < d.getPrecio()) {
            btnFichar.setText("SIN FONDOS");
            btnFichar.setBackground(new Color(60, 60, 70));
            btnFichar.setForeground(VistaPrincipal.COLOR_TEXTO_MUTED);
        }

        btnFichar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                realizarFichaje(d);
            }
        });
        footer.add(btnFichar, BorderLayout.SOUTH);

        card.add(footer, BorderLayout.SOUTH);

        return card;
    }

    private JPanel crearBarraStat(String nombreStat, int valor, Color colorBarra) {
        JPanel statRow = new JPanel(new BorderLayout(5, 0));
        statRow.setBackground(VistaPrincipal.COLOR_TARJETA);

        JLabel lblName = new JLabel(nombreStat);
        lblName.setFont(new Font("Segoe UI", Font.PLAIN, 10));
        lblName.setForeground(VistaPrincipal.COLOR_TEXTO_MUTED);
        statRow.add(lblName, BorderLayout.WEST);

        JLabel lblVal = new JLabel(String.valueOf(valor), JLabel.RIGHT);
        lblVal.setFont(new Font("Segoe UI", Font.BOLD, 10));
        lblVal.setForeground(VistaPrincipal.COLOR_TEXTO);
        statRow.add(lblVal, BorderLayout.EAST);

        // Pequeña barra visual debajo
        JProgressBar bar = new JProgressBar(0, 100);
        bar.setUI(new javax.swing.plaf.basic.BasicProgressBarUI());
        bar.setValue(valor);
        bar.setPreferredSize(new Dimension(0, 4));
        bar.setForeground(colorBarra);
        bar.setBackground(new Color(40, 40, 50));
        bar.setBorder(null);

        JPanel content = new JPanel(new BorderLayout());
        content.setBackground(VistaPrincipal.COLOR_TARJETA);
        content.add(statRow, BorderLayout.NORTH);
        content.add(bar, BorderLayout.SOUTH);

        return content;
    }

    private void realizarFichaje(Deportista d) {
        // Validación: sólo se permite fichar de la selección del entrenador
        if (!jugadorActivo.getSeleccionElegida().getNombrePais().equalsIgnoreCase(d.getPais())) {
            JOptionPane.showMessageDialog(
                this,
                "¡Fichaje denegado! " + jugadorActivo.getNombre() + " dirige a " + jugadorActivo.getSeleccionElegida().getNombrePais() +
                " y solo puede fichar futbolistas de esa selección.\n" +
                d.getNombre() + " pertenece a: " + d.getPais() + ".",
                "Nacionalidad Incorrecta",
                JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        if (jugadorActivo.getPresupuesto() < d.getPrecio()) {
            JOptionPane.showMessageDialog(
                this,
                "No tienes suficientes monedas para fichar a " + d.getNombre() + ".\n" +
                "Costo: " + d.getPrecio() + " | Saldo: " + jugadorActivo.getPresupuesto(),
                "Presupuesto Insuficiente",
                JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        // Ejecutar compra
        boolean exito = mercado.comprarFutbolista(jugadorActivo, d.getNombre());
        if (exito) {
            // Cambiar turno automáticamente tras una compra exitosa
            pasarTurno();
        } else {
            // Mostrar error visual
            JOptionPane.showMessageDialog(
                this,
                "No se pudo realizar el fichaje. Verifique su disponibilidad.",
                "Error de Compra",
                JOptionPane.ERROR_MESSAGE
            );
        }
    }

    /**
     * Layout que distribuye los elementos y pasa de fila automáticamente
     * sin recortar los componentes al cambiar el tamaño de ventana.
     */
    static class WrapLayout extends FlowLayout {
        public WrapLayout() { super(); }
        public WrapLayout(int align) { super(align); }
        public WrapLayout(int align, int hgap, int vgap) { super(align, hgap, vgap); }

        @Override
        public Dimension preferredLayoutSize(Container target) {
            return layoutSize(target, true);
        }

        @Override
        public Dimension minimumLayoutSize(Container target) {
            Dimension minimum = layoutSize(target, false);
            minimum.width -= (getHgap() + 1);
            return minimum;
        }

        private Dimension layoutSize(Container target, boolean preferred) {
            synchronized (target.getTreeLock()) {
                int targetWidth = target.getSize().width;
                if (targetWidth == 0) targetWidth = Integer.MAX_VALUE;

                int hgap = getHgap();
                int vgap = getVgap();
                Insets insets = target.getInsets();
                int maxwidth = targetWidth - (insets.left + insets.right + hgap * 2);
                int nmembers = target.getComponentCount();
                int x = 0;
                int y = insets.top + vgap;
                int rowHeight = 0;

                for (int i = 0; i < nmembers; i++) {
                    Component m = target.getComponent(i);
                    if (m.isVisible()) {
                        Dimension d = preferred ? m.getPreferredSize() : m.getMinimumSize();
                        if (x == 0 || x + d.width <= maxwidth) {
                            if (x > 0) x += hgap;
                            x += d.width;
                            rowHeight = Math.max(rowHeight, d.height);
                        } else {
                            x = d.width;
                            y += vgap + rowHeight;
                            rowHeight = d.height;
                        }
                    }
                }
                y += rowHeight + vgap + insets.bottom;
                return new Dimension(targetWidth, y);
            }
        }
    }

    /**
     * Panel que fuerza el ancho para ajustarse al viewport de un JScrollPane,
     * permitiendo que el WrapLayout realice el ajuste y salto de línea.
     */
    static class ScrollableJPanel extends JPanel implements Scrollable {
        @Override
        public Dimension getPreferredScrollableViewportSize() {
            return getPreferredSize();
        }

        @Override
        public int getScrollableUnitIncrement(Rectangle visibleRect, int orientation, int direction) {
            return 16;
        }

        @Override
        public int getScrollableBlockIncrement(Rectangle visibleRect, int orientation, int direction) {
            return 64;
        }

        @Override
        public boolean getScrollableTracksViewportWidth() {
            return true; // Forzar a ajustar al ancho del scroll
        }

        @Override
        public boolean getScrollableTracksViewportHeight() {
            return false;
        }
    }
}
