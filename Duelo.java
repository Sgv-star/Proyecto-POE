import java.util.*;

public class Duelo {
    
    Campo campo;
    byte turno;

    public Duelo() {

    }

    public Campo getCampo() {
        return campo;
    }
    public byte getTurno() {
        return turno;
    }

    protected void setCampo(Campo campo) {
        this.campo = campo;
    }
    protected void setTurno(byte turno) {
        this.turno = turno;
    }

    public void imprimirCampo(){
        System.out.println("_____________________________________________________________________________");
        System.out.println("---------------------------Estado Actual Del Campo---------------------------");
        System.out.println("");
        System.out.println("Jugador: " + campo.getJugador1().getNombre());
        for(int i=0; i<5; i++){
            if(campo.getMagicasEnCampoJugador1()[i] != null){
                Magia magica = (Magia) campo.getMagicasEnCampoJugador1()[i];
                if(magica.isVisible()){
                    System.out.println(i + ". " + magica.getNombre() + " | HAB: " + magica.getCuadroDeTexto() + " | Se está usando");
                }
                else{
                    System.out.println(i + ". " + magica.getNombre() + " | HAB: " + magica.getCuadroDeTexto() + " | Colocada (sin usar)");
                }
            }
            else{
                System.out.println("Espacio vacío");
            }
        }
        for(int i=0; i<5; i++){
            if(campo.getMonstruosEnCampoJugador1()[i] != null){
                Monstruo monstruo = (Monstruo) campo.getMonstruosEnCampoJugador1()[i];
                if(monstruo.isEnPosicionAtaque()){
                    System.out.println(i + ". " + monstruo.getNombre() + " | ATK: " + monstruo.getAtaque() + " | DEF: " + monstruo.getDefensa() + " | En posición de ataque");
                }
                else{
                    System.out.println(i + ". " + monstruo.getNombre() + " | ATK: " + monstruo.getAtaque() + " | DEF: " + monstruo.getDefensa() + " | En posición de defensa");
                }
            }
            else{
                System.out.println("Espacio vacío");
            }
        }
        System.out.println("");
        System.out.println("Jugador: " + campo.getJugador2().getNombre());
        for(int i=0; i<5; i++){
            if(campo.getMagicasEnCampoJugador2()[i] != null){
                Magia magica = (Magia) campo.getMagicasEnCampoJugador2()[i];
                if(magica.isVisible()){
                    System.out.println(i + ". " + magica.getNombre() + " | HAB: " + magica.getCuadroDeTexto() + " | Se está usando");
                }
                else{
                    System.out.println(i + ". " + magica.getNombre() + " | HAB: " + magica.getCuadroDeTexto() + " | Colocada (sin usar)");
                }
            }
            else{
                System.out.println("Espacio vacío");
            }
        }
        for(int i=0; i<5; i++){
            if(campo.getMonstruosEnCampoJugador2()[i] != null){
                Monstruo monstruo = (Monstruo) campo.getMonstruosEnCampoJugador2()[i];
                if(monstruo.isEnPosicionAtaque()){
                    System.out.println(i + ". " + monstruo.getNombre() + " | ATK: " + monstruo.getAtaque() + " | DEF: " + monstruo.getDefensa() + " | En posición de ataque");
                }
                else{
                    System.out.println(i + ". " + monstruo.getNombre() + " | ATK: " + monstruo.getAtaque() + " | DEF: " + monstruo.getDefensa() + " | En posición de defensa");
                }
            }
            else{
                System.out.println("Espacio vacío");
            }
        }
        System.out.println("");
        System.out.println("_____________________________________________________________________________");
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
                System.out.println(j + ". " + monstruo.getNombre() + " | ATK: " + monstruo.getAtaque() + " | DEF: " + monstruo.getDefensa());
            }
            else if(atacante.getMano().get(j) instanceof Magia){
                Magia magica = (Magia) atacante.getMano().get(j);
                System.out.println(j + ". " + magica.getNombre() + " | HAB: " + magica.getCuadroDeTexto());
            }
        }
        System.out.println("----------------Cementerio----------------");
        for(Carta carta : cementerioAtacante){
            if(carta instanceof Monstruo){
                Monstruo m = (Monstruo) carta;
                System.out.println(i + ". " + m.getNombre() + " | ATK: " + m.getAtaque() + " | DEF: " + m.getDefensa());
                i++;
            }
            else if(carta instanceof Magia){
                Magia mg = (Magia) carta;
                System.out.println(i + ". " + mg.getNombre() + " | HAB: " + mg.getCuadroDeTexto());
                i++;
            }
        }
    }

    public void juego(){
        Scanner scaner = new Scanner(System.in);
        boolean hayUnGanador = false;
        Mazo mazo = new Mazo();
        Jugador atacante = new Jugador(), defensor = new Jugador();
        Monstruo[] monstruosAtacantes, monstruosDefensores;
        Magia[] magiasAtacantes, magiasDefensoras;
        List<Carta> cementerioAtacante, cementerioDefensor;

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
            if(atacante.getLP() != 0 || defensor.getLP() != 0){
                System.out.println("\n");
                System.out.println("Turno: " + turno);

                //Draw Phase (Fase de Robo)
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
                System.out.println("\n");
                System.out.println("------Standby Phase------");

                //Main Phase 1 (Fase Principal 1)
                System.out.println("\n");
                System.out.println("------Main Phase 1------");
                System.out.println("");
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
                            System.out.println("Qué tipo de carta quiere colocar: \n1. Monstruo. \n2. Mágica.");
                            System.out.print("Opción: ");
                            opcionCartaAColocar = scaner.nextByte();
                            scaner.nextLine();
                            System.out.println("");
                        } while(opcionCartaAColocar != 1 && opcionCartaAColocar != 2);
                        do{
                            System.out.println("Escriba el número que ocupa en su mano la carta que quiere colocar: ");
                            System.out.print("Opción: ");
                            indiceCartaAColocar = scaner.nextByte();
                            scaner.nextLine();
                            System.out.println("");
                        } while(indiceCartaAColocar >= atacante.getMano().size() || indiceCartaAColocar < 0);
                        if(opcionCartaAColocar == 1){
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
                                if(magiasAtacantes[i] == null && atacante.getMano().get(indiceCartaAColocar) instanceof Magia){
                                    magiasAtacantes[i] = (Magia) atacante.getMano().get(indiceCartaAColocar);
                                    atacante.getMano().remove(indiceCartaAColocar);
                                    if(posicionCartaAColocar == 1){
                                        magiasAtacantes[i].setVisible(false);
                                        break;
                                    }
                                    else if(posicionCartaAColocar == 2){
                                        magiasAtacantes[i].setVisible(true);
                                        break;
                                    }
                                }
                            }
                        }
                    }
                    else if(opcionMainPhase1 == 2){
                        System.out.println("Escoja que tipo de carta quiere cambiar de posición: \n1. Mágica. \n2. Monstruo.");
                        System.out.print("Opción: ");
                        opcionCartaAColocar = scaner.nextByte();
                        scaner.nextLine();
                        System.out.println("");
                        System.out.println("Escriba el número que ocupa la carta cuya posición quiere cambiar. ");
                        System.out.print("Opción: ");
                        indiceCartaAColocar = scaner.nextByte();
                        scaner.nextLine();
                        System.out.println("");
                        if(opcionCartaAColocar == 1){
                            if(magiasAtacantes[indiceCartaAColocar] != null && !magiasAtacantes[indiceCartaAColocar].isVisible()){
                                magiasAtacantes[indiceCartaAColocar].setVisible(true);
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

                //Battle Phase (Fase de Batalla)
                boolean entroAFaseDeBatalla = false;
                byte opcionFaseDeBatalla = 0;
                if(turno != 0){
                    System.out.println("\n");
                    System.out.println("------Battle Phase------");
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
                                if(magiasAtacantes[i] != null){
                                    magiasAtacantes[i].jugar(campo, turno, scaner);
                                    break;
                                }
                            }
                            for(int i=0; i<5; i++){
                                if(monstruosAtacantes[i] != null && monstruosAtacantes[i].isPuedeAtacar()){
                                    monstruosAtacantes[i].jugar(campo, turno, scaner);
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
                System.out.println("\n");
                System.out.println("------Main Phase 2------");
                System.out.println("");
                if(entroAFaseDeBatalla){
                    do{
                        do{
                            this.imprimirMano();
                            System.out.println("Escoge una opción: \n1. Colocar una carta. \n2. Cambiar una carta de posición. \n3. Saltar fase.");
                            System.out.print("Opción: ");
                            opcionMainPhase1 = scaner.nextByte();
                            scaner.nextLine();
                            System.out.println("");
                        } while(opcionMainPhase1 != 1 && opcionMainPhase1 != 2 && opcionMainPhase1 != 3);
                        if(opcionMainPhase1 == 1){
                            do{
                                System.out.println("Qué tipo de carta quiere colocar: \n1. Monstruo. \n2. Mágica.");
                                System.out.print("Opción: ");
                                opcionCartaAColocar = scaner.nextByte();
                                scaner.nextLine();
                                System.out.println("");
                            } while(opcionCartaAColocar != 1 && opcionCartaAColocar != 2);
                            do{
                                System.out.println("Escriba el número que ocupa en su mano la carta que quiere colocar: ");
                                System.out.print("Opción: ");
                                indiceCartaAColocar = scaner.nextByte();
                                scaner.nextLine();
                                System.out.println("");
                            } while(indiceCartaAColocar >= atacante.getMano().size() || indiceCartaAColocar < 0);
                            if(opcionCartaAColocar == 1){
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
                                    if(magiasAtacantes[i] == null && atacante.getMano().get(indiceCartaAColocar) instanceof Magia){
                                        magiasAtacantes[i] = (Magia) atacante.getMano().get(indiceCartaAColocar);
                                        atacante.getMano().remove(indiceCartaAColocar);
                                        if(posicionCartaAColocar == 1){
                                            magiasAtacantes[i].setVisible(false);
                                            break;
                                        }
                                        else if(posicionCartaAColocar == 2){
                                            magiasAtacantes[i].setVisible(true);
                                            break;
                                        }
                                    }
                                }
                            }
                        }
                        else if(opcionMainPhase1 == 2){
                            System.out.println("Escoja que tipo de carta quiere cambiar de posición: \n1. Mágica. \n2. Monstruo.");
                            System.out.print("Opción: ");
                            opcionCartaAColocar = scaner.nextByte();
                            scaner.nextLine();
                            System.out.println("");
                            System.out.println("Escriba el número que ocupa la carta cuya posición quiere cambiar. ");
                            System.out.print("Opción: ");
                            indiceCartaAColocar = scaner.nextByte();
                            scaner.nextLine();
                            System.out.println("");
                            if(opcionCartaAColocar == 1){
                                if(magiasAtacantes[indiceCartaAColocar] != null && !magiasAtacantes[indiceCartaAColocar].isVisible()){
                                    magiasAtacantes[indiceCartaAColocar].setVisible(true);
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
                    } while(opcionMainPhase1 != 3);
                }
                if(turno != 0){
                    this.imprimirCampo();
                }

                //End Phase (Fase Final)
                System.out.println("\n");
                System.out.println("------End Phase------");
                System.out.println("");
                while(atacante.getMano().size()>6){
                    this.imprimirMano();
                    System.out.println("No puede terminar el turno con más de 6 cartas en la mano, actualmente tiene: " + atacante.getMano().size());
                    System.out.println("Ingrese el número que ocupa la carta que va a eliminar y enviar al cementerio en su mano: ");
                    System.out.print("Opción: ");
                    byte cartaAEliminar = scaner.nextByte();
                    scaner.nextLine();
                    System.out.println("");
                    cementerioAtacante.add(atacante.getMano().remove(cartaAEliminar));
                }
                for(int i=0; i<5; i++){
                    if(monstruosAtacantes[i] != null){
                        monstruosAtacantes[i].setYaCambioPosicionEnEsteTurno(false);
                        monstruosAtacantes[i].setPuedeAtacar(true);
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
        }
        else if(atacante.getLP() == 0 || atacante.getMazo().size() == 0){

            System.out.println("FELICIDADES, " + defensor.getNombre() + ", HAS GANADO!!!");
        }
    }

}
