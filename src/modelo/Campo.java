package modelo;

import java.util.*;

public class Campo {

    private Monstruo[] monstruosJugador1 = new Monstruo[5];
    private Monstruo[] monstruosJugador2 = new Monstruo[5];
    private Carta[] magicasYTrampasJugador1 = new Carta[5];
    private Carta[] magicasYTrampasJugador2 = new Carta[5];
    private List<Carta> cementerioJugador1 = new ArrayList<>();
    private List<Carta> cementerioJugador2 = new ArrayList<>();
    private Jugador jugador1;
    private Jugador jugador2;

    public Campo(Jugador jugador1, Jugador jugador2) {
        this.jugador1 = jugador1;
        this.jugador2 = jugador2;
    }
    
    public Campo() {}

    public Monstruo[] obtenerMonstruosJugador1() { 
        return monstruosJugador1; 
    }
    public Monstruo[] obtenerMonstruosJugador2() { 
        return monstruosJugador2; 
    }
    public Carta[] obtenerMagicasYTrampasJugador1() { 
        return magicasYTrampasJugador1; 
    }
    public Carta[] obtenerMagicasYTrampasJugador2() { 
        return magicasYTrampasJugador2; 
    }
    public List<Carta> obtenerCementerioJugador1() { 
        return cementerioJugador1; 
    }
    public List<Carta> obtenerCementerioJugador2() { 
        return cementerioJugador2; 
    }
    public Jugador obtenerJugador1() { 
        return jugador1; 
    }
    public Jugador obtenerJugador2() { 
        return jugador2; 
    }

    public void establecerJugador1(Jugador jugador) { 
        this.jugador1 = jugador; 
    }
    public void establecerJugador2(Jugador jugador) { 
        this.jugador2 = jugador; 
    }
    
    public boolean colocarCarta(Carta carta, byte turno, byte posicion) {
        boolean esTurnoJugador1 = (turno % 2 == 0);
        if (carta instanceof Monstruo) {
            Monstruo[] campo = esTurnoJugador1 ? monstruosJugador1 : monstruosJugador2;
            if (campo[posicion] == null) {
                campo[posicion] = (Monstruo) carta;
                return true;
            }
        } else {
            Carta[] campo = esTurnoJugador1 ? magicasYTrampasJugador1 : magicasYTrampasJugador2;
            if (campo[posicion] == null) {
                campo[posicion] = carta;
                return true;
            }
        }
        return false;
    }
}
