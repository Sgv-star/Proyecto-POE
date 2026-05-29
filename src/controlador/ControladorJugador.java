package controlador;

import modelo.Carta;
import modelo.Jugador;
import vista.VistaDuelo;

public class ControladorJugador {
    private Jugador modelo;
    private VistaDuelo vista;

    public ControladorJugador(Jugador modelo, VistaDuelo vista) {
        this.modelo = modelo;
        this.vista = vista;
    }

    public void recibirDaño(int cantidad) {
        short nuevosPuntos = (short) (modelo.obtenerPuntosVida() - cantidad);
        if (nuevosPuntos < 0) nuevosPuntos = 0;
        modelo.establecerPuntosVida(nuevosPuntos);
        vista.actualizarTablero();
    }

    public void robarCarta() {
        if (!modelo.obtenerMazo().isEmpty()) {
            Carta carta = modelo.obtenerMazo().remove(0);
            modelo.obtenerMano().add(carta);
        }
        vista.actualizarTablero();
    }
}
