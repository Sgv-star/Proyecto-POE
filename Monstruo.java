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
    public boolean jugar(Campo campo, byte turno, Scanner scaner){
        Jugador atacante, defensor;
        Monstruo[] monstruosAtacantes, monstruosDefensores;
        Magia[] magiasAtacantes, magiasDefensoras;
        List<Carta> cementerioAtacante, cementerioDefensor; 
        byte indiceDelAtacante = 0, indiceDelEnemigo = 0;
        boolean monstruosAtacantesNoEstaVacio = false, monstruosDefensoresNoEstaVacio = false;
        if(turno%2==0){
            atacante = campo.getJugador1();
            defensor = campo.getJugador2();
            monstruosAtacantes = campo.getMonstruosEnCampoJugador1();
            magiasAtacantes = campo.getMagicasEnCampoJugador1();
            cementerioAtacante = campo.getCementerioJugador1();
            monstruosDefensores = campo.getMonstruosEnCampoJugador2();
            magiasDefensoras = campo.getMagicasEnCampoJugador2();
            cementerioDefensor = campo.getCementerioJugador2();
        }
        else{
            atacante = campo.getJugador2();
            defensor = campo.getJugador1();
            monstruosAtacantes = campo.getMonstruosEnCampoJugador2();
            magiasAtacantes = campo.getMagicasEnCampoJugador2();
            cementerioAtacante = campo.getCementerioJugador2();
            monstruosDefensores = campo.getMonstruosEnCampoJugador1();
            magiasDefensoras = campo.getMagicasEnCampoJugador1();
            cementerioDefensor = campo.getCementerioJugador1();
        }
        for(Monstruo m : monstruosAtacantes){
            if(m != null){
                for(Magia cm : magiasDefensoras){
                    if(cm != null && cm.getTipoHabilidadEspecial() == TipoHabilidadEspecial.ESPADAS_DE_LA_LUZ_REVELADORA && cm.isVisible()){
                        m.setPuedeAtacar(false);
                        break;
                    }
                }
                monstruosAtacantesNoEstaVacio = true;
            }
        }
        if(monstruosAtacantesNoEstaVacio){
            System.out.print("Escoja el número de su carta atacante: ");
            indiceDelAtacante = scaner.nextByte();
            scaner.nextLine();
            System.out.println("");
            if(monstruosAtacantes[indiceDelAtacante] == null && !puedeAtacar){
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
            else{
                monstruosDefensoresNoEstaVacio = false;
            }
        }
        if(monstruosAtacantes[indiceDelAtacante].isEnPosicionAtaque()){
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
                defensor.setLP((short) (defensor.getLP() - monstruosAtacantes[indiceDelAtacante].getAtaque()));
                if(defensor.getLP() < 0){
                    defensor.setLP((short) 0);
                }
                monstruosAtacantes[indiceDelAtacante].setPuedeAtacar(false);
                return true;
            }
            if(monstruosDefensoresNoEstaVacio && monstruosDefensores[indiceDelEnemigo].isEnPosicionAtaque()){
                if(monstruosAtacantes[indiceDelAtacante].getAtaque() > monstruosDefensores[indiceDelEnemigo].getAtaque()){
                    defensor.setLP((short) (defensor.getLP() + (monstruosDefensores[indiceDelEnemigo].getAtaque() - monstruosAtacantes[indiceDelAtacante].getAtaque())));
                    if(defensor.getLP() < 0){
                        defensor.setLP((short) 0);
                    }
                    cementerioDefensor.add(monstruosDefensores[indiceDelEnemigo]);
                    monstruosDefensores[indiceDelEnemigo] = null;
                    monstruosAtacantes[indiceDelAtacante].setPuedeAtacar(false);
                    return true;
                }
                else if(monstruosAtacantes[indiceDelAtacante].getAtaque() < monstruosDefensores[indiceDelEnemigo].getAtaque()){
                    atacante.setLP((short) (atacante.getLP() + (monstruosAtacantes[indiceDelAtacante].getAtaque() - monstruosDefensores[indiceDelEnemigo].getAtaque())));
                    if(atacante.getLP() < 0){
                        atacante.setLP((short) 0);
                    }
                    cementerioAtacante.add(monstruosAtacantes[indiceDelAtacante]);
                    monstruosAtacantes[indiceDelAtacante] = null;
                    return true;
                }
                else if(monstruosAtacantes[indiceDelAtacante].getAtaque() == monstruosDefensores[indiceDelEnemigo].getAtaque()){
                    cementerioDefensor.add(monstruosDefensores[indiceDelEnemigo]);
                    cementerioAtacante.add(monstruosAtacantes[indiceDelAtacante]);
                    monstruosDefensores[indiceDelEnemigo] = null;
                    monstruosAtacantes[indiceDelAtacante] = null;
                    return true;
                }
            }
            else if(monstruosDefensoresNoEstaVacio && !monstruosDefensores[indiceDelEnemigo].isEnPosicionAtaque()){
                if(monstruosAtacantes[indiceDelAtacante].getAtaque() > monstruosDefensores[indiceDelEnemigo].getDefensa()){
                    cementerioDefensor.add(monstruosDefensores[indiceDelEnemigo]);
                    monstruosDefensores[indiceDelEnemigo] = null;
                    monstruosAtacantes[indiceDelAtacante].setPuedeAtacar(false);
                    return true;
                }
                else if(monstruosAtacantes[indiceDelAtacante].getAtaque() < monstruosDefensores[indiceDelEnemigo].getDefensa()){
                    atacante.setLP((short) (atacante.getLP() + (monstruosAtacantes[indiceDelAtacante].getAtaque() - monstruosDefensores[indiceDelEnemigo].getDefensa())));
                    if(atacante.getLP() < 0){
                        atacante.setLP((short) 0);
                    }
                    monstruosAtacantes[indiceDelAtacante].setPuedeAtacar(false);
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
