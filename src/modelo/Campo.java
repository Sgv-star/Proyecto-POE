package modelo;

import java.util.*;

public class Campo {

    private Monstruo[] monstruosJugador1 = new Monstruo[5];
    private Monstruo[] monstruosJugador2 = new Monstruo[5];
    private Carta[] magicasYTrampasJugador1 = new Carta[5];
    private Carta[] magicasYTrampasJugador2 = new Carta[5];
    private HashMap<String, Carta> cementerioJugador1 = new HashMap<>();
    private HashMap<String, Carta> cementerioJugador2 = new HashMap<>();
    private Set<String> cartasEnCampoJ1 = new HashSet<>();
    private Set<String> cartasEnCampoJ2 = new HashSet<>();
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
    public HashMap<String, Carta> obtenerCementerioJugador1() { 
        return cementerioJugador1; 
    }
    public HashMap<String, Carta> obtenerCementerioJugador2() { 
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
                if (esTurnoJugador1) {
                    cartasEnCampoJ1.add(carta.obtenerNombre());
                }
                else{
                    cartasEnCampoJ2.add(carta.obtenerNombre());
                }
                return true;
            }
        } else {
            Carta[] campo = esTurnoJugador1 ? magicasYTrampasJugador1 : magicasYTrampasJugador2;
            if (campo[posicion] == null) {
                campo[posicion] = carta;
                if (esTurnoJugador1) {
                    cartasEnCampoJ1.add(carta.obtenerNombre());
                }
                else{
                    cartasEnCampoJ2.add(carta.obtenerNombre());
                }
                return true;
            }
        }
        return false;
    }

    public boolean estaEnCampo(String nombre, int jugador) {
        if (jugador == 1) {
            return cartasEnCampoJ1.contains(nombre);
        } 
        else{
            return cartasEnCampoJ2.contains(nombre);
        }
    }

    public void removerDelCampo(String nombre, int jugador) {
        if (jugador == 1){
            cartasEnCampoJ1.remove(nombre);
        }
        else{
            cartasEnCampoJ2.remove(nombre);
        }
    }

    public void agregarAlCementerio(Carta carta, int jugador) {
        if (jugador == 1){
            cementerioJugador1.put(carta.obtenerNombre(), carta);
        } 
        else{
            cementerioJugador2.put(carta.obtenerNombre(), carta);
        }
    }

}
