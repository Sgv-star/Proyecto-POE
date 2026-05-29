package vista;

import java.util.*;
import modelo.Campo;
import modelo.Carta;

public interface IVista {
    void mostrarMensaje(String mensaje);
    int pedirIndiceMano(int max);
    int pedirIndiceCampo(String mensaje);
    int pedirOpcionPosicion();
    void actualizarTurnoYFase(int turno, String fase);
    void actualizarPuntosVida(String n1, int p1, String n2, int p2);
    void actualizarZonasCampo(Campo campo);
    void refrescarDialogoCartas(LinkedList<Carta> mano, HashMap<String, Carta> cementerio);
    void establecerInstruccion(String texto);
    void irAJuego();
    void actualizarTablero();
    
    String obtenerNombre1();
    String obtenerNombre2();
    void vincularControlador(controlador.ControladorDuelo controlador);
}
