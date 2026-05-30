package controlador;

import java.util.*;
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
        Monstruo[] monstruosDefensor = (turno % 2 == 0) ? campo.obtenerMonstruosJugador2() : campo.obtenerMonstruosJugador1();
        Monstruo[] monstruosAtacante = (turno % 2 == 0) ? campo.obtenerMonstruosJugador1() : campo.obtenerMonstruosJugador2();
        Carta[] magiasYTrampasAtacante = (turno % 2 == 0) ? campo.obtenerMagicasYTrampasJugador2() : campo.obtenerMagicasYTrampasJugador1();
        Carta[] magiasYTrampasDefensor = (turno % 2 == 0) ? campo.obtenerMagicasYTrampasJugador1() : campo.obtenerMagicasYTrampasJugador2();
        HashMap<String, Carta> cementerioAtacante = (turno % 2 == 0) ? campo.obtenerCementerioJugador2() : campo.obtenerCementerioJugador1();
        HashMap<String, Carta> cementerioDefensor = (turno % 2 == 0) ? campo.obtenerCementerioJugador1() : campo.obtenerCementerioJugador2();
        Trampa trampa = (Trampa) magiasYTrampasAtacante[cartaAActivar];
        switch (modelo.obtenerTipoHabilidad()) {
            case FUERZA_ESPEJO -> {
                for (int i = 0; i < 5; i++) {
                    if (monstruosAtacante[i] != null && monstruosAtacante[i].estaEnPosicionAtaque()) {
                        campo.removerDelCampo(monstruosAtacante[i].obtenerNombre(), turno % 2 == 0 ? 1 : 2);
                        monstruosAtacante[i] = null;
                    }
                }
            }

            case CILINDRO_MAGICO -> {
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
                    cementerioDefensor.put(magiasYTrampasDefensor[cartaAActivar].obtenerNombre(), magiasYTrampasDefensor[cartaAActivar]);
                    magiasYTrampasDefensor[cartaAActivar] = null;
                }
            }

            case TRIBUTO_TORRENCIAL -> {
                for(int j=0; j<5; j++){
                    if(monstruosAtacante[j] != null){
                        campo.removerDelCampo(monstruosAtacante[j].obtenerNombre(), turno % 2 == 0 ? 1 : 2);
                        cementerioAtacante.put(monstruosAtacante[j].obtenerNombre(), monstruosAtacante[j]);
                        monstruosAtacante[j] = null;
                    }
                    if(monstruosDefensor[j] != null){
                        campo.removerDelCampo(monstruosDefensor[j].obtenerNombre(), turno % 2 == 0 ? 1 : 2);
                        cementerioDefensor.put(monstruosDefensor[j].obtenerNombre(), monstruosDefensor[j]);
                        monstruosDefensor[j] = null;
                    }
                }
                trampa.establecerEstaActivada(false);
                cementerioDefensor.put(magiasYTrampasDefensor[cartaAActivar].obtenerNombre(), magiasYTrampasDefensor[cartaAActivar]);
                magiasYTrampasDefensor[cartaAActivar] = null;
            }

            case ARMADURA_SAKURETSU -> {
                if(monstruosAtacante[byteAux] != null){
                    campo.removerDelCampo(monstruosAtacante[byteAux].obtenerNombre(), turno % 2 == 0 ? 1 : 2);
                    cementerioAtacante.put(monstruosAtacante[byteAux].obtenerNombre(), monstruosAtacante[byteAux]);
                    monstruosAtacante[byteAux] = null;
                }
                trampa.establecerEstaActivada(false);
                cementerioDefensor.put(magiasYTrampasDefensor[cartaAActivar].obtenerNombre(), magiasYTrampasDefensor[cartaAActivar]);
                magiasYTrampasDefensor[cartaAActivar] = null;
            }

            case LLAMADA_CONDENADOS -> {
                byte indiceMonstruoARevivir=0;
                for(int j=0; j<cementerioDefensor.size(); j++){
                    if(cementerioDefensor.get(stringAux) instanceof Monstruo){
                        indiceMonstruoARevivir = (byte) j;
                        break;
                    }
                }
                for(int j=0; j<5; j++){
                    if(monstruosDefensor[j] == null && new ArrayList<>(cementerioDefensor.values()).get(indiceMonstruoARevivir) instanceof Monstruo){
                        monstruosDefensor[j] = (Monstruo) new ArrayList<>(cementerioDefensor.values()).get(indiceMonstruoARevivir);
                        monstruosDefensor[j].establecerEnPosicionAtaque(true);
                        break;
                    }
                }
                trampa.establecerEstaActivada(false);
                cementerioDefensor.put(magiasYTrampasDefensor[cartaAActivar].obtenerNombre(), magiasYTrampasDefensor[cartaAActivar]);
                magiasYTrampasDefensor[cartaAActivar] = null;
            }

            case SOMBREROS_MAGICOS -> {
                if(trampa.obtenerTurnosActiva() < 1){
                    byte indiceMonstruoAOcultar=0;
                    for(int j=0; j<5; j++){
                        if(monstruosDefensor[j] != null){
                            if(monstruosDefensor[j].obtenerNombre().equals(stringAux)){
                                indiceMonstruoAOcultar = (byte) j;
                                break;
                            }
                        }
                    }
                    List<Monstruo> cartasAOcultar = new ArrayList<> ();
                    cartasAOcultar.add(monstruosDefensor[indiceMonstruoAOcultar]);
                    cartasAOcultar.add(new Monstruo("Sombrero 1", "Una carta mágica que oculta a un monstruo", (byte) 0, (short) 0, (short) 0));
                    cartasAOcultar.add(new Monstruo("Sombrero 2", "Una carta mágica que oculta a un monstruo", (byte) 0, (short) 0, (short) 0));
                    Collections.shuffle(cartasAOcultar);
                    monstruosDefensor[indiceMonstruoAOcultar] = null;
                    for(int j=0; j<5; j++){
                        if(monstruosDefensor[j] == null && !cartasAOcultar.isEmpty()){
                            monstruosDefensor[j] = cartasAOcultar.remove(0);
                        }
                    }
                    for(int j=0; j<oponente.obtenerMazo().size(); j++){
                        if(oponente.obtenerMazo().get(j).obtenerNombre().equals(stringAux)){
                            cementerioDefensor.put(oponente.obtenerMazo().get(j).obtenerNombre(), oponente.obtenerMazo().remove(j));
                        }
                        else if(oponente.obtenerMazo().get(j).obtenerNombre().equals(stringAux)){
                            cementerioDefensor.put(oponente.obtenerMazo().remove(j).obtenerNombre(), oponente.obtenerMazo().remove(j));
                        }
                    }
                    trampa.establecerTurnosActiva((byte) (trampa.obtenerTurnosActiva()+1));
                    trampa.establecerEstaActivada(true);
                }
                else if(trampa.obtenerTurnosActiva() > 0){
                    trampa.establecerTurnosActiva((byte) 0);
                    for(int j=0; j<5; j++){
                        if(monstruosDefensor[j] != null){
                            if(monstruosDefensor[j].obtenerNombre().equals("Sombrero 1") || monstruosDefensor[j].obtenerNombre().equals("Sombrero 2")){
                                monstruosDefensor[j] = null;
                            }
                        }
                    }
                    trampa.establecerEstaActivada(false);
                    cementerioDefensor.put(magiasYTrampasDefensor[cartaAActivar].obtenerNombre(), magiasYTrampasDefensor[cartaAActivar]);
                    magiasYTrampasDefensor[cartaAActivar] = null;
                }
            }

            case WABOKU -> {
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
                    cementerioDefensor.put(magiasYTrampasDefensor[cartaAActivar].obtenerNombre(), magiasYTrampasDefensor[cartaAActivar]);
                    magiasYTrampasDefensor[cartaAActivar] = null;
                }
            }

            case MURO_ESPEJO -> {
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
                        cementerioDefensor.put(magiasYTrampasDefensor[cartaAActivar].obtenerNombre(), magiasYTrampasDefensor[cartaAActivar]);
                        magiasYTrampasDefensor[cartaAActivar] = null;
                    }
                }
            }

            case REPRESION -> {
                if(trampa.obtenerTurnosActiva() < 1){
                    for(int j=0; j<5; j++){
                        if(monstruosAtacante[j] != null && monstruosAtacante[j].obtenerNombre().equals(stringAux)){
                            for(int k=0; k<5; k++){
                                if(monstruosDefensor[k] == null){
                                    monstruosDefensor[k] = monstruosAtacante[j];
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
                        if(monstruosDefensor[j] != null && monstruosDefensor[j].obtenerNombre().equals(trampa.obtenerMonstruoRobado())){
                            for(int k=0; k<5; k++){
                                if(monstruosAtacante[k] == null){
                                    monstruosAtacante[k] = monstruosDefensor[j];
                                    monstruosDefensor[j] = null;
                                    break;
                                }
                            }
                            break;
                        }
                    }
                    trampa.establecerEstaActivada(false);
                    trampa.establecerTurnosActiva((byte) 0);
                    trampa.establecerMonstruoRobado("");
                    cementerioDefensor.put(magiasYTrampasDefensor[cartaAActivar].obtenerNombre(), magiasYTrampasDefensor[cartaAActivar]);
                    magiasYTrampasDefensor[cartaAActivar] = null;
                }
            }

            case DRENAJE_HABILIDAD -> {
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
                    cementerioDefensor.put(magiasYTrampasDefensor[cartaAActivar].obtenerNombre(), magiasYTrampasDefensor[cartaAActivar]);
                    magiasYTrampasDefensor[cartaAActivar] = null;
                }
            }

            case null, default -> {}
        }
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
