package controlador;

import modelo.*;
import vista.*;

public class ControladorFlujo {
    private VistaDuelo vista;
    private Campo modelo;
    private byte turnoActual;

    public ControladorFlujo(VistaDuelo vista, Campo modelo) {
        this.vista = vista;
        this.modelo = modelo;
        this.turnoActual = 0;
    }

    public void pasarTurno() {
        turnoActual++;
        vista.mostrarMensaje("Turno del Jugador " + (turnoActual % 2 == 0 ? "1" : "2"));
        vista.actualizarTablero();
    }

    public byte obtenerTurnoActual() {
        return turnoActual;
    }
}
