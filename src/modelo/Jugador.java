package modelo;

import java.util.*;

public class Jugador {

    private String nombre;
    private short puntosVida;
    private Stack<Carta> mazo = new Stack<>();
    private LinkedList<Carta> mano = new LinkedList<>();
    private HashMap<String, Carta> cementerio = new LinkedHashMap<>();

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
    public Stack<Carta> obtenerMazo() {
        return mazo;
    }
    public LinkedList<Carta> obtenerMano() {
        return mano;
    }
    public List<Carta> obtenerCementerio(){
        return new ArrayList<>(cementerio.values());
    }
    public HashMap<String, Carta> obtenerMapaCementerio() {
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
                this.mazo.push(mazoGeneral.obtenerCartas().pop());
            }
        }
    }
    
    public void inicializarMano() {
        for(byte i=0; i<5; i++){
            if (!this.mazo.isEmpty()) {
                this.mano.add(this.mazo.pop());
            }
        }
    }
}
