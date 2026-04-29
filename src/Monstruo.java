
import java.util.*;

public class Monstruo extends Carta {

    private byte nivel;
    private short ataqueBase;
    private short defensaBase;
    private short ataque;
    private short defensa;
    private boolean enPosicionAtaque;
    private boolean yaCambioPosicionEnEsteTurno;
    private boolean puedeAtacar;

    public Monstruo(String nombre, String cuadroDeTexto, byte nivel, short ataque, short defensa) {
        super(nombre, cuadroDeTexto, true);
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
    public boolean isEnPosicionAtaque() {
        return enPosicionAtaque;
    }
    public boolean isYaCambioPosicionEnEsteTurno() {
        return yaCambioPosicionEnEsteTurno;
    }
    public boolean isPuedeAtacar(){
        return puedeAtacar;
    }

    protected void setNivel(byte nivel) {
        this.nivel = nivel;
    }
    protected void setAtaqueBase(short ataqueBase){
        this.ataqueBase = ataqueBase;
    }
    protected void setDefensaBase(short defensaBase){
        this.defensaBase = defensaBase;
    }
    protected void setAtaque(short ataque) {
        this.ataque = ataque;
    }
    protected void setDefensa(short defensa) {
        this.defensa = defensa;
    }
    public void setEnPosicionAtaque(boolean enPosicionAtaque) {
        if(!yaCambioPosicionEnEsteTurno){
            this.enPosicionAtaque = enPosicionAtaque;
            this.yaCambioPosicionEnEsteTurno = true;
        }
    }
    public void setEnPosicionAtaque() {
        if (!yaCambioPosicionEnEsteTurno){
            this.enPosicionAtaque = !enPosicionAtaque;
            this.yaCambioPosicionEnEsteTurno = true;
        }
    }
    public void setYaCambioPosicionEnEsteTurno(boolean yaCambioPosicionEnEsteTurno){
        this.yaCambioPosicionEnEsteTurno = yaCambioPosicionEnEsteTurno;
    }
    public void setPuedeAtacar(boolean puedeAtacar){
        this.puedeAtacar = puedeAtacar;
    }

    @Override
    public boolean jugar(Campo campo, byte turno, byte cartaAActivar, byte byteAux, String stringAux, String stringAux2, String stringAux3, List<Monstruo> lista){
        Jugador atacante, defensor;
        Monstruo[] monstruosAtacantes, monstruosDefensores;
        Carta[] magiasYTrampasAtacantes, magiasYTrampasDefensoras;
        List<Carta> cementerioAtacante, cementerioDefensor; 
        boolean monstruosAtacantesNoEstaVacio = false, monstruosDefensoresNoEstaVacio = false;
        if(turno%2==0){
            atacante = campo.getJugador1();
            defensor = campo.getJugador2();
            monstruosAtacantes = campo.getMonstruosEnCampoJugador1();
            magiasYTrampasAtacantes = campo.getMagicasYTrampasEnCampoJugador1();
            cementerioAtacante = campo.getCementerioJugador1();
            monstruosDefensores = campo.getMonstruosEnCampoJugador2();
            magiasYTrampasDefensoras = campo.getMagicasYTrampasEnCampoJugador2();
            cementerioDefensor = campo.getCementerioJugador2();
        }
        else{
            atacante = campo.getJugador2();
            defensor = campo.getJugador1();
            monstruosAtacantes = campo.getMonstruosEnCampoJugador2();
            magiasYTrampasAtacantes = campo.getMagicasYTrampasEnCampoJugador2();
            cementerioAtacante = campo.getCementerioJugador2();
            monstruosDefensores = campo.getMonstruosEnCampoJugador1();
            magiasYTrampasDefensoras = campo.getMagicasYTrampasEnCampoJugador1();
            cementerioDefensor = campo.getCementerioJugador1();
        }
        if(monstruosAtacantes[cartaAActivar] != null){
            for(Carta carta : magiasYTrampasDefensoras){
                if(carta instanceof Magia){
                    Magia mg = (Magia) carta;
                    if(mg != null && mg.getTipoHabilidadEspecialMagia() == TipoHabilidadEspecialMagia.ESPADAS_DE_LA_LUZ_REVELADORA && mg.isVisible()){
                        monstruosAtacantes[cartaAActivar].setPuedeAtacar(false);
                        break;
                    }
                }
            }
            monstruosAtacantesNoEstaVacio = true;
        }
        if(monstruosAtacantesNoEstaVacio){
            if(!puedeAtacar){
                return false;
            }
        }
        else{
            return false;
        }
        for(Monstruo m : monstruosDefensores){
            if(m != null){
                monstruosDefensoresNoEstaVacio = true;
                break;
            }
        }
        if(monstruosAtacantes[cartaAActivar].isEnPosicionAtaque()){
            if(monstruosDefensoresNoEstaVacio){
                if(byteAux == 6 || monstruosDefensores[byteAux] == null) {
                return false;
                }
            }
            else{
                defensor.setLP((short) (defensor.getLP() - monstruosAtacantes[cartaAActivar].getAtaque()));
                if(defensor.getLP() < 0){
                    defensor.setLP((short) 0);
                }
                monstruosAtacantes[cartaAActivar].setPuedeAtacar(false);
                return true;
            }
            if(monstruosDefensoresNoEstaVacio && monstruosDefensores[byteAux].isEnPosicionAtaque()){
                if(monstruosAtacantes[cartaAActivar].getAtaque() > monstruosDefensores[byteAux].getAtaque()){
                    defensor.setLP((short) (defensor.getLP() + (monstruosDefensores[byteAux].getAtaque() - monstruosAtacantes[cartaAActivar].getAtaque())));
                    if(defensor.getLP() < 0){
                        defensor.setLP((short) 0);
                    }
                    cementerioDefensor.add(monstruosDefensores[byteAux]);
                    monstruosDefensores[byteAux] = null;
                    monstruosAtacantes[cartaAActivar].setPuedeAtacar(false);
                    return true;
                }
                else if(monstruosAtacantes[cartaAActivar].getAtaque() < monstruosDefensores[byteAux].getAtaque()){
                    atacante.setLP((short) (atacante.getLP() + (monstruosAtacantes[cartaAActivar].getAtaque() - monstruosDefensores[byteAux].getAtaque())));
                    if(atacante.getLP() < 0){
                        atacante.setLP((short) 0);
                    }
                    cementerioAtacante.add(monstruosAtacantes[cartaAActivar]);
                    monstruosAtacantes[cartaAActivar] = null;
                    return true;
                }
                else if(monstruosAtacantes[cartaAActivar].getAtaque() == monstruosDefensores[byteAux].getAtaque()){
                    cementerioDefensor.add(monstruosDefensores[byteAux]);
                    cementerioAtacante.add(monstruosAtacantes[cartaAActivar]);
                    monstruosDefensores[byteAux] = null;
                    monstruosAtacantes[cartaAActivar] = null;
                    return true;
                }
            }
            else if(monstruosDefensoresNoEstaVacio && !monstruosDefensores[byteAux].isEnPosicionAtaque()){
                if(monstruosAtacantes[cartaAActivar].getAtaque() > monstruosDefensores[byteAux].getDefensa()){
                    cementerioDefensor.add(monstruosDefensores[byteAux]);
                    monstruosDefensores[byteAux] = null;
                    monstruosAtacantes[cartaAActivar].setPuedeAtacar(false);
                    return true;
                }
                else if(monstruosAtacantes[cartaAActivar].getAtaque() < monstruosDefensores[byteAux].getDefensa()){
                    atacante.setLP((short) (atacante.getLP() + (monstruosAtacantes[cartaAActivar].getAtaque() - monstruosDefensores[byteAux].getDefensa())));
                    if(atacante.getLP() < 0){
                        atacante.setLP((short) 0);
                    }
                    monstruosAtacantes[cartaAActivar].setPuedeAtacar(false);
                    return true;
                }
            }
        }
        else{
            return false;
        }
        return false;
    }

}
