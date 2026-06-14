package com.champs2026.gui;

import com.champs2026.logica.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Pantalla inicial de registro para configurar a los entrenadores y sus selecciones.
 */
public class PanelRegistro extends JPanel {

    private VistaPrincipal framePrincipal;
    private JTextField txtNombre1;
    private JTextField txtNombre2;
    private JComboBox<String> comboPais1;
    private JComboBox<String> comboPais2;
    private JLabel lblError;
    private JLabel lblEstadoArchivo;

    private ArrayList<Deportista> bancoComun;

    private static final String[] PAISES = {
        "Argentina", "Brasil", "Francia", "España", "Alemania", 
        "Italia", "Inglaterra", "Portugal", "Uruguay", "Colombia", "México",
        "Bélgica", "Noruega", "Croacia"
    };

    public PanelRegistro(VistaPrincipal framePrincipal) {
        this.framePrincipal = framePrincipal;
        
        setLayout(new BorderLayout());
        setBackground(VistaPrincipal.COLOR_FONDO);
        setBorder(new EmptyBorder(40, 40, 40, 40));

        // 1. Cabecera (Header)
        JPanel panelCabecera = new JPanel(new GridLayout(2, 1, 5, 5));
        panelCabecera.setBackground(VistaPrincipal.COLOR_FONDO);
        
        JLabel lblTitulo = new JLabel("CHAMPS 2026", JLabel.CENTER);
        lblTitulo.setFont(VistaPrincipal.FUENTE_TITULO);
        lblTitulo.setForeground(VistaPrincipal.COLOR_EXITO);
        
        JLabel lblSubtitulo = new JLabel("Registro Oficial de Directores Técnicos", JLabel.CENTER);
        lblSubtitulo.setFont(VistaPrincipal.FUENTE_SUBTITULO);
        lblSubtitulo.setForeground(VistaPrincipal.COLOR_TEXTO);

        panelCabecera.add(lblTitulo);
        panelCabecera.add(lblSubtitulo);
        add(panelCabecera, BorderLayout.NORTH);

        // 2. Contenedor central (Grid para dos jugadores)
        JPanel panelCentro = new JPanel(new GridLayout(1, 2, 40, 0));
        panelCentro.setBackground(VistaPrincipal.COLOR_FONDO);
        panelCentro.setBorder(new EmptyBorder(30, 0, 30, 0));

        // Jugador 1
        JPanel panelJ1 = crearPanelJugador("DIRECTOR TÉCNICO 1", 1);
        // Jugador 2
        JPanel panelJ2 = crearPanelJugador("DIRECTOR TÉCNICO 2", 2);

        panelCentro.add(panelJ1);
        panelCentro.add(panelJ2);
        add(panelCentro, BorderLayout.CENTER);

        // 3. Panel Inferior (Control, errores y estado)
        JPanel panelInferior = new JPanel();
        panelInferior.setLayout(new BoxLayout(panelInferior, BoxLayout.Y_AXIS));
        panelInferior.setBackground(VistaPrincipal.COLOR_FONDO);

        lblError = new JLabel(" ", JLabel.CENTER);
        lblError.setFont(VistaPrincipal.FUENTE_NEGRITA);
        lblError.setForeground(VistaPrincipal.COLOR_ALERTA);
        lblError.setAlignmentX(Component.CENTER_ALIGNMENT);

        lblEstadoArchivo = new JLabel(" ", JLabel.CENTER);
        lblEstadoArchivo.setFont(VistaPrincipal.FUENTE_NORMAL);
        lblEstadoArchivo.setForeground(VistaPrincipal.COLOR_TEXTO_MUTED);
        lblEstadoArchivo.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton btnComenzar = new JButton("COMENZAR CAMPEONATO");
        btnComenzar.setUI(new javax.swing.plaf.basic.BasicButtonUI());
        btnComenzar.setFont(VistaPrincipal.FUENTE_BOTON);
        btnComenzar.setBackground(VistaPrincipal.COLOR_ACENTO);
        btnComenzar.setForeground(VistaPrincipal.COLOR_TEXTO);
        btnComenzar.setFocusPainted(false);
        btnComenzar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnComenzar.setPreferredSize(new Dimension(280, 50));
        btnComenzar.setMaximumSize(new Dimension(280, 50));
        btnComenzar.setAlignmentX(Component.CENTER_ALIGNMENT);

        btnComenzar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                validarYComenzar();
            }
        });

        panelInferior.add(lblError);
        panelInferior.add(Box.createRigidArea(new Dimension(0, 10)));
        panelInferior.add(lblEstadoArchivo);
        panelInferior.add(Box.createRigidArea(new Dimension(0, 20)));
        panelInferior.add(btnComenzar);

        add(panelInferior, BorderLayout.SOUTH);

        // Cargar futbolistas en segundo plano o al iniciar
        cargarFutbolistas();
    }

    private JPanel crearPanelJugador(String titulo, int numJugador) {
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setBackground(VistaPrincipal.COLOR_TARJETA);
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(VistaPrincipal.COLOR_TEXTO_MUTED, 1, true),
            new EmptyBorder(30, 30, 30, 30)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 0, 10, 0);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;

        JLabel lblTituloJ = new JLabel(titulo);
        lblTituloJ.setFont(VistaPrincipal.FUENTE_SUBTITULO);
        lblTituloJ.setForeground(VistaPrincipal.COLOR_ACENTO);
        lblTituloJ.setHorizontalAlignment(JLabel.CENTER);
        panel.add(lblTituloJ, gbc);

        gbc.gridy++;
        JLabel lblNombre = new JLabel("Nombre Completo:");
        lblNombre.setFont(VistaPrincipal.FUENTE_NEGRITA);
        lblNombre.setForeground(VistaPrincipal.COLOR_TEXTO);
        panel.add(lblNombre, gbc);

        gbc.gridy++;
        JTextField txtNombre = new JTextField();
        txtNombre.setFont(VistaPrincipal.FUENTE_NORMAL);
        txtNombre.setBackground(Color.WHITE);
        txtNombre.setForeground(Color.BLACK);
        txtNombre.setCaretColor(Color.BLACK);
        txtNombre.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(VistaPrincipal.COLOR_TEXTO_MUTED, 1, true),
            new EmptyBorder(8, 10, 8, 10)
        ));
        panel.add(txtNombre, gbc);

        gbc.gridy++;
        JLabel lblPais = new JLabel("Selección Nacional a Dirigir:");
        lblPais.setFont(VistaPrincipal.FUENTE_NEGRITA);
        lblPais.setForeground(VistaPrincipal.COLOR_TEXTO);
        panel.add(lblPais, gbc);

        gbc.gridy++;
        JComboBox<String> comboPais = new JComboBox<>(PAISES);
        comboPais.setFont(VistaPrincipal.FUENTE_NORMAL);
        comboPais.setBackground(Color.WHITE);
        comboPais.setForeground(Color.BLACK);
        comboPais.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                JLabel l = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                l.setBackground(isSelected ? VistaPrincipal.COLOR_ACENTO : Color.WHITE);
                l.setForeground(isSelected ? Color.WHITE : Color.BLACK);
                l.setBorder(new EmptyBorder(5, 10, 5, 10));
                return l;
            }
        });
        panel.add(comboPais, gbc);

        if (numJugador == 1) {
            txtNombre1 = txtNombre;
            comboPais1 = comboPais;
            comboPais1.setSelectedIndex(0); // Argentina
        } else {
            txtNombre2 = txtNombre;
            comboPais2 = comboPais;
            comboPais2.setSelectedIndex(1); // Brasil
        }

        return panel;
    }

    private void cargarFutbolistas() {
        bancoComun = LectorDatos.cargarMercadoDesdeArchivo("futbolistas.txt");
        if (bancoComun.isEmpty()) {
            lblEstadoArchivo.setText("<html><font color='#EF4444'>[ERROR]: No se pudo cargar futbolistas.txt. Asegúrese de que existe en la carpeta del proyecto.</font></html>");
        } else {
            lblEstadoArchivo.setText("Base de datos de futbolistas cargada con éxito. Se detectaron " + bancoComun.size() + " futbolistas.");
        }
    }

    private void validarYComenzar() {
        String nombre1 = txtNombre1.getText().trim();
        String nombre2 = txtNombre2.getText().trim();
        String pais1 = (String) comboPais1.getSelectedItem();
        String pais2 = (String) comboPais2.getSelectedItem();

        if (nombre1.isEmpty() || nombre2.isEmpty()) {
            lblError.setText("Error: Ambos directores técnicos deben ingresar sus nombres.");
            return;
        }

        if (nombre1.equalsIgnoreCase(nombre2)) {
            lblError.setText("Error de Validación: ¡Los nombres de los directores técnicos no pueden ser iguales!");
            return;
        }

        if (pais1.equalsIgnoreCase(pais2)) {
            lblError.setText("Error de Validación: ¡Ambos entrenadores no pueden dirigir la misma Selección Nacional!");
            return;
        }

        if (bancoComun == null || bancoComun.isEmpty()) {
            lblError.setText("Error de Datos: No hay futbolistas cargados en el mercado.");
            return;
        }

        lblError.setText(" ");

        // Crear los objetos lógicos
        SeleccionNacional sel1 = new SeleccionNacional(pais1);
        SeleccionNacional sel2 = new SeleccionNacional(pais2);
        Jugador j1 = new Jugador(nombre1, sel1);
        Jugador j2 = new Jugador(nombre2, sel2);

        // Iniciar mercado en el Frame Principal
        framePrincipal.iniciarMercado(j1, j2, bancoComun);
    }
}
