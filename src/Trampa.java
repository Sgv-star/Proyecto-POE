import java.util.*;

public class Trampa extends Carta implements Activable{

    private TipoHabilidadEspecialTrampa tipoHabilidadEspecial;
    private byte turnosActiva;
    private String monstruoARobarPorUnTurno;

    public Trampa(String nombre, String cuadroDeTexto, TipoHabilidadEspecialTrampa tipoHabilidadEspecial) {
        super(nombre, cuadroDeTexto, false);
        this.tipoHabilidadEspecial = tipoHabilidadEspecial;
        this.turnosActiva = 0;
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

    public void setTipoHabilidadEspecialTrampa(TipoHabilidadEspecialTrampa tipoHabilidadEspecial) {
        this.tipoHabilidadEspecial = tipoHabilidadEspecial;
    }
    public void setTurnosActiva(byte turnosActiva){
        this.turnosActiva = turnosActiva;
    }
    public void setMonstruoARobarPorUnTurno(String monstruoARobarPorUnTurno){
        this.monstruoARobarPorUnTurno = monstruoARobarPorUnTurno;
    }

    @Override
    public void ActivarEfecto(Campo campo, byte turno, Scanner scaner) {
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
        for(int i=0; i<5; i++){
            if(magiasYTrampasAtacantes[i] != null && magiasYTrampasAtacantes[i].isVisible() && magiasYTrampasAtacantes[i] instanceof Trampa){
                Trampa trampa = (Trampa) magiasYTrampasAtacantes[i];
                switch(trampa.getTipoHabilidadEspecialTrampa()){

                    case FUERZA_DE_ESPEJO:
                        for(int j=0; j<5; j++){
                            if(monstruosDefensores[j] != null){
                                if(monstruosDefensores[j].isEnPosicionAtaque()){
                                    monstruosDefensores[j] = null;
                                }
                            }
                        }
                        cementerioAtacante.add(magiasYTrampasAtacantes[i]);
                        magiasYTrampasAtacantes[i] = null;
                        break;

                    case CILINDRO_MAGICO:
                        System.out.println("Escriba el nombre exacto del monstruo cuyo ataque quiere negar");
                        System.out.print("Nombre: ");
                        String monstruoANegar = scaner.nextLine();
                        System.out.println("");
                        for(Monstruo m : monstruosDefensores){
                            if(m.getNombre().equals(monstruoANegar)){
                                m.setAtaque((short) 0);
                                defensor.setLP((short) (defensor.getLP() - m.getAtaqueBase()));
                                break;
                            }
                        }
                        cementerioAtacante.add(magiasYTrampasAtacantes[i]);
                        magiasYTrampasAtacantes[i] = null;
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
                        cementerioAtacante.add(magiasYTrampasAtacantes[i]);
                        magiasYTrampasAtacantes[i] = null;
                        break;

                    case ARMADURA_DE_SAKURETSU:
                        for(int j=0; j<5; j++){
                            if(monstruosDefensores[j] != null){
                                cementerioDefensor.add(monstruosDefensores[j]);
                                monstruosDefensores[j] = null;
                            }
                        }
                        cementerioAtacante.add(magiasYTrampasAtacantes[i]);
                        magiasYTrampasAtacantes[i] = null;
                        break;

                    case LLAMADA_DE_LOS_CONDENADOS:
                        byte indiceMonstruoARevivir=0;
                        System.out.println("Ingrese el nombre del monstruo en su cementerio que quiere devolver al campo");
                        System.out.print("Nombre: ");
                        String monstruoADevolver = scaner.nextLine();
                        System.out.println("");
                        for(int j=0; j<cementerioAtacante.size(); j++){
                            if(cementerioAtacante.get(j).getNombre().equals(monstruoADevolver) && cementerioAtacante.get(j) instanceof Monstruo){
                                indiceMonstruoARevivir = (byte) j;
                                break;
                            }
                        }
                        for(int j=0; j<5; j++){
                            if(monstruosAtacantes[j] != null && cementerioAtacante.get(indiceMonstruoARevivir) instanceof Monstruo){
                                monstruosAtacantes[j] = (Monstruo) cementerioAtacante.remove(indiceMonstruoARevivir);
                                break;
                            }
                        }
                        cementerioAtacante.add(magiasYTrampasAtacantes[i]);
                        magiasYTrampasAtacantes[i] = null;
                        break;

                    case SOMBREROS_MAGICOS:
                        if(trampa.getTurnosActiva() < 1){
                            byte indiceMonstruoAOcultar=0;
                            System.out.println("Ingrese el nombre del monstruo que quiere ocultar");
                            System.out.print("Nombre: ");
                            String nombreMonstruoAOcultar = scaner.nextLine();
                            System.out.println("");
                            System.out.println("Ingrese los nombres de las cartas magicas o de trampa de su mazo que se usaran para ocultar al monstruo");
                            System.out.print("Carta 1: ");
                            String nombreMagica1AOcultar = scaner.nextLine();
                            System.out.println("");
                            System.out.print("Carta 2: ");
                            String nombreMagica2AOcultar = scaner.nextLine();
                            System.out.println("");
                            for(int j=0; j<5; j++){
                                if(monstruosAtacantes[j].getNombre().equals(nombreMonstruoAOcultar)){
                                    indiceMonstruoAOcultar = (byte) j;
                                    break;
                                }
                            }
                            List<Monstruo> cartasAOcultar = new ArrayList<> ();
                            cartasAOcultar.add(monstruosAtacantes[indiceMonstruoAOcultar]);
                            cartasAOcultar.add(new Monstruo("Sombrero 1", "Una carta mágica que oculta a un monstruo", (byte) 0, (short) 0, (short) 0));
                            cartasAOcultar.add(new Monstruo("Sombrero 2", "Una carta mágica que oculta a un monstruo", (byte) 0, (short) 0, (short) 0));
                            Collections.shuffle(cartasAOcultar);
                            for(int j=0; j<5; j++){
                                if(monstruosAtacantes[j] != null && cartasAOcultar.size() > 0){
                                    monstruosAtacantes[j] = cartasAOcultar.remove(0);
                                }
                            }
                            for(int j=0; j<atacante.getMazo().size(); j++){
                                if(atacante.getMazo().get(j).getNombre().equals(nombreMagica1AOcultar)){
                                    cementerioAtacante.add(atacante.getMazo().remove(j));
                                }
                                else if(atacante.getMazo().get(j).getNombre().equals(nombreMagica2AOcultar)){
                                    cementerioAtacante.add(atacante.getMazo().remove(j));
                                }
                            }
                            trampa.setTurnosActiva((byte) (trampa.getTurnosActiva()+1));
                        }
                        else if(trampa.getTurnosActiva() > 0){
                            trampa.setTurnosActiva((byte) 0);
                            for(int j=0; j<5; j++){
                                if(monstruosAtacantes[j].getNombre().equals("Sombrero 1") || monstruosAtacantes[j].getNombre().equals("Sombrero 2")){
                                    monstruosAtacantes[j] = null;
                                }
                            }
                            cementerioAtacante.add(magiasYTrampasAtacantes[i]);
                            magiasYTrampasAtacantes[i] = null;
                        }
                        break;

                    case WABOKU:
                        if(trampa.getTurnosActiva() < 1){
                            for(int j=0; j<5; j++){
                                monstruosDefensores[j].setAtaque((short) 0);
                            }
                            trampa.setTurnosActiva((byte) (trampa.getTurnosActiva()+1));
                        }
                        else if(trampa.getTurnosActiva() > 0){
                            trampa.setTurnosActiva((byte) 0);
                            for(int j=0; j<5; j++){
                                monstruosDefensores[j].setAtaque(monstruosDefensores[j].getAtaqueBase());
                            }
                            cementerioAtacante.add(magiasYTrampasAtacantes[i]);
                            magiasYTrampasAtacantes[i] = null;
                        }
                        break;

                    case MURO_DE_ESPEJO:
                        if(trampa.getTurnosActiva() < 1){
                            for(int j=0; j<5; j++){
                                monstruosDefensores[j].setAtaque((short) (monstruosDefensores[j].getAtaque() / 2));
                            }
                            trampa.setTurnosActiva((byte) (trampa.getTurnosActiva()+1));
                        }
                        else if(trampa.getTurnosActiva() > 0){
                            byte opcionMuroDeEspejo = 0;
                            System.out.println("Quiere mantener el efecto de Muro de Espejo durante un turno más a cambio de 2000 LP \n1. Si \2. No");
                            System.out.print("Opción: ");
                            opcionMuroDeEspejo = scaner.nextByte();
                            scaner.nextLine();
                            System.out.println("");
                            trampa.setTurnosActiva((byte) 0);
                            if(opcionMuroDeEspejo == 1){
                                atacante.setLP((short) (atacante.getLP()-2000));
                            }
                            else if(opcionMuroDeEspejo == 2){
                                for(int j=0; j<5; j++){
                                    monstruosDefensores[j].setAtaque(monstruosDefensores[j].getAtaqueBase());
                                }
                                cementerioAtacante.add(magiasYTrampasAtacantes[i]);
                                magiasYTrampasAtacantes[i] = null;
                            }
                        }
                        break;

                    case CONTROL_DE_LA_MENTE:
                        String monstruoARobar;
                        if(trampa.getTurnosActiva() < 1){
                            System.out.println("Escriba el nombre del monstruo que robará por un turno");
                            System.out.print("Nombre: ");
                            monstruoARobar = scaner.nextLine();
                            trampa.setMonstruoARobarPorUnTurno(monstruoARobar);
                            System.out.println("");
                            for(int j=0; j<5; j++){
                                if(monstruosDefensores[j] != null && monstruosDefensores[j].getNombre().equals(monstruoARobar)){
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
                            trampa.setTurnosActiva((byte) (trampa.getTurnosActiva()+1));
                        }
                        else if(trampa.getTurnosActiva() > 0){
                            for(int j=0; j<5; j++){
                                if(monstruosAtacantes[j] != null && monstruosAtacantes[j].getNombre().equals(trampa.getMonstruoARobarPorUnTurno())){
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
                            trampa.setTurnosActiva((byte) 0);
                            trampa.setMonstruoARobarPorUnTurno("");
                            cementerioAtacante.add(magiasYTrampasAtacantes[i]);
                            magiasYTrampasAtacantes[i] = null;
                        }
                        break;

                    case DRENAJE_DE_HABILIDAD:
                        atacante.setLP((short) (atacante.getLP()-1000));
                        if(trampa.getTurnosActiva() < 1){
                            for(int j=0; j<5; j++){
                                monstruosDefensores[j].setAtaque((short) (0));
                            }
                            trampa.setTurnosActiva((byte) (trampa.getTurnosActiva()+1));
                        }
                        else if(trampa.getTurnosActiva() > 0){
                            for(int j=0; j<5; j++){
                                monstruosDefensores[j].setAtaque(monstruosDefensores[j].getAtaqueBase());
                            }
                            trampa.setTurnosActiva((byte) 0);
                            cementerioAtacante.add(magiasYTrampasAtacantes[i]);
                            magiasYTrampasAtacantes[i] = null;
                        }
                        break;

                }
            }
        }
    }

    @Override
    public boolean jugar(Campo campo, byte turno, Scanner scaner) {
        return true;
    }

    
    
}
