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
        short nuevosPuntos = (short) (modelo.getPuntosVida() - cantidad);
        if (nuevosPuntos < 0) nuevosPuntos = 0;
        modelo.setPuntosVida(nuevosPuntos);
        vista.actualizarTablero();
    }

    public void robarCarta() {
        if (!modelo.getMazo().isEmpty()) {
            Carta carta = modelo.getMazo().pop();
            modelo.getMano().add(carta);
        }
        vista.actualizarTablero();
    }
}
