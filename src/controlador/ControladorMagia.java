package controlador;

import modelo.*;
import vista.VistaDuelo;
import java.util.List;

public class ControladorMagia {
    private Magia modelo;
    private VistaDuelo vista;

    public ControladorMagia(Magia modelo, VistaDuelo vista) {
        this.modelo = modelo;
        this.vista = vista;
    }

    public void activarEfecto(Campo campo, byte turno, byte cartaAActivar, String objetivoNombre, List<Monstruo> listaMateriales) {
        // Lógica de efectos de Magia migrada de Magia.java
        // Por brevedad y simplicidad para el pasante, se implementan los casos principales
        switch (modelo.obtenerTipoHabilidad()) {
            case AGUJERO_NEGRO:
                limpiarMonstruos(campo);
                break;
            case OLLA_CODICIA:
                robarCartas(campo, turno, 2);
                break;
            case RAIGEKI:
                destruirMonstruosOponente(campo, turno);
                break;
            // Otros casos...
        }
        vista.actualizarTablero();
    }

    private void limpiarMonstruos(Campo campo) {
        for (int i = 0; i < 5; i++) {
            if (campo.obtenerMonstruosJugador1()[i] != null) {
                campo.obtenerCementerioJugador1().add(campo.obtenerMonstruosJugador1()[i]);
                campo.obtenerMonstruosJugador1()[i] = null;
            }
            if (campo.obtenerMonstruosJugador2()[i] != null) {
                campo.obtenerCementerioJugador2().add(campo.obtenerMonstruosJugador2()[i]);
                campo.obtenerMonstruosJugador2()[i] = null;
            }
        }
    }

    private void robarCartas(Campo campo, byte turno, int cantidad) {
        Jugador actual = (turno % 2 == 0) ? campo.obtenerJugador1() : campo.obtenerJugador2();
        for (int i = 0; i < cantidad; i++) {
            if (!actual.obtenerMazo().isEmpty()) {
                actual.obtenerMano().add(actual.obtenerMazo().remove(0));
            }
        }
    }

    private void destruirMonstruosOponente(Campo campo, byte turno) {
        Monstruo[] oponente = (turno % 2 == 0) ? campo.obtenerMonstruosJugador2() : campo.obtenerMonstruosJugador1();
        List<Carta> cementerio = (turno % 2 == 0) ? campo.obtenerCementerioJugador2() : campo.obtenerCementerioJugador1();
        for (int i = 0; i < 5; i++) {
            if (oponente[i] != null) {
                cementerio.add(oponente[i]);
                oponente[i] = null;
            }
        }
    }
}
