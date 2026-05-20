package controlador;

import modelo.*;
import vista.VistaDuelo;
import java.util.List;
import java.util.ArrayList;

public class ControladorDuelo {
    private Campo campo;
    private VistaDuelo vista;
    private int turno;
    private String fase;
    private int jugadorActivo;

    public ControladorDuelo(Campo campo, VistaDuelo vista) {
        this.campo = campo;
        this.vista = vista;
        this.turno = 1;
        this.fase = "Main 1";
        this.jugadorActivo = 1;

        vincularEventos();
    }

    private void vincularEventos() {
        vista.obtenerBotonEmpezar().addActionListener(e -> {
            String n1 = vista.obtenerNombre1();
            String n2 = vista.obtenerNombre2();
            Mazo mazo = new Mazo();
            campo.establecerJugador1(new Jugador(n1, mazo));
            campo.establecerJugador2(new Jugador(n2, mazo));
            vista.irAJuego();
            actualizarInterfaz();
        });

        // BOTÓN ÚNICO: SIGUIENTE FASE
        vista.obtenerBotonSiguienteFase().addActionListener(e -> avanzarTurno());

        vista.obtenerBotonAtacar().addActionListener(e -> ejecutarBatalla());
        vista.obtenerBotonPonerCarta().addActionListener(e -> ponerCarta());
        vista.obtenerBotonVerMano().addActionListener(e -> actualizarInterfaz());
    }

    private void avanzarTurno() {
        if (fase.equals("Main 1")) {
            // Jugador 1 termina su turno -> Jugador 2 roba -> Main 2
            jugadorActivo = 2;
            robarCarta(2);
            fase = "Main 2";
        } else if (fase.equals("Main 2")) {
            // Jugador 2 termina su turno -> Fase de Batalla (Ataca el activo)
            fase = "Batalla";
        } else if (fase.equals("Batalla")) {
            // Se termina el turno completo -> Siguiente turno, Jugador 1 roba
            fase = "Main 1";
            turno++;
            jugadorActivo = 1;
            robarCarta(1);
        }
        actualizarInterfaz();
    }

    private void robarCarta(int numJugador) {
        Jugador j = (numJugador == 1) ? campo.obtenerJugador1() : campo.obtenerJugador2();
        if (!j.obtenerMazo().isEmpty()) {
            Carta robada = j.obtenerMazo().remove(0);
            j.obtenerMano().add(robada);
            vista.mostrarMensaje(j.obtenerNombre() + " ha robado la carta: " + robada.obtenerNombre());
        } else {
            vista.mostrarMensaje(j.obtenerNombre() + " no tiene más cartas en su mazo.");
        }
    }

    private void ponerCarta() {
        Jugador actual = (jugadorActivo == 1) ? campo.obtenerJugador1() : campo.obtenerJugador2();
        if (actual.obtenerMano().isEmpty()) {
            vista.mostrarMensaje("Mano vacía.");
            return;
        }

        int idx = vista.pedirIndiceMano(actual.obtenerMano().size());
        if (idx < 0 || idx >= actual.obtenerMano().size()) return;

        Carta carta = actual.obtenerMano().get(idx);
        
        // Lógica de Sacrificios para Monstruos
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

        // Búsqueda automática de espacio vacío
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
        for (int i=0; i<5; i++) if (campoM[i] != null) ocupados.add(i);

        if (ocupados.size() < cantidad) return false;

        for (int i = 0; i < cantidad; i++) {
            int idx = vista.pedirIndiceCampo("Elige monstruo para SACRIFICAR (" + (i+1) + "/" + cantidad + ")");
            if (idx < 0 || idx > 4 || campoM[idx] == null) {
                vista.mostrarMensaje("Selección inválida. Sacrificio cancelado.");
                return false; 
            }
            cem.add(campoM[idx]);
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

    private void ejecutarBatalla() {
        int idxAtk = vista.pedirIndiceCampo("Tu monstruo atacante");
        int idxDef = vista.pedirIndiceCampo("Monstruo OBJETIVO (Oponente)");
        if (idxAtk < 0 || idxDef < 0) return;

        Jugador atk = (jugadorActivo == 1) ? campo.obtenerJugador1() : campo.obtenerJugador2();
        Jugador def = (jugadorActivo == 1) ? campo.obtenerJugador2() : campo.obtenerJugador1();
        Monstruo mAtk = (jugadorActivo == 1) ? campo.obtenerMonstruosJugador1()[idxAtk] : campo.obtenerMonstruosJugador2()[idxAtk];
        Monstruo mDef = (jugadorActivo == 1) ? campo.obtenerMonstruosJugador2()[idxDef] : campo.obtenerMonstruosJugador1()[idxDef];

        if (mAtk == null || !mAtk.estaEnPosicionAtaque()) {
            vista.mostrarMensaje("Elegir un atacante válido en posición de ataque.");
            return;
        }

        if (mDef == null) {
            def.establecerPuntosVida((short)(def.obtenerPuntosVida() - mAtk.obtenerAtaque()));
            vista.mostrarMensaje("¡Ataque directo!");
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
    }

    private void removerDelCampo(Monstruo m, int numJugador) {
        Monstruo[] c = (numJugador == 1) ? campo.obtenerMonstruosJugador1() : campo.obtenerMonstruosJugador2();
        List<Carta> cem = (numJugador == 1) ? campo.obtenerCementerioJugador1() : campo.obtenerCementerioJugador2();
        for (int i=0; i<5; i++) if (c[i] == m) { c[i] = null; break; }
        cem.add(m);
    }

    private void actualizarInterfaz() {
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
        else if (fase.equals("Batalla")) instruccion += " | ¡Presiona ATACAR!";
        vista.establecerInstruccion(instruccion);
        
        vista.actualizarTablero();
    }
}
