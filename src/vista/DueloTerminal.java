package vista;

import java.util.*;

public class DueloTerminal implements DueloInterfaz {
    
    Campo campo;
    byte turno;
    String fase;

    public DueloTerminal() {

    }

    public Campo getCampo() {
        return campo;
    }
    public byte getTurno() {
        return turno;
    }
    public String getFase(){
        return fase;
    }

    protected void setCampo(Campo campo) {
        this.campo = campo;
    }
    protected void setTurno(byte turno) {
        this.turno = turno;
    }
    protected void setFase(String fase){
        this.fase = fase;
    }

    public void imprimirCampo(){
        System.out.println("_____________________________________________________________________________");
        System.out.println("---------------------------Estado Actual Del Campo---------------------------");
        System.out.println("");
        System.out.println("Jugador: " + campo.getJugador1().getNombre());
        for(int i=0; i<5; i++){
            if(campo.getMagicasYTrampasEnCampoJugador1()[i] != null){
                if(!campo.getMagicasYTrampasEnCampoJugador1()[i].isVisible()){
                    System.out.println(i + ". Carta oculta");
                }
                else if(campo.getMagicasYTrampasEnCampoJugador1()[i] instanceof Magia){
                    Magia magica = (Magia) campo.getMagicasYTrampasEnCampoJugador1()[i];
                    if(magica.isVisible()){
                        System.out.println(i + ". | MAG | " + magica.getNombre() + " | HAB: " + magica.getCuadroDeTexto() + " | Se está usando");
                    }
                    else{
                        System.out.println(i + ". | MAG | " + magica.getNombre() + " | HAB: " + magica.getCuadroDeTexto() + " | Colocada (sin usar)");
                    }
                }
                else if(campo.getMagicasYTrampasEnCampoJugador1()[i] instanceof Trampa){
                    Trampa trampa = (Trampa) campo.getMagicasYTrampasEnCampoJugador1()[i];
                    if(trampa.isVisible()){
                        System.out.println(i + ". | TRA | " + trampa.getNombre() + " | HAB: " + trampa.getCuadroDeTexto() + " | Se está usando");
                    }
                    else{
                        System.out.println(i + ". | TRA | " + trampa.getNombre() + " | HAB: " + trampa.getCuadroDeTexto() + " | Colocada (sin usar)");
                    }
                }
            }
            else{
                System.out.println("Espacio vacío");
            }
        }
        for(int i=0; i<5; i++){
            if(campo.getMonstruosEnCampoJugador1()[i] != null){
                Monstruo monstruo = (Monstruo) campo.getMonstruosEnCampoJugador1()[i];
                if(!monstruo.isVisible()){
                    System.out.println(i + ". Carta oculta");
                }
                else if(monstruo.isEnPosicionAtaque()){
                    System.out.println(i + ". | MON | " + monstruo.getNombre() +  " | LVL: " + monstruo.getNivel() + " | ATK: " + monstruo.getAtaque() + " | DEF: " + monstruo.getDefensa() + " | En posición de ataque");
                }
                else{
                    System.out.println(i + ". | MON | " + monstruo.getNombre() +  " | LVL: " + monstruo.getNivel() + " | ATK: " + monstruo.getAtaque() + " | DEF: " + monstruo.getDefensa() + " | En posición de defensa");
                }
            }
            else{
                System.out.println("Espacio vacío");
            }
        }
        System.out.println("");
        System.out.println("Jugador: " + campo.getJugador2().getNombre());
        for(int i=0; i<5; i++){
            if(campo.getMagicasYTrampasEnCampoJugador2()[i] != null){
                if(!campo.getMagicasYTrampasEnCampoJugador2()[i].isVisible()){
                    System.out.println(i + ". Carta oculta");
                }
                else if(campo.getMagicasYTrampasEnCampoJugador2()[i] instanceof Magia){
                    Magia magica = (Magia) campo.getMagicasYTrampasEnCampoJugador2()[i];
                    if(magica.isVisible()){
                        System.out.println(i + ". | MAG | " + magica.getNombre() + " | HAB: " + magica.getCuadroDeTexto() + " | Se está usando");
                    }
                    else{
                        System.out.println(i + ". | MAG | " + magica.getNombre() + " | HAB: " + magica.getCuadroDeTexto() + " | Colocada (sin usar)");
                    }
                }
                else if(campo.getMagicasYTrampasEnCampoJugador2()[i] instanceof Trampa){
                    Trampa trampa = (Trampa) campo.getMagicasYTrampasEnCampoJugador2()[i];
                    if(trampa.isVisible()){
                        System.out.println(i + ". | TRA | " + trampa.getNombre() + " | HAB: " + trampa.getCuadroDeTexto() + " | Se está usando");
                    }
                    else{
                        System.out.println(i + ". | TRA | " + trampa.getNombre() + " | HAB: " + trampa.getCuadroDeTexto() + " | Colocada (sin usar)");
                    }
                }
            }
            else{
                System.out.println("Espacio vacío");
            }
        }
        for(int i=0; i<5; i++){
            if(campo.getMonstruosEnCampoJugador2()[i] != null){
                Monstruo monstruo = (Monstruo) campo.getMonstruosEnCampoJugador2()[i];
                if(!monstruo.isVisible()){
                    System.out.println(i + ". Carta oculta");
                }
                else if(monstruo.isEnPosicionAtaque()){
                    System.out.println(i + ". | MON | " + monstruo.getNombre() + " | LVL: " + monstruo.getNivel() + " | ATK: " + monstruo.getAtaque() + " | DEF: " + monstruo.getDefensa() + " | En posición de ataque");
                }
                else{
                    System.out.println(i + ". | MON | " + monstruo.getNombre() + " | LVL: " + monstruo.getNivel() + " | ATK: " + monstruo.getAtaque() + " | DEF: " + monstruo.getDefensa() + " | En posición de defensa");
                }
            }
            else{
                System.out.println("Espacio vacío");
            }
        }
        System.out.println("");
        System.out.println("_____________________________________________________________________________");
    }

    public void imprimirMazoAtacante(){
        Jugador atacante;
        if(turno%2==0){
            atacante = campo.getJugador1();
        }
        else{
            atacante = campo.getJugador2();
        }
        System.out.println("----------Tu Mazo----------");
        for(int j=0; j<atacante.getMazo().size(); j++){
            if(atacante.getMazo().get(j) instanceof Monstruo){
                Monstruo monstruo = (Monstruo) atacante.getMazo().get(j);
                System.out.println("MON | " + monstruo.getNombre() + " | LVL: " + monstruo.getNivel() + " | ATK: " + monstruo.getAtaque() + " | DEF: " + monstruo.getDefensa());
            }
            else if(atacante.getMazo().get(j) instanceof Magia){
                Magia magica = (Magia) atacante.getMazo().get(j);
                System.out.println("MAG | " + magica.getNombre() + " | HAB: " + magica.getCuadroDeTexto());
            }
            else if(atacante.getMazo().get(j) instanceof Trampa){
                Trampa trampaImprimir = (Trampa) atacante.getMazo().get(j);
                System.out.println("TRA | " + trampaImprimir.getNombre() + " | HAB: " + trampaImprimir.getCuadroDeTexto());
            }
        }
    }

    public void imprimirMazoDefensor(){
        Jugador defensor;
        if(turno%2==0){
            defensor = campo.getJugador2();
        }
        else{
            defensor = campo.getJugador1();
        }
        System.out.println("----------Tu Mazo----------");
        for(int j=0; j<defensor.getMazo().size(); j++){
            if(defensor.getMazo().get(j) instanceof Monstruo){
                Monstruo monstruo = (Monstruo) defensor.getMazo().get(j);
                System.out.println("MON | " + monstruo.getNombre() + " | LVL: " + monstruo.getNivel() + " | ATK: " + monstruo.getAtaque() + " | DEF: " + monstruo.getDefensa());
            }
            else if(defensor.getMazo().get(j) instanceof Magia){
                Magia magica = (Magia) defensor.getMazo().get(j);
                System.out.println("MAG | " + magica.getNombre() + " | HAB: " + magica.getCuadroDeTexto());
            }
            else if(defensor.getMazo().get(j) instanceof Trampa){
                Trampa trampaImprimir = (Trampa) defensor.getMazo().get(j);
                System.out.println("TRA | " + trampaImprimir.getNombre() + " | HAB: " + trampaImprimir.getCuadroDeTexto());
            }
        }
    }

    public void imprimirMano(){
        byte i = 0;
        Jugador atacante;
        List<Carta> cementerioAtacante;
        if(turno%2==0){
            atacante = campo.getJugador1();
            cementerioAtacante = campo.getCementerioJugador1();
        }
        else{
            atacante = campo.getJugador2();
            cementerioAtacante = campo.getCementerioJugador2();
        }
        System.out.println("--------Cartas En Mano De " + atacante.getNombre() + "--------");
        System.out.println("LP: " + atacante.getLP());
        for(int j=0; j<atacante.getMano().size(); j++){
            if(atacante.getMano().get(j) instanceof Monstruo){
                Monstruo monstruo = (Monstruo) atacante.getMano().get(j);
                System.out.println(j + ". | MON | " + monstruo.getNombre() + " | LVL: " + monstruo.getNivel() + " | ATK: " + monstruo.getAtaque() + " | DEF: " + monstruo.getDefensa());
            }
            else if(atacante.getMano().get(j) instanceof Magia){
                Magia magica = (Magia) atacante.getMano().get(j);
                System.out.println(j + ". | MAG | " + magica.getNombre() + " | HAB: " + magica.getCuadroDeTexto());
            }
            else if(atacante.getMano().get(j) instanceof Trampa){
                Trampa trampa = (Trampa) atacante.getMano().get(j);
                System.out.println(j + ". | TRA | " + trampa.getNombre() + " | HAB: " + trampa.getCuadroDeTexto());
            }
        }
        System.out.println("----------------Cementerio----------------");
        for(Carta carta : cementerioAtacante){
            if(carta instanceof Monstruo){
                Monstruo m = (Monstruo) carta;
                System.out.println(i + ". | MON | " + m.getNombre() + " | LVL: " + m.getNivel() + " | ATK: " + m.getAtaque() + " | DEF: " + m.getDefensa());
                i++;
            }
            else if(carta instanceof Magia){
                Magia mg = (Magia) carta;
                System.out.println(i + ". | MAG | " + mg.getNombre() + " | HAB: " + mg.getCuadroDeTexto());
                i++;
            }
            else if(carta instanceof Trampa){
                Trampa t = (Trampa) carta;
                System.out.println(i + ". | TRA | " + t.getNombre() + " | HAB: " + t.getCuadroDeTexto());
            }
        }
    }

    public void imprimirCementerios(){
        byte i = 0;
        System.out.println("----------------Cementerio De " + campo.getJugador1().getNombre() + "----------------");
        for(Carta carta : campo.getCementerioJugador1()){
            if(carta instanceof Monstruo){
                Monstruo m = (Monstruo) carta;
                System.out.println(i + ". | MON | " + m.getNombre() + " | LVL: " + m.getNivel() + " | ATK: " + m.getAtaque() + " | DEF: " + m.getDefensa());
                i++;
            }
            else if(carta instanceof Magia){
                Magia mg = (Magia) carta;
                System.out.println(i + ". | MAG | " + mg.getNombre() + " | HAB: " + mg.getCuadroDeTexto());
                i++;
            }
            else if(carta instanceof Trampa){
                Trampa t = (Trampa) carta;
                System.out.println(i + ". | TRA | " + t.getNombre() + " | HAB: " + t.getCuadroDeTexto());
            }
        }
        i = 0;
        System.out.println("----------------Cementerio De " + campo.getJugador2().getNombre() + "----------------");
        for(Carta carta : campo.getCementerioJugador2()){
            if(carta instanceof Monstruo){
                Monstruo m = (Monstruo) carta;
                System.out.println(i + ". | MON | " + m.getNombre() + " | LVL: " + m.getNivel() + " | ATK: " + m.getAtaque() + " | DEF: " + m.getDefensa());
                i++;
            }
            else if(carta instanceof Magia){
                Magia mg = (Magia) carta;
                System.out.println(i + ". | MAG | " + mg.getNombre() + " | HAB: " + mg.getCuadroDeTexto());
                i++;
            }
            else if(carta instanceof Trampa){
                Trampa t = (Trampa) carta;
                System.out.println(i + ". | TRA | " + t.getNombre() + " | HAB: " + t.getCuadroDeTexto());
            }
        }
    }

    public void juego(){
        Scanner scaner = new Scanner(System.in);
        Mazo mazo = new Mazo();
        Jugador atacante = new Jugador(), defensor = new Jugador();
        Monstruo[] monstruosAtacantes, monstruosDefensores;
        Carta[] magiasYTrampasAtacantes, magiasYTrampasDefensoras;
        List<Carta> cementerioAtacante, cementerioDefensor;
        List<Monstruo> listaVacia = new ArrayList<> ();
        boolean hayUnGanador = false;

        //Seteo de objetos iniciales 
        System.out.print("Escriba el nombre del jugador 1: ");
        String nombreJugador1 = scaner.nextLine();
        System.out.println("");
        System.out.print("Escriba el nombre del jugador 2: ");
        String nombreJugador2 = scaner.nextLine();
        System.out.println("");
        Jugador jugador1 = new Jugador(nombreJugador1, mazo);
        Jugador jugador2 = new Jugador(nombreJugador2, mazo);
        Campo campo = new Campo(jugador1, jugador2);
        this.setCampo(campo);
        this.setTurno((byte) 0);

        //Inicio del juego, y continuación hasta que haya un ganador
        while(!hayUnGanador){
            boolean defensorTieneTrampaDeInvocacion = false, defensorTieneTrampaDeAtaque = false;
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
                if(magiasYTrampasDefensoras[i] != null && magiasYTrampasDefensoras[i] instanceof Trampa){
                    Trampa trampa = (Trampa) magiasYTrampasDefensoras[i];
                    TipoHabilidadEspecialTrampa tipo = trampa.getTipoHabilidadEspecialTrampa();
                    if(tipo == TipoHabilidadEspecialTrampa.FUERZA_DE_ESPEJO || tipo == TipoHabilidadEspecialTrampa.CILINDRO_MAGICO || tipo == TipoHabilidadEspecialTrampa.ARMADURA_DE_SAKURETSU){
                        defensorTieneTrampaDeAtaque = true;
                    }
                    else{
                        defensorTieneTrampaDeInvocacion = true;
                    }
                }
            }
            if(atacante.getLP() != 0 || defensor.getLP() != 0){
                System.out.println("\n");
                System.out.println("Turno: " + turno);

                //Draw Phase (Fase de Robo)
                setFase("Draw Phase");
                System.out.println("");
                System.out.println("------Draw Phase------");
                System.out.println("");
                if(turno != 0){
                    if(atacante.getMazo().size() > 0){
                        System.out.println("Has robado: " + atacante.getMazo().get(0).getNombre());
                        atacante.getMano().add(atacante.getMazo().remove(0));
                    }
                    else{
                        hayUnGanador = true;
                    }
                }
                this.imprimirMano();

                //Standby Phase (Fase de Espera)
                setFase("Standby Phase");
                System.out.println("\n");
                System.out.println("------Standby Phase------");
                if(defensorTieneTrampaDeInvocacion || defensorTieneTrampaDeAtaque){
                    for(int i=0; i<5; i++){
                        if(magiasYTrampasDefensoras[i] instanceof Trampa){
                            Trampa trampa = (Trampa) magiasYTrampasDefensoras[i];
                            if(trampa.isActivada()){
                                trampa.jugar(campo, this.getTurno(), (byte) i, (byte) 0, "", "", "", listaVacia);
                            }
                        }
                    }
                }

                //Main Phase 1 (Fase Principal 1)
                setFase("Main Phase 1");
                System.out.println("\n");
                System.out.println("------Main Phase 1------");
                System.out.println("");
                byte activarTrampa = 0;
                if(defensorTieneTrampaDeInvocacion){
                    System.out.println("Defensor, tiene trampas que puede usar en la Main Phase 1, quiere activar una trampa? \n1. Si \n2. No");
                    System.out.print("Opción: ");
                    activarTrampa = scaner.nextByte();
                    scaner.nextLine();
                    System.out.println("");
                }
                byte opcionMainPhase1 = 0, opcionCartaAColocar = 0, indiceCartaAColocar = 0, posicionCartaAColocar = 0;
                do{
                    do{
                        System.out.println("Escoge una opción: \n1. Colocar una carta. \n2. Cambiar una carta de posición. \n3. Saltar fase.");
                        System.out.print("Opción: ");
                        opcionMainPhase1 = scaner.nextByte();
                        scaner.nextLine();
                        System.out.println("");
                    } while(opcionMainPhase1 != 1 && opcionMainPhase1 != 2 && opcionMainPhase1 != 3);
                    if(opcionMainPhase1 == 1){
                        do{
                            System.out.println("Qué tipo de carta quiere colocar: \n1. Monstruo. \n2. Mágica. \n3. Trampa");
                            System.out.print("Opción: ");
                            opcionCartaAColocar = scaner.nextByte();
                            scaner.nextLine();
                            System.out.println("");
                        } while(opcionCartaAColocar != 1 && opcionCartaAColocar != 2 && opcionCartaAColocar != 3);
                        do{
                            System.out.println("Escriba el número que ocupa en su mano la carta que quiere colocar: ");
                            System.out.print("Opción: ");
                            indiceCartaAColocar = scaner.nextByte();
                            scaner.nextLine();
                            System.out.println("");
                        } while(indiceCartaAColocar >= atacante.getMano().size() || indiceCartaAColocar < 0);
                        if(opcionCartaAColocar == 1 && atacante.getMano().get(indiceCartaAColocar) instanceof Monstruo){
                            boolean posibleColocar = false;
                            Monstruo m = (Monstruo) atacante.getMano().get(indiceCartaAColocar);
                            if(m.getNivel() > 4){
                                byte monstruoASacrificar = 0;
                                do{
                                    do{
                                        System.out.println("Debe sacrificar un monstruo en campo para invocar a este monstruo.");
                                        System.out.println("Ingrese el indice del monstruo en campo a sacrificar o escriba 6 para cancelar.");
                                        System.out.print("Opción: ");
                                        monstruoASacrificar = scaner.nextByte();
                                        scaner.nextLine();
                                        System.out.println("");
                                    } while(monstruoASacrificar<0 || monstruoASacrificar >6);
                                    if(monstruoASacrificar<6 && monstruosAtacantes[monstruoASacrificar] != null){
                                        cementerioAtacante.add(monstruosAtacantes[monstruoASacrificar]);
                                        monstruosAtacantes[monstruoASacrificar] = null;
                                        posibleColocar = true;
                                        break;
                                    }
                                } while(monstruoASacrificar != 6 );
                            }
                            else{
                                posibleColocar = true;;
                            }
                            if(posibleColocar){
                                do{
                                    System.out.println("Escoja la posición en la que quiere colocar al monstruo: \n1. Ataque. \n2. Defensa.");
                                    System.out.print("Opción: ");
                                    posicionCartaAColocar = scaner.nextByte();
                                    scaner.nextLine();
                                    System.out.println("");
                                } while(posicionCartaAColocar != 1 && posicionCartaAColocar != 2);
                                for(int i=0; i<5; i++){
                                    if(monstruosAtacantes[i] == null && atacante.getMano().get(indiceCartaAColocar) instanceof Monstruo){
                                        monstruosAtacantes[i] = (Monstruo) atacante.getMano().get(indiceCartaAColocar);
                                        atacante.getMano().remove(indiceCartaAColocar);
                                        if(posicionCartaAColocar == 1){
                                            monstruosAtacantes[i].setEnPosicionAtaque(true);
                                            break;
                                        }
                                        else if(posicionCartaAColocar == 2){
                                            monstruosAtacantes[i].setEnPosicionAtaque(false);
                                            break;
                                        }
                                    }
                                }
                                if(defensorTieneTrampaDeInvocacion && activarTrampa == 1){
                                    for(int i=0; i<5; i++){
                                        if(magiasYTrampasDefensoras[i] instanceof Trampa){
                                            byte activarTrampaInvocacion = 0;
                                            Trampa trampa = (Trampa) magiasYTrampasDefensoras[i];
                                            if(!trampa.isActivada() && trampa.getTipoHabilidadEspecialTrampa() == TipoHabilidadEspecialTrampa.TRIBUTO_TORRENCIAL){
                                                System.out.println("Defensor, quiere activar la trampa Tributo Torrencial? \n1. Si \n2. No");
                                                System.out.print("Opción: ");
                                                activarTrampaInvocacion = scaner.nextByte();
                                                scaner.nextLine();
                                                System.out.println("");
                                                if(activarTrampaInvocacion == 1){
                                                    trampa.jugar(campo, this.getTurno(), (byte) i, (byte) 0, "", "", "", listaVacia);
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        else if(opcionCartaAColocar == 2){
                            do{
                                System.out.println("Escoja que quiere hacer con la carta mágica: \n1. Colocar. \n2. Usar.");
                                System.out.print("Opción: ");
                                posicionCartaAColocar = scaner.nextByte();
                                scaner.nextLine();
                                System.out.println("");
                            } while(posicionCartaAColocar != 1 && posicionCartaAColocar != 2);
                            for(int i=0; i<5; i++){
                                if(magiasYTrampasAtacantes[i] == null && atacante.getMano().get(indiceCartaAColocar) instanceof Magia){
                                    magiasYTrampasAtacantes[i] = atacante.getMano().get(indiceCartaAColocar);
                                    atacante.getMano().remove(indiceCartaAColocar);
                                    if(posicionCartaAColocar == 1){
                                        magiasYTrampasAtacantes[i].setVisible(false);
                                        break;
                                    }
                                    else if(posicionCartaAColocar == 2){
                                        magiasYTrampasAtacantes[i].setVisible(true);
                                        break;
                                    }
                                }
                            }
                        }
                        else if(opcionCartaAColocar == 3){
                            for(int i=0; i<5; i++){
                                if(magiasYTrampasAtacantes[i] == null && atacante.getMano().get(indiceCartaAColocar) instanceof Trampa){
                                    magiasYTrampasAtacantes[i] = atacante.getMano().get(indiceCartaAColocar);
                                    atacante.getMano().remove(indiceCartaAColocar);
                                    magiasYTrampasAtacantes[i].setVisible(false);
                                    break;
                                }
                            }
                        }
                    }
                    else if(opcionMainPhase1 == 2){
                        System.out.println("Escoja que tipo de carta quiere cambiar de posición: \n1. Mágica. \n2. Monstruo. \n3. Trampa");
                        System.out.print("Opción: ");
                        opcionCartaAColocar = scaner.nextByte();
                        scaner.nextLine();
                        System.out.println("");
                        System.out.println("Escriba el número que ocupa la carta cuya posición quiere cambiar. ");
                        System.out.print("Opción: ");
                        indiceCartaAColocar = scaner.nextByte();
                        scaner.nextLine();
                        System.out.println("");
                        if(opcionCartaAColocar == 1 || opcionCartaAColocar == 3){
                            if(magiasYTrampasAtacantes[indiceCartaAColocar] != null && !magiasYTrampasAtacantes[indiceCartaAColocar].isVisible()){
                                magiasYTrampasAtacantes[indiceCartaAColocar].setVisible(true);
                            }
                        }
                        else if(opcionCartaAColocar == 2){
                            if(monstruosAtacantes[indiceCartaAColocar] != null && monstruosAtacantes[indiceCartaAColocar].isEnPosicionAtaque()){
                                monstruosAtacantes[indiceCartaAColocar].setEnPosicionAtaque(false);
                            }
                            else if(monstruosAtacantes[indiceCartaAColocar] != null){
                                monstruosAtacantes[indiceCartaAColocar].setEnPosicionAtaque(true);
                            }
                        }
                    }
                    else if(opcionMainPhase1 == 3){
                        break;
                    }
                    this.imprimirMano();
                } while(opcionMainPhase1 != 3);
                this.imprimirCampo();
                if(defensorTieneTrampaDeInvocacion && activarTrampa == 1){
                    for(int i=0; i<5; i++){
                        if(magiasYTrampasDefensoras[i] instanceof Trampa){
                            byte activarTrampaInvocacion = 0;
                            Trampa trampa = (Trampa) magiasYTrampasDefensoras[i];
                            if(!trampa.isActivada() && trampa.getTipoHabilidadEspecialTrampa() == TipoHabilidadEspecialTrampa.LLAMADA_DE_LOS_CONDENADOS){
                                System.out.println("Defensor, quiere activar la trampa Llamada de los Condenados? \n1. Si \n2. No");
                                System.out.print("Opción: ");
                                activarTrampaInvocacion = scaner.nextByte();
                                scaner.nextLine();
                                System.out.println("");
                                if(activarTrampaInvocacion == 1){
                                    imprimirCementerios();
                                    System.out.println("Ingrese el nombre del monstruo en su cementerio que quiere devolver al campo");
                                    System.out.print("Nombre: ");
                                    String monstruoADevolver = scaner.nextLine();
                                    System.out.println("");
                                    trampa.jugar(campo, this.getTurno(), (byte) i, (byte) 0, monstruoADevolver, "", "", listaVacia);
                                }
                            }
                            else if(!trampa.isActivada() && trampa.getTipoHabilidadEspecialTrampa() == TipoHabilidadEspecialTrampa.SOMBREROS_MAGICOS){
                                System.out.println("Defensor, quiere activar la trampa Sombreros Mágicos? \n1. Si \n2. No");
                                System.out.print("Opción: ");
                                activarTrampaInvocacion = scaner.nextByte();
                                scaner.nextLine();
                                System.out.println("");
                                if(activarTrampaInvocacion == 1){
                                    imprimirMazoDefensor();
                                    System.out.println("Ingrese el nombre del monstruo en campo que quiere ocultar");
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
                                    trampa.jugar(campo, this.getTurno(), (byte) i, (byte) 0, nombreMonstruoAOcultar, nombreMagica1AOcultar, nombreMagica2AOcultar, listaVacia);
                                }
                            }
                            else if(!trampa.isActivada() && trampa.getTipoHabilidadEspecialTrampa() == TipoHabilidadEspecialTrampa.WABOKU){
                                System.out.println("Defensor, quiere activar la trampa Waboku? \n1. Si \n2. No");
                                System.out.print("Opción: ");
                                activarTrampaInvocacion = scaner.nextByte();
                                scaner.nextLine();
                                System.out.println("");
                                if(activarTrampaInvocacion == 1){
                                    trampa.jugar(campo, this.getTurno(), (byte) i, (byte) 0, "", "", "", listaVacia);
                                }
                            }
                            else if(trampa.getTipoHabilidadEspecialTrampa() == TipoHabilidadEspecialTrampa.MURO_DE_ESPEJO){
                                if(trampa.getTurnosActiva() > 0){
                                    byte opcionMuroDeEspejo = 0;
                                    System.out.println("Defensor, quiere mantener el efecto de Muro de Espejo durante un turno más a cambio de 2000 LP? \n1. Si \2. No");
                                    System.out.print("Opción: ");
                                    opcionMuroDeEspejo = scaner.nextByte();
                                    scaner.nextLine();
                                    System.out.println("");
                                    trampa.jugar(campo, this.getTurno(), (byte) i, opcionMuroDeEspejo, "", "", "", listaVacia);
                                }
                                else if(!trampa.isActivada()){
                                    System.out.println("Defensor, quiere activar la trampa Muro de Espejo? \n1. Si \n2. No");
                                    System.out.print("Opción: ");
                                    activarTrampaInvocacion = scaner.nextByte();
                                    scaner.nextLine();
                                    System.out.println("");
                                    if(activarTrampaInvocacion == 1){
                                        trampa.jugar(campo, this.getTurno(), (byte) i, (byte) 0, "", "", "", listaVacia);
                                    }
                                }
                            }
                            else if(trampa.getTipoHabilidadEspecialTrampa() == TipoHabilidadEspecialTrampa.REPRESION){
                                if(!trampa.isActivada() && trampa.getTurnosActiva() < 1){
                                    System.out.println("Defensor, quiere activar la trampa Represión? \n1. Si \n2. No");
                                    System.out.print("Opción: ");
                                    activarTrampaInvocacion = scaner.nextByte();
                                    scaner.nextLine();
                                    System.out.println("");
                                    if(activarTrampaInvocacion == 1){
                                        System.out.println("Escriba el nombre del monstruo que robará por un turno");
                                        System.out.print("Nombre: ");
                                        String monstruoARobar = scaner.nextLine();
                                        trampa.setMonstruoARobarPorUnTurno(monstruoARobar);
                                        System.out.println("");
                                        trampa.jugar(campo, this.getTurno(), (byte) i, (byte) 0, monstruoARobar, "", "", listaVacia);
                                    }
                                }
                                else if(trampa.getTurnosActiva() > 0){
                                    trampa.jugar(campo, this.getTurno(), (byte) i, (byte) 0, "", "", "", listaVacia);
                                }
                            }
                            else if(!trampa.isActivada() && trampa.getTipoHabilidadEspecialTrampa() == TipoHabilidadEspecialTrampa.DRENAJE_DE_HABILIDAD){
                                System.out.println("Defensor, quiere activar la trampa Drenaje de Habilidad? \n1. Si \n2. No");
                                System.out.print("Opción: ");
                                activarTrampaInvocacion = scaner.nextByte();
                                scaner.nextLine();
                                System.out.println("");
                                if(activarTrampaInvocacion == 1){
                                    trampa.jugar(campo, this.getTurno(), (byte) i, (byte) 0, "", "", "", listaVacia);
                                }
                            }
                        }
                    }
                }

                //Battle Phase (Fase de Batalla)
                setFase("Battle Phase");
                boolean entroAFaseDeBatalla = false;
                byte opcionFaseDeBatalla = 0;
                if(turno != 0){
                    System.out.println("\n");
                    System.out.println("------Battle Phase------");
                    if(defensorTieneTrampaDeAtaque){
                        System.out.println("Defensor, tiene trampas que puede usar en la Fase de Batalla, quiere activar una trampa? \n1. Si \n2. No");
                        System.out.print("Opción: ");
                        activarTrampa = scaner.nextByte();
                        scaner.nextLine();
                        System.out.println("");
                    }
                    while(opcionFaseDeBatalla != 2){
                        System.out.println("");
                        System.out.println("Quiere entrar a fase de batalla?, escoja una opción: \n1. Sí. \n2. No.");
                        System.out.print("Opción: ");
                        opcionFaseDeBatalla = scaner.nextByte();
                        scaner.nextLine();
                        System.out.println("");
                        if(opcionFaseDeBatalla == 1){
                            entroAFaseDeBatalla = true;
                            for(int i=0; i<5; i++){
                                if(magiasYTrampasAtacantes[i] != null && magiasYTrampasAtacantes[i].isVisible() && magiasYTrampasAtacantes[i] instanceof Magia){
                                    Magia magia = (Magia) magiasYTrampasAtacantes[i];
                                    switch(magia.getTipoHabilidadEspecialMagia()){

                                        case MONSTRUO_RENACIDO:
                                            imprimirCementerios();
                                            System.out.print("Escriba el nombre exacto del monstruo que quiere devolver al campo: ");
                                            String nombreARevivir = scaner.nextLine();
                                            System.out.println("");
                                            magia.jugar(campo, turno, (byte) i, (byte) 0, nombreARevivir, "", "", listaVacia);
                                            break;

                                        case AGUJERO_NEGRO:
                                            magia.jugar(campo, turno, (byte) i, (byte) 0, "", "", "", listaVacia);
                                            break;

                                        case TIFON_DEL_ESPACIO_MISTICO:
                                            System.out.print("Escriba el nombre exacto de la carta mágica o trampa de su oponente que quiere destruir: ");
                                            String magicaOTrampaADestruir = scaner.nextLine();
                                            System.out.println("");
                                            magia.jugar(campo, turno, (byte) i, (byte) 0, magicaOTrampaADestruir, "", "", listaVacia);
                                            break;

                                        case ESPADAS_DE_LA_LUZ_REVELADORA:
                                            magia.jugar(campo, turno, (byte) i, (byte) 0, "", "", "", listaVacia);
                                            break;

                                        case OLLA_DE_LA_CODICIA:
                                            magia.jugar(campo, turno, (byte) i, (byte) 0, "", "", "", listaVacia);
                                            break;

                                        case ENTIERRO_INSENSATO:
                                            imprimirMazoAtacante();
                                            System.out.print("Escriba el nombre exacto de la carta de su mazo que quiere enviar al cementerio: ");
                                            String cartaAEnviarAlCementerio = scaner.nextLine();
                                            System.out.println("");
                                            magia.jugar(campo, turno, (byte) i, (byte) 0, cartaAEnviarAlCementerio, "", "", listaVacia);
                                            break;

                                        case RAIGEKI:
                                            magia.jugar(campo, turno, (byte) i, (byte) 0, "", "", "", listaVacia);
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
                                                magia.jugar(campo, turno, (byte) i, (byte) 0, monstruoADestruir, "", "", listaVacia);
                                            }
                                            else{
                                                System.out.println("No tiene al Mago Oscuro");
                                            }
                                            break;

                                        case UNIDAD:
                                            if(magia.getTurnosActiva() == (byte) 0){
                                                System.out.print("Escriba exactamente el nombre del monstruo cuya defensa quiere aumentar: ");
                                                String monstruoAFortalecer = scaner.nextLine();
                                                System.out.println("");
                                                magia.jugar(campo, turno, (byte) i, (byte) 0, monstruoAFortalecer, "", "", listaVacia);
                                            }
                                            else if(magia.getTurnosActiva() == (byte) 1){
                                                magia.jugar(campo, turno, (byte) i, (byte) 0, "", "", "", listaVacia);
                                            }
                                            break;

                                        case POLIMERIZACION:
                                            byte numeroDeMonstruosEnPosesion = 0, numeroDeMonstruosAFusionar = 0;
                                            List<Monstruo> arregloDeMonstruosParaFusion = new ArrayList<> ();
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
                                                System.out.print("Escriba exactamente el nombre del monstruo " + j + " a fusionar: ");
                                                String monstruoAFusionar = scaner.nextLine();
                                                System.out.println("");
                                                for(int k=atacante.getMano().size()-1; k>=0; k--){
                                                    if(k<5 && monstruosAtacantes[k] != null && monstruosAtacantes[k].getNombre().equals(monstruoAFusionar)){
                                                        arregloDeMonstruosParaFusion.add(monstruosAtacantes[k]);
                                                        monstruosAtacantes[k] = null;
                                                    }
                                                    else if(atacante.getMano().get(k) instanceof Monstruo && atacante.getMano().get(k).getNombre().equals(monstruoAFusionar)){
                                                        arregloDeMonstruosParaFusion.add((Monstruo) atacante.getMano().remove(k));
                                                    }
                                                }
                                            }
                                            magia.jugar(campo, turno, (byte) i, (byte) 0, "", "", "", arregloDeMonstruosParaFusion);
                                            break;

                                        case null, default:
                                            break;

                                    }
                                }
                            }
                            boolean monstruosDefensoresNoEstaVacio = false, monstruosAtacantesNoEstaVacio = false;
                            for(Monstruo m : monstruosAtacantes){
                                if(m != null){
                                    monstruosAtacantesNoEstaVacio = true;
                                    break;
                                }
                            }
                            for(Monstruo m : monstruosDefensores){
                                if(m != null){
                                    monstruosDefensoresNoEstaVacio = true;
                                    break;
                                }
                            }
                            if(monstruosAtacantesNoEstaVacio){
                                if(defensorTieneTrampaDeAtaque && activarTrampa == 1){
                                    for(int j=0; j<5; j++){
                                        if(magiasYTrampasDefensoras[j] instanceof Trampa){
                                            byte activarTrampaAtaque = 0;
                                            Trampa trampa = (Trampa) magiasYTrampasDefensoras[j];
                                            if(trampa.getTipoHabilidadEspecialTrampa() == TipoHabilidadEspecialTrampa.CILINDRO_MAGICO){
                                                System.out.println("Defensor, quiere activar la trampa Cilindro Mágico? \n1. Si \n2. No");
                                                System.out.print("Opción: ");
                                                activarTrampaAtaque = scaner.nextByte();
                                                scaner.nextLine();
                                                System.out.println("");
                                                if(activarTrampaAtaque == 1){
                                                    if(trampa.getTurnosActiva() < 1){
                                                        System.out.println("Escriba el nombre exacto del monstruo cuyo ataque quiere negar");
                                                        System.out.print("Nombre: ");
                                                        String monstruoANegar = scaner.nextLine();
                                                        System.out.println("");
                                                        trampa.jugar(campo, turno, (byte) j, (byte) 0, monstruoANegar, "", "", listaVacia);
                                                    }
                                                    else if(trampa.getTurnosActiva() > 0){
                                                        trampa.jugar(campo, turno, (byte) j, (byte) 0, "", "", "", listaVacia);
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                                for(int i=0; i<5; i++){
                                    if(monstruosAtacantes[i] != null){
                                        byte monstruoAAtacar = (byte) i;
                                        if(monstruosDefensoresNoEstaVacio){
                                            System.out.print("Escoja el número de que carta atacará su monstruo " + i + " o digite 6 para no atacar: ");
                                            byte indiceDelEnemigo = scaner.nextByte();
                                            scaner.nextLine();
                                            System.out.println("");
                                            byte activarTrampaAtaque = 0;
                                            if(indiceDelEnemigo != 6  && monstruosAtacantes[monstruoAAtacar].isEnPosicionAtaque()){
                                                monstruosAtacantes[monstruoAAtacar].jugar(campo, turno, monstruoAAtacar, indiceDelEnemigo, "", "", "", listaVacia);
                                                for(int j=0; j<5; j++){
                                                    if(magiasYTrampasDefensoras[j] instanceof Trampa){
                                                        activarTrampaAtaque = 0;
                                                        Trampa trampa = (Trampa) magiasYTrampasDefensoras[j];
                                                        if(trampa.getTipoHabilidadEspecialTrampa() == TipoHabilidadEspecialTrampa.ARMADURA_DE_SAKURETSU){
                                                            System.out.println("Defensor, quiere activar la trampa Armadura De Sakuretsu? \n1. Si \n2. No");
                                                            System.out.print("Opción: ");
                                                            activarTrampaAtaque = scaner.nextByte();
                                                            scaner.nextLine();
                                                            System.out.println("");
                                                            if(activarTrampaAtaque == 1){
                                                                trampa.jugar(campo, turno, (byte) j, (byte) monstruoAAtacar, "", "", "", listaVacia);
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                        else if(monstruosAtacantes[monstruoAAtacar].isPuedeAtacar() && !monstruosDefensoresNoEstaVacio){
                                            monstruosAtacantes[monstruoAAtacar].jugar(campo, turno, (byte) monstruoAAtacar, (byte) 0, "", "", "", listaVacia);
                                            for(int j=0; j<5; j++){
                                                if(magiasYTrampasDefensoras[j] instanceof Trampa){
                                                    byte activarTrampaAtaque = 0;
                                                    Trampa trampa = (Trampa) magiasYTrampasDefensoras[j];
                                                    if(trampa.getTipoHabilidadEspecialTrampa() == TipoHabilidadEspecialTrampa.ARMADURA_DE_SAKURETSU){
                                                        System.out.println("Defensor, quiere activar la trampa Armadura De Sakuretsu? \n1. Si \n2. No");
                                                        System.out.print("Opción: ");
                                                        activarTrampaAtaque = scaner.nextByte();
                                                        scaner.nextLine();
                                                        System.out.println("");
                                                        if(activarTrampaAtaque == 1){
                                                            trampa.jugar(campo, turno, (byte) j, (byte) monstruoAAtacar, "", "", "", listaVacia);
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                        else{
                                            System.out.println("La carta no puede atacar porque está en posición de defensa");
                                        }
                                        if(defensorTieneTrampaDeAtaque && activarTrampa == 1){
                                            for(int j=0; j<5; j++){
                                                if(magiasYTrampasDefensoras[j] instanceof Trampa){
                                                    byte activarTrampaAtaque = 0;
                                                    Trampa trampa = (Trampa) magiasYTrampasDefensoras[j];
                                                    if(trampa.getTipoHabilidadEspecialTrampa() == TipoHabilidadEspecialTrampa.FUERZA_DE_ESPEJO){
                                                        System.out.println("Defensor, quiere activar la trampa Fuerza de Espejo? \n1. Si \n2. No");
                                                        System.out.print("Opción: ");
                                                        activarTrampaAtaque = scaner.nextByte();
                                                        scaner.nextLine();
                                                        System.out.println("");
                                                        if(activarTrampaAtaque == 1){
                                                            trampa.jugar(campo, turno, (byte) j, (byte) 0, "", "", "", listaVacia);
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        this.imprimirCampo();
                    }
                    if(atacante.getLP() == 0 || atacante.getLP() < 0){
                        hayUnGanador = true;
                    }
                    else if(defensor.getLP() == 0 || defensor.getLP() < 0){
                        hayUnGanador = true;
                    }
                    if(hayUnGanador){
                        break;
                    }
                }

                //Main Phase 2 (Fase Principal 2)
                setFase("Main Phase 2");
                System.out.println("\n");
                System.out.println("------Main Phase 2------");
                System.out.println("");
                if(entroAFaseDeBatalla){
                    do{
                        activarTrampa = 0;
                        if(defensorTieneTrampaDeInvocacion){
                            System.out.println("Defensor, tiene trampas que puede usar en la Main Phase 2, quiere activar una trampa? \n1. Si \n2. No");
                            System.out.print("Opción: ");
                            activarTrampa = scaner.nextByte();
                            scaner.nextLine();
                            System.out.println("");
                        }
                        opcionMainPhase1 = 0;
                        opcionCartaAColocar = 0;
                        indiceCartaAColocar = 0;
                        posicionCartaAColocar = 0;
                        do{
                            do{
                                System.out.println("Escoge una opción: \n1. Colocar una carta. \n2. Cambiar una carta de posición. \n3. Saltar fase.");
                                System.out.print("Opción: ");
                                opcionMainPhase1 = scaner.nextByte();
                                scaner.nextLine();
                                System.out.println("");
                            } while(opcionMainPhase1 != 1 && opcionMainPhase1 != 2 && opcionMainPhase1 != 3);
                            if(opcionMainPhase1 == 1){
                                do{
                                    System.out.println("Qué tipo de carta quiere colocar: \n1. Monstruo. \n2. Mágica. \n3. Trampa");
                                    System.out.print("Opción: ");
                                    opcionCartaAColocar = scaner.nextByte();
                                    scaner.nextLine();
                                    System.out.println("");
                                } while(opcionCartaAColocar != 1 && opcionCartaAColocar != 2 && opcionCartaAColocar != 3);
                                do{
                                    System.out.println("Escriba el número que ocupa en su mano la carta que quiere colocar: ");
                                    System.out.print("Opción: ");
                                    indiceCartaAColocar = scaner.nextByte();
                                    scaner.nextLine();
                                    System.out.println("");
                                } while(indiceCartaAColocar >= atacante.getMano().size() || indiceCartaAColocar < 0);
                                if(opcionCartaAColocar == 1 && atacante.getMano().get(indiceCartaAColocar) instanceof Monstruo){
                                    boolean posibleColocar = false;
                                    Monstruo m = (Monstruo) atacante.getMano().get(indiceCartaAColocar);
                                    if(m.getNivel() > 4){
                                        byte monstruoASacrificar = 0;
                                        do{
                                            do{
                                                System.out.println("Debe sacrificar un monstruo en campo para invocar a este monstruo.");
                                                System.out.println("Ingrese el indice del monstruo en campo a sacrificar o escriba 6 para cancelar.");
                                                System.out.print("Opción: ");
                                                monstruoASacrificar = scaner.nextByte();
                                                scaner.nextLine();
                                                System.out.println("");
                                            } while(monstruoASacrificar<0 || monstruoASacrificar >6);
                                            if(monstruoASacrificar<6 && monstruosAtacantes[monstruoASacrificar] != null){
                                                cementerioAtacante.add(monstruosAtacantes[monstruoASacrificar]);
                                                monstruosAtacantes[monstruoASacrificar] = null;
                                                posibleColocar = true;
                                                break;
                                            }
                                        } while(monstruoASacrificar != 6 );
                                    }
                                    else{
                                        posibleColocar = true;;
                                    }
                                    if(posibleColocar){
                                        do{
                                            System.out.println("Escoja la posición en la que quiere colocar al monstruo: \n1. Ataque. \n2. Defensa.");
                                            System.out.print("Opción: ");
                                            posicionCartaAColocar = scaner.nextByte();
                                            scaner.nextLine();
                                            System.out.println("");
                                        } while(posicionCartaAColocar != 1 && posicionCartaAColocar != 2);
                                        for(int i=0; i<5; i++){
                                            if(monstruosAtacantes[i] == null && atacante.getMano().get(indiceCartaAColocar) instanceof Monstruo){
                                                monstruosAtacantes[i] = (Monstruo) atacante.getMano().get(indiceCartaAColocar);
                                                atacante.getMano().remove(indiceCartaAColocar);
                                                if(posicionCartaAColocar == 1){
                                                    monstruosAtacantes[i].setEnPosicionAtaque(true);
                                                    break;
                                                }
                                                else if(posicionCartaAColocar == 2){
                                                    monstruosAtacantes[i].setEnPosicionAtaque(false);
                                                    break;
                                                }
                                            }
                                        }
                                        if(defensorTieneTrampaDeInvocacion && activarTrampa == 1){
                                            for(int i=0; i<5; i++){
                                                if(magiasYTrampasDefensoras[i] instanceof Trampa){
                                                    byte activarTrampaInvocacion = 0;
                                                    Trampa trampa = (Trampa) magiasYTrampasDefensoras[i];
                                                    if(!trampa.isActivada() && trampa.getTipoHabilidadEspecialTrampa() == TipoHabilidadEspecialTrampa.TRIBUTO_TORRENCIAL){
                                                        System.out.println("Defensor, quiere activar la trampa Tributo Torrencial? \n1. Si \n2. No");
                                                        System.out.print("Opción: ");
                                                        activarTrampaInvocacion = scaner.nextByte();
                                                        scaner.nextLine();
                                                        System.out.println("");
                                                        if(activarTrampaInvocacion == 1){
                                                            trampa.jugar(campo, this.getTurno(), (byte) i, (byte) 0, "", "", "", listaVacia);
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                                else if(opcionCartaAColocar == 2){
                                    do{
                                        System.out.println("Escoja que quiere hacer con la carta mágica: \n1. Colocar. \n2. Usar.");
                                        System.out.print("Opción: ");
                                        posicionCartaAColocar = scaner.nextByte();
                                        scaner.nextLine();
                                        System.out.println("");
                                    } while(posicionCartaAColocar != 1 && posicionCartaAColocar != 2);
                                    for(int i=0; i<5; i++){
                                        if(magiasYTrampasAtacantes[i] == null && atacante.getMano().get(indiceCartaAColocar) instanceof Magia){
                                            magiasYTrampasAtacantes[i] = atacante.getMano().get(indiceCartaAColocar);
                                            atacante.getMano().remove(indiceCartaAColocar);
                                            if(posicionCartaAColocar == 1){
                                                magiasYTrampasAtacantes[i].setVisible(false);
                                                break;
                                            }
                                            else if(posicionCartaAColocar == 2){
                                                magiasYTrampasAtacantes[i].setVisible(true);
                                                break;
                                            }
                                        }
                                    }
                                }
                                else if(opcionCartaAColocar == 3){
                                    for(int i=0; i<5; i++){
                                        if(magiasYTrampasAtacantes[i] == null && atacante.getMano().get(indiceCartaAColocar) instanceof Trampa){
                                            magiasYTrampasAtacantes[i] = atacante.getMano().get(indiceCartaAColocar);
                                            atacante.getMano().remove(indiceCartaAColocar);
                                            magiasYTrampasAtacantes[i].setVisible(false);
                                            break;
                                        }
                                    }
                                }
                            }
                            else if(opcionMainPhase1 == 2){
                                System.out.println("Escoja que tipo de carta quiere cambiar de posición: \n1. Mágica. \n2. Monstruo. \n3. Trampa");
                                System.out.print("Opción: ");
                                opcionCartaAColocar = scaner.nextByte();
                                scaner.nextLine();
                                System.out.println("");
                                System.out.println("Escriba el número que ocupa la carta cuya posición quiere cambiar. ");
                                System.out.print("Opción: ");
                                indiceCartaAColocar = scaner.nextByte();
                                scaner.nextLine();
                                System.out.println("");
                                if(opcionCartaAColocar == 1 || opcionCartaAColocar == 3){
                                    if(magiasYTrampasAtacantes[indiceCartaAColocar] != null && !magiasYTrampasAtacantes[indiceCartaAColocar].isVisible()){
                                        magiasYTrampasAtacantes[indiceCartaAColocar].setVisible(true);
                                    }
                                }
                                else if(opcionCartaAColocar == 2){
                                    if(monstruosAtacantes[indiceCartaAColocar] != null && monstruosAtacantes[indiceCartaAColocar].isEnPosicionAtaque()){
                                        monstruosAtacantes[indiceCartaAColocar].setEnPosicionAtaque(false);
                                    }
                                    else if(monstruosAtacantes[indiceCartaAColocar] != null){
                                        monstruosAtacantes[indiceCartaAColocar].setEnPosicionAtaque(true);
                                    }
                                }
                            }
                            else if(opcionMainPhase1 == 3){
                                break;
                            }
                            this.imprimirMano();
                        } while(opcionMainPhase1 != 3);
                        this.imprimirCampo();
                        if(defensorTieneTrampaDeInvocacion){
                            for(int i=0; i<5; i++){
                                if(magiasYTrampasDefensoras[i] instanceof Trampa){
                                    Trampa trampa = (Trampa) magiasYTrampasDefensoras[i];
                                    if(trampa.isActivada()){
                                        trampa.jugar(campo, this.getTurno(), (byte) i, (byte) 0, "", "", "", listaVacia);
                                    }
                                }
                            }
                        }
                        if(defensorTieneTrampaDeInvocacion && activarTrampa == 1){
                            for(int i=0; i<5; i++){
                                if(magiasYTrampasDefensoras[i] instanceof Trampa){
                                    byte activarTrampaInvocacion = 0;
                                    Trampa trampa = (Trampa) magiasYTrampasDefensoras[i];
                                    if(!trampa.isActivada() && trampa.getTipoHabilidadEspecialTrampa() == TipoHabilidadEspecialTrampa.LLAMADA_DE_LOS_CONDENADOS){
                                        System.out.println("Defensor, quiere activar la trampa Llamada de los Condenados? \n1. Si \n2. No");
                                        System.out.print("Opción: ");
                                        activarTrampaInvocacion = scaner.nextByte();
                                        scaner.nextLine();
                                        System.out.println("");
                                        if(activarTrampaInvocacion == 1){
                                            imprimirCementerios();
                                            System.out.println("Ingrese el nombre del monstruo en su cementerio que quiere devolver al campo");
                                            System.out.print("Nombre: ");
                                            String monstruoADevolver = scaner.nextLine();
                                            System.out.println("");
                                            trampa.jugar(campo, this.getTurno(), (byte) i, (byte) 0, monstruoADevolver, "", "", listaVacia);
                                        }
                                    }
                                    else if(!trampa.isActivada() && trampa.getTipoHabilidadEspecialTrampa() == TipoHabilidadEspecialTrampa.SOMBREROS_MAGICOS){
                                        System.out.println("Defensor, quiere activar la trampa Sombreros Mágicos? \n1. Si \n2. No");
                                        System.out.print("Opción: ");
                                        activarTrampaInvocacion = scaner.nextByte();
                                        scaner.nextLine();
                                        System.out.println("");
                                        if(activarTrampaInvocacion == 1){
                                            imprimirMazoDefensor();
                                            System.out.println("Ingrese el nombre del monstruo en campo que quiere ocultar");
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
                                            trampa.jugar(campo, this.getTurno(), (byte) i, (byte) 0, nombreMonstruoAOcultar, nombreMagica1AOcultar, nombreMagica2AOcultar, listaVacia);
                                        }
                                    }
                                    else if(!trampa.isActivada() && trampa.getTipoHabilidadEspecialTrampa() == TipoHabilidadEspecialTrampa.WABOKU){
                                        System.out.println("Defensor, quiere activar la trampa Waboku? \n1. Si \n2. No");
                                        System.out.print("Opción: ");
                                        activarTrampaInvocacion = scaner.nextByte();
                                        scaner.nextLine();
                                        System.out.println("");
                                        if(activarTrampaInvocacion == 1){
                                            trampa.jugar(campo, this.getTurno(), (byte) i, (byte) 0, "", "", "", listaVacia);
                                        }
                                    }
                                    else if(trampa.getTipoHabilidadEspecialTrampa() == TipoHabilidadEspecialTrampa.MURO_DE_ESPEJO){
                                        if(trampa.getTurnosActiva() > 0){
                                            byte opcionMuroDeEspejo = 0;
                                            System.out.println("Quiere mantener el efecto de Muro de Espejo durante un turno más a cambio de 2000 LP \n1. Si \2. No");
                                            System.out.print("Opción: ");
                                            opcionMuroDeEspejo = scaner.nextByte();
                                            scaner.nextLine();
                                            System.out.println("");
                                            trampa.jugar(campo, this.getTurno(), (byte) i, opcionMuroDeEspejo, "", "", "", listaVacia);
                                        }
                                        else if(!trampa.isActivada()){
                                            System.out.println("Defensor, quiere activar la trampa Muro de Espejo? \n1. Si \n2. No");
                                            System.out.print("Opción: ");
                                            activarTrampaInvocacion = scaner.nextByte();
                                            scaner.nextLine();
                                            System.out.println("");
                                            if(activarTrampaInvocacion == 1){
                                                trampa.jugar(campo, this.getTurno(), (byte) i, (byte) 0, "", "", "", listaVacia);
                                            }
                                        }
                                    }
                                    else if(trampa.getTipoHabilidadEspecialTrampa() == TipoHabilidadEspecialTrampa.REPRESION){
                                        if(!trampa.isActivada() && trampa.getTurnosActiva() < 1){
                                            System.out.println("Defensor, quiere activar la trampa Represión? \n1. Si \n2. No");
                                            System.out.print("Opción: ");
                                            activarTrampaInvocacion = scaner.nextByte();
                                            scaner.nextLine();
                                            System.out.println("");
                                            if(activarTrampaInvocacion == 1){
                                                System.out.println("Escriba el nombre del monstruo que robará por un turno");
                                                System.out.print("Nombre: ");
                                                String monstruoARobar = scaner.nextLine();
                                                trampa.setMonstruoARobarPorUnTurno(monstruoARobar);
                                                System.out.println("");
                                                trampa.jugar(campo, this.getTurno(), (byte) i, (byte) 0, monstruoARobar, "", "", listaVacia);
                                            }
                                        }
                                        else if(trampa.getTurnosActiva() > 0){
                                            trampa.jugar(campo, this.getTurno(), (byte) i, (byte) 0, "", "", "", listaVacia);
                                        }
                                    }
                                    else if(!trampa.isActivada() && trampa.getTipoHabilidadEspecialTrampa() == TipoHabilidadEspecialTrampa.DRENAJE_DE_HABILIDAD){
                                        System.out.println("Defensor, quiere activar la trampa Drenaje de Habilidad? \n1. Si \n2. No");
                                        System.out.print("Opción: ");
                                        activarTrampaInvocacion = scaner.nextByte();
                                        scaner.nextLine();
                                        System.out.println("");
                                        if(activarTrampaInvocacion == 1){
                                            trampa.jugar(campo, this.getTurno(), (byte) i, (byte) 0, "", "", "", listaVacia);
                                        }
                                    }
                                }
                            }
                        }
                    } while(opcionMainPhase1 != 3);
                }
                if(turno != 0){
                    this.imprimirCampo();
                }

                //End Phase (Fase Final)
                setFase("End Phase");
                System.out.println("\n");
                System.out.println("------End Phase------");
                System.out.println("");
                while(atacante.getMano().size()>6){
                    this.imprimirMano();
                    byte cartaAEliminar = 0;
                    do{
                        System.out.println("No puede terminar el turno con más de 6 cartas en la mano, actualmente tiene: " + atacante.getMano().size());
                        System.out.println("Ingrese el número que ocupa la carta que va a eliminar y enviar al cementerio en su mano: ");
                        System.out.print("Opción: ");
                        cartaAEliminar = scaner.nextByte();
                        scaner.nextLine();
                        System.out.println("");
                    } while(cartaAEliminar < 0 || cartaAEliminar >= atacante.getMano().size() );
                    cementerioAtacante.add(atacante.getMano().remove(cartaAEliminar));
                }
                for(int i=0; i<5; i++){
                    if(monstruosAtacantes[i] != null){
                        monstruosAtacantes[i].setYaCambioPosicionEnEsteTurno(false);
                        monstruosAtacantes[i].setPuedeAtacar(true);
                    }
                    if(monstruosDefensores[i] != null){
                        monstruosDefensores[i].setYaCambioPosicionEnEsteTurno(false);
                        monstruosDefensores[i].setPuedeAtacar(true);
                    }
                }
                turno++;
            }

            else{
                hayUnGanador = true;
            }
        }

        if(defensor.getLP() == 0 || defensor.getMazo().size() == 0){
            System.out.println("FELICIDADES, " + atacante.getNombre() + ", HAS GANADO!!!");
            scaner.close();
        }
        else if(atacante.getLP() == 0 || atacante.getMazo().size() == 0){
            System.out.println("FELICIDADES, " + defensor.getNombre() + ", HAS GANADO!!!");
            scaner.close();
        }
    }

}
