package controlador;

import java.util.LinkedList;
import java.util.List;
import modelo.*;
import vista.VistaDuelo;

public class ControladorMagia {
    private Magia modelo;
    private VistaDuelo vista;

    public ControladorMagia(Magia modelo, VistaDuelo vista) {
        this.modelo = modelo;
        this.vista = vista;
    }

    public void activarEfecto(Campo campo, byte turno, byte cartaAActivar, String objetivoNombre, List<Monstruo> listaMateriales) {
        Jugador oponente = (turno % 2 == 0) ? campo.obtenerJugador2() : campo.obtenerJugador1();
        Jugador atacante = (turno % 2 == 0) ? campo.obtenerJugador1() : campo.obtenerJugador2();
        Monstruo[] monstruosOponente = (turno % 2 == 0) ? campo.obtenerMonstruosJugador2() : campo.obtenerMonstruosJugador1();
        Monstruo[] monstruosAtacante = (turno % 2 == 0) ? campo.obtenerMonstruosJugador1() : campo.obtenerMonstruosJugador2();
        Carta[] magiasYTrampasAtacante = (turno % 2 == 0) ? campo.obtenerMagicasYTrampasJugador2() : campo.obtenerMagicasYTrampasJugador1();
        Carta[] magiasYTrampasDefensor = (turno % 2 == 0) ? campo.obtenerMagicasYTrampasJugador1() : campo.obtenerMagicasYTrampasJugador2();
        LinkedList<Carta> cementerioAtacante = (turno % 2 == 0) ? campo.obtenerCementerioJugador2() : campo.obtenerCementerioJugador1();
        LinkedList<Carta> cementerioDefensor = (turno % 2 == 0) ? campo.obtenerCementerioJugador1() : campo.obtenerCementerioJugador2();
        switch (modelo.obtenerTipoHabilidad()) {
            case AGUJERO_NEGRO -> {
                limpiarMonstruos(campo);
                cementerioAtacante.add(magiasYTrampasAtacante[cartaAActivar]);
                magiasYTrampasAtacante[cartaAActivar] = null;
            }

            case OLLA_CODICIA -> {
                robarCartas(campo, turno, 2);
                cementerioAtacante.add(magiasYTrampasAtacante[cartaAActivar]);
                magiasYTrampasAtacante[cartaAActivar] = null;
            }

            case RAIGEKI -> {
                destruirMonstruosOponente(campo, turno);
                cementerioAtacante.add(magiasYTrampasAtacante[cartaAActivar]);
                magiasYTrampasAtacante[cartaAActivar] = null;
            }

            case MONSTRUO_RENACIDO -> {
                for(int j = 0; j<campo.obtenerCementerioJugador1().size(); j++){
                    if(campo.obtenerCementerioJugador1().get(j).obtenerNombre().equals(objetivoNombre)){
                        for(int k = 0; k<5; k++){
                            if( monstruosAtacante[k] == null){
                                monstruosAtacante[k] = (Monstruo) campo.obtenerCementerioJugador1().get(j);
                                campo.obtenerCementerioJugador1().remove(j);
                                break;
                            }
                        }
                    }
                }
                for(int j = 0; j<campo.obtenerCementerioJugador2().size(); j++){
                    if(campo.obtenerCementerioJugador2().get(j).obtenerNombre().equals(objetivoNombre)){
                        for(int k = 0; k<5; k++){
                            if( monstruosAtacante[k] == null){
                                monstruosAtacante[k] = (Monstruo) campo.obtenerCementerioJugador2().get(j);
                                campo.obtenerCementerioJugador2().remove(j);
                                break;
                            }
                        }
                    }
                }
                cementerioAtacante.add(magiasYTrampasAtacante[cartaAActivar]);
                magiasYTrampasAtacante[cartaAActivar] = null;
            }

            case TIFON_ESPACIO_MISTICO -> {
                for(int j=0; j<5; j++){
                    if(magiasYTrampasDefensor[j] != null && magiasYTrampasDefensor[j].obtenerNombre().equals(objetivoNombre)){
                        cementerioDefensor.add(magiasYTrampasDefensor[j]);
                        magiasYTrampasDefensor[j] = null;
                        break;
                    }
                }
                cementerioAtacante.add(magiasYTrampasAtacante[cartaAActivar]);
                magiasYTrampasAtacante[cartaAActivar] = null;
            }

            case ESPADAS_LUZ_REVELADORA -> {
                Magia carta = (Magia) magiasYTrampasAtacante[cartaAActivar];
                if (carta.obtenerTurnosActiva() < 3) {
                    for(int j=0; j<5; j++){
                        if(monstruosOponente[j] != null){
                            if(monstruosOponente[j].puedeAtacar()){
                                monstruosOponente[j].establecerPuedeAtacar(false);
                            }
                            if(!monstruosOponente[j].esVisible()){
                                monstruosOponente[j].establecerVisible(true);
                            }
                        }
                    }
                    carta.establecerTurnosActiva((byte) (carta.obtenerTurnosActiva()+1));
                } else {
                    carta.establecerTurnosActiva((byte) 0);
                    cementerioAtacante.add(magiasYTrampasAtacante[cartaAActivar]);
                    magiasYTrampasAtacante[cartaAActivar] = null;
                    for(int j = 0; j<5; j++){
                        if(monstruosOponente[j] != null){
                            if(!monstruosOponente[j].puedeAtacar()){
                                monstruosOponente[j].establecerPuedeAtacar(true);
                            }
                        }
                    }
                }
            }

            case ENTIERRO_INSENSATO -> {
                for(int j=0; j<atacante.obtenerMazo().size(); j++){
                    if(atacante.obtenerMazo().get(j).obtenerNombre().equals(objetivoNombre)){
                        cementerioAtacante.add(atacante.obtenerMazo().remove(j));
                        break;
                    }
                }
                cementerioAtacante.add(magiasYTrampasAtacante[cartaAActivar]);
                magiasYTrampasAtacante[cartaAActivar] = null;
            }

            case MIL_CUCHILLOS -> {
                boolean tieneMagoOscuro = false;
                for(Monstruo m : monstruosAtacante){
                    if(m != null && m.obtenerNombre().equals("Mago Oscuro")){
                        tieneMagoOscuro = true;
                        break;
                    }
                }
                if(tieneMagoOscuro){
                    for(int j=0; j<5; j++){
                        if(monstruosOponente[j] != null && monstruosOponente[j].obtenerNombre().equals(cartaAActivar)){
                            cementerioDefensor.add(monstruosOponente[j]);
                            monstruosOponente[j] = null;
                            break;
                        }
                    }
                }
                cementerioAtacante.add(magiasYTrampasAtacante[cartaAActivar]);
                magiasYTrampasAtacante[cartaAActivar] = null;
            }

            case UNIDAD -> {
                Magia carta = (Magia) magiasYTrampasAtacante[cartaAActivar];
                if(carta.obtenerTurnosActiva() == (byte) 0){
                    short sumaDeDefensa = 0;
                    for(Monstruo m : monstruosAtacante){
                        if(m != null){
                            sumaDeDefensa += m.obtenerDefensa();
                        }
                    }
                    for(int j=0; j<5; j++){
                        if(monstruosAtacante[j] != null && monstruosAtacante[j].obtenerNombre().equals(cartaAActivar)){
                            monstruosAtacante[j].establecerDefensa(sumaDeDefensa);
                        }
                    }
                    carta.establecerTurnosActiva((byte) (carta.obtenerTurnosActiva()+1));
                }
                else if(carta.obtenerTurnosActiva() == (byte) 1){
                    for(Monstruo m : monstruosAtacante){
                        if(m != null){
                            m.establecerDefensa(m.obtenerDefensaBase());
                        }
                    }
                    carta.establecerTurnosActiva((byte) 0);
                    cementerioAtacante.add(magiasYTrampasAtacante[cartaAActivar]);
                    magiasYTrampasAtacante[cartaAActivar] = null;
                }
            }

            case POLIMERIZACION -> {
                Monstruo monstruoFusion = new Monstruo("Monstruo de Fusión", "Un monstruo creado a partir de Polimerización.", (byte) 0, (short) 0, (short) 0);
                for(int j=0; j<listaMateriales.size(); j++){
                    monstruoFusion.establecerNivel((byte) (monstruoFusion.obtenerNivel() + listaMateriales.get(j).obtenerNivel()));
                    monstruoFusion.establecerAtaque((short) (monstruoFusion.obtenerAtaque() + listaMateriales.get(j).obtenerAtaque()));
                    monstruoFusion.establecerDefensa((short) (monstruoFusion.obtenerDefensa() + listaMateriales.get(j).obtenerDefensa()));
                    cementerioAtacante.add(listaMateriales.get(j));
                }
                for(int j=0; j<5; j++){
                    if(monstruosAtacante[j] == null){
                        monstruosAtacante[j] = monstruoFusion;
                        break;
                    }
                }
                cementerioAtacante.add(magiasYTrampasAtacante[cartaAActivar]);
                magiasYTrampasAtacante[cartaAActivar] = null;
            }
        }
        vista.actualizarTablero();
    }

    private void limpiarMonstruos(Campo campo) {
        for (int i = 0; i < 5; i++) {
            if (campo.obtenerMonstruosJugador1()[i] != null) {
                campo.obtenerCementerioJugador1().add(campo.obtenerMonstruosJugador1()[i]);
                campo.obtenerMonstruosJugador1()[i] = null;
            }
            if (campo.obtenerMonstruosJugador2()[i] != null) {
                campo.obtenerCementerioJugador2().add(campo.obtenerMonstruosJugador2()[i]);
                campo.obtenerMonstruosJugador2()[i] = null;
            }
        }
    }

    private void robarCartas(Campo campo, byte turno, int cantidad) {
        Jugador actual = (turno % 2 == 0) ? campo.obtenerJugador1() : campo.obtenerJugador2();
        for (int i = 0; i < cantidad; i++) {
            if (!actual.obtenerMazo().isEmpty()) {
                actual.obtenerMano().add(actual.obtenerMazo().remove(0));
            }
        }
    }

    private void destruirMonstruosOponente(Campo campo, byte turno) {
        Monstruo[] oponente = (turno % 2 == 0) ? campo.obtenerMonstruosJugador2() : campo.obtenerMonstruosJugador1();
        List<Carta> cementerio = (turno % 2 == 0) ? campo.obtenerCementerioJugador2() : campo.obtenerCementerioJugador1();
        for (int i = 0; i < 5; i++) {
            if (oponente[i] != null) {
                cementerio.add(oponente[i]);
                oponente[i] = null;
            }
        }
    }
}
