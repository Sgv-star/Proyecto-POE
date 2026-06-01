package controlador;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import modelo.*;
import vista.IVista;
import vista.VistaDuelo;

public class ControladorDuelo {
    private Campo campo;
    private IVista vista;
    private int turno;
    private String fase;
    private int jugadorActivo;
    private PersistenciaPartida persistenciaPartida;
    private RegistroResultados registroResultados;
    private boolean dueloTerminado;

    public ControladorDuelo(Campo campo, IVista vista) {
        this.campo = campo;
        this.vista = vista;
        this.turno = 1;
        this.fase = "Main 1";
        this.jugadorActivo = 1;
        this.persistenciaPartida = new PersistenciaPartida();
        this.registroResultados = new RegistroResultados();
        this.dueloTerminado = false;
        actualizarResumenResultados();
    }

    public void iniciarDuelo() {
        iniciarNuevaPartida(vista.getNombre1(), vista.getNombre2());
    }

    private void iniciarNuevaPartida(String n1, String n2) {
        Mazo mazo = new Mazo();
        campo.setJugador1(new Jugador(n1, mazo));
        campo.setJugador2(new Jugador(n2, mazo));
        limpiarCampo();
        turno = 1;
        fase = "Main 1";
        jugadorActivo = 1;
        dueloTerminado = false;
        vista.irAJuego();
        actualizarInterfaz();
    }

    public void avanzarTurno() {
        if (dueloTerminado) return;
        switch (fase) {
            case "Main 1" -> {
                if(turno == 1 && jugadorActivo == 1){
                    fase = "Main 2";
                }
                else{
                    fase = "Batalla";
                }
            }
            case "Batalla" -> {
                fase = "Main 2";
            }
            case "Main 2" -> {
                fase = "Main 1";
                jugadorActivo = (jugadorActivo == 1) ? 2 : 1;
                if (jugadorActivo == 1) turno++;
                robarCarta(jugadorActivo);
                if (dueloTerminado) return;
            }
            default -> {
            }
        }
        actualizarInterfaz();
    }

    private void robarCarta(int numJugador) {
        Jugador j = (numJugador == 1) ? campo.getJugador1() : campo.getJugador2();
        if (!j.getMazo().isEmpty()) {
            Carta robada = j.getMazo().pop();
            j.getMano().add(robada);
            vista.mostrarMensaje(j.getNombre() + " ha robado la carta: " + robada.getNombre());
        }
        else{
            vista.mostrarMensaje(j.getNombre() + " no tiene mas cartas en su mazo.");
            Jugador ganador = (numJugador == 1) ? campo.getJugador2() : campo.getJugador1();
            finalizarDuelo(ganador, j);
        }
    }

    public void ponerCarta() {
        if (dueloTerminado) return;
        Jugador actual = (jugadorActivo == 1) ? campo.getJugador1() : campo.getJugador2();
        if (actual.getMano().isEmpty()) {
            vista.mostrarMensaje("Mano vacia.");
            return;
        }

        int idx = vista.pedirIndiceMano(actual.getMano().size());
        if (idx < 0 || idx >= actual.getMano().size()) return;

        Carta carta = actual.getMano().get(idx);

        if (carta instanceof Monstruo m) {
            int req = (m.getNivel() >= 7) ? 2 : (m.getNivel() >= 5 ? 1 : 0);

            if (req > 0) {
                if (!realizarSacrificios(req)) {
                    vista.mostrarMensaje("No tienes suficientes monstruos para sacrificar.");
                    return;
                }
            }
        }

        byte posVacia = buscarEspacioVacio(carta);
        if (posVacia == -1) {
            vista.mostrarMensaje("No hay espacios libres.");
            return;
        }

        if (carta instanceof Monstruo monstruo) {
            int modo = vista.pedirOpcionPosicion();
            if (modo == -1) return;
            monstruo.setEnPosicionAtaque(modo == 0);
        }

        if (campo.colocarCarta(carta, (byte)(jugadorActivo - 1), posVacia)) {
            actual.getMano().remove(idx);
            vista.mostrarMensaje(carta.getNombre() + " invocado.");
        }
        actualizarInterfaz();
    }

    private boolean realizarSacrificios(int cantidad) {
        Monstruo[] campoM = (jugadorActivo == 1) ? campo.getMonstruosJugador1() : campo.getMonstruosJugador2();
        List<Carta> cem = (jugadorActivo == 1) ? campo.getCementerioJugador1() : campo.getCementerioJugador2();

        List<Integer> ocupados = new ArrayList<>();
        for (int i = 0; i < 5; i++) if (campoM[i] != null) ocupados.add(i);

        if (ocupados.size() < cantidad) return false;

        for (int i = 0; i < cantidad; i++) {
            int idx = vista.pedirIndiceCampo("Elige monstruo para SACRIFICAR (" + (i + 1) + "/" + cantidad + ")");
            if (idx < 0 || idx > 4 || campoM[idx] == null) {
                vista.mostrarMensaje("Seleccion invalida. Sacrificio cancelado.");
                return false;
            }
            cem.add(campoM[idx]);
            campo.removerDelCampo(campoM[idx].getNombre(), jugadorActivo);
            campoM[idx] = null;
        }
        return true;
    }

    private byte buscarEspacioVacio(Carta c) {
        Object[] zones = (c instanceof Monstruo) ?
            ((jugadorActivo == 1) ? campo.getMonstruosJugador1() : campo.getMonstruosJugador2()) :
            ((jugadorActivo == 1) ? campo.getMagicasYTrampasJugador1() : campo.getMagicasYTrampasJugador2());

        for (byte i = 0; i < 5; i++) if (zones[i] == null) return i;
        return -1;
    }

    public void ejecutarBatalla() {
        if (dueloTerminado) return;
        int idxAtk = vista.pedirIndiceCampo("Tu monstruo atacante");
        int idxDef = vista.pedirIndiceCampo("Monstruo OBJETIVO (Oponente)");
        if (idxAtk < 0 || idxDef < 0) return;

        Jugador atk = (jugadorActivo == 1) ? campo.getJugador1() : campo.getJugador2();
        Jugador def = (jugadorActivo == 1) ? campo.getJugador2() : campo.getJugador1();
        Monstruo mAtk = (jugadorActivo == 1) ? campo.getMonstruosJugador1()[idxAtk] : campo.getMonstruosJugador2()[idxAtk];
        Monstruo mDef = (jugadorActivo == 1) ? campo.getMonstruosJugador2()[idxDef] : campo.getMonstruosJugador1()[idxDef];

        if (mAtk == null || !mAtk.estaEnPosicionAtaque()) {
            vista.mostrarMensaje("Elegir un atacante valido en posicion de ataque.");
            return;
        }

        if (mDef == null) {
            def.setPuntosVida((short)(def.getPuntosVida() - mAtk.getAtaque()));
            vista.mostrarMensaje("Ataque directo.");
        } else {
            if (mDef.estaEnPosicionAtaque()) {
                if (mAtk.getAtaque() > mDef.getAtaque()) {
                    def.setPuntosVida((short)(def.getPuntosVida() - (mAtk.getAtaque() - mDef.getAtaque())));
                    removerDelCampo(mDef, (jugadorActivo == 1 ? 2 : 1));
                } else if (mAtk.getAtaque() < mDef.getAtaque()) {
                    atk.setPuntosVida((short)(atk.getPuntosVida() - (mDef.getAtaque() - mAtk.getAtaque())));
                    removerDelCampo(mAtk, jugadorActivo);
                } else {
                    removerDelCampo(mAtk, jugadorActivo);
                    removerDelCampo(mDef, (jugadorActivo == 1 ? 2 : 1));
                }
            } else {
                if (mAtk.getAtaque() > mDef.getDefensa()) {
                    removerDelCampo(mDef, (jugadorActivo == 1 ? 2 : 1));
                } else if (mAtk.getAtaque() < mDef.getDefensa()) {
                    atk.setPuntosVida((short)(atk.getPuntosVida() - (mDef.getDefensa() - mAtk.getAtaque())));
                }
            }
        }
        actualizarInterfaz();
        verificarFinDuelo();
    }

    private void removerDelCampo(Monstruo m, int numJugador) {
        Monstruo[] c = (numJugador == 1) ? campo.getMonstruosJugador1() : campo.getMonstruosJugador2();
        List<Carta> cem = (numJugador == 1) ? campo.getCementerioJugador1() : campo.getCementerioJugador2();
        for (int i = 0; i < 5; i++) if (c[i] == m) { c[i] = null; campo.removerDelCampo(m.getNombre(), numJugador); break; }
        cem.add(m);
    }

    public void guardarPartida() {
        if (campo.getJugador1() == null || campo.getJugador2() == null) {
            vista.mostrarMensaje("No hay una partida para guardar.");
            return;
        }
        try {
            persistenciaPartida.guardar(campo, turno, fase, jugadorActivo);
            vista.mostrarMensaje("Partida guardada correctamente como: " + campo.getJugador1().getNombre() + " vs " + campo.getJugador2().getNombre());
        } catch (IOException e) {
            vista.mostrarMensaje("No se pudo guardar la partida: " + e.getMessage());
        }
    }

    public void cargarPartida() {
        try {
            List<String> partidas = persistenciaPartida.getNombresPartidas();
            if (partidas.isEmpty()) {
                vista.mostrarMensaje("No hay partidas guardadas.");
                return;
            }
            String seleccion = partidas.get(0);
            if (vista instanceof VistaDuelo vistaDuelo) {
                seleccion = vistaDuelo.seleccionarPartida(partidas);
            }
            if (seleccion == null || seleccion.trim().isEmpty()) {
                return;
            }
            cargarPartida(seleccion);
        } catch (IOException e) {
            vista.mostrarMensaje("No se pudo cargar la partida: " + e.getMessage());
        }
    }

    public void cargarPartida(String seleccion) {
        try {
            PersistenciaPartida.EstadoPartida estado = persistenciaPartida.cargar(campo, seleccion);
            turno = estado.getTurno();
            fase = estado.getFase();
            jugadorActivo = estado.getJugadorActivo();
            dueloTerminado = false;
            vista.irAJuego();
            actualizarInterfaz();
            vista.mostrarMensaje("Partida cargada correctamente: " + seleccion);
        } catch (IOException e) {
            vista.mostrarMensaje("No se pudo cargar la partida: " + e.getMessage());
        }
    }

    public List<String> getNombresPartidasGuardadas() {
        try {
            return persistenciaPartida.getNombresPartidas();
        } catch (IOException e) {
            vista.mostrarMensaje("No se pudieron leer las partidas guardadas: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public String getResumenResultados() {
        return registroResultados.getResumen();
    }

    private void verificarFinDuelo() {
        Jugador j1 = campo.getJugador1();
        Jugador j2 = campo.getJugador2();
        if (j1.getPuntosVida() <= 0) {
            finalizarDuelo(j2, j1);
        } else if (j2.getPuntosVida() <= 0) {
            finalizarDuelo(j1, j2);
        }
    }

    private void finalizarDuelo(Jugador ganador, Jugador perdedor) {
        if (dueloTerminado) return;
        dueloTerminado = true;
        try {
            registroResultados.guardarResultado(ganador, perdedor, turno);
            actualizarResumenResultados();
        } catch (IOException e) {
            vista.mostrarMensaje("No se pudo guardar el resultado: " + e.getMessage());
        }
        vista.mostrarMensaje("Ganador: " + ganador.getNombre());
        if (vista instanceof VistaDuelo vistaDuelo) {
            vistaDuelo.irAFinal();
        }
    }

    public void actualizarInterfaz() {
        Jugador j1 = campo.getJugador1();
        Jugador j2 = campo.getJugador2();
        Jugador actual = (jugadorActivo == 1) ? j1 : j2;
        List<Carta> cem = (jugadorActivo == 1) ? campo.getCementerioJugador1() : campo.getCementerioJugador2();

        vista.actualizarTurnoYFase(turno, fase);
        vista.actualizarPuntosVida(j1.getNombre(), j1.getPuntosVida(), j2.getNombre(), j2.getPuntosVida());
        vista.actualizarZonasCampo(campo);
        vista.refrescarDialogoCartas(actual.getMano(), cem);

        String instruccion = "TURNO DE " + actual.getNombre().toUpperCase();
        if (fase.equals("Main 1") || fase.equals("Main 2")) instruccion += " | Pon una carta.";
        else if (fase.equals("Batalla")) instruccion += " | Presiona ATACAR.";
        vista.setInstruccion(instruccion);

        vista.actualizarTablero();
    }

    private void actualizarResumenResultados() {
        if (vista instanceof VistaDuelo vistaDuelo) {
            vistaDuelo.setResumenResultados(registroResultados.getResumen());
        }
    }

    private void limpiarCampo() {
        for (int i = 0; i < 5; i++) {
            campo.getMonstruosJugador1()[i] = null;
            campo.getMonstruosJugador2()[i] = null;
            campo.getMagicasYTrampasJugador1()[i] = null;
            campo.getMagicasYTrampasJugador2()[i] = null;
        }
        campo.getCementerioJugador1().clear();
        campo.getCementerioJugador2().clear();
        campo.limpiarControlCampo();
    }
}
