package vista;

import java.awt.*;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import modelo.Carta;
import modelo.Magia;
import modelo.Monstruo;

public class VistaDuelo extends JFrame implements IVista {
    private JPanel contenedorPrincipal;
    private CardLayout navegador;

    private JPanel panelInicio;
    private JPanel panelJuego;
    private JPanel panelFinal;

    private JTextField entradaNombre1;
    private JTextField entradaNombre2;
    private JButton botonEmpezar;
    private JButton botonCargarPartida;
    private JLabel logoInicio;
    private JTextArea areaResultados;

    private JLabel etiquetaTurno;
    private JLabel etiquetaFase;
    private JLabel etiquetaInstruccion;
    private JLabel etiquetaJugador1;
    private JLabel etiquetaJugador2;

    // BOTÓN ÚNICO DE FASE
    private JButton botonSiguienteFase;

    private JButton botonAtacar, botonPonerCarta, botonVerMano, botonGuardarPartida;

    private JLabel[] monstruosJ1 = new JLabel[5];
    private JLabel[] magiasYTrampasJ1 = new JLabel[5];
    private JLabel[] monstruosJ2 = new JLabel[5];
    private JLabel[] magiasYTrampasJ2 = new JLabel[5];

    private JDialog dialogoCartas;
    private JTable tablaMano;
    private JTable tablaCementerio;

    public VistaDuelo() {
        setTitle("Duelo de Monstruos - POO");
        setSize(1000, 900);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        navegador = new CardLayout();
        contenedorPrincipal = new JPanel(navegador);

        inicializarPanelInicio();
        inicializarPanelJuego();
        inicializarPanelFinal();
        inicializarDialogoCartas();

        contenedorPrincipal.add(panelInicio, "inicio");
        contenedorPrincipal.add(panelJuego, "juego");
        contenedorPrincipal.add(panelFinal, "final");

        add(contenedorPrincipal);
        navegador.show(contenedorPrincipal, "inicio");
    }

    private void inicializarPanelInicio() {
        panelInicio = new JPanel(null) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                try {
                    Image img = new ImageIcon("src/imagenes/yugilogo-convertido-a-600x500-convertido-a-800x700.jpeg").getImage();
                    g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
                } catch (Exception e) {}
            }
        };

        logoInicio = new JLabel();
        try {
            ImageIcon iconLogo = new ImageIcon("src/imagenes/yugiletras-convertido-a-250x101-removebg-preview.png");
            logoInicio.setIcon(iconLogo);
        } catch (Exception e) {}
        logoInicio.setBounds(375, 50, 250, 101);
        panelInicio.add(logoInicio);

        JLabel lblJ1 = new JLabel("Jugador 1:");
        lblJ1.setBounds(350, 200, 100, 30);
        lblJ1.setForeground(Color.WHITE);
        panelInicio.add(lblJ1);

        entradaNombre1 = new JTextField("Duelista 1");
        entradaNombre1.setBounds(450, 200, 200, 30);
        panelInicio.add(entradaNombre1);

        JLabel lblJ2 = new JLabel("Jugador 2:");
        lblJ2.setBounds(350, 250, 100, 30);
        lblJ2.setForeground(Color.WHITE);
        panelInicio.add(lblJ2);

        entradaNombre2 = new JTextField("Duelista 2");
        entradaNombre2.setBounds(450, 250, 200, 30);
        panelInicio.add(entradaNombre2);

        botonEmpezar = new JButton("Comenzar Duelo");
        botonEmpezar.setBounds(425, 320, 150, 40);
        panelInicio.add(botonEmpezar);

        botonCargarPartida = new JButton("Cargar Partida");
        botonCargarPartida.setBounds(425, 370, 150, 40);
        panelInicio.add(botonCargarPartida);

        areaResultados = new JTextArea();
        areaResultados.setEditable(false);
        areaResultados.setLineWrap(true);
        areaResultados.setWrapStyleWord(true);
        areaResultados.setFont(new Font("Monospaced", Font.PLAIN, 13));
        areaResultados.setBackground(new Color(10, 10, 10, 180));
        areaResultados.setForeground(Color.WHITE);
        JScrollPane scrollResultados = new JScrollPane(areaResultados);
        scrollResultados.setBounds(300, 440, 400, 220);
        panelInicio.add(scrollResultados);
    }

    private void inicializarPanelJuego() {
        panelJuego = new JPanel(null) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                try {
                    Image img = new ImageIcon("src/imagenes/yugilogo-convertido-a-600x500-convertido-a-800x700.jpeg").getImage();
                    g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
                } catch (Exception e) {}
            }
        };

        etiquetaTurno = new JLabel("Turno: 1");
        etiquetaTurno.setBounds(20, 20, 100, 20);
        etiquetaTurno.setForeground(Color.WHITE);
        panelJuego.add(etiquetaTurno);

        etiquetaFase = new JLabel("Fase: ROBO", SwingConstants.CENTER);
        etiquetaFase.setBounds(400, 20, 200, 20);
        etiquetaFase.setForeground(Color.WHITE);
        etiquetaFase.setFont(new Font("Arial", Font.BOLD, 14));
        panelJuego.add(etiquetaFase);

        etiquetaInstruccion = new JLabel("Esperando inicio...", SwingConstants.CENTER);
        etiquetaInstruccion.setBounds(200, 50, 600, 30);
        etiquetaInstruccion.setForeground(Color.ORANGE);
        etiquetaInstruccion.setFont(new Font("Arial", Font.ITALIC, 16));
        panelJuego.add(etiquetaInstruccion);

        etiquetaJugador1 = new JLabel("Jugador 1: 8000 LP");
        etiquetaJugador1.setBounds(20, 100, 300, 20);
        etiquetaJugador1.setForeground(Color.WHITE);
        panelJuego.add(etiquetaJugador1);

        etiquetaJugador2 = new JLabel("Jugador 2: 8000 LP");
        etiquetaJugador2.setBounds(20, 650, 300, 20);
        etiquetaJugador2.setForeground(Color.WHITE);
        panelJuego.add(etiquetaJugador2);

        for (int i = 0; i < 5; i++) {
            magiasYTrampasJ1[i] = crearZona("Vacio", 50 + (i * 120), 130, 100, 70);
            panelJuego.add(magiasYTrampasJ1[i]);
            monstruosJ1[i] = crearZona("Vacio", 50 + (i * 120), 210, 100, 80);
            panelJuego.add(monstruosJ1[i]);

            monstruosJ2[i] = crearZona("Vacio", 50 + (i * 120), 400, 100, 80);
            panelJuego.add(monstruosJ2[i]);
            magiasYTrampasJ2[i] = crearZona("Vacio", 50 + (i * 120), 490, 100, 70);
            panelJuego.add(magiasYTrampasJ2[i]);
        }

        // UNIFICADO: Botón Siguiente Fase
        botonSiguienteFase = new JButton("SIGUIENTE FASE");
        botonSiguienteFase.setBounds(700, 150, 280, 50);
        botonSiguienteFase.setBackground(new Color(50, 50, 150));
        botonSiguienteFase.setForeground(Color.WHITE);
        botonSiguienteFase.setFont(new Font("Arial", Font.BOLD, 16));
        panelJuego.add(botonSiguienteFase);

        botonAtacar = new JButton("ATACAR");
        botonAtacar.setBounds(700, 550, 280, 40);
        botonAtacar.setBackground(new Color(150, 0, 0));
        botonAtacar.setForeground(Color.WHITE);
        panelJuego.add(botonAtacar);

        botonVerMano = new JButton("Ver Mano/Cem");
        botonVerMano.setBounds(700, 600, 150, 40);
        panelJuego.add(botonVerMano);

        botonPonerCarta = new JButton("Poner Carta");
        botonPonerCarta.setBounds(860, 600, 120, 40);
        panelJuego.add(botonPonerCarta);

        botonGuardarPartida = new JButton("Guardar Partida");
        botonGuardarPartida.setBounds(700, 650, 280, 40);
        panelJuego.add(botonGuardarPartida);
    }

    private JLabel crearZona(String texto, int x, int y, int w, int h) {
        JLabel zona = new JLabel(texto, SwingConstants.CENTER);
        zona.setBounds(x, y, w, h);
        zona.setBorder(BorderFactory.createLineBorder(Color.YELLOW));
        zona.setForeground(Color.LIGHT_GRAY);
        zona.setFont(new Font("Arial Narrow", Font.PLAIN, 11));
        return zona;
    }

    private void inicializarDialogoCartas() {
        dialogoCartas = new JDialog(this, "Mano y Cementerio", false);
        dialogoCartas.setSize(400, 600);
        dialogoCartas.setLayout(new GridLayout(2, 1));
        Point loc = this.getLocation();
        dialogoCartas.setLocation(loc.x + 1010, loc.y);

        String[] columnas = {"#", "Nombre", "Tipo", "ATK/DEF"};
        tablaMano = new JTable(new DefaultTableModel(columnas, 0));
        tablaCementerio = new JTable(new DefaultTableModel(columnas, 0));

        dialogoCartas.add(new JScrollPane(tablaMano));
        dialogoCartas.add(new JScrollPane(tablaCementerio));
    }

    private void inicializarPanelFinal() {
        panelFinal = new JPanel(null) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                try {
                    Image img = new ImageIcon("src/imagenes/yugilogo-convertido-a-600x500-convertido-a-800x700.jpeg").getImage();
                    g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
                } catch (Exception e) {}
            }
        };
        JLabel txtFinal = new JLabel("¡DUELO FINALIZADO!", SwingConstants.CENTER);
        txtFinal.setFont(new Font("Serif", Font.BOLD, 40));
        txtFinal.setForeground(Color.RED);
        txtFinal.setBounds(0, 300, 1000, 100);
        panelFinal.add(txtFinal);
    }

    public void setInstruccion(String texto) {
        etiquetaInstruccion.setText(texto);
    }

    public void gestionarBotonesFase(String faseActual) {
        boolean esBatalla = faseActual.equals("Batalla");
        boolean esM1 = faseActual.equals("Main 1");
        boolean esM2 = faseActual.equals("Main 2");

        botonAtacar.setEnabled(esBatalla);
        botonPonerCarta.setEnabled(esM1 || esM2);
    }

    public int pedirIndiceMano(int max) {
        String res = JOptionPane.showInputDialog(this, "Elige índice de carta en mano (0-" + (max-1) + "):");
        try { return Integer.parseInt(res); } catch (Exception e) { return -1; }
    }

    public int pedirIndiceCampo(String mensaje) {
        String res = JOptionPane.showInputDialog(this, mensaje + " (índice 0-4):");
        try { return Integer.parseInt(res); } catch (Exception e) { return -1; }
    }

    public int pedirOpcionPosicion() {
        String[] opciones = {"Ataque", "Defensa"};
        return JOptionPane.showOptionDialog(this, "¿En qué posición?", "Posición",
            JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, opciones, opciones[0]);
    }

    public void actualizarPuntosVida(String n1, int p1, String n2, int p2) {
        etiquetaJugador1.setText(n1 + ": " + p1 + " LP");
        etiquetaJugador2.setText(n2 + ": " + p2 + " LP");
    }

    public void actualizarTurnoYFase(int turno, String fase) {
        etiquetaTurno.setText("Turno: " + turno);
        etiquetaFase.setText("Fase: " + fase.toUpperCase());
        gestionarBotonesFase(fase);
    }

    public void irAJuego() {
        navegador.show(contenedorPrincipal, "juego");
        dialogoCartas.setVisible(true);
    }

    public void irAFinal() {
        dialogoCartas.setVisible(false);
        navegador.show(contenedorPrincipal, "final");
    }

    public void setResumenResultados(String texto) {
        areaResultados.setText(texto);
    }

    public String seleccionarPartida(List<String> partidas) {
        if (partidas == null || partidas.isEmpty()) return null;
        Object seleccion = JOptionPane.showInputDialog(this, "Selecciona una partida guardada:", "Cargar Partida", JOptionPane.QUESTION_MESSAGE, null, partidas.toArray(), partidas.get(0));
        return seleccion == null ? null : seleccion.toString();
    }

    public void actualizarTablero() { repaint(); revalidate(); }

    public void actualizarZonasCampo(modelo.Campo campo) {
        for (int i = 0; i < 5; i++) {
            actualizarLabelZona(monstruosJ1[i], campo.getMonstruosJugador1()[i], "MON");
            actualizarLabelZona(magiasYTrampasJ1[i], campo.getMagicasYTrampasJugador1()[i], "M/T");
            actualizarLabelZona(monstruosJ2[i], campo.getMonstruosJugador2()[i], "MON");
            actualizarLabelZona(magiasYTrampasJ2[i], campo.getMagicasYTrampasJugador2()[i], "M/T");
        }
    }

    private void actualizarLabelZona(JLabel label, Carta carta, String defaultText) {
        if (carta == null) {
            label.setText(defaultText);
            label.setForeground(Color.LIGHT_GRAY);
        } else {
            String color = (carta instanceof Monstruo) ? "yellow" : "cyan";
            String info = "<html><center><b style='color:" + color + "'>" + carta.getNombre() + "</b><br>";
            if (carta instanceof Monstruo) {
                Monstruo m = (Monstruo) carta;
                info += "<small>Nvl:" + m.getNivel() + " | " + m.getAtaque() + "/" + m.getDefensa() + "</small><br>";
                info += "<i style='color:white'>" + (m.estaEnPosicionAtaque() ? "[ATK]" : "[DEF]") + "</i>";
            } else {
                info += "<small>(" + (carta instanceof Magia ? "MAGIA" : "TRAMPA") + ")</small>";
            }
            info += "</center></html>";
            label.setText(info);
        }
    }

    public void mostrarMensaje(String m) { JOptionPane.showMessageDialog(this, m); }

    public void refrescarDialogoCartas(List<Carta> mano, List<Carta> cementerio) {
        DefaultTableModel modelMano = (DefaultTableModel) tablaMano.getModel();
        DefaultTableModel modelCem = (DefaultTableModel) tablaCementerio.getModel();
        modelMano.setRowCount(0);
        modelCem.setRowCount(0);
        int i = 0;
        for (Carta c : mano) {
            String stats = (c instanceof Monstruo) ? ((Monstruo)c).getAtaque() + "/" + ((Monstruo)c).getDefensa() : "-";
            String tipo = (c instanceof Monstruo) ? "MON (" + ((Monstruo)c).getNivel() + ")" : (c instanceof Magia ? "MAG" : "TRA");
            modelMano.addRow(new Object[]{i++, c.getNombre(), tipo, stats});
        }
        i = 0;
        for (Carta c : cementerio) {
            String stats = (c instanceof Monstruo) ? ((Monstruo)c).getAtaque() + "/" + ((Monstruo)c).getDefensa() : "-";
            String tipo = (c instanceof Monstruo) ? "MON" : (c instanceof Magia ? "MAG" : "TRA");
            modelCem.addRow(new Object[]{i++, c.getNombre(), tipo, stats});
        }
    }

    public JButton getBotonEmpezar() { return botonEmpezar; }
    public JButton getBotonCargarPartida() { return botonCargarPartida; }
    public JButton getBotonVerMano() { return botonVerMano; }
    public JButton getBotonPonerCarta() { return botonPonerCarta; }
    public JButton getBotonAtacar() { return botonAtacar; }
    public JButton getBotonSiguienteFase() { return botonSiguienteFase; }
    public JButton getBotonGuardarPartida() { return botonGuardarPartida; }
    public String getNombre1() { return entradaNombre1.getText(); }
    public String getNombre2() { return entradaNombre2.getText(); }

    @Override
    public void vincularControlador(controlador.ControladorDuelo controlador) {
        botonEmpezar.addActionListener(e -> controlador.iniciarDuelo());
        botonSiguienteFase.addActionListener(e -> controlador.avanzarTurno());
        botonAtacar.addActionListener(e -> controlador.ejecutarBatalla());
        botonPonerCarta.addActionListener(e -> controlador.ponerCarta());
        botonVerMano.addActionListener(e -> controlador.actualizarInterfaz());
        botonCargarPartida.addActionListener(e -> controlador.cargarPartida());
        botonGuardarPartida.addActionListener(e -> controlador.guardarPartida());
    }
}
