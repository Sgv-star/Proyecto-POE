package vista;

import modelo.DueloLogica;
import modelo.Jugador;

public interface DueloInterfaz {
    void actualizarDuelo(DueloLogica duelo);
    void actualizarCampo();
    void mostrarMensaje(String titulo, String mensaje);
    void mostrarError(String titulo, String mensaje);
    void mostrarPanelJuego();
    String pedirEntrada(String titulo, String mensaje);
    boolean pedirConfirmacion(String titulo, String mensaje);
    void mostrarGameOver(Jugador ganador);
    void limpiar();
}
