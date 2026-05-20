package controlador;

import modelo.Mazo;
import vista.VistaDuelo;

public class ControladorMazo {
    private Mazo modelo;
    private VistaDuelo vista;

    public ControladorMazo(Mazo modelo, VistaDuelo vista) {
        this.modelo = modelo;
        this.vista = vista;
    }

    public void barajarMazo() {
        modelo.barajar();
        vista.mostrarMensaje("Mazo barajado.");
    }
}
