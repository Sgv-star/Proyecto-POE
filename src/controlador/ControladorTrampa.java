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
        Jugador oponente = (turno % 2 == 0) ? campo.obtenerJugador2() : campo.obtenerJugador1();
        Jugador atacante = (turno % 2 == 0) ? campo.obtenerJugador1() : campo.obtenerJugador2();
        Monstruo[] monstruosOponente = (turno % 2 == 0) ? campo.obtenerMonstruosJugador2() : campo.obtenerMonstruosJugador1();
        Monstruo[] monstruosAtacante = (turno % 2 == 0) ? campo.obtenerMonstruosJugador1() : campo.obtenerMonstruosJugador2();
        Carta[] magiasYTrampasAtacante = (turno % 2 == 0) ? campo.obtenerMagicasYTrampasJugador2() : campo.obtenerMagicasYTrampasJugador1();
        Carta[] magiasYTrampasDefensor = (turno % 2 == 0) ? campo.obtenerMagicasYTrampasJugador1() : campo.obtenerMagicasYTrampasJugador2();
        List<Carta> cementerioAtacante = (turno % 2 == 0) ? campo.obtenerCementerioJugador2() : campo.obtenerCementerioJugador1();
        List<Carta> cementerioDefensor = (turno % 2 == 0) ? campo.obtenerCementerioJugador1() : campo.obtenerCementerioJugador2();
        Trampa trampa = (Trampa) magiasYTrampasAtacante[cartaAActivar];
        switch (modelo.obtenerTipoHabilidad()) {
            case FUERZA_ESPEJO:
                destruirAtacantes(campo, turno);
                break;

            case CILINDRO_MAGICO:
                if(trampa.obtenerTurnosActiva() < 1){
                    for(Monstruo m : monstruosAtacante){
                        if(m != null && m.obtenerNombre().equals(stringAux)){
                            m.establecerAtaque((short) 0);
                            atacante.establecerPuntosVida((short) (atacante.obtenerPuntosVida() - m.obtenerAtaqueBase()));
                            break;
                        }
                    }
                    trampa.establecerTurnosActiva((byte) (trampa.obtenerTurnosActiva()+1));
                    trampa.establecerEstaActivada(true);
                }
                else if(trampa.obtenerTurnosActiva() > 0){
                    trampa.establecerTurnosActiva((byte) 0);
                    for(int j=0; j<5; j++){
                        if(monstruosAtacante[j] != null){
                            monstruosAtacante[j].establecerAtaque(monstruosAtacante[j].obtenerAtaqueBase());
                        }
                    }
                    trampa.establecerEstaActivada(false);
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
                trampa.establecerEstaActivada(false);
                cementerioDefensor.add(magiasYTrampasDefensor[cartaAActivar]);
                magiasYTrampasDefensor[cartaAActivar] = null;
                break;

            case ARMADURA_SAKURETSU:
                if(monstruosAtacante[byteAux] != null){
                    cementerioAtacante.add(monstruosAtacante[byteAux]);
                    monstruosAtacante[byteAux] = null;
                }
                trampa.establecerEstaActivada(false);
                cementerioDefensor.add(magiasYTrampasDefensor[cartaAActivar]);
                magiasYTrampasDefensor[cartaAActivar] = null;
                break;

            case LLAMADA_CONDENADOS:
                byte indiceMonstruoARevivir=0;
                for(int j=0; j<cementerioDefensor.size(); j++){
                    if(cementerioDefensor.get(j).obtenerNombre().equals(stringAux) && cementerioDefensor.get(j) instanceof Monstruo){
                        indiceMonstruoARevivir = (byte) j;
                        break;
                    }
                }
                for(int j=0; j<5; j++){
                    if(monstruosOponente[j] == null && cementerioDefensor.get(indiceMonstruoARevivir) instanceof Monstruo){
                        monstruosOponente[j] = (Monstruo) cementerioDefensor.remove(indiceMonstruoARevivir);
                        monstruosOponente[j].establecerEnPosicionAtaque(true);
                        break;
                    }
                }
                trampa.establecerEstaActivada(false);
                cementerioDefensor.add(magiasYTrampasDefensor[cartaAActivar]);
                magiasYTrampasDefensor[cartaAActivar] = null;
                break;

            case SOMBREROS_MAGICOS:
                if(trampa.obtenerTurnosActiva() < 1){
                    byte indiceMonstruoAOcultar=0;
                    for(int j=0; j<5; j++){
                        if(monstruosOponente[j] != null){
                            if(monstruosOponente[j].obtenerNombre().equals(stringAux)){
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
                    for(int j=0; j<oponente.obtenerMazo().size(); j++){
                        if(oponente.obtenerMazo().get(j).obtenerNombre().equals(stringAux)){
                            cementerioDefensor.add(oponente.obtenerMazo().remove(j));
                        }
                        else if(oponente.obtenerMazo().get(j).obtenerNombre().equals(stringAux)){
                            cementerioDefensor.add(oponente.obtenerMazo().remove(j));
                        }
                    }
                    trampa.establecerTurnosActiva((byte) (trampa.obtenerTurnosActiva()+1));
                    trampa.establecerEstaActivada(true);
                }
                else if(trampa.obtenerTurnosActiva() > 0){
                    trampa.establecerTurnosActiva((byte) 0);
                    for(int j=0; j<5; j++){
                        if(monstruosOponente[j] != null){
                            if(monstruosOponente[j].obtenerNombre().equals("Sombrero 1") || monstruosOponente[j].obtenerNombre().equals("Sombrero 2")){
                                monstruosOponente[j] = null;
                            }
                        }
                    }
                    trampa.establecerEstaActivada(false);
                    cementerioDefensor.add(magiasYTrampasDefensor[cartaAActivar]);
                    magiasYTrampasDefensor[cartaAActivar] = null;
                }
                break;

            case WABOKU:
                if(trampa.obtenerTurnosActiva() < 1){
                    for(int j=0; j<5; j++){
                        if(monstruosAtacante[j] != null){
                            monstruosAtacante[j].establecerAtaque((short) 0);
                        }
                    }
                    trampa.establecerTurnosActiva((byte) (trampa.obtenerTurnosActiva()+1));
                    trampa.establecerEstaActivada(true);
                }
                else if(trampa.obtenerTurnosActiva() > 0){
                    trampa.establecerTurnosActiva((byte) 0);
                    for(int j=0; j<5; j++){
                        if(monstruosAtacante[j] != null){
                            monstruosAtacante[j].establecerAtaque(monstruosAtacante[j].obtenerAtaqueBase());
                        }
                    }
                    trampa.establecerEstaActivada(false);
                    cementerioDefensor.add(magiasYTrampasDefensor[cartaAActivar]);
                    magiasYTrampasDefensor[cartaAActivar] = null;
                }
                break;

            case MURO_ESPEJO:
                if(trampa.obtenerTurnosActiva() < 1){
                    for(int j=0; j<5; j++){
                        if(monstruosAtacante[j] != null){
                            monstruosAtacante[j].establecerAtaque((short) (monstruosAtacante[j].obtenerAtaque() / 2));
                        }
                    }
                    trampa.establecerTurnosActiva((byte) (trampa.obtenerTurnosActiva()+1));
                    trampa.establecerEstaActivada(true);
                }
                else if(trampa.obtenerTurnosActiva() > 0){
                    trampa.establecerTurnosActiva((byte) 0);
                    if(byteAux == 1){
                        atacante.establecerPuntosVida((short) (atacante.obtenerPuntosVida()-2000));
                    }
                    else if(byteAux == 2){
                        for(int j=0; j<5; j++){
                            if(monstruosAtacante[j] != null){
                                monstruosAtacante[j].establecerAtaque(monstruosAtacante[j].obtenerAtaqueBase());
                            }
                        }
                        trampa.establecerEstaActivada(false);
                        cementerioDefensor.add(magiasYTrampasDefensor[cartaAActivar]);
                        magiasYTrampasDefensor[cartaAActivar] = null;
                    }
                }
                break;

            case REPRESION:
                if(trampa.obtenerTurnosActiva() < 1){
                    for(int j=0; j<5; j++){
                        if(monstruosAtacante[j] != null && monstruosAtacante[j].obtenerNombre().equals(stringAux)){
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
                    trampa.establecerTurnosActiva((byte) (trampa.obtenerTurnosActiva()+1));
                    trampa.establecerEstaActivada(true);
                }
                else if(trampa.obtenerTurnosActiva() > 0){
                    for(int j=0; j<5; j++){
                        if(monstruosOponente[j] != null && monstruosOponente[j].obtenerNombre().equals(trampa.obtenerMonstruoRobado())){
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
                    trampa.establecerEstaActivada(false);
                    trampa.establecerTurnosActiva((byte) 0);
                    trampa.establecerMonstruoRobado("");
                    cementerioDefensor.add(magiasYTrampasDefensor[cartaAActivar]);
                    magiasYTrampasDefensor[cartaAActivar] = null;
                }
                break;

            case DRENAJE_HABILIDAD:
                oponente.establecerPuntosVida(((short) (oponente.obtenerPuntosVida()-1000)));
                if(trampa.obtenerTurnosActiva() < 1){
                    for(int j=0; j<5; j++){
                        if(monstruosAtacante[j] != null){
                            monstruosAtacante[j].establecerAtaque((short) (0));
                        }
                    }
                    trampa.establecerTurnosActiva((byte) (trampa.obtenerTurnosActiva()+1));
                    trampa.establecerEstaActivada(true);
                }
                else if(trampa.obtenerTurnosActiva() > 0){
                    for(int j=0; j<5; j++){
                        if(monstruosAtacante[j] != null){
                            monstruosAtacante[j].establecerAtaque(monstruosAtacante[j].obtenerAtaqueBase());
                        }
                    }
                    trampa.establecerEstaActivada(false);
                    trampa.establecerTurnosActiva((byte) 0);
                    cementerioDefensor.add(magiasYTrampasDefensor[cartaAActivar]);
                    magiasYTrampasDefensor[cartaAActivar] = null;
                }
                break;
        }
        campo.reconstruirControlCampo();
        vista.actualizarTablero();
    }

    private void destruirAtacantes(Campo campo, byte turno) {
        Monstruo[] atacantes = (turno % 2 == 0) ? campo.obtenerMonstruosJugador1() : campo.obtenerMonstruosJugador2();
        for (int i = 0; i < 5; i++) {
            if (atacantes[i] != null && atacantes[i].estaEnPosicionAtaque()) {
                atacantes[i] = null;
            }
        }
    }

}
