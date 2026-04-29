
import java.util.*;

public class Magia extends Carta implements Activable{

    private TipoHabilidadEspecialMagia tipoHabilidadEspecial;
    private byte turnosActiva;

    public Magia(String nombre, String cuadroDeTexto, TipoHabilidadEspecialMagia tipoHabilidadEspecial) {
        super(nombre, cuadroDeTexto, true);
        this.tipoHabilidadEspecial = tipoHabilidadEspecial;
        this.turnosActiva = 0;
    }

    public TipoHabilidadEspecialMagia getTipoHabilidadEspecialMagia() {
        return tipoHabilidadEspecial;
    }
    public byte getTurnosActiva(){
        return turnosActiva;
    }

    protected void setTipoHabilidadEspecialMagia(TipoHabilidadEspecialMagia tipoHabilidadEspecial) {
        this.tipoHabilidadEspecial = tipoHabilidadEspecial;
    }
    protected void setTurnosActiva(byte turnosActiva){
        this.turnosActiva = turnosActiva;
    }

    @Override
    public void ActivarEfecto(Campo campo, byte turno, byte cartaAActivar, byte byteAux, String stringAux, String stringAux2, String stringAux3, List<Monstruo> lista){
        Jugador atacante, defensor;
        Monstruo[] monstruosAtacantes, monstruosDefensores;
        Carta[] magiasYTrampasAtacantes, magiasYTrampasDefensoras;
        List<Carta> cementerioAtacante, cementerioDefensor;
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
        switch(getTipoHabilidadEspecialMagia()){

            case MONSTRUO_RENACIDO:
                for(int j = 0; j<campo.getCementerioJugador1().size(); j++){
                    if(campo.getCementerioJugador1().get(j).getNombre().equals(stringAux)){
                        for(int k = 0; k<5; k++){
                            if( monstruosAtacantes[k] == null){
                                monstruosAtacantes[k] = (Monstruo) campo.getCementerioJugador1().get(j);
                                campo.getCementerioJugador1().remove(j);
                                break;
                            }
                        }
                    }
                }
                for(int j = 0; j<campo.getCementerioJugador2().size(); j++){
                    if(campo.getCementerioJugador2().get(j).getNombre().equals(stringAux)){
                        for(int k = 0; k<5; k++){
                            if( monstruosAtacantes[k] == null){
                                monstruosAtacantes[k] = (Monstruo) campo.getCementerioJugador2().get(j);
                                campo.getCementerioJugador2().remove(j);
                                break;
                            }
                        }
                    }
                }
                cementerioAtacante.add(magiasYTrampasAtacantes[cartaAActivar]);
                magiasYTrampasAtacantes[cartaAActivar] = null;
                break;

            case AGUJERO_NEGRO:
                for(int j = 0; j<5; j++){
                    if(monstruosAtacantes[j] != null){
                        cementerioAtacante.add(monstruosAtacantes[j]);
                    }
                    if(monstruosDefensores[j] != null){
                        cementerioDefensor.add(monstruosDefensores[j]);
                    }
                    monstruosAtacantes[j] = null;
                    monstruosDefensores[j] = null;
                }
                cementerioAtacante.add(magiasYTrampasAtacantes[cartaAActivar]);
                magiasYTrampasAtacantes[cartaAActivar] = null;
                break;

            case TIFON_DEL_ESPACIO_MISTICO:
                for(int j=0; j<5; j++){
                    if(magiasYTrampasDefensoras[j] != null && magiasYTrampasDefensoras[j].getNombre().equals(stringAux)){
                        cementerioDefensor.add(magiasYTrampasDefensoras[j]);
                        magiasYTrampasDefensoras[j] = null;
                        break;
                    }
                }
                cementerioAtacante.add(magiasYTrampasAtacantes[cartaAActivar]);
                magiasYTrampasAtacantes[cartaAActivar] = null;
                break;

            case ESPADAS_DE_LA_LUZ_REVELADORA:
                if(getTurnosActiva() < 3){
                    for(int j=0; j<5; j++){
                        if(monstruosDefensores[j] != null){
                            if(monstruosDefensores[j].isPuedeAtacar()){
                                monstruosDefensores[j].setPuedeAtacar(false);
                            }
                            if(!monstruosDefensores[j].isVisible()){
                                monstruosDefensores[j].setVisible(true);
                            }
                        }
                    }
                    setTurnosActiva((byte) (getTurnosActiva()+1));
                    break;
                }
                else{
                    setTurnosActiva((byte) 0);
                    cementerioAtacante.add(magiasYTrampasAtacantes[cartaAActivar]);
                    magiasYTrampasAtacantes[cartaAActivar] = null;
                    for(int j = 0; j<5; j++){
                        if(monstruosDefensores[j] != null){
                            if(!monstruosDefensores[j].isPuedeAtacar()){
                                monstruosDefensores[j].setPuedeAtacar(true);
                            }
                        }
                    }
                    break;
                }

            case OLLA_DE_LA_CODICIA:
                if(atacante.getMazo().size()>=2){
                    atacante.getMano().add(atacante.getMazo().remove(0));
                    atacante.getMano().add(atacante.getMazo().remove(0));
                }
                else if(atacante.getMazo().size()>=1){
                    atacante.getMano().add(atacante.getMazo().remove(0));
                }
                cementerioAtacante.add(magiasYTrampasAtacantes[cartaAActivar]);
                magiasYTrampasAtacantes[cartaAActivar] = null;
                break;

            case ENTIERRO_INSENSATO:
                for(int j=0; j<atacante.getMazo().size(); j++){
                    if(atacante.getMazo().get(j).getNombre().equals(stringAux)){
                        cementerioAtacante.add(atacante.getMazo().remove(j));
                        break;
                    }
                }
                cementerioAtacante.add(magiasYTrampasAtacantes[cartaAActivar]);
                magiasYTrampasAtacantes[cartaAActivar] = null;
                break;

            case RAIGEKI:
                for(int j=0; j<5; j++){
                    if(monstruosDefensores[j] != null){
                        cementerioDefensor.add(monstruosDefensores[j]);
                        monstruosDefensores[j] = null;
                    }
                }
                cementerioAtacante.add(magiasYTrampasAtacantes[cartaAActivar]);
                magiasYTrampasAtacantes[cartaAActivar] = null;
                break;

            case MIL_CUCHILLOS:
                boolean tieneMagoOscuro = false;
                for(Monstruo m : monstruosAtacantes){
                    if(m != null && m.getNombre().equals("Mago Oscuro")){
                        tieneMagoOscuro = true;
                        break;
                    }
                }
                if(tieneMagoOscuro){
                    for(int j=0; j<5; j++){
                        if(monstruosDefensores[j] != null && monstruosDefensores[j].getNombre().equals(stringAux)){
                            cementerioDefensor.add(monstruosDefensores[j]);
                            monstruosDefensores[j] = null;
                            break;
                        }
                    }
                }
                cementerioAtacante.add(magiasYTrampasAtacantes[cartaAActivar]);
                magiasYTrampasAtacantes[cartaAActivar] = null;
                break;

            case UNIDAD:
                if(getTurnosActiva() == (byte) 0){
                    short sumaDeDefensa = 0;
                    for(Monstruo m : monstruosAtacantes){
                        if(m != null){
                            sumaDeDefensa += m.getDefensa();
                        }
                    }
                    for(int j=0; j<5; j++){
                        if(monstruosAtacantes[j] != null && monstruosAtacantes[j].getNombre().equals(stringAux)){
                            monstruosAtacantes[j].setDefensa(sumaDeDefensa);
                        }
                    }
                    setTurnosActiva((byte) (getTurnosActiva()+1));
                }
                else if(getTurnosActiva() == (byte) 1){
                    for(Monstruo m : monstruosAtacantes){
                        if(m != null){
                            m.setDefensa(m.getDefensaBase());
                        }
                    }
                    setTurnosActiva((byte) 0);
                    cementerioAtacante.add(magiasYTrampasAtacantes[cartaAActivar]);
                    magiasYTrampasAtacantes[cartaAActivar] = null;
                }
                break;

            case POLIMERIZACION:
                Monstruo monstruoFusion = new Monstruo("Monstruo de Fusión", "Un monstruo creado a partir de Polimerización.", (byte) 0, (short) 0, (short) 0);
                for(int j=0; j<lista.size(); j++){
                    monstruoFusion.setNivel((byte) (monstruoFusion.getNivel() + lista.get(j).getNivel()));
                    monstruoFusion.setAtaqueBase((short) (monstruoFusion.getAtaqueBase() + lista.get(j).getAtaque()));
                    monstruoFusion.setDefensaBase((short) (monstruoFusion.getDefensaBase() + lista.get(j).getDefensa()));
                    cementerioAtacante.add(lista.get(j));
                }
                for(int j=0; j<5; j++){
                    if(monstruosAtacantes[j] == null){
                        monstruosAtacantes[j] = monstruoFusion;
                        break;
                    }
                }
                cementerioAtacante.add(magiasYTrampasAtacantes[cartaAActivar]);
                magiasYTrampasAtacantes[cartaAActivar] = null;
                break;

        }
    }

    @Override
    public boolean jugar(Campo campo, byte turno, byte cartaAActivar, byte byteAux, String stringAux, String stringAux2, String stringAux3, List<Monstruo> lista){
        ActivarEfecto(campo, turno, cartaAActivar, byteAux, stringAux, stringAux2, stringAux3, lista);
        return true;
    }
    
}
