package controlador;

import modelo.*;
import vista.VistaDuelo;

public class ControladorTrampa {
    private Trampa modelo;
    private VistaDuelo vista;

    public ControladorTrampa(Trampa modelo, VistaDuelo vista) {
        this.modelo = modelo;
        this.vista = vista;
    }

    public void activarEfecto(Campo campo, byte turno, byte cartaAActivar, byte byteAux, String stringAux) {
        // Lógica de efectos de Trampa migrada de Trampa.java
        switch (modelo.obtenerTipoHabilidad()) {
            case FUERZA_ESPEJO:
                destruirAtacantes(campo, turno);
                break;
            case CILINDRO_MAGICO:
                reflejarDanio(campo, turno, stringAux);
                break;
            // Otros casos...
        }
        vista.actualizarTablero();
    }

    private void destruirAtacantes(Campo campo, byte turno) {
        Monstruo[] atacantes = (turno % 2 == 0) ? campo.obtenerMonstruosJugador1() : campo.obtenerMonstruosJugador2();
        for (int i = 0; i < 5; i++) {
            if (atacantes[i] != null && atacantes[i].estaEnPosicionAtaque()) {
                atacantes[i] = null;
            }
        }
    }

    private void reflejarDanio(Campo campo, byte turno, String nombreMonstruo) {
        Jugador atacante = (turno % 2 == 0) ? campo.obtenerJugador1() : campo.obtenerJugador2();
        // Lógica simplificada de reflejo
        vista.mostrarMensaje("Daño reflejado al atacante.");
    }
}
