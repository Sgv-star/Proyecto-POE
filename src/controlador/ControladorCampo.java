package controlador;

import modelo.Campo;
import modelo.Carta;
import vista.VistaDuelo;

public class ControladorCampo {
    private Campo modelo;
    private VistaDuelo vista;

    public ControladorCampo(Campo modelo, VistaDuelo vista) {
        this.modelo = modelo;
        this.vista = vista;
    }

    public void colocarCartaEnCampo(Carta carta, int posicion, byte turno) {
        if (modelo.colocarCarta(carta, turno, (byte) posicion)) {
            vista.mostrarMensaje("Carta colocada en el campo.");
        } else {
            vista.mostrarMensaje("No se pudo colocar la carta. Espacio ocupado.");
        }
        vista.actualizarTablero();
    }

    public void enviarAlCementerio(Carta carta, int numeroJugador) {
        if (numeroJugador == 1) {
            modelo.obtenerCementerioJugador1().put(carta.obtenerNombre(), carta);
        } else {
            modelo.obtenerCementerioJugador2().put(carta.obtenerNombre(), carta);
        }
        vista.actualizarTablero();
    }
}
