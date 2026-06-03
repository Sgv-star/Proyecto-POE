package vista;

import java.util.List;
import java.util.Scanner;
import modelo.*;
import controlador.ControladorDuelo;

public class VistaConsola implements IVista {
    private Scanner scanner = new Scanner(System.in);
    private ControladorDuelo controlador;
    private boolean enJuego = false;

    @Override
    public void mostrarMensaje(String mensaje) {
        System.out.println("\n[MENSAJE] " + mensaje);
    }

    @Override
    public int pedirIndiceMano(int max) {
        System.out.print("Elige indice de carta en mano (0-" + (max - 1) + "): ");
        try { return Integer.parseInt(scanner.nextLine()); } catch (Exception e) { return -1; }
    }

    @Override
    public int pedirIndiceCampo(String mensaje) {
        System.out.print(mensaje + " (indice 0-4): ");
        try { return Integer.parseInt(scanner.nextLine()); } catch (Exception e) { return -1; }
    }

    @Override
    public int pedirOpcionPosicion() {
        System.out.print("Posicion? (0: Ataque, 1: Defensa): ");
        try { return Integer.parseInt(scanner.nextLine()); } catch (Exception e) { return -1; }
    }

    @Override
    public void actualizarTurnoYFase(int turno, String fase) {
        System.out.println("\n========================================");
        System.out.println("TURNO: " + turno + " | FASE: " + fase.toUpperCase());
    }

    @Override
    public void actualizarPuntosVida(String n1, int p1, String n2, int p2) {
        System.out.println(n1 + ": " + p1 + " LP  vs  " + n2 + ": " + p2 + " LP");
    }

    @Override
    public void actualizarZonasCampo(Campo campo) {
        System.out.println("--- CAMPO J1 (Monstruos) ---");
        imprimirFila(campo.getMonstruosJugador1());
        System.out.println("--- CAMPO J1 (Magias/Trampas) ---");
        imprimirFila(campo.getMagicasYTrampasJugador1());
        System.out.println("----------------------------");
        System.out.println("--- CAMPO J2 (Monstruos) ---");
        imprimirFila(campo.getMonstruosJugador2());
        System.out.println("--- CAMPO J2 (Magias/Trampas) ---");
        imprimirFila(campo.getMagicasYTrampasJugador2());
    }

    private void imprimirFila(Object[] fila) {
        for (int i = 0; i < 5; i++) {
            if (fila[i] == null) {
                System.out.print("[ Vacio ] ");
            } else if (fila[i] instanceof Monstruo) {
                Monstruo m = (Monstruo) fila[i];
                System.out.print("[MON: " + m.getNombre() + " Lvl:" + m.getNivel() + " " + m.getAtaque() + "/" + m.getDefensa() + (m.estaEnPosicionAtaque() ? " A" : " D") + "] ");
            } else if (fila[i] instanceof Magia) {
                System.out.print("[MAG: " + ((Carta)fila[i]).getNombre() + "] ");
            } else if (fila[i] instanceof Trampa) {
                System.out.print("[TRA: " + ((Carta)fila[i]).getNombre() + "] ");
            } else {
                Carta c = (Carta) fila[i];
                System.out.print("[" + c.getNombre() + "] ");
            }
        }
        System.out.println();
    }

    @Override
    public void refrescarDialogoCartas(List<Carta> mano, List<Carta> cementerio) {
        System.out.println("--- TU MANO ---");
        for (int i = 0; i < mano.size(); i++) {
            Carta c = mano.get(i);
            String tipo = (c instanceof Monstruo) ? "MON" : (c instanceof Magia ? "MAG" : "TRA");
            String info = i + ": [" + tipo + "] " + c.getNombre();
            if (c instanceof Monstruo) {
                Monstruo m = (Monstruo) c;
                info += " [Lvl:" + m.getNivel() + " ATK:" + m.getAtaque() + " DEF:" + m.getDefensa() + "]";
            }
            System.out.println(info);
        }
    }

    @Override
    public void setInstruccion(String texto) {
        System.out.println("[GUIA] " + texto);
    }

    @Override
    public void irAJuego() {
        enJuego = true;
    }

    @Override
    public void actualizarTablero() {
    }

    @Override
    public String getNombre1() {
        System.out.print("Nombre Jugador 1: ");
        return scanner.nextLine();
    }

    @Override
    public String getNombre2() {
        System.out.print("Nombre Jugador 2: ");
        return scanner.nextLine();
    }

    @Override
    public void vincularControlador(ControladorDuelo controlador) {
        this.controlador = controlador;
    }

    public void iniciarBucle() {
        mostrarInicio();

        while (enJuego) {
            System.out.println("\nAcciones: [P]oner Carta, [A]tacar, [S]iguiente Fase, [V]er Estado, [G]uardar, [Q]uit");
            System.out.print("Elige accion: ");
            String accion = scanner.nextLine().toUpperCase();

            switch (accion) {
                case "P": controlador.ponerCarta(); break;
                case "A": controlador.ejecutarBatalla(); break;
                case "S": controlador.avanzarTurno(); break;
                case "V": controlador.actualizarInterfaz(); break;
                case "G": controlador.guardarPartida(); break;
                case "Q": enJuego = false; break;
                default: System.out.println("Accion no valida.");
            }
        }
        System.out.println("Gracias por jugar!");
    }

    private void mostrarInicio() {
        boolean esperando = true;
        while (esperando) {
            System.out.println("\n" + controlador.getResumenResultados());
            System.out.println("\nInicio: [N]ueva Partida, [C]argar Partida, [Q]uit");
            System.out.print("Elige opcion: ");
            String accion = scanner.nextLine().toUpperCase();

            switch (accion) {
                case "N":
                    controlador.iniciarDuelo();
                    esperando = false;
                    break;
                case "C":
                    if (cargarPartidaDesdeConsola()) {
                        esperando = false;
                    }
                    break;
                case "Q":
                    enJuego = false;
                    esperando = false;
                    break;
                default:
                    System.out.println("Opcion no valida.");
            }
        }
    }

    private boolean cargarPartidaDesdeConsola() {
        List<String> partidas = controlador.getNombresPartidasGuardadas();
        if (partidas.isEmpty()) {
            System.out.println("No hay partidas guardadas.");
            return false;
        }

        System.out.println("\nPartidas guardadas:");
        for (int i = 0; i < partidas.size(); i++) {
            System.out.println(i + ". " + partidas.get(i));
        }

        System.out.print("Elige partida: ");
        try {
            int indice = Integer.parseInt(scanner.nextLine());
            if (indice < 0 || indice >= partidas.size()) {
                System.out.println("Partida no valida.");
                return false;
            }
            controlador.cargarPartida(partidas.get(indice));
            return enJuego;
        } catch (Exception e) {
            System.out.println("Partida no valida.");
            return false;
        }
    }
}
