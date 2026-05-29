package controlador;

import modelo.*;
import vista.*;

public class ControladorInicio {
    private VistaDuelo vista;
    private Campo modelo;

    public ControladorInicio(VistaDuelo vista, Campo modelo) {
        this.vista = vista;
        this.modelo = modelo;
    }

    public void iniciarJuego(String nombre1, String nombre2) {
        Mazo mazoGeneral = new Mazo();
        
        Jugador j1 = new Jugador(nombre1, mazoGeneral);
        Jugador j2 = new Jugador(nombre2, mazoGeneral);
        
        modelo.establecerJugador1(j1);
        modelo.establecerJugador2(j2);
        
        vista.mostrarMensaje("¡Juego Iniciado!");
    }
}
