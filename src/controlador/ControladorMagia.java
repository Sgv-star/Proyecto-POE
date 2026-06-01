package controlador;

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
        Jugador oponente = (turno % 2 == 0) ? campo.getJugador2() : campo.getJugador1();
        Jugador atacante = (turno % 2 == 0) ? campo.getJugador1() : campo.getJugador2();
        Monstruo[] monstruosOponente = (turno % 2 == 0) ? campo.getMonstruosJugador2() : campo.getMonstruosJugador1();
        Monstruo[] monstruosAtacante = (turno % 2 == 0) ? campo.getMonstruosJugador1() : campo.getMonstruosJugador2();
        Carta[] magiasYTrampasAtacante = (turno % 2 == 0) ? campo.getMagicasYTrampasJugador2() : campo.getMagicasYTrampasJugador1();
        Carta[] magiasYTrampasDefensor = (turno % 2 == 0) ? campo.getMagicasYTrampasJugador1() : campo.getMagicasYTrampasJugador2();
        List<Carta> cementerioAtacante = (turno % 2 == 0) ? campo.getCementerioJugador2() : campo.getCementerioJugador1();
        List<Carta> cementerioDefensor = (turno % 2 == 0) ? campo.getCementerioJugador1() : campo.getCementerioJugador2();
        switch (modelo.getTipoHabilidad()) {
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
                for(int j = 0; j<campo.getCementerioJugador1().size(); j++){
                    if(campo.getCementerioJugador1().get(j).getNombre().equals(objetivoNombre)){
                        for(int k = 0; k<5; k++){
                            if( monstruosAtacante[k] == null){
                                monstruosAtacante[k] = (Monstruo) campo.getCementerioJugador1().get(j);
                                campo.getCementerioJugador1().remove(j);
                                break;
                            }
                        }
                    }
                }
                for(int j = 0; j<campo.getCementerioJugador2().size(); j++){
                    if(campo.getCementerioJugador2().get(j).getNombre().equals(objetivoNombre)){
                        for(int k = 0; k<5; k++){
                            if( monstruosAtacante[k] == null){
                                monstruosAtacante[k] = (Monstruo) campo.getCementerioJugador2().get(j);
                                campo.getCementerioJugador2().remove(j);
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
                    if(magiasYTrampasDefensor[j] != null && magiasYTrampasDefensor[j].getNombre().equals(objetivoNombre)){
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
                if (carta.getTurnosActiva() < 3) {
                    for(int j=0; j<5; j++){
                        if(monstruosOponente[j] != null){
                            if(monstruosOponente[j].puedeAtacar()){
                                monstruosOponente[j].setPuedeAtacar(false);
                            }
                            if(!monstruosOponente[j].esVisible()){
                                monstruosOponente[j].setVisible(true);
                            }
                        }
                    }
                    carta.setTurnosActiva((byte) (carta.getTurnosActiva()+1));
                } else {
                    carta.setTurnosActiva((byte) 0);
                    cementerioAtacante.add(magiasYTrampasAtacante[cartaAActivar]);
                    magiasYTrampasAtacante[cartaAActivar] = null;
                    for(int j = 0; j<5; j++){
                        if(monstruosOponente[j] != null){
                            if(!monstruosOponente[j].puedeAtacar()){
                                monstruosOponente[j].setPuedeAtacar(true);
                            }
                        }
                    }
                }
            }

            case ENTIERRO_INSENSATO -> {
                for(int j=0; j<atacante.getMazo().size(); j++){
                    if(atacante.getMazo().get(j).getNombre().equals(objetivoNombre)){
                        cementerioAtacante.add(atacante.getMazo().remove(j));
                        break;
                    }
                }
                cementerioAtacante.add(magiasYTrampasAtacante[cartaAActivar]);
                magiasYTrampasAtacante[cartaAActivar] = null;
            }

            case MIL_CUCHILLOS -> {
                boolean tieneMagoOscuro = false;
                for(Monstruo m : monstruosAtacante){
                    if(m != null && m.getNombre().equals("Mago Oscuro")){
                        tieneMagoOscuro = true;
                        break;
                    }
                }
                if(tieneMagoOscuro){
                    for(int j=0; j<5; j++){
                        if(monstruosOponente[j] != null && monstruosOponente[j].getNombre().equals(cartaAActivar)){
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
                if(carta.getTurnosActiva() == (byte) 0){
                    short sumaDeDefensa = 0;
                    for(Monstruo m : monstruosAtacante){
                        if(m != null){
                            sumaDeDefensa += m.getDefensa();
                        }
                    }
                    for(int j=0; j<5; j++){
                        if(monstruosAtacante[j] != null && monstruosAtacante[j].getNombre().equals(cartaAActivar)){
                            monstruosAtacante[j].setDefensa(sumaDeDefensa);
                        }
                    }
                    carta.setTurnosActiva((byte) (carta.getTurnosActiva()+1));
                }
                else if(carta.getTurnosActiva() == (byte) 1){
                    for(Monstruo m : monstruosAtacante){
                        if(m != null){
                            m.setDefensa(m.getDefensaBase());
                        }
                    }
                    carta.setTurnosActiva((byte) 0);
                    cementerioAtacante.add(magiasYTrampasAtacante[cartaAActivar]);
                    magiasYTrampasAtacante[cartaAActivar] = null;
                }
            }

            case POLIMERIZACION -> {
                Monstruo monstruoFusion = new Monstruo("Monstruo de Fusión", "Un monstruo creado a partir de Polimerización.", (byte) 0, (short) 0, (short) 0);
                for(int j=0; j<listaMateriales.size(); j++){
                    monstruoFusion.setNivel((byte) (monstruoFusion.getNivel() + listaMateriales.get(j).getNivel()));
                    monstruoFusion.setAtaque((short) (monstruoFusion.getAtaque() + listaMateriales.get(j).getAtaque()));
                    monstruoFusion.setDefensa((short) (monstruoFusion.getDefensa() + listaMateriales.get(j).getDefensa()));
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
        campo.reconstruirControlCampo();
        vista.actualizarTablero();
    }

    private void limpiarMonstruos(Campo campo) {
        for (int i = 0; i < 5; i++) {
            if (campo.getMonstruosJugador1()[i] != null) {
                campo.getCementerioJugador1().add(campo.getMonstruosJugador1()[i]);
                campo.getMonstruosJugador1()[i] = null;
            }
            if (campo.getMonstruosJugador2()[i] != null) {
                campo.getCementerioJugador2().add(campo.getMonstruosJugador2()[i]);
                campo.getMonstruosJugador2()[i] = null;
            }
        }
    }

    private void robarCartas(Campo campo, byte turno, int cantidad) {
        Jugador actual = (turno % 2 == 0) ? campo.getJugador1() : campo.getJugador2();
        for (int i = 0; i < cantidad; i++) {
            if (!actual.getMazo().isEmpty()) {
                actual.getMano().add(actual.getMazo().pop());
            }
        }
    }

    private void destruirMonstruosOponente(Campo campo, byte turno) {
        Monstruo[] oponente = (turno % 2 == 0) ? campo.getMonstruosJugador2() : campo.getMonstruosJugador1();
        List<Carta> cementerio = (turno % 2 == 0) ? campo.getCementerioJugador2() : campo.getCementerioJugador1();
        for (int i = 0; i < 5; i++) {
            if (oponente[i] != null) {
                cementerio.add(oponente[i]);
                oponente[i] = null;
            }
        }
    }
}
