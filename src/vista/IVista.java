package vista;

import java.util.List;
import modelo.Carta;
import modelo.Campo;

public interface IVista {
    void mostrarMensaje(String mensaje);
    int pedirIndiceMano(int max);
    int pedirIndiceCampo(String mensaje);
    int pedirOpcionPosicion();
    void actualizarTurnoYFase(int turno, String fase);
    void actualizarPuntosVida(String n1, int p1, String n2, int p2);
    void actualizarZonasCampo(Campo campo);
    void refrescarDialogoCartas(List<Carta> mano, List<Carta> cementerio);
    void setInstruccion(String texto);
    void irAJuego();
    void actualizarTablero();

    // Métodos para obtener datos de inicio (pueden variar entre implementaciones)
    String getNombre1();
    String getNombre2();
    void vincularControlador(controlador.ControladorDuelo controlador);
}
