package src;
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
        else{
            System.out.println("No se puede cambiar de posición más de una vez por turno.");
        }
    }
    public void setEnPosicionAtaque() {
        if (!yaCambioPosicionEnEsteTurno){
            this.enPosicionAtaque = !enPosicionAtaque;
            this.yaCambioPosicionEnEsteTurno = true;
        }
        else{
            System.out.println("No se puede cambiar de posición más de una vez por turno.");
        }
    }
    public void setYaCambioPosicionEnEsteTurno(boolean yaCambioPosicionEnEsteTurno){
        this.yaCambioPosicionEnEsteTurno = yaCambioPosicionEnEsteTurno;
    }
    public void setPuedeAtacar(boolean puedeAtacar){
        this.puedeAtacar = puedeAtacar;
    }

    @Override
    public boolean jugar(Campo campo, byte turno, Scanner scaner, byte cartaAActivar, byte indiceDelEnemigo){
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
            if(monstruosAtacantes[cartaAActivar] == null && !puedeAtacar){
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
                System.out.print("Escoja el número de que carta atacar o digite 6 para no atacar: ");
                indiceDelEnemigo = scaner.nextByte();
                scaner.nextLine();
                System.out.println("");
                if(indiceDelEnemigo == 6 || monstruosDefensores[indiceDelEnemigo] == null) {
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
            if(monstruosDefensoresNoEstaVacio && monstruosDefensores[indiceDelEnemigo].isEnPosicionAtaque()){
                if(monstruosAtacantes[cartaAActivar].getAtaque() > monstruosDefensores[indiceDelEnemigo].getAtaque()){
                    defensor.setLP((short) (defensor.getLP() + (monstruosDefensores[indiceDelEnemigo].getAtaque() - monstruosAtacantes[cartaAActivar].getAtaque())));
                    if(defensor.getLP() < 0){
                        defensor.setLP((short) 0);
                    }
                    cementerioDefensor.add(monstruosDefensores[indiceDelEnemigo]);
                    monstruosDefensores[indiceDelEnemigo] = null;
                    monstruosAtacantes[cartaAActivar].setPuedeAtacar(false);
                    return true;
                }
                else if(monstruosAtacantes[cartaAActivar].getAtaque() < monstruosDefensores[indiceDelEnemigo].getAtaque()){
                    atacante.setLP((short) (atacante.getLP() + (monstruosAtacantes[cartaAActivar].getAtaque() - monstruosDefensores[indiceDelEnemigo].getAtaque())));
                    if(atacante.getLP() < 0){
                        atacante.setLP((short) 0);
                    }
                    cementerioAtacante.add(monstruosAtacantes[cartaAActivar]);
                    monstruosAtacantes[cartaAActivar] = null;
                    return true;
                }
                else if(monstruosAtacantes[cartaAActivar].getAtaque() == monstruosDefensores[indiceDelEnemigo].getAtaque()){
                    cementerioDefensor.add(monstruosDefensores[indiceDelEnemigo]);
                    cementerioAtacante.add(monstruosAtacantes[cartaAActivar]);
                    monstruosDefensores[indiceDelEnemigo] = null;
                    monstruosAtacantes[cartaAActivar] = null;
                    return true;
                }
            }
            else if(monstruosDefensoresNoEstaVacio && !monstruosDefensores[indiceDelEnemigo].isEnPosicionAtaque()){
                if(monstruosAtacantes[cartaAActivar].getAtaque() > monstruosDefensores[indiceDelEnemigo].getDefensa()){
                    cementerioDefensor.add(monstruosDefensores[indiceDelEnemigo]);
                    monstruosDefensores[indiceDelEnemigo] = null;
                    monstruosAtacantes[cartaAActivar].setPuedeAtacar(false);
                    return true;
                }
                else if(monstruosAtacantes[cartaAActivar].getAtaque() < monstruosDefensores[indiceDelEnemigo].getDefensa()){
                    atacante.setLP((short) (atacante.getLP() + (monstruosAtacantes[cartaAActivar].getAtaque() - monstruosDefensores[indiceDelEnemigo].getDefensa())));
                    if(atacante.getLP() < 0){
                        atacante.setLP((short) 0);
                    }
                    monstruosAtacantes[cartaAActivar].setPuedeAtacar(false);
                    return true;
                }
            }
        }
        else{
            System.out.println("La carta no puede atacar porque está en posición de defensa");
            return false;
        }
        return false;
    }

}
