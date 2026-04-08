import java.util.*;

public class Magia extends Carta implements Activable{

    private TipoHabilidadEspecial tipoHabilidadEspecial;
    private byte turnosActiva;

    public Magia(String nombre, String cuadroDeTexto, TipoHabilidadEspecial tipoHabilidadEspecial) {
        super(nombre, cuadroDeTexto, true);
        this.tipoHabilidadEspecial = tipoHabilidadEspecial;
        this.turnosActiva = 0;
    }

    public TipoHabilidadEspecial getTipoHabilidadEspecial() {
        return tipoHabilidadEspecial;
    }
    public byte getTurnosActiva(){
        return turnosActiva;
    }

    public void setTipoHabilidadEspecial(TipoHabilidadEspecial tipoHabilidadEspecial) {
        this.tipoHabilidadEspecial = tipoHabilidadEspecial;
    }
    public void setTurnosActiva(byte turnosActiva){
        this.turnosActiva = turnosActiva;
    }

    @Override
    public void ActivarEfecto(Campo campo, byte turno, Scanner scaner){
        Jugador atacante, defensor;
        Monstruo[] monstruosAtacantes, monstruosDefensores;
        Magia[] magiasAtacantes, magiasDefensoras;
        List<Carta> cementerioAtacante, cementerioDefensor;
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
        for(int i=0; i<5; i++){
            if(magiasAtacantes[i] != null && magiasAtacantes[i].isVisible()){
                switch(magiasAtacantes[i].getTipoHabilidadEspecial()){

                    case MONSTRUO_RENACIDO:
                        System.out.print("Escriba el nombre exacto del monstruo que quiere devolver al campo: ");
                        String nombreARevivir = scaner.nextLine();
                        System.out.println("");
                        for(int j = 0; j<campo.getCementerioJugador1().size(); j++){
                            if(campo.getCementerioJugador1().get(j).getNombre().equals(nombreARevivir)){
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
                            if(campo.getCementerioJugador2().get(j).getNombre().equals(nombreARevivir)){
                                for(int k = 0; k<5; k++){
                                    if( monstruosAtacantes[k] == null){
                                        monstruosAtacantes[k] = (Monstruo) campo.getCementerioJugador2().get(j);
                                        campo.getCementerioJugador2().remove(j);
                                        break;
                                    }
                                }
                            }
                        }
                        cementerioAtacante.add(magiasAtacantes[i]);
                        magiasAtacantes[i] = null;
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
                        cementerioAtacante.add(magiasAtacantes[i]);
                        magiasAtacantes[i] = null;
                        break;

                    case TIFON_DEL_ESPACIO_MISTICO:
                        System.out.print("Escriba el nombre exacto de la carta mágica de su oponente que quiere destruir: ");
                        String magicaADestruir = scaner.nextLine();
                        System.out.println("");
                        for(int j=0; j<5; j++){
                            if(magiasDefensoras[j] != null && magiasDefensoras[j].getNombre().equals(magicaADestruir)){
                                cementerioDefensor.add(magiasDefensoras[j]);
                                magiasDefensoras[j] = null;
                                break;
                            }
                        }
                        cementerioAtacante.add(magiasAtacantes[i]);
                        magiasAtacantes[i] = null;
                        break;

                    case ESPADAS_DE_LA_LUZ_REVELADORA:
                        if(magiasAtacantes[i].getTurnosActiva() < 3){
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
                            magiasAtacantes[i].setTurnosActiva((byte) (magiasAtacantes[i].getTurnosActiva()+1));
                            break;
                        }
                        else{
                            magiasAtacantes[i].setTurnosActiva((byte) 0);
                            cementerioAtacante.add(magiasAtacantes[i]);
                            magiasAtacantes[i] = null;
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
                        else{
                            System.out.println("No hay cartas disponibles en el mazo.");
                        }
                        cementerioAtacante.add(magiasAtacantes[i]);
                        magiasAtacantes[i] = null;
                        break;

                    case ENTIERRO_INSENSATO:
                        System.out.println("----------Tu Mazo----------");
                        for(int j=0; j<atacante.getMazo().size(); j++){
                            if(atacante.getMazo().get(j) instanceof Monstruo){
                                Monstruo monstruo = (Monstruo) atacante.getMazo().get(j);
                                System.out.println(monstruo.getNombre() + " | ATK: " + monstruo.getAtaque() + " | DEF: " + monstruo.getDefensa());
                            }
                            else if(atacante.getMazo().get(j) instanceof Magia){
                                Magia magica = (Magia) atacante.getMazo().get(j);
                                System.out.println(magica.getNombre() + " | HAB: " + magica.getCuadroDeTexto());
                            }
                        }
                        System.out.print("Escriba el nombre exacto de la carta de su mazo que quiere enviar al cementerio: ");
                        String cartaAEnviarAlCementerio = scaner.nextLine();
                        System.out.println("");
                        for(int j=0; j<atacante.getMazo().size(); j++){
                            if(atacante.getMazo().get(j).getNombre().equals(cartaAEnviarAlCementerio)){
                                cementerioAtacante.add(atacante.getMazo().remove(j));
                                break;
                            }
                        }
                        cementerioAtacante.add(magiasAtacantes[i]);
                        magiasAtacantes[i] = null;
                        break;

                    case RAIGEKI:
                        for(int j=0; j<5; j++){
                            if(monstruosDefensores[j] != null){
                                cementerioDefensor.add(monstruosDefensores[j]);
                                monstruosDefensores[j] = null;
                            }
                        }
                        cementerioAtacante.add(magiasAtacantes[i]);
                        magiasAtacantes[i] = null;
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
                            System.out.print("Escriba exactamente el nombre del monstruo rival que quiere destruir: ");
                            String monstruoADestruir = scaner.nextLine();
                            System.out.println("");
                            for(int j=0; j<5; j++){
                                if(monstruosDefensores[j] != null && monstruosDefensores[j].getNombre().equals(monstruoADestruir)){
                                    cementerioDefensor.add(monstruosDefensores[j]);
                                    monstruosDefensores[j] = null;
                                    break;
                                }
                            }
                        }
                        else{
                            System.out.println("No tiene al Mago Oscuro");
                        }
                        cementerioAtacante.add(magiasAtacantes[i]);
                        magiasAtacantes[i] = null;
                        break;

                    case UNIDAD:
                        if(magiasAtacantes[i].getTurnosActiva() == (byte) 0){
                            short sumaDeDefensa = 0;
                            System.out.print("Escriba exactamente el nombre del monstruo cuya defensa quiere aumentar: ");
                            String monstruoAFortalecer = scaner.nextLine();
                            System.out.println("");
                            for(Monstruo m : monstruosAtacantes){
                                if(m != null){
                                    sumaDeDefensa += m.getDefensa();
                                }
                            }
                            for(int j=0; j<5; j++){
                                if(monstruosAtacantes[j] != null && monstruosAtacantes[j].getNombre().equals(monstruoAFortalecer)){
                                    monstruosAtacantes[j].setDefensa(sumaDeDefensa);
                                }
                            }
                            magiasAtacantes[i].setTurnosActiva((byte) (magiasAtacantes[i].getTurnosActiva()+1));
                        }
                        else if(magiasAtacantes[i].getTurnosActiva() == (byte) 1){
                            for(Monstruo m : monstruosAtacantes){
                                if(m != null){
                                    m.setDefensa(m.getDefensaBase());
                                }
                            }
                            magiasAtacantes[i].setTurnosActiva((byte) 0);
                            cementerioAtacante.add(magiasAtacantes[i]);
                            magiasAtacantes[i] = null;
                        }
                        break;

                    case POLIMERIZACION:
                        byte numeroDeMonstruosEnPosesion = 0, numeroDeMonstruosAFusionar = 0;
                        Monstruo monstruoFusion = new Monstruo("Monstruo de Fusión", "Un monstruo creado a partir de Polimerización.", (byte) 0, (short) 0, (short) 0);
                        for(Monstruo m : monstruosAtacantes){
                            if(m != null){
                                numeroDeMonstruosEnPosesion++;
                            }
                        }
                        for(Carta carta : atacante.getMano()){
                            if(carta instanceof Monstruo){
                                numeroDeMonstruosEnPosesion++;
                            }
                        }
                        do{
                            System.out.print("Escriba el número de monstruos que quiere fusionar: ");
                            numeroDeMonstruosAFusionar = scaner.nextByte();
                            scaner.nextLine();
                            System.out.println("");
                        } while(numeroDeMonstruosAFusionar > numeroDeMonstruosEnPosesion);
                        for(int j=0; j<numeroDeMonstruosAFusionar; j++){
                            boolean monstruoEnCampo = false;
                            System.out.print("Escriba exactamente el nombre del monstruo " + j + " a fusionar: ");
                            String monstruoAFusionar = scaner.nextLine();
                            System.out.println("");
                            for(int k=0; k < 5; k++){
                                if(monstruosAtacantes[k] != null && monstruosAtacantes[k].getNombre().equals(monstruoAFusionar)){
                                    monstruoFusion.setNivel((byte) (monstruoFusion.getNivel() + monstruosAtacantes[k].getNivel()));
                                    monstruoFusion.setAtaque((short) (monstruoFusion.getAtaque() + monstruosAtacantes[k].getAtaque()));
                                    monstruoFusion.setDefensa((short) (monstruoFusion.getDefensa() + monstruosAtacantes[k].getDefensa()));
                                    cementerioAtacante.add(monstruosAtacantes[k]);
                                    monstruosAtacantes[k] = null;
                                    monstruoEnCampo = true;
                                    break;
                                }
                            }
                            if(!monstruoEnCampo){
                                for(int k=0; k<atacante.getMano().size(); k++){
                                    if(atacante.getMano().get(k) != null && atacante.getMano().get(k).getNombre().equals(monstruoAFusionar)){
                                        Monstruo monstruo = (Monstruo) atacante.getMano().get(k);
                                        monstruoFusion.setNivel((byte) (monstruoFusion.getNivel() + monstruo.getNivel()));
                                        monstruoFusion.setAtaque((short) (monstruoFusion.getAtaque() + monstruo.getAtaque()));
                                        monstruoFusion.setDefensa((short) (monstruoFusion.getDefensa() + monstruo.getDefensa()));
                                        cementerioAtacante.add(atacante.getMano().remove(k));
                                        break;
                                    }
                                }
                            }
                        }
                        for(int j=0; j<5; j++){
                            if(monstruosAtacantes[j] == null){
                                monstruosAtacantes[j] = monstruoFusion;
                                break;
                            }
                        }
                        cementerioAtacante.add(magiasAtacantes[i]);
                        magiasAtacantes[i] = null;
                        break;

                }
            }
        }
    }

    @Override
    public boolean jugar(Campo campo, byte turno, Scanner scaner){
        ActivarEfecto(campo, turno, scaner);
        return true;
    }
    
}
