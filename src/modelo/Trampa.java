package modelo;

import java.util.*;

public class Trampa extends Carta implements Activable{

    private TipoHabilidadEspecialTrampa tipoHabilidadEspecial;
    private byte turnosActiva;
    private String monstruoARobarPorUnTurno;
    private TipoHabilidadEspecialTrampa momentoDeActivacion;
    private boolean activada;

    public Trampa(String nombre, String cuadroDeTexto, TipoHabilidadEspecialTrampa tipoHabilidadEspecial, TipoHabilidadEspecialTrampa momentoDeActivacion) {
        super(nombre, cuadroDeTexto, false);
        this.tipoHabilidadEspecial = tipoHabilidadEspecial;
        this.turnosActiva = 0;
        this.momentoDeActivacion = momentoDeActivacion;
        this.activada = false;
    }

    public TipoHabilidadEspecialTrampa getTipoHabilidadEspecialTrampa() {
        return tipoHabilidadEspecial;
    }
    public byte getTurnosActiva(){
        return turnosActiva;
    }
    public String getMonstruoARobarPorUnTurno(){
        return monstruoARobarPorUnTurno;
    }
    public TipoHabilidadEspecialTrampa getMomentoDeActivacion() {
        return momentoDeActivacion;
    }
    public boolean isActivada() {
        return activada;
    }

    protected void setTipoHabilidadEspecialTrampa(TipoHabilidadEspecialTrampa tipoHabilidadEspecial) {
        this.tipoHabilidadEspecial = tipoHabilidadEspecial;
    }
    protected void setTurnosActiva(byte turnosActiva){
        this.turnosActiva = turnosActiva;
    }
    public void setMonstruoARobarPorUnTurno(String monstruoARobarPorUnTurno){
        this.monstruoARobarPorUnTurno = monstruoARobarPorUnTurno;
    }
    protected void setMomentoDeActivacion(TipoHabilidadEspecialTrampa momentoDeActivacion) {
        this.momentoDeActivacion = momentoDeActivacion;
    }
    protected void setActivada(boolean activada){
        this.activada= activada;
    }

    @Override
    public void ActivarEfecto(Campo campo, byte turno, byte cartaAActivar, byte byteAux, String stringAux, String stringAux2, String stringAux3, List<Monstruo> lista) {
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
        if(magiasYTrampasDefensoras[cartaAActivar] != null && magiasYTrampasDefensoras[cartaAActivar] instanceof Trampa){
            magiasYTrampasDefensoras[cartaAActivar].setVisible(true);
        }
        if(magiasYTrampasDefensoras[cartaAActivar].isVisible() && magiasYTrampasDefensoras[cartaAActivar] instanceof Trampa){
            Trampa trampa = (Trampa) magiasYTrampasDefensoras[cartaAActivar];
            switch(trampa.getTipoHabilidadEspecialTrampa()){

                case FUERZA_DE_ESPEJO:
                    for(int j=0; j<5; j++){
                        if(monstruosAtacantes[j] != null && monstruosAtacantes[j].isEnPosicionAtaque()){
                            monstruosAtacantes[j] = null;
                        }
                    }
                    setActivada(false);
                    cementerioDefensor.add(magiasYTrampasDefensoras[cartaAActivar]);
                    magiasYTrampasDefensoras[cartaAActivar] = null;
                    break;

                case CILINDRO_MAGICO:
                    if(trampa.getTurnosActiva() < 1){
                        for(Monstruo m : monstruosAtacantes){
                            if(m != null && m.getNombre().equals(stringAux)){
                                m.setAtaque((short) 0);
                                atacante.setLP((short) (atacante.getLP() - m.getAtaqueBase()));
                                break;
                            }
                        }
                        trampa.setTurnosActiva((byte) (trampa.getTurnosActiva()+1));
                        setActivada(true);
                    }
                    else if(trampa.getTurnosActiva() > 0){
                        trampa.setTurnosActiva((byte) 0);
                        for(int j=0; j<5; j++){
                            if(monstruosAtacantes[j] != null){
                                monstruosAtacantes[j].setAtaque(monstruosAtacantes[j].getAtaqueBase());
                            }
                        }
                        setActivada(false);
                        cementerioDefensor.add(magiasYTrampasDefensoras[cartaAActivar]);
                        magiasYTrampasDefensoras[cartaAActivar] = null;
                    }
                    break;

                case TRIBUTO_TORRENCIAL:
                    for(int j=0; j<5; j++){
                        if(monstruosAtacantes[j] != null){
                            cementerioAtacante.add(monstruosAtacantes[j]);
                            monstruosAtacantes[j] = null;
                        }
                        if(monstruosDefensores[j] != null){
                            cementerioDefensor.add(monstruosDefensores[j]);
                            monstruosDefensores[j] = null;
                        }
                    }
                    setActivada(false);
                    cementerioDefensor.add(magiasYTrampasDefensoras[cartaAActivar]);
                    magiasYTrampasDefensoras[cartaAActivar] = null;
                    break;

                case ARMADURA_DE_SAKURETSU:
                    if(monstruosAtacantes[byteAux] != null){
                        cementerioAtacante.add(monstruosAtacantes[byteAux]);
                        monstruosAtacantes[byteAux] = null;
                    }
                    setActivada(false);
                    cementerioDefensor.add(magiasYTrampasDefensoras[cartaAActivar]);
                    magiasYTrampasDefensoras[cartaAActivar] = null;
                    break;

                case LLAMADA_DE_LOS_CONDENADOS:
                    byte indiceMonstruoARevivir=0;
                    for(int j=0; j<cementerioDefensor.size(); j++){
                        if(cementerioDefensor.get(j).getNombre().equals(stringAux) && cementerioDefensor.get(j) instanceof Monstruo){
                            indiceMonstruoARevivir = (byte) j;
                            break;
                        }
                    }
                    for(int j=0; j<5; j++){
                        if(monstruosDefensores[j] == null && cementerioDefensor.get(indiceMonstruoARevivir) instanceof Monstruo){
                            monstruosDefensores[j] = (Monstruo) cementerioDefensor.remove(indiceMonstruoARevivir);
                            monstruosDefensores[j].setEnPosicionAtaque(true);
                            break;
                        }
                    }
                    setActivada(false);
                    cementerioDefensor.add(magiasYTrampasDefensoras[cartaAActivar]);
                    magiasYTrampasDefensoras[cartaAActivar] = null;
                    break;

                case SOMBREROS_MAGICOS:
                    if(trampa.getTurnosActiva() < 1){
                        byte indiceMonstruoAOcultar=0;
                        for(int j=0; j<5; j++){
                            if(monstruosDefensores[j] != null){
                                if(monstruosDefensores[j].getNombre().equals(stringAux)){
                                    indiceMonstruoAOcultar = (byte) j;
                                    break;
                                }
                            }
                        }
                        List<Monstruo> cartasAOcultar = new ArrayList<> ();
                        cartasAOcultar.add(monstruosDefensores[indiceMonstruoAOcultar]);
                        cartasAOcultar.add(new Monstruo("Sombrero 1", "Una carta mágica que oculta a un monstruo", (byte) 0, (short) 0, (short) 0));
                        cartasAOcultar.add(new Monstruo("Sombrero 2", "Una carta mágica que oculta a un monstruo", (byte) 0, (short) 0, (short) 0));
                        Collections.shuffle(cartasAOcultar);
                        monstruosDefensores[indiceMonstruoAOcultar] = null;
                        for(int j=0; j<5; j++){
                            if(monstruosDefensores[j] == null && cartasAOcultar.size() > 0){
                                monstruosDefensores[j] = cartasAOcultar.remove(0);
                            }
                        }
                        for(int j=0; j<defensor.getMazo().size(); j++){
                            if(defensor.getMazo().get(j).getNombre().equals(stringAux2)){
                                cementerioDefensor.add(defensor.getMazo().remove(j));
                            }
                            else if(defensor.getMazo().get(j).getNombre().equals(stringAux3)){
                                cementerioDefensor.add(defensor.getMazo().remove(j));
                            }
                        }
                        trampa.setTurnosActiva((byte) (trampa.getTurnosActiva()+1));
                        setActivada(true);
                    }
                    else if(trampa.getTurnosActiva() > 0){
                        trampa.setTurnosActiva((byte) 0);
                        for(int j=0; j<5; j++){
                            if(monstruosDefensores[j] != null){
                                if(monstruosDefensores[j].getNombre().equals("Sombrero 1") || monstruosDefensores[j].getNombre().equals("Sombrero 2")){
                                    monstruosDefensores[j] = null;
                                }
                            }
                        }
                        setActivada(false);
                        cementerioDefensor.add(magiasYTrampasDefensoras[cartaAActivar]);
                        magiasYTrampasDefensoras[cartaAActivar] = null;
                    }
                    break;

                case WABOKU:
                    if(trampa.getTurnosActiva() < 1){
                        for(int j=0; j<5; j++){
                            if(monstruosAtacantes[j] != null){
                                monstruosAtacantes[j].setAtaque((short) 0);
                            }
                        }
                        trampa.setTurnosActiva((byte) (trampa.getTurnosActiva()+1));
                        setActivada(true);
                    }
                    else if(trampa.getTurnosActiva() > 0){
                        trampa.setTurnosActiva((byte) 0);
                        for(int j=0; j<5; j++){
                            if(monstruosAtacantes[j] != null){
                                monstruosAtacantes[j].setAtaque(monstruosAtacantes[j].getAtaqueBase());
                            }
                        }
                        setActivada(false);
                        cementerioDefensor.add(magiasYTrampasDefensoras[cartaAActivar]);
                        magiasYTrampasDefensoras[cartaAActivar] = null;
                    }
                    break;

                case MURO_DE_ESPEJO:
                    if(trampa.getTurnosActiva() < 1){
                        for(int j=0; j<5; j++){
                            if(monstruosAtacantes[j] != null){
                                monstruosAtacantes[j].setAtaque((short) (monstruosAtacantes[j].getAtaque() / 2));
                            }
                        }
                        trampa.setTurnosActiva((byte) (trampa.getTurnosActiva()+1));
                        setActivada(true);
                    }
                    else if(trampa.getTurnosActiva() > 0){
                        trampa.setTurnosActiva((byte) 0);
                        if(byteAux == 1){
                            atacante.setLP((short) (atacante.getLP()-2000));
                        }
                        else if(byteAux == 2){
                            for(int j=0; j<5; j++){
                                if(monstruosAtacantes[j] != null){
                                    monstruosAtacantes[j].setAtaque(monstruosAtacantes[j].getAtaqueBase());
                                }
                            }
                            setActivada(false);
                            cementerioDefensor.add(magiasYTrampasDefensoras[cartaAActivar]);
                            magiasYTrampasDefensoras[cartaAActivar] = null;
                        }
                    }
                    break;

                case REPRESION:
                    if(trampa.getTurnosActiva() < 1){
                        for(int j=0; j<5; j++){
                            if(monstruosAtacantes[j] != null && monstruosAtacantes[j].getNombre().equals(stringAux)){
                                for(int k=0; k<5; k++){
                                    if(monstruosDefensores[k] == null){
                                        monstruosDefensores[k] = monstruosAtacantes[j];
                                        monstruosAtacantes[j] = null;
                                        break;
                                    }
                                }
                                break;
                            }
                            
                        }
                        trampa.setTurnosActiva((byte) (trampa.getTurnosActiva()+1));
                        setActivada(true);
                    }
                    else if(trampa.getTurnosActiva() > 0){
                        for(int j=0; j<5; j++){
                            if(monstruosDefensores[j] != null && monstruosDefensores[j].getNombre().equals(trampa.getMonstruoARobarPorUnTurno())){
                                for(int k=0; k<5; k++){
                                    if(monstruosAtacantes[k] == null){
                                        monstruosAtacantes[k] = monstruosDefensores[j];
                                        monstruosDefensores[j] = null;
                                        break;
                                    }
                                }
                                break;
                            }
                        }
                        setActivada(false);
                        setTurnosActiva((byte) 0);
                        setMonstruoARobarPorUnTurno("");
                        cementerioDefensor.add(magiasYTrampasDefensoras[cartaAActivar]);
                        magiasYTrampasDefensoras[cartaAActivar] = null;
                    }
                    break;

                case DRENAJE_DE_HABILIDAD:
                    defensor.setLP((short) (defensor.getLP()-1000));
                    if(trampa.getTurnosActiva() < 1){
                        for(int j=0; j<5; j++){
                            if(monstruosAtacantes[j] != null){
                                monstruosAtacantes[j].setAtaque((short) (0));
                            }
                        }
                        trampa.setTurnosActiva((byte) (trampa.getTurnosActiva()+1));
                        setActivada(true);
                    }
                    else if(trampa.getTurnosActiva() > 0){
                        for(int j=0; j<5; j++){
                            if(monstruosAtacantes[j] != null){
                                monstruosAtacantes[j].setAtaque(monstruosAtacantes[j].getAtaqueBase());
                            }
                        }
                        setActivada(false);
                        setTurnosActiva((byte) 0);
                        cementerioDefensor.add(magiasYTrampasDefensoras[cartaAActivar]);
                        magiasYTrampasDefensoras[cartaAActivar] = null;
                    }
                    break;

                    case null, default:

            }
        }
    }

    @Override
    public boolean jugar(Campo campo, byte turno, byte cartaAActivar, byte byteAux, String stringAux, String stringAux2, String stringAux3, List<Monstruo> lista) {
        ActivarEfecto(campo, turno, cartaAActivar, byteAux, stringAux, stringAux2, stringAux3, lista);
        return true;
    }

}
