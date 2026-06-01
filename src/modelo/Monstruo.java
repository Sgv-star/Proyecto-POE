package modelo;

import java.util.*;

public class Monstruo extends Carta {
    private byte nivel;
    private short ataqueBase;
    private short defensaBase;
    private short ataque;
    private short defensa;
    private boolean enPosicionAtaque;
    private boolean yaCambioPosicion;
    private boolean puedeAtacar;

    public Monstruo(String nombre, String descripcion, byte nivel, short ataque, short defensa) {
        super(nombre, descripcion, true);
        this.nivel = nivel;
        this.ataqueBase = ataque;
        this.defensaBase = defensa;
        this.ataque = ataque;
        this.defensa = defensa;
        this.enPosicionAtaque = true;
        this.puedeAtacar = true;
    }

    public byte getNivel() {
        return nivel;
    }
    public short getAtaqueBase() {
        return ataqueBase;
    }
    public short getDefensaBase() {
        return defensaBase;
    }
    public short getAtaque() {
        return ataque;
    }
    public short getDefensa() {
        return defensa;
    }
    public boolean estaEnPosicionAtaque() {
        return enPosicionAtaque;
    }
    public boolean yaCambioPosicion() {
        return yaCambioPosicion;
    }
    public boolean puedeAtacar() {
        return puedeAtacar;
    }

    public void setNivel(byte nivel){
        this.nivel = nivel;
    }
    public void setAtaque(short ataque) {
        this.ataque = ataque;
    }
    public void setDefensa(short defensa) {
        this.defensa = defensa;
    }
    public void setEnPosicionAtaque(boolean enPosicionAtaque) {
        this.enPosicionAtaque = enPosicionAtaque;
    }
    public void setYaCambioPosicion(boolean yaCambioPosicion) {
        this.yaCambioPosicion = yaCambioPosicion;
    }
    public void setPuedeAtacar(boolean puedeAtacar) {
        this.puedeAtacar = puedeAtacar;
    }

    @Override
    public boolean jugar(Campo campo, byte turno, byte cartaAActivar, byte byteAux, String stringAux, String stringAux2, String stringAux3, List<Monstruo> lista) {
        // La lógica de juego se delega al controlador
        return true;
    }
}
