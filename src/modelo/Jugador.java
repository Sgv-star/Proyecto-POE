package modelo;

import java.util.*;

public class Jugador {

    private String nombre;
    private short puntosVida;
    private List<Carta> mazo = new ArrayList<>();
    private List<Carta> mano = new ArrayList<>();
    private List<Carta> cementerio = new ArrayList<>();

    public Jugador() {
        this.puntosVida = 8000;
    }

    public Jugador(String nombre, Mazo mazoGeneral) {
        this.nombre = nombre;
        this.puntosVida = 8000;
        inicializarMazo(mazoGeneral);
        inicializarMano();
    }

    public String obtenerNombre() {
        return nombre;
    }
    public short obtenerPuntosVida() {
        return puntosVida;
    }
    public List<Carta> obtenerMazo() {
        return mazo;
    }
    public List<Carta> obtenerMano() {
        return mano;
    }
    public List<Carta> obtenerCementerio(){
        return cementerio;
    }
    
    public void establecerNombre(String nombre) {
        this.nombre = nombre;
    }
    public void establecerPuntosVida(short lp) {
        this.puntosVida = lp;
    }
    
    public void inicializarMazo(Mazo mazoGeneral) {
        for(byte i=0; i<25; i++){
            if (!mazoGeneral.obtenerCartas().isEmpty()) {
                this.mazo.add(mazoGeneral.obtenerCartas().remove(0));
            }
        }
    }
    
    public void inicializarMano() {
        for(byte i=0; i<5; i++){
            if (!this.mazo.isEmpty()) {
                this.mano.add(this.mazo.remove(0));
            }
        }
    }
}
