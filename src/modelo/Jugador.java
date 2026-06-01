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

    public String getNombre() {
        return nombre;
    }
    public short getPuntosVida() {
        return puntosVida;
    }
    public Stack<Carta> getMazo() {
        return mazo;
    }
    public LinkedList<Carta> getMano() {
        return mano;
    }
    public List<Carta> getCementerio(){
        return new ArrayList<>(cementerio.values());
    }
    public HashMap<String, Carta> getMapaCementerio() {
        return cementerio;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public void setPuntosVida(short lp) {
        this.puntosVida = lp;
    }

    public void inicializarMazo(Mazo mazoGeneral) {
        for(byte i=0; i<25; i++){
            if (!mazoGeneral.getCartas().isEmpty()) {
                this.mazo.push(mazoGeneral.getCartas().pop());
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
