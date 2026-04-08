import java.util.*;

public class Campo {

    private Monstruo[] monstruosEnCampoJugador1 = new Monstruo[5];
    private Monstruo[] monstruosEnCampoJugador2 = new Monstruo[5];
    private Magia[] magicasEnCampoJugador1 = new Magia[5];
    private Magia[] magicasEnCampoJugador2 = new Magia[5];
    private List<Carta> cementerioJugador1 = new ArrayList<> ();
    private List<Carta> cementerioJugador2 = new ArrayList<> ();
    Jugador jugador1;
    Jugador jugador2;

    public Campo(Jugador jugador1, Jugador jugador2) {
        this.jugador1 = jugador1;
        this.jugador2 = jugador2;
    }s

    public Monstruo[] getMonstruosEnCampoJugador1() {
        return monstruosEnCampoJugador1;
    }
    public Monstruo[] getMonstruosEnCampoJugador2() {
        return monstruosEnCampoJugador2;
    }
    public Magia[] getMagicasEnCampoJugador1() {
        return magicasEnCampoJugador1;
    }
    public Magia[] getMagicasEnCampoJugador2() {
        return magicasEnCampoJugador2;
    }
    public List<Carta> getCementerioJugador1() {
        return cementerioJugador1;
    }
    public List<Carta> getCementerioJugador2() {
        return cementerioJugador2;
    }
    public Jugador getJugador1() {
        return jugador1;
    }
    public Jugador getJugador2() {
        return jugador2;
    }

    public boolean colocarCarta(Carta carta, byte turno, byte posicion){
        if(carta instanceof Monstruo){
            if(turno%2==0 && monstruosEnCampoJugador1[posicion]==null){
                monstruosEnCampoJugador1[posicion] = (Monstruo) carta;
                return true;
            }
            else if(turno%2!=0 && monstruosEnCampoJugador2[posicion]==null){
                monstruosEnCampoJugador2[posicion] = (Monstruo) carta;
                return true;
            }
            else{
                return false;
            }
        }
        else if(carta instanceof Magia){
            if(turno%2==0 && magicasEnCampoJugador1[posicion]==null){
                magicasEnCampoJugador1[posicion] = (Magia) carta;
                return true;
            }
            else if(turno%2!=0 && magicasEnCampoJugador2[posicion]==null){
                magicasEnCampoJugador2[posicion] = (Magia) carta;
                return true;
            }
            else{
                return false;
            }
        }
        else{
            return false;
        }
    }
    public void setJugador1(Jugador jugador1) {
        this.jugador1 = jugador1;
    }
    public void setJugador2(Jugador jugador2) {
        this.jugador2 = jugador2;
    }
    
}
