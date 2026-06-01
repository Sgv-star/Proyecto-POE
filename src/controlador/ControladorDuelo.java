package controlador;

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
        iniciarNuevaPartida(vista.obtenerNombre1(), vista.obtenerNombre2());
    }

    private void iniciarNuevaPartida(String n1, String n2) {
        Mazo mazo = new Mazo();
        campo.establecerJugador1(new Jugador(n1, mazo));
        campo.establecerJugador2(new Jugador(n2, mazo));
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
        if (fase.equals("Main 1")) {
            jugadorActivo = 2;
            robarCarta(2);
            if (dueloTerminado) return;
            fase = "Main 2";
        } else if (fase.equals("Main 2")) {
            fase = "Batalla";
        } else if (fase.equals("Batalla")) {
            fase = "Main 1";
            turno++;
            jugadorActivo = 1;
            robarCarta(1);
            if (dueloTerminado) return;
        }
        actualizarInterfaz();
    }

    private void robarCarta(int numJugador) {
        Jugador j = (numJugador == 1) ? campo.obtenerJugador1() : campo.obtenerJugador2();
        if (!j.obtenerMazo().isEmpty()) {
            Carta robada = j.obtenerMazo().pop();
            j.obtenerMano().add(robada);
            vista.mostrarMensaje(j.obtenerNombre() + " ha robado la carta: " + robada.obtenerNombre());
        } else {
            vista.mostrarMensaje(j.obtenerNombre() + " no tiene mas cartas en su mazo.");
            Jugador ganador = (numJugador == 1) ? campo.obtenerJugador2() : campo.obtenerJugador1();
            finalizarDuelo(ganador, j);
        }
    }

    public void ponerCarta() {
        if (dueloTerminado) return;
        Jugador actual = (jugadorActivo == 1) ? campo.obtenerJugador1() : campo.obtenerJugador2();
        if (actual.obtenerMano().isEmpty()) {
            vista.mostrarMensaje("Mano vacia.");
            return;
        }

        int idx = vista.pedirIndiceMano(actual.obtenerMano().size());
        if (idx < 0 || idx >= actual.obtenerMano().size()) return;

        Carta carta = actual.obtenerMano().get(idx);

        if (carta instanceof Monstruo) {
            Monstruo m = (Monstruo) carta;
            int req = (m.obtenerNivel() >= 7) ? 2 : (m.obtenerNivel() >= 5 ? 1 : 0);

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

        if (carta instanceof Monstruo) {
            int modo = vista.pedirOpcionPosicion();
            if (modo == -1) return;
            ((Monstruo) carta).establecerEnPosicionAtaque(modo == 0);
        }

        if (campo.colocarCarta(carta, (byte)(jugadorActivo - 1), posVacia)) {
            actual.obtenerMano().remove(idx);
            vista.mostrarMensaje(carta.obtenerNombre() + " invocado.");
        }
        actualizarInterfaz();
    }

    private boolean realizarSacrificios(int cantidad) {
        Monstruo[] campoM = (jugadorActivo == 1) ? campo.obtenerMonstruosJugador1() : campo.obtenerMonstruosJugador2();
        List<Carta> cem = (jugadorActivo == 1) ? campo.obtenerCementerioJugador1() : campo.obtenerCementerioJugador2();

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
            campo.removerDelCampo(campoM[idx].obtenerNombre(), jugadorActivo);
            campoM[idx] = null;
        }
        return true;
    }

    private byte buscarEspacioVacio(Carta c) {
        Object[] zones = (c instanceof Monstruo) ?
            ((jugadorActivo == 1) ? campo.obtenerMonstruosJugador1() : campo.obtenerMonstruosJugador2()) :
            ((jugadorActivo == 1) ? campo.obtenerMagicasYTrampasJugador1() : campo.obtenerMagicasYTrampasJugador2());

        for (byte i = 0; i < 5; i++) if (zones[i] == null) return i;
        return -1;
    }

    public void ejecutarBatalla() {
        if (dueloTerminado) return;
        int idxAtk = vista.pedirIndiceCampo("Tu monstruo atacante");
        int idxDef = vista.pedirIndiceCampo("Monstruo OBJETIVO (Oponente)");
        if (idxAtk < 0 || idxDef < 0) return;

        Jugador atk = (jugadorActivo == 1) ? campo.obtenerJugador1() : campo.obtenerJugador2();
        Jugador def = (jugadorActivo == 1) ? campo.obtenerJugador2() : campo.obtenerJugador1();
        Monstruo mAtk = (jugadorActivo == 1) ? campo.obtenerMonstruosJugador1()[idxAtk] : campo.obtenerMonstruosJugador2()[idxAtk];
        Monstruo mDef = (jugadorActivo == 1) ? campo.obtenerMonstruosJugador2()[idxDef] : campo.obtenerMonstruosJugador1()[idxDef];

        if (mAtk == null || !mAtk.estaEnPosicionAtaque()) {
            vista.mostrarMensaje("Elegir un atacante valido en posicion de ataque.");
            return;
        }

        if (mDef == null) {
            def.establecerPuntosVida((short)(def.obtenerPuntosVida() - mAtk.obtenerAtaque()));
            vista.mostrarMensaje("Ataque directo.");
        } else {
            if (mDef.estaEnPosicionAtaque()) {
                if (mAtk.obtenerAtaque() > mDef.obtenerAtaque()) {
                    def.establecerPuntosVida((short)(def.obtenerPuntosVida() - (mAtk.obtenerAtaque() - mDef.obtenerAtaque())));
                    removerDelCampo(mDef, (jugadorActivo == 1 ? 2 : 1));
                } else if (mAtk.obtenerAtaque() < mDef.obtenerAtaque()) {
                    atk.establecerPuntosVida((short)(atk.obtenerPuntosVida() - (mDef.obtenerAtaque() - mAtk.obtenerAtaque())));
                    removerDelCampo(mAtk, jugadorActivo);
                } else {
                    removerDelCampo(mAtk, jugadorActivo);
                    removerDelCampo(mDef, (jugadorActivo == 1 ? 2 : 1));
                }
            } else {
                if (mAtk.obtenerAtaque() > mDef.obtenerDefensa()) {
                    removerDelCampo(mDef, (jugadorActivo == 1 ? 2 : 1));
                } else if (mAtk.obtenerAtaque() < mDef.obtenerDefensa()) {
                    atk.establecerPuntosVida((short)(atk.obtenerPuntosVida() - (mDef.obtenerDefensa() - mAtk.obtenerAtaque())));
                }
            }
        }
        actualizarInterfaz();
        verificarFinDuelo();
    }

    private void removerDelCampo(Monstruo m, int numJugador) {
        Monstruo[] c = (numJugador == 1) ? campo.obtenerMonstruosJugador1() : campo.obtenerMonstruosJugador2();
        List<Carta> cem = (numJugador == 1) ? campo.obtenerCementerioJugador1() : campo.obtenerCementerioJugador2();
        for (int i = 0; i < 5; i++) if (c[i] == m) { c[i] = null; campo.removerDelCampo(m.obtenerNombre(), numJugador); break; }
        cem.add(m);
    }

    public void guardarPartida() {
        if (campo.obtenerJugador1() == null || campo.obtenerJugador2() == null) {
            vista.mostrarMensaje("No hay una partida para guardar.");
            return;
        }
        try {
            persistenciaPartida.guardar(campo, turno, fase, jugadorActivo);
            vista.mostrarMensaje("Partida guardada correctamente como: " + campo.obtenerJugador1().obtenerNombre() + " vs " + campo.obtenerJugador2().obtenerNombre());
        } catch (Exception e) {
            vista.mostrarMensaje("No se pudo guardar la partida: " + e.getMessage());
        }
    }

    public void cargarPartida() {
        try {
            List<String> partidas = persistenciaPartida.obtenerNombresPartidas();
            if (partidas.isEmpty()) {
                vista.mostrarMensaje("No hay partidas guardadas.");
                return;
            }
            String seleccion = partidas.get(0);
            if (vista instanceof VistaDuelo) {
                seleccion = ((VistaDuelo) vista).seleccionarPartida(partidas);
            }
            if (seleccion == null || seleccion.trim().isEmpty()) {
                return;
            }
            cargarPartida(seleccion);
        } catch (Exception e) {
            vista.mostrarMensaje("No se pudo cargar la partida: " + e.getMessage());
        }
    }

    public void cargarPartida(String seleccion) {
        try {
            PersistenciaPartida.EstadoPartida estado = persistenciaPartida.cargar(campo, seleccion);
            turno = estado.obtenerTurno();
            fase = estado.obtenerFase();
            jugadorActivo = estado.obtenerJugadorActivo();
            dueloTerminado = false;
            vista.irAJuego();
            actualizarInterfaz();
            vista.mostrarMensaje("Partida cargada correctamente: " + seleccion);
        } catch (Exception e) {
            vista.mostrarMensaje("No se pudo cargar la partida: " + e.getMessage());
        }
    }

    public List<String> obtenerNombresPartidasGuardadas() {
        try {
            return persistenciaPartida.obtenerNombresPartidas();
        } catch (Exception e) {
            vista.mostrarMensaje("No se pudieron leer las partidas guardadas: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public String obtenerResumenResultados() {
        return registroResultados.obtenerResumen();
    }

    private void verificarFinDuelo() {
        Jugador j1 = campo.obtenerJugador1();
        Jugador j2 = campo.obtenerJugador2();
        if (j1.obtenerPuntosVida() <= 0) {
            finalizarDuelo(j2, j1);
        } else if (j2.obtenerPuntosVida() <= 0) {
            finalizarDuelo(j1, j2);
        }
    }

    private void finalizarDuelo(Jugador ganador, Jugador perdedor) {
        if (dueloTerminado) return;
        dueloTerminado = true;
        try {
            registroResultados.guardarResultado(ganador, perdedor, turno);
            actualizarResumenResultados();
        } catch (Exception e) {
            vista.mostrarMensaje("No se pudo guardar el resultado: " + e.getMessage());
        }
        vista.mostrarMensaje("Ganador: " + ganador.obtenerNombre());
        if (vista instanceof VistaDuelo) {
            ((VistaDuelo) vista).irAFinal();
        }
    }

    public void actualizarInterfaz() {
        Jugador j1 = campo.obtenerJugador1();
        Jugador j2 = campo.obtenerJugador2();
        Jugador actual = (jugadorActivo == 1) ? j1 : j2;
        List<Carta> cem = (jugadorActivo == 1) ? campo.obtenerCementerioJugador1() : campo.obtenerCementerioJugador2();

        vista.actualizarTurnoYFase(turno, fase);
        vista.actualizarPuntosVida(j1.obtenerNombre(), j1.obtenerPuntosVida(), j2.obtenerNombre(), j2.obtenerPuntosVida());
        vista.actualizarZonasCampo(campo);
        vista.refrescarDialogoCartas(actual.obtenerMano(), cem);

        String instruccion = "TURNO DE " + actual.obtenerNombre().toUpperCase();
        if (fase.equals("Main 1") || fase.equals("Main 2")) instruccion += " | Pon una carta.";
        else if (fase.equals("Batalla")) instruccion += " | Presiona ATACAR.";
        vista.establecerInstruccion(instruccion);

        vista.actualizarTablero();
    }

    private void actualizarResumenResultados() {
        if (vista instanceof VistaDuelo) {
            ((VistaDuelo) vista).establecerResumenResultados(registroResultados.obtenerResumen());
        }
    }

    private void limpiarCampo() {
        for (int i = 0; i < 5; i++) {
            campo.obtenerMonstruosJugador1()[i] = null;
            campo.obtenerMonstruosJugador2()[i] = null;
            campo.obtenerMagicasYTrampasJugador1()[i] = null;
            campo.obtenerMagicasYTrampasJugador2()[i] = null;
        }
        campo.obtenerCementerioJugador1().clear();
        campo.obtenerCementerioJugador2().clear();
        campo.limpiarControlCampo();
    }
}
