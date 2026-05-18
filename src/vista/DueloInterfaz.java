package vista;

import modelo.*;
import controlador.*;

public interface DueloInterfaz {
    void actualizarControlador(DueloControlador controlador);
    void actualizarCampo();
    void mostrarMensaje(String titulo, String mensaje);
    void mostrarError(String titulo, String mensaje);
    void mostrarPanelJuego();
    String pedirEntrada(String titulo, String mensaje);
    boolean pedirConfirmacion(String titulo, String mensaje);
    void mostrarGameOver(Jugador ganador);
    void limpiar();
}
