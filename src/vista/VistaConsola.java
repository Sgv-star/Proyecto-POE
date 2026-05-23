package vista;

import java.util.Scanner;
import java.util.List;
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
        System.out.print("Elige índice de carta en mano (0-" + (max - 1) + "): ");
        try { return Integer.parseInt(scanner.nextLine()); } catch (Exception e) { return -1; }
    }

    @Override
    public int pedirIndiceCampo(String mensaje) {
        System.out.print(mensaje + " (índice 0-4): ");
        try { return Integer.parseInt(scanner.nextLine()); } catch (Exception e) { return -1; }
    }

    @Override
    public int pedirOpcionPosicion() {
        System.out.print("¿Posición? (0: Ataque, 1: Defensa): ");
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
        imprimirFila(campo.obtenerMonstruosJugador1());
        System.out.println("--- CAMPO J1 (Magias/Trampas) ---");
        imprimirFila(campo.obtenerMagicasYTrampasJugador1());
        System.out.println("----------------------------");
        System.out.println("--- CAMPO J2 (Monstruos) ---");
        imprimirFila(campo.obtenerMonstruosJugador2());
        System.out.println("--- CAMPO J2 (Magias/Trampas) ---");
        imprimirFila(campo.obtenerMagicasYTrampasJugador2());
    }

    private void imprimirFila(Object[] fila) {
        for (int i = 0; i < 5; i++) {
            if (fila[i] == null) {
                System.out.print("[ Vacio ] ");
            } else if (fila[i] instanceof Monstruo) {
                Monstruo m = (Monstruo) fila[i];
                System.out.print("[MON: " + m.obtenerNombre() + " Lvl:" + m.obtenerNivel() + " " + m.obtenerAtaque() + "/" + m.obtenerDefensa() + (m.estaEnPosicionAtaque() ? " A" : " D") + "] ");
            } else if (fila[i] instanceof Magia) {
                System.out.print("[MAG: " + ((Carta)fila[i]).obtenerNombre() + "] ");
            } else if (fila[i] instanceof Trampa) {
                System.out.print("[TRA: " + ((Carta)fila[i]).obtenerNombre() + "] ");
            } else {
                Carta c = (Carta) fila[i];
                System.out.print("[" + c.obtenerNombre() + "] ");
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
            String info = i + ": [" + tipo + "] " + c.obtenerNombre();
            if (c instanceof Monstruo) {
                Monstruo m = (Monstruo) c;
                info += " [Lvl:" + m.obtenerNivel() + " ATK:" + m.obtenerAtaque() + " DEF:" + m.obtenerDefensa() + "]";
            }
            System.out.println(info);
        }
    }

    @Override
    public void establecerInstruccion(String texto) {
        System.out.println("[GUIA] " + texto);
    }

    @Override
    public void irAJuego() {
        enJuego = true;
    }

    @Override
    public void actualizarTablero() {
        // En consola, la actualización es automática por el flujo de System.out
    }

    @Override
    public String obtenerNombre1() {
        System.out.print("Nombre Jugador 1: ");
        return scanner.nextLine();
    }

    @Override
    public String obtenerNombre2() {
        System.out.print("Nombre Jugador 2: ");
        return scanner.nextLine();
    }

    @Override
    public void vincularControlador(ControladorDuelo controlador) {
        this.controlador = controlador;
    }

    public void iniciarBucle() {
        controlador.iniciarDuelo();
        
        while (enJuego) {
            System.out.println("\nAcciones: [P]oner Carta, [A]tacar, [S]iguiente Fase, [V]er Estado, [Q]uit");
            System.out.print("Elige acción: ");
            String accion = scanner.nextLine().toUpperCase();
            
            switch (accion) {
                case "P": controlador.ponerCarta(); break;
                case "A": controlador.ejecutarBatalla(); break;
                case "S": controlador.avanzarTurno(); break;
                case "V": controlador.actualizarInterfaz(); break;
                case "Q": enJuego = false; break;
                default: System.out.println("Acción no válida.");
            }
        }
        System.out.println("¡Gracias por jugar!");
    }
}
