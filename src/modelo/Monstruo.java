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

    public byte obtenerNivel() { 
        return nivel; 
    }
    public short obtenerAtaqueBase() { 
        return ataqueBase; 
    }
    public short obtenerDefensaBase() { 
        return defensaBase; 
    }
    public short obtenerAtaque() { 
        return ataque; 
    }
    public short obtenerDefensa() { 
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

    public void establecerNivel(byte nivel){
        this.nivel = nivel;
    }
    public void establecerAtaque(short ataque) { 
        this.ataque = ataque; 
    }
    public void establecerDefensa(short defensa) { 
        this.defensa = defensa; 
    }
    public void establecerEnPosicionAtaque(boolean enPosicionAtaque) { 
        this.enPosicionAtaque = enPosicionAtaque; 
    }
    public void establecerYaCambioPosicion(boolean yaCambioPosicion) { 
        this.yaCambioPosicion = yaCambioPosicion; 
    }
    public void establecerPuedeAtacar(boolean puedeAtacar) { 
        this.puedeAtacar = puedeAtacar; 
    }

    @Override
    public boolean jugar(Campo campo, byte turno, byte cartaAActivar, byte byteAux, String stringAux, String stringAux2, String stringAux3, List<Monstruo> lista) {
        return true;
    }
}
