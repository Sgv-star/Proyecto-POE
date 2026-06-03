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
            modelo.setEnPosicionAtaque(!modelo.estaEnPosicionAtaque());
            modelo.setYaCambioPosicion(true);
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
            defensor.setPuntosVida((short) (defensor.getPuntosVida() - modelo.getAtaque()));
        } else {
            if (objetivo.estaEnPosicionAtaque()) {
                if (modelo.getAtaque() > objetivo.getAtaque()) {
                    defensor.setPuntosVida((short) (defensor.getPuntosVida() - (modelo.getAtaque() - objetivo.getAtaque())));
                    cementerioDefensor.add(objetivo);
                } else if (modelo.getAtaque() < objetivo.getAtaque()) {
                    atacante.setPuntosVida((short) (atacante.getPuntosVida() - (objetivo.getAtaque() - modelo.getAtaque())));
                    cementerioAtacante.add(modelo);
                } else {
                    cementerioDefensor.add(objetivo);
                    cementerioAtacante.add(modelo);
                }
            } else {
                if (modelo.getAtaque() > objetivo.getDefensa()) {
                    cementerioDefensor.add(objetivo);
                } else if (modelo.getAtaque() < objetivo.getDefensa()) {
                    atacante.setPuntosVida((short) (atacante.getPuntosVida() - (objetivo.getDefensa() - modelo.getAtaque())));
                }
            }
        }
        modelo.setPuedeAtacar(false);
        vista.actualizarTablero();
    }
}
