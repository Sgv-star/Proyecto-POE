package controlador;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import modelo.*;
import vista.VistaDuelo;

public class ControladorTrampa {
    private Trampa modelo;
    private VistaDuelo vista;

    public ControladorTrampa(Trampa modelo, VistaDuelo vista) {
        this.modelo = modelo;
        this.vista = vista;
    }

    public void activarEfecto(Campo campo, byte turno, byte cartaAActivar, byte byteAux, String stringAux) {
        Jugador oponente = (turno % 2 == 0) ? campo.getJugador2() : campo.getJugador1();
        Jugador atacante = (turno % 2 == 0) ? campo.getJugador1() : campo.getJugador2();
        Monstruo[] monstruosOponente = (turno % 2 == 0) ? campo.getMonstruosJugador2() : campo.getMonstruosJugador1();
        Monstruo[] monstruosAtacante = (turno % 2 == 0) ? campo.getMonstruosJugador1() : campo.getMonstruosJugador2();
        Carta[] magiasYTrampasAtacante = (turno % 2 == 0) ? campo.getMagicasYTrampasJugador2() : campo.getMagicasYTrampasJugador1();
        Carta[] magiasYTrampasDefensor = (turno % 2 == 0) ? campo.getMagicasYTrampasJugador1() : campo.getMagicasYTrampasJugador2();
        List<Carta> cementerioAtacante = (turno % 2 == 0) ? campo.getCementerioJugador2() : campo.getCementerioJugador1();
        List<Carta> cementerioDefensor = (turno % 2 == 0) ? campo.getCementerioJugador1() : campo.getCementerioJugador2();
        Trampa trampa = (Trampa) magiasYTrampasAtacante[cartaAActivar];
        switch (modelo.getTipoHabilidad()) {
            case FUERZA_ESPEJO:
                destruirAtacantes(campo, turno);
                break;

            case CILINDRO_MAGICO:
                if(trampa.getTurnosActiva() < 1){
                    for(Monstruo m : monstruosAtacante){
                        if(m != null && m.getNombre().equals(stringAux)){
                            m.setAtaque((short) 0);
                            atacante.setPuntosVida((short) (atacante.getPuntosVida() - m.getAtaqueBase()));
                            break;
                        }
                    }
                    trampa.setTurnosActiva((byte) (trampa.getTurnosActiva()+1));
                    trampa.setEstaActivada(true);
                }
                else if(trampa.getTurnosActiva() > 0){
                    trampa.setTurnosActiva((byte) 0);
                    for(int j=0; j<5; j++){
                        if(monstruosAtacante[j] != null){
                            monstruosAtacante[j].setAtaque(monstruosAtacante[j].getAtaqueBase());
                        }
                    }
                    trampa.setEstaActivada(false);
                    cementerioDefensor.add(magiasYTrampasDefensor[cartaAActivar]);
                    magiasYTrampasDefensor[cartaAActivar] = null;
                }
                break;

            case TRIBUTO_TORRENCIAL:
                for(int j=0; j<5; j++){
                    if(monstruosAtacante[j] != null){
                        cementerioAtacante.add(monstruosAtacante[j]);
                        monstruosAtacante[j] = null;
                    }
                    if(monstruosOponente[j] != null){
                        cementerioDefensor.add(monstruosOponente[j]);
                        monstruosOponente[j] = null;
                    }
                }
                trampa.setEstaActivada(false);
                cementerioDefensor.add(magiasYTrampasDefensor[cartaAActivar]);
                magiasYTrampasDefensor[cartaAActivar] = null;
                break;

            case ARMADURA_SAKURETSU:
                if(monstruosAtacante[byteAux] != null){
                    cementerioAtacante.add(monstruosAtacante[byteAux]);
                    monstruosAtacante[byteAux] = null;
                }
                trampa.setEstaActivada(false);
                cementerioDefensor.add(magiasYTrampasDefensor[cartaAActivar]);
                magiasYTrampasDefensor[cartaAActivar] = null;
                break;

            case LLAMADA_CONDENADOS:
                byte indiceMonstruoARevivir=0;
                for(int j=0; j<cementerioDefensor.size(); j++){
                    if(cementerioDefensor.get(j).getNombre().equals(stringAux) && cementerioDefensor.get(j) instanceof Monstruo){
                        indiceMonstruoARevivir = (byte) j;
                        break;
                    }
                }
                for(int j=0; j<5; j++){
                    if(monstruosOponente[j] == null && cementerioDefensor.get(indiceMonstruoARevivir) instanceof Monstruo){
                        monstruosOponente[j] = (Monstruo) cementerioDefensor.remove(indiceMonstruoARevivir);
                        monstruosOponente[j].setEnPosicionAtaque(true);
                        break;
                    }
                }
                trampa.setEstaActivada(false);
                cementerioDefensor.add(magiasYTrampasDefensor[cartaAActivar]);
                magiasYTrampasDefensor[cartaAActivar] = null;
                break;

            case SOMBREROS_MAGICOS:
                if(trampa.getTurnosActiva() < 1){
                    byte indiceMonstruoAOcultar=0;
                    for(int j=0; j<5; j++){
                        if(monstruosOponente[j] != null){
                            if(monstruosOponente[j].getNombre().equals(stringAux)){
                                indiceMonstruoAOcultar = (byte) j;
                                break;
                            }
                        }
                    }
                    List<Monstruo> cartasAOcultar = new ArrayList<> ();
                    cartasAOcultar.add(monstruosOponente[indiceMonstruoAOcultar]);
                    cartasAOcultar.add(new Monstruo("Sombrero 1", "Una carta mágica que oculta a un monstruo", (byte) 0, (short) 0, (short) 0));
                    cartasAOcultar.add(new Monstruo("Sombrero 2", "Una carta mágica que oculta a un monstruo", (byte) 0, (short) 0, (short) 0));
                    Collections.shuffle(cartasAOcultar);
                    monstruosOponente[indiceMonstruoAOcultar] = null;
                    for(int j=0; j<5; j++){
                        if(monstruosOponente[j] == null && cartasAOcultar.size() > 0){
                            monstruosOponente[j] = cartasAOcultar.remove(0);
                        }
                    }
                    for(int j=0; j<oponente.getMazo().size(); j++){
                        if(oponente.getMazo().get(j).getNombre().equals(stringAux)){
                            cementerioDefensor.add(oponente.getMazo().remove(j));
                        }
                        else if(oponente.getMazo().get(j).getNombre().equals(stringAux)){
                            cementerioDefensor.add(oponente.getMazo().remove(j));
                        }
                    }
                    trampa.setTurnosActiva((byte) (trampa.getTurnosActiva()+1));
                    trampa.setEstaActivada(true);
                }
                else if(trampa.getTurnosActiva() > 0){
                    trampa.setTurnosActiva((byte) 0);
                    for(int j=0; j<5; j++){
                        if(monstruosOponente[j] != null){
                            if(monstruosOponente[j].getNombre().equals("Sombrero 1") || monstruosOponente[j].getNombre().equals("Sombrero 2")){
                                monstruosOponente[j] = null;
                            }
                        }
                    }
                    trampa.setEstaActivada(false);
                    cementerioDefensor.add(magiasYTrampasDefensor[cartaAActivar]);
                    magiasYTrampasDefensor[cartaAActivar] = null;
                }
                break;

            case WABOKU:
                if(trampa.getTurnosActiva() < 1){
                    for(int j=0; j<5; j++){
                        if(monstruosAtacante[j] != null){
                            monstruosAtacante[j].setAtaque((short) 0);
                        }
                    }
                    trampa.setTurnosActiva((byte) (trampa.getTurnosActiva()+1));
                    trampa.setEstaActivada(true);
                }
                else if(trampa.getTurnosActiva() > 0){
                    trampa.setTurnosActiva((byte) 0);
                    for(int j=0; j<5; j++){
                        if(monstruosAtacante[j] != null){
                            monstruosAtacante[j].setAtaque(monstruosAtacante[j].getAtaqueBase());
                        }
                    }
                    trampa.setEstaActivada(false);
                    cementerioDefensor.add(magiasYTrampasDefensor[cartaAActivar]);
                    magiasYTrampasDefensor[cartaAActivar] = null;
                }
                break;

            case MURO_ESPEJO:
                if(trampa.getTurnosActiva() < 1){
                    for(int j=0; j<5; j++){
                        if(monstruosAtacante[j] != null){
                            monstruosAtacante[j].setAtaque((short) (monstruosAtacante[j].getAtaque() / 2));
                        }
                    }
                    trampa.setTurnosActiva((byte) (trampa.getTurnosActiva()+1));
                    trampa.setEstaActivada(true);
                }
                else if(trampa.getTurnosActiva() > 0){
                    trampa.setTurnosActiva((byte) 0);
                    if(byteAux == 1){
                        atacante.setPuntosVida((short) (atacante.getPuntosVida()-2000));
                    }
                    else if(byteAux == 2){
                        for(int j=0; j<5; j++){
                            if(monstruosAtacante[j] != null){
                                monstruosAtacante[j].setAtaque(monstruosAtacante[j].getAtaqueBase());
                            }
                        }
                        trampa.setEstaActivada(false);
                        cementerioDefensor.add(magiasYTrampasDefensor[cartaAActivar]);
                        magiasYTrampasDefensor[cartaAActivar] = null;
                    }
                }
                break;

            case REPRESION:
                if(trampa.getTurnosActiva() < 1){
                    for(int j=0; j<5; j++){
                        if(monstruosAtacante[j] != null && monstruosAtacante[j].getNombre().equals(stringAux)){
                            for(int k=0; k<5; k++){
                                if(monstruosOponente[k] == null){
                                    monstruosOponente[k] = monstruosAtacante[j];
                                    monstruosAtacante[j] = null;
                                    break;
                                }
                            }
                            break;
                        }

                    }
                    trampa.setTurnosActiva((byte) (trampa.getTurnosActiva()+1));
                    trampa.setEstaActivada(true);
                }
                else if(trampa.getTurnosActiva() > 0){
                    for(int j=0; j<5; j++){
                        if(monstruosOponente[j] != null && monstruosOponente[j].getNombre().equals(trampa.getMonstruoRobado())){
                            for(int k=0; k<5; k++){
                                if(monstruosAtacante[k] == null){
                                    monstruosAtacante[k] = monstruosOponente[j];
                                    monstruosOponente[j] = null;
                                    break;
                                }
                            }
                            break;
                        }
                    }
                    trampa.setEstaActivada(false);
                    trampa.setTurnosActiva((byte) 0);
                    trampa.setMonstruoRobado("");
                    cementerioDefensor.add(magiasYTrampasDefensor[cartaAActivar]);
                    magiasYTrampasDefensor[cartaAActivar] = null;
                }
                break;

            case DRENAJE_HABILIDAD:
                oponente.setPuntosVida(((short) (oponente.getPuntosVida()-1000)));
                if(trampa.getTurnosActiva() < 1){
                    for(int j=0; j<5; j++){
                        if(monstruosAtacante[j] != null){
                            monstruosAtacante[j].setAtaque((short) (0));
                        }
                    }
                    trampa.setTurnosActiva((byte) (trampa.getTurnosActiva()+1));
                    trampa.setEstaActivada(true);
                }
                else if(trampa.getTurnosActiva() > 0){
                    for(int j=0; j<5; j++){
                        if(monstruosAtacante[j] != null){
                            monstruosAtacante[j].setAtaque(monstruosAtacante[j].getAtaqueBase());
                        }
                    }
                    trampa.setEstaActivada(false);
                    trampa.setTurnosActiva((byte) 0);
                    cementerioDefensor.add(magiasYTrampasDefensor[cartaAActivar]);
                    magiasYTrampasDefensor[cartaAActivar] = null;
                }
                break;
        }
        campo.reconstruirControlCampo();
        vista.actualizarTablero();
    }

    private void destruirAtacantes(Campo campo, byte turno) {
        Monstruo[] atacantes = (turno % 2 == 0) ? campo.getMonstruosJugador1() : campo.getMonstruosJugador2();
        for (int i = 0; i < 5; i++) {
            if (atacantes[i] != null && atacantes[i].estaEnPosicionAtaque()) {
                atacantes[i] = null;
            }
        }
    }

}
