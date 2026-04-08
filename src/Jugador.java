package src;
import java.util.*;

public class Jugador {

    private String nombre;
    private short LP;
    private List<Carta> mazo = new ArrayList<>();
    private List<Carta> mano = new ArrayList<>();
    private List<Carta> cementerio = new ArrayList<>();

    public Jugador(){
        
    }

    public Jugador(String nombre, Mazo mazoGeneral) {
        this.nombre = nombre;
        LP = (short) 8000;
        setMazo(mazoGeneral);
        setMano(mazo);
    }

    public String getNombre() {
        return nombre;
    }
    public short getLP() {
        return LP;
    }
    public List<Carta> getMazo() {
        return mazo;
    }
    public List<Carta> getMano() {
        return mano;
    }
    public List<Carta> getCementerio(){
        return cementerio;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public void setLP(short lP) {
        LP = lP;
    }
    public void setMazo(Mazo mazoGeneral) {
        for(byte i=0; i<20; i++){
            this.mazo.add(mazoGeneral.getMazoGeneral().remove(0));
        }
    }
    public void setMano(List<Carta> mazo) {
        for(byte i=0; i<5; i++){
            this.mano.add(mazo.remove(0));
        }
    }
    
}