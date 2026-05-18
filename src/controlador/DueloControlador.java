package controlador;

import modelo.*;
import vista.*;
import java.util.ArrayList;
import java.util.List;

public class DueloControlador {
    private DueloLogica duelo;
    private DueloInterfaz vista;
    private boolean usarTrampas = true;

    public DueloControlador(DueloInterfaz vista) {
        this.vista = vista;
    }

    public void iniciarJuego(String nombreJugador1, String nombreJugador2) {
        duelo = new DueloLogica();
        duelo.setearElementosInicialesDePartida(nombreJugador1, nombreJugador2);
        duelo.setFase("Main Phase 1");
        vista.actualizarControlador(this);
        vista.mostrarPanelJuego();
        vista.actualizarCampo();
    }

    public void manejarColocarMonstruoEnAtaque() {
        String entrada = vista.pedirEntrada("Monstruo", "Ingrese el índice en su mano del monstruo a colocar:");

        if (entrada == null || entrada.trim().isEmpty()) {
            return;
        }

        try {
            byte indiceCartaEnMano = Byte.parseByte(entrada.trim());
            Monstruo m = (Monstruo) duelo.getAtacante().getMano().get(indiceCartaEnMano);

            if (m.getNivel() <= 4) {
                if (duelo.colocarMonstruo(indiceCartaEnMano, (byte) 0, true)) {
                    vista.actualizarCampo();
                    ejecutarTributoTorrencial();
                    vista.actualizarCampo();
                }
            } else {
                String inputSacrificio = vista.pedirEntrada("Sacrificio",
                    "Ingrese el índice en campo del monstruo a sacrificar");
                try {
                    byte indiceCartaEnManoSacrificio = Byte.parseByte(inputSacrificio);
                    duelo.colocarMonstruo(indiceCartaEnMano, indiceCartaEnManoSacrificio, true);
                    vista.actualizarCampo();
                    ejecutarTributoTorrencial();
                    vista.actualizarCampo();
                } catch (NumberFormatException e) {
                    vista.mostrarError("Error", "Índice inválido");
                }
            }
        } catch (NumberFormatException e) {
            vista.mostrarError("Error", "Índice inválido");
        }
    }

    public void manejarColocarMonstruoEnDefensa() {
        String entrada = vista.pedirEntrada("Monstruo", "Ingrese el índice en su mano del monstruo a colocar:");

        if (entrada == null || entrada.trim().isEmpty()) {
            return;
        }

        try {
            byte indiceCartaEnMano = Byte.parseByte(entrada.trim());
            Monstruo m = (Monstruo) duelo.getAtacante().getMano().get(indiceCartaEnMano);

            if (m.getNivel() <= 4) {
                if (duelo.colocarMonstruo(indiceCartaEnMano, (byte) 0, false)) {
                    vista.actualizarCampo();
                    ejecutarTributoTorrencial();
                    vista.actualizarCampo();
                }
            } else {
                String inputSacrificio = vista.pedirEntrada("Sacrificio",
                    "Ingrese el índice en campo del monstruo a sacrificar");
                try {
                    byte indiceCartaEnManoSacrificio = Byte.parseByte(inputSacrificio);
                    duelo.colocarMonstruo(indiceCartaEnMano, indiceCartaEnManoSacrificio, false);
                    vista.actualizarCampo();
                    ejecutarTributoTorrencial();
                    vista.actualizarCampo();
                } catch (NumberFormatException e) {
                    vista.mostrarError("Error", "Índice inválido");
                }
            }
        } catch (NumberFormatException e) {
            vista.mostrarError("Error", "Índice inválido");
        }
    }

    public void manejarColocarTrampa() {
        String entrada = vista.pedirEntrada("Trampa", "Ingrese el índice en su mano de la trampa a colocar:");

        if (entrada == null || entrada.trim().isEmpty()) {
            return;
        }

        try {
            byte indiceCartaEnMano = Byte.parseByte(entrada.trim());
            if (duelo.colocarTrampa(indiceCartaEnMano)) {
                vista.actualizarCampo();
            }
        } catch (NumberFormatException e) {
            vista.mostrarError("Error", "Índice inválido");
        }
    }

    public void manejarColocarMagiaUsada() {
        String entrada = vista.pedirEntrada("Magia", "Ingrese el índice en su mano de la magia a colocar:");

        if (entrada == null || entrada.trim().isEmpty()) {
            return;
        }

        try {
            byte indiceCartaEnMano = Byte.parseByte(entrada.trim());
            if (duelo.colocarMagia(indiceCartaEnMano, true)) {
                vista.actualizarCampo();
            }
        } catch (NumberFormatException e) {
            vista.mostrarError("Error", "Índice inválido");
        }
    }

    public void manejarColocarMagiaSinUsar() {
        String entrada = vista.pedirEntrada("Magia", "Ingrese el índice en su mano de la magia a colocar:");

        if (entrada == null || entrada.trim().isEmpty()) {
            return;
        }

        try {
            byte indiceCartaEnMano = Byte.parseByte(entrada.trim());
            if (duelo.colocarMagia(indiceCartaEnMano, false)) {
                vista.actualizarCampo();
            }
        } catch (NumberFormatException e) {
            vista.mostrarError("Error", "Índice inválido");
        }
    }

    public void manejarCambiarPosicionMonstruo() {
        String entrada = vista.pedirEntrada("Cambiar Posición",
            "Ingrese el índice en campo del monstruo cuya posición desea cambiar:");

        if (entrada == null || entrada.trim().isEmpty()) {
            return;
        }

        try {
            byte indiceCampo = Byte.parseByte(entrada.trim());
            if (duelo.cambiarPosicionDeMonstruo(indiceCampo)) {
                vista.actualizarCampo();
            } else {
                vista.mostrarError("Error", "No se pudo cambiar la posición");
            }
        } catch (NumberFormatException e) {
            vista.mostrarError("Error", "Índice inválido");
        }
    }

    public void manejarCambiarPosicionMagia() {
        String entrada = vista.pedirEntrada("Cambiar Posición",
            "Ingrese el índice en campo de la magia cuya posición desea cambiar:");

        if (entrada == null || entrada.trim().isEmpty()) {
            return;
        }

        try {
            byte indiceCampo = Byte.parseByte(entrada.trim());
            if (duelo.cambiarPosicionDeMagia(indiceCampo)) {
                vista.actualizarCampo();
            } else {
                vista.mostrarError("Error", "No se pudo cambiar la posición");
            }
        } catch (NumberFormatException e) {
            vista.mostrarError("Error", "Índice inválido");
        }
    }

    public void manejarAtaque() {
        String entradaAtacante = vista.pedirEntrada("Ataque", "Ingrese el índice del monstruo atacante:");

        if (entradaAtacante == null || entradaAtacante.trim().isEmpty()) {
            return;
        }

        try {
            byte indiceAtacante = Byte.parseByte(entradaAtacante.trim());
            String entradaDefensor = vista.pedirEntrada("Ataque",
                "Ingrese el índice del objetivo (monstruo o defensa directa):");

            if (entradaDefensor == null || entradaDefensor.trim().isEmpty()) {
                return;
            }

            byte indiceDefensor = Byte.parseByte(entradaDefensor.trim());
            if (duelo.atacar(indiceAtacante, indiceDefensor)) {
                vista.actualizarCampo();
                ejecutarArmaduraDeSakuretsu(indiceDefensor);
                ejecutarCilindroMagico();
                vista.actualizarCampo();
            }
        } catch (NumberFormatException e) {
            vista.mostrarError("Error", "Índice inválido");
        }
    }

    public void manejarSiguienteFase() {
        if (duelo.muchasCartasEnMano()) {
            String entrada = vista.pedirEntrada("Descartar Cartas",
                "Tiene más de 6 cartas. Ingrese los índices a descartar separados por comas:");

            if (entrada != null && !entrada.trim().isEmpty()) {
                try {
                    String[] indices = entrada.split(",");
                    for (String indice : indices) {
                        duelo.descartarCarta(Byte.parseByte(indice.trim()));
                    }
                } catch (NumberFormatException e) {
                    vista.mostrarError("Error", "Formato inválido");
                    return;
                }
            }
        }

        duelo.siguienteFase();

        if (duelo.hayUnGanador()) {
            Jugador ganador = duelo.getGanador();
            vista.mostrarGameOver(ganador);
        } else {
            vista.actualizarCampo();
        }
    }

    public void manejarSaltarFase() {
        duelo.saltarFase();
        vista.actualizarCampo();
    }

    public void manejarTerminarTurno() {
        duelo.terminarTurno();
        vista.actualizarCampo();
    }

    private void ejecutarCilindroMagico() {
        if (usarTrampas) {
            List<Monstruo> listaVacia = new ArrayList<>();
            for (int i = 0; i < 5; i++) {
                Carta c = duelo.getMagicasYTrampasEnCampoDefensor()[i];
                if (c instanceof Trampa && c.getNombre().equals("Cilindro Mágico")) {
                    Trampa t = (Trampa) c;
                    if (preguntarUsarTrampa(t.getNombre())) {
                        if (t.getTurnosActiva() < 1) {
                            String monstruoANegar = vista.pedirEntrada("Cilindro Mágico",
                                "Escriba el nombre exacto del monstruo cuyo ataque quiere negar");
                            t.jugar(duelo.getCampo(), duelo.getTurno(), (byte) i, (byte) 0,
                                monstruoANegar, "", "", listaVacia);
                        } else if (t.getTurnosActiva() > 0) {
                            t.jugar(duelo.getCampo(), duelo.getTurno(), (byte) i, (byte) 0, "", "", "", listaVacia);
                        }
                    }
                }
            }
        }
    }

    private void ejecutarArmaduraDeSakuretsu(byte monstruoAEliminar) {
        if (usarTrampas) {
            List<Monstruo> listaVacia = new ArrayList<>();
            for (int i = 0; i < 5; i++) {
                Carta c = duelo.getMagicasYTrampasEnCampoDefensor()[i];
                if (c instanceof Trampa && c.getNombre().equals("Armadura de Sakuretsu")) {
                    Trampa t = (Trampa) c;
                    if (preguntarUsarTrampa(t.getNombre())) {
                        t.jugar(duelo.getCampo(), duelo.getTurno(), (byte) i, monstruoAEliminar, "", "", "", listaVacia);
                    }
                }
            }
        }
    }

    private void ejecutarFuerzaDeEspejo() {
        if (usarTrampas) {
            List<Monstruo> listaVacia = new ArrayList<>();
            for (int i = 0; i < 5; i++) {
                Carta c = duelo.getMagicasYTrampasEnCampoDefensor()[i];
                if (c instanceof Trampa && c.getNombre().equals("Fuerza de Espejo")) {
                    Trampa t = (Trampa) c;
                    if (preguntarUsarTrampa(t.getNombre())) {
                        t.jugar(duelo.getCampo(), duelo.getTurno(), (byte) i, (byte) 0, "", "", "", listaVacia);
                    }
                }
            }
        }
    }

    private void ejecutarTributoTorrencial() {
        if (usarTrampas) {
            List<Monstruo> listaVacia = new ArrayList<>();
            for (int i = 0; i < 5; i++) {
                Carta c = duelo.getMagicasYTrampasEnCampoDefensor()[i];
                if (c instanceof Trampa && c.getNombre().equals("Tributo Torrencial")) {
                    Trampa t = (Trampa) c;
                    if (preguntarUsarTrampa(t.getNombre())) {
                        t.jugar(duelo.getCampo(), duelo.getTurno(), (byte) i, (byte) 0, "", "", "", listaVacia);
                    }
                }
            }
        }
    }

    public void ejecutarTrampasDeInvocacion(String fase) {
        if (usarTrampas) {
            List<Monstruo> listaVacia = new ArrayList<>();
            for (int i = 0; i < 5; i++) {
                Carta c = duelo.getMagicasYTrampasEnCampoDefensor()[i];
                if (c instanceof Trampa) {
                    Trampa t = (Trampa) c;
                    switch (t.getTipoHabilidadEspecialTrampa()) {
                        case LLAMADA_DE_LOS_CONDENADOS:
                            if (!t.isActivada()) {
                                if (preguntarUsarTrampa(t.getNombre())) {
                                    String monstruoARevivir = vista.pedirEntrada("Llamada de los Condenados",
                                        "Escriba el nombre exacto del monstruo de su cementerio que quiere revivir");
                                    t.jugar(duelo.getCampo(), duelo.getTurno(), (byte) i, (byte) 0,
                                        monstruoARevivir, "", "", listaVacia);
                                }
                            }
                            break;
                        case SOMBREROS_MAGICOS:
                            if (!t.isActivada()) {
                                if (preguntarUsarTrampa(t.getNombre())) {
                                    String nombreMonstruoAOcultar = vista.pedirEntrada("Sombreros Mágicos",
                                        "Escriba el nombre exacto del monstruo que quiere ocultar");
                                    String nombreMagica1 = vista.pedirEntrada("Sombreros Mágicos",
                                        "Escriba el nombre exacto de la carta mágica 1 que servirá de sombrero");
                                    String nombreMagica2 = vista.pedirEntrada("Sombreros Mágicos",
                                        "Escriba el nombre exacto de la carta mágica 2 que servirá de sombrero");
                                    t.jugar(duelo.getCampo(), duelo.getTurno(), (byte) i, (byte) 0,
                                        nombreMonstruoAOcultar, nombreMagica1, nombreMagica2, listaVacia);
                                }
                            }
                            break;
                        case WABOKU:
                            if (!t.isActivada()) {
                                if (preguntarUsarTrampa(t.getNombre())) {
                                    t.jugar(duelo.getCampo(), duelo.getTurno(), (byte) i, (byte) 0, "", "", "", listaVacia);
                                }
                            }
                            break;
                        case MURO_DE_ESPEJO:
                            if (!t.isActivada()) {
                                if (preguntarUsarTrampa(t.getNombre())) {
                                    t.jugar(duelo.getCampo(), duelo.getTurno(), (byte) i, (byte) 0, "", "", "", listaVacia);
                                }
                            } else if (t.getTurnosActiva() > 0) {
                                byte opcion = vista.pedirConfirmacion("Muro de Espejo",
                                    "Defensor, quiere mantener el efecto de muro de espejo por un turno más?") ? (byte) 1 : (byte) 2;
                                t.jugar(duelo.getCampo(), duelo.getTurno(), (byte) i, opcion, "", "", "", listaVacia);
                            }
                            break;
                        case REPRESION:
                            if (!t.isActivada() && t.getTurnosActiva() < 1) {
                                if (preguntarUsarTrampa(t.getNombre())) {
                                    String monstruoARobar = vista.pedirEntrada("Represión",
                                        "Escriba el nombre exacto del monstruo que robará por un turno");
                                    t.setMonstruoARobarPorUnTurno(monstruoARobar);
                                    t.jugar(duelo.getCampo(), duelo.getTurno(), (byte) i, (byte) 0,
                                        monstruoARobar, "", "", listaVacia);
                                }
                            } else if (t.getTurnosActiva() > 0) {
                                t.jugar(duelo.getCampo(), duelo.getTurno(), (byte) i, (byte) 0, "", "", "", listaVacia);
                            }
                            break;
                        case DRENAJE_DE_HABILIDAD:
                            if (!t.isActivada()) {
                                t.jugar(duelo.getCampo(), duelo.getTurno(), (byte) i, (byte) 0, "", "", "", listaVacia);
                            }
                            break;
                        case null, default:
                            break;
                    }
                }
            }
        }
    }

    private boolean preguntarUsarTrampa(String nombreTrampa) {
        return vista.pedirConfirmacion("Usar Trampa",
            "Quiere usar la trampa: " + nombreTrampa + "?");
    }

    public DueloLogica getDuelo() {
        return duelo;
    }

    public void setUsarTrampas(boolean usar) {
        this.usarTrampas = usar;
    }
}
