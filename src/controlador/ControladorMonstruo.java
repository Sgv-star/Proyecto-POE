package controlador;

import java.util.List;
import modelo.*;
import vista.VistaDuelo;

public class ControladorMonstruo {
    private Monstruo modelo;
    private VistaDuelo vista;

    public ControladorMonstruo(Monstruo modelo, VistaDuelo vista) {
        this.modelo = modelo;
        this.vista = vista;
    }

    public void cambiarPosicion() {
        if (!modelo.yaCambioPosicion()) {
            modelo.establecerEnPosicionAtaque(!modelo.estaEnPosicionAtaque());
            modelo.establecerYaCambioPosicion(true);
            vista.actualizarTablero();
        } else {
            vista.mostrarMensaje("Ya cambió de posición este turno.");
        }
    }

    public void ejecutarAtaque(Monstruo objetivo, Jugador atacante, Jugador defensor, List<Carta> cementerioAtacante, List<Carta> cementerioDefensor) {
        if (!modelo.puedeAtacar()) {
            vista.mostrarMensaje("Este monstruo no puede atacar.");
            return;
        }

        if (objetivo == null) {
            defensor.establecerPuntosVida((short) (defensor.obtenerPuntosVida() - modelo.obtenerAtaque()));
        } else {
            if (objetivo.estaEnPosicionAtaque()) {
                if (modelo.obtenerAtaque() > objetivo.obtenerAtaque()) {
                    defensor.establecerPuntosVida((short) (defensor.obtenerPuntosVida() - (modelo.obtenerAtaque() - objetivo.obtenerAtaque())));
                    cementerioDefensor.add(objetivo);
                } else if (modelo.obtenerAtaque() < objetivo.obtenerAtaque()) {
                    atacante.establecerPuntosVida((short) (atacante.obtenerPuntosVida() - (objetivo.obtenerAtaque() - modelo.obtenerAtaque())));
                    cementerioAtacante.add(modelo);
                } else {
                    cementerioDefensor.add(objetivo);
                    cementerioAtacante.add(modelo);
                }
            } else {
                if (modelo.obtenerAtaque() > objetivo.obtenerDefensa()) {
                    cementerioDefensor.add(objetivo);
                } else if (modelo.obtenerAtaque() < objetivo.obtenerDefensa()) {
                    atacante.establecerPuntosVida((short) (atacante.obtenerPuntosVida() - (objetivo.obtenerDefensa() - modelo.obtenerAtaque())));
                }
            }
        }
        modelo.establecerPuedeAtacar(false);
        vista.actualizarTablero();
    }
}
