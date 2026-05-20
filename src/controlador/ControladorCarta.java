package controlador;

import modelo.Carta;
import vista.VistaDuelo;

public class ControladorCarta {
    private Carta modelo;
    private VistaDuelo vista;

    public ControladorCarta(Carta modelo, VistaDuelo vista) {
        this.modelo = modelo;
        this.vista = vista;
    }

    public void voltearCarta() {
        modelo.establecerVisible(!modelo.esVisible());
        vista.actualizarTablero();
    }
    
    public String obtenerNombre() {
        return modelo.obtenerNombre();
    }
}
