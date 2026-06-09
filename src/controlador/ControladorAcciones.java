package controlador;

import modelo.Campo;
import modelo.Carta;
import vista.VistaDuelo;

public class ControladorAcciones {
    private VistaDuelo vista;
    private Campo modelo;

    public ControladorAcciones(VistaDuelo vista, Campo modelo) {
        this.vista = vista;
        this.modelo = modelo;
    }

    public void atacar(int indiceAtacante, int indiceDefensor, byte turno) {
        vista.mostrarMensaje("Atacando...");
        vista.actualizarTablero();
    }

    public void colocarCarta(Carta carta, int posicion, byte turno) {
        if (modelo.colocarCarta(carta, turno, (byte)posicion)) {
            vista.mostrarMensaje("Carta colocada.");
        } else {
            vista.mostrarMensaje("No se pudo colocar la carta.");
        }
        vista.actualizarTablero();
    }
}
