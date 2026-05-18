package modelo;

import java.util.*;

public class DueloLogica {
    
    Campo campo;
    byte turno;
    String fase;

    public DueloLogica() {
        this.campo = new Campo();
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
    public Jugador getAtacante(){
        if(turno%2==0){
            return campo.getJugador1();
        }
        else{
            return campo.getJugador2();
        }
    }
    public Jugador getDefensor(){
        if(turno%2 != 0){
            return campo.getJugador1();
        }
        else{
            return campo.getJugador2();
        }
    }
    public Monstruo[] getMonstruosEnCampoAtacante(){
        if(turno%2 == 0){
            return campo.getMonstruosEnCampoJugador1();
        }
        else{
            return campo.getMonstruosEnCampoJugador2();
        }
    }
    public Monstruo[] getMonstruosEnCampoDefensor(){
        if(turno%2 != 0){
            return campo.getMonstruosEnCampoJugador1();
        }
        else{
            return campo.getMonstruosEnCampoJugador2();
        }
    }
    public Carta[] getMagicasYTrampasEnCampoAtacante(){
        if(turno%2 == 0){
            return campo.getMagicasYTrampasEnCampoJugador1();
        }
        else{
            return campo.getMagicasYTrampasEnCampoJugador2();
        }
    }
    public Carta[] getMagicasYTrampasEnCampoDefensor(){
        if(turno%2 != 0){
            return campo.getMagicasYTrampasEnCampoJugador1();
        }
        else{
            return campo.getMagicasYTrampasEnCampoJugador2();
        }
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

    //Seteo inicial de objetos
    protected void setearElementosInicialesDePartida(String nombreJugador1, String nombreJugador2){
        Mazo mazo = new Mazo();
        setTurno((byte) 0);
        setFase("Draw Phase");
        Jugador jugador1 = new Jugador(nombreJugador1, mazo), jugador2 = new Jugador(nombreJugador2, mazo);
        Campo campo = new Campo(jugador1, jugador2);
        this.setCampo(campo);
    }
    protected boolean isMonstruosAtacantesVacio(){
        if(turno % 2 == 0){
            for(Monstruo m : campo.getMonstruosEnCampoJugador1()){
                if(m != null){
                    return false;
                }
            }
            return true;
        }
        else{
            for(Monstruo m : campo.getMonstruosEnCampoJugador2()){
                if(m != null){
                    return false;
                }
            }
            return true;
        }
    }
    protected boolean isMonstruosDefensoresVacio(){
        if(turno % 2 != 0){
            for(Monstruo m : campo.getMonstruosEnCampoJugador1()){
                if(m != null){
                    return false;
                }
            }
            return true;
        }
        else{
            for(Monstruo m : campo.getMonstruosEnCampoJugador2()){
                if(m != null){
                    return false;
                }
            }
            return true;
        }
    }
    protected boolean isDefensorTieneTrampaDeInvocacion(){
        if(turno % 2 != 0){
            for(Carta c : campo.getMagicasYTrampasEnCampoJugador1()){
                if(c instanceof Trampa){
                    Trampa trampa = (Trampa) c;
                    if(trampa.getMomentoDeActivacion() == TipoHabilidadEspecialTrampa.INVOCACION && !trampa.isVisible()){
                        return true;
                    }
                }
            }
            return false;
        }
        else{
            for(Carta c : campo.getMagicasYTrampasEnCampoJugador2()){
                if(c instanceof Trampa){
                    Trampa trampa = (Trampa) c;
                    if(trampa.getMomentoDeActivacion() == TipoHabilidadEspecialTrampa.INVOCACION && !trampa.isVisible()){
                        return true;
                    }
                }
            }
            return false;
        }
    }
    protected boolean isDefensorTieneTrampaDeAtaque(){
        if(turno % 2 != 0){
            for(Carta c : campo.getMagicasYTrampasEnCampoJugador1()){
                if(c instanceof Trampa){
                    Trampa trampa = (Trampa) c;
                    if(trampa.getMomentoDeActivacion() == TipoHabilidadEspecialTrampa.BATALLA && !trampa.isVisible()){
                        return true;
                    }
                }
            }
            return false;
        }
        else{
            for(Carta c : campo.getMagicasYTrampasEnCampoJugador2()){
                if(c instanceof Trampa){
                    Trampa trampa = (Trampa) c;
                    if(trampa.getMomentoDeActivacion() == TipoHabilidadEspecialTrampa.BATALLA && !trampa.isVisible()){
                        return true;
                    }
                }
            }
            return false;
        }
    }
    protected boolean isAtacanteTIeneMagoOscuro(){
        if(turno % 2 == 0){
            for(Monstruo m : campo.getMonstruosEnCampoJugador1()){
                if(m != null && m.getNombre().equals("Mago Oscuro")){
                    return true;
                }
            }
            return false;
        }
        else{
            for(Monstruo m : campo.getMonstruosEnCampoJugador2()){
                if(m != null && m.getNombre().equals("Mago Oscuro")){
                    return true;
                }
            }
            return false;
        }
    }
    protected byte getCuantosMonstruosTieneAtacante(){
        byte numeroDeMonstruosEnPosesion = 0;
        for(Monstruo m : getMonstruosEnCampoAtacante()){
            if(m != null){
                numeroDeMonstruosEnPosesion++;
            }
        }
        for(Carta carta : getAtacante().getMano()){
            if(carta instanceof Monstruo){
                numeroDeMonstruosEnPosesion++;
            }
        }
        return numeroDeMonstruosEnPosesion;
    }

    //Fases y turnos
    protected boolean robarCarta(){
        if(turno != 0){
            if(getAtacante().getMazo().size() > 0){
                getAtacante().getMano().add(getAtacante().getMazo().remove(0));
                return true;
            }
            else{
                return false;
            }
        }
        return true;
    }
    protected void resolverEfectosContinuos(){
        List<Monstruo> listaVacia = new ArrayList<> ();
        for(int i=0; i<5; i++){
            Carta c = getMagicasYTrampasEnCampoAtacante()[i];
            if(c instanceof Trampa && ((Trampa)c).isActivada()){
                c.jugar(campo, getTurno(), (byte) i, (byte) 0, "", "", "", listaVacia);
            }
            else if(c instanceof Magia && ((Magia) c).isVisible()){
                Magia mg = (Magia) getMagicasYTrampasEnCampoAtacante()[i];
                if(mg.getTurnosActiva() > 0){
                    mg.jugar(campo, getTurno(), (byte) i, (byte) 0, "", "", "", listaVacia);
                }
            }
        }
    }
    protected void siguienteFase(){
        if(getFase().equals("Draw Phase")){
            setFase("Main Phase 1");
        }
        else if(getFase().equals("Main Phase 1")){
            setFase("Battle Phase");
        }
        else if(getFase().equals("Battle Phase")){
            setFase("Main Phase 2");
        }
        else if(getFase().equals("Main Phase 2")){
            setFase("End Phase");
        }
        else if(getFase().equals("End Phase")){
            setFase("Draw Phase");
            terminarTurno();
        }
    }
    protected void saltarFase(){
        if(getFase().equals("Main Phase 1")){
            setFase("Battle Phase");
        }
        else if(getFase().equals("Battle Phase")){
            setFase("End Phase");
        }
        else if(getFase().equals("Main Phase 2")){
            setFase("End Phase");
        }
    }
    protected void terminarTurno(){
        for(Monstruo m : campo.getMonstruosEnCampoJugador1()){
            if(m != null){
                m.setPuedeAtacar(true);
                m.setYaCambioPosicionEnEsteTurno(false);
            }
        }
        for(Monstruo m : campo.getMonstruosEnCampoJugador2()){
            if(m != null){
                m.setPuedeAtacar(true);
                m.setYaCambioPosicionEnEsteTurno(false);
            }
        }
        setTurno((byte) (getTurno() + 1));
    }
    //Colocar cartas
    protected boolean sacrificarMonstruo(byte indiceEnCampo){
        if(turno % 2 == 0){
            if(indiceEnCampo >= 0 && indiceEnCampo <= 4){
                if(campo.getMonstruosEnCampoJugador1()[indiceEnCampo] != null){
                    campo.getCementerioJugador1().add(campo.getMonstruosEnCampoJugador1()[indiceEnCampo]);
                    campo.getMonstruosEnCampoJugador1()[indiceEnCampo] = null;
                    return true;
                }
            }
        }
        else{
            if(indiceEnCampo >= 0 && indiceEnCampo <= 4){
                if(campo.getMonstruosEnCampoJugador2()[indiceEnCampo] != null){
                    campo.getCementerioJugador2().add(campo.getMonstruosEnCampoJugador2()[indiceEnCampo]);
                    campo.getMonstruosEnCampoJugador2()[indiceEnCampo] = null;
                    return true;
                }
            }
        }
        return false;
    }
    protected boolean colocarMonstruo(byte indiceMano, byte indiceSacrificio, boolean enPosicionAtaque){
        if(turno % 2 == 0){
            if(campo.getJugador1().getMano().get(indiceMano) instanceof Monstruo){
                Monstruo monstruo = (Monstruo) campo.getJugador1().getMano().get(indiceMano);
                for(int i=0; i<5; i++){
                    if(campo.getMonstruosEnCampoJugador1()[i] == null){
                        if(monstruo.getNivel() <= 4 || (monstruo.getNivel() > 4 && sacrificarMonstruo(indiceSacrificio))){
                            campo.getMonstruosEnCampoJugador1()[i] = monstruo;
                            campo.getJugador1().getMano().remove(indiceMano);
                            if(enPosicionAtaque){
                                campo.getMonstruosEnCampoJugador1()[i].setEnPosicionAtaque(true);
                                return true;
                            }
                            else{
                                campo.getMonstruosEnCampoJugador1()[i].setEnPosicionAtaque(false);
                                return true;
                            }
                        }
                    }
                }
            }
            return false;
        }
        else{
            if(campo.getJugador2().getMano().get(indiceMano) instanceof Monstruo){
                Monstruo monstruo = (Monstruo) campo.getJugador2().getMano().get(indiceMano);
                for(int i=0; i<5; i++){
                    if(campo.getMonstruosEnCampoJugador2()[i] == null){
                        if(monstruo.getNivel() <= 4 || (monstruo.getNivel() > 4 && sacrificarMonstruo(indiceSacrificio))){
                            campo.getMonstruosEnCampoJugador2()[i] = monstruo;
                            campo.getJugador2().getMano().remove(indiceMano);
                            if(enPosicionAtaque){
                                campo.getMonstruosEnCampoJugador2()[i].setEnPosicionAtaque(true);
                                return true;
                            }
                            else{
                                campo.getMonstruosEnCampoJugador2()[i].setEnPosicionAtaque(false);
                                return true;
                            }
                        }
                    }
                }
            }
            return false;
        }
    }
    protected boolean colocarMagia(byte indiceMano, boolean usar){
        if(turno % 2 == 0){
            if(campo.getJugador1().getMano().get(indiceMano) instanceof Magia){
                for(int i=0; i<5; i++){
                    if(campo.getMagicasYTrampasEnCampoJugador1()[i] == null){
                        campo.getMagicasYTrampasEnCampoJugador1()[i] = campo.getJugador1().getMano().remove(indiceMano);
                        if(usar){
                            campo.getMagicasYTrampasEnCampoJugador1()[i].setVisible(true);
                            return true;
                        }
                        else{
                            campo.getMagicasYTrampasEnCampoJugador1()[i].setVisible(false);
                            return true;
                        }
                    }
                }
            }
            return false;
        }
        else{
            if(campo.getJugador2().getMano().get(indiceMano) instanceof Magia){
                for(int i=0; i<5; i++){
                    if(campo.getMagicasYTrampasEnCampoJugador2()[i] == null){
                        campo.getMagicasYTrampasEnCampoJugador2()[i] = campo.getJugador2().getMano().remove(indiceMano);
                        if(usar){
                            campo.getMagicasYTrampasEnCampoJugador2()[i].setVisible(true);
                            return true;
                        }
                        else{
                            campo.getMagicasYTrampasEnCampoJugador2()[i].setVisible(false);
                            return true;
                        }
                    }
                }
            }
            return false;
        }
    }
    protected boolean colocarTrampa(byte indiceMano){
        if(turno % 2 == 0){
            if(campo.getJugador1().getMano().get(indiceMano) instanceof Trampa){
                for(int i=0; i<5; i++){
                    if(campo.getMagicasYTrampasEnCampoJugador1()[i] == null){
                        campo.getMagicasYTrampasEnCampoJugador1()[i] = campo.getJugador1().getMano().remove(indiceMano);
                        campo.getMagicasYTrampasEnCampoJugador1()[i].setVisible(false);
                        return true;
                    }
                }
            }
            return false;
        }
        else{
            if(campo.getJugador2().getMano().get(indiceMano) instanceof Trampa){
                for(int i=0; i<5; i++){
                    if(campo.getMagicasYTrampasEnCampoJugador2()[i] == null){
                        campo.getMagicasYTrampasEnCampoJugador2()[i] = campo.getJugador2().getMano().remove(indiceMano);
                        campo.getMagicasYTrampasEnCampoJugador2()[i].setVisible(false);
                        return true;
                    }
                }
            }
            return false;
        }
    }
    //Cambiar Posiciones
    protected boolean cambiarPosicionDeMonstruo(byte indiceCampo){
        if(turno % 2 == 0){
            if(!campo.getMonstruosEnCampoJugador1()[indiceCampo].isYaCambioPosicionEnEsteTurno()){
                campo.getMonstruosEnCampoJugador1()[indiceCampo].setEnPosicionAtaque(!campo.getMonstruosEnCampoJugador1()[indiceCampo].isEnPosicionAtaque());
                return true;
            }
            else{
                return false;
            }
        }
        else{
            if(!campo.getMonstruosEnCampoJugador2()[indiceCampo].isYaCambioPosicionEnEsteTurno()){
                campo.getMonstruosEnCampoJugador2()[indiceCampo].setEnPosicionAtaque(!campo.getMonstruosEnCampoJugador2()[indiceCampo].isEnPosicionAtaque());
                return true;
            }
            else{
                return false;
            }
        }
    }
    protected boolean cambiarPosicionDeMagia(byte indiceCampo){
        if(turno % 2 == 0){
            if(campo.getMagicasYTrampasEnCampoJugador1()[indiceCampo] instanceof Magia && !campo.getMagicasYTrampasEnCampoJugador1()[indiceCampo].isVisible()){
                campo.getMagicasYTrampasEnCampoJugador1()[indiceCampo].setVisible(true);
                return true;
            }
            else{
                return false;
            }
        }
        else{
            if(campo.getMagicasYTrampasEnCampoJugador2()[indiceCampo] instanceof Magia && !campo.getMagicasYTrampasEnCampoJugador2()[indiceCampo].isVisible()){
                campo.getMagicasYTrampasEnCampoJugador2()[indiceCampo].setVisible(true);
                return true;
            }
            else{
                return false;
            }
        }
    }
    //Fase de batalla
    protected boolean atacar(byte indiceAtacante, byte indiceDefensor){
        List<Monstruo> listaVacia = new ArrayList<> ();
        if(turno % 2 == 0){
            if(campo.getMonstruosEnCampoJugador1()[indiceAtacante] != null && campo.getMonstruosEnCampoJugador1()[indiceAtacante].isEnPosicionAtaque() && campo.getMonstruosEnCampoJugador1()[indiceAtacante].isPuedeAtacar()){
                campo.getMonstruosEnCampoJugador1()[indiceAtacante].jugar(campo, turno, indiceAtacante, indiceDefensor, "", "", "", listaVacia);
                return true;
            }
            else{
                return false;
            }
        }
        else{
            if(campo.getMonstruosEnCampoJugador2()[indiceAtacante] != null && campo.getMonstruosEnCampoJugador2()[indiceAtacante].isEnPosicionAtaque() && campo.getMonstruosEnCampoJugador2()[indiceAtacante].isPuedeAtacar()){
                campo.getMonstruosEnCampoJugador2()[indiceAtacante].jugar(campo, turno, indiceAtacante, indiceDefensor, "", "", "", listaVacia);
                return true;
            }
            else{
                return false;
            }
        }
    }
    //Activar efectos
    protected void activarMagia(byte cartaAActivar, byte byteAux, String stringAux, String stringAux2, String stringAux3, List<Monstruo> lista){
        if(turno % 2 == 0){
            if(campo.getMagicasYTrampasEnCampoJugador1()[cartaAActivar] != null && campo.getMagicasYTrampasEnCampoJugador1()[cartaAActivar] instanceof Magia && campo.getMagicasYTrampasEnCampoJugador1()[cartaAActivar].isVisible()){
                campo.getMagicasYTrampasEnCampoJugador1()[cartaAActivar].jugar(campo, turno, cartaAActivar, byteAux, stringAux, stringAux2, stringAux3, lista);
            }
        }
        else{
            if(campo.getMagicasYTrampasEnCampoJugador2()[cartaAActivar] != null && campo.getMagicasYTrampasEnCampoJugador2()[cartaAActivar] instanceof Magia && campo.getMagicasYTrampasEnCampoJugador2()[cartaAActivar].isVisible()){
                campo.getMagicasYTrampasEnCampoJugador2()[cartaAActivar].jugar(campo, turno, cartaAActivar, byteAux, stringAux, stringAux2, stringAux3, lista);
            }
        }
    }
    protected void activarTrampa(byte cartaAActivar, byte byteAux, String stringAux, String stringAux2, String stringAux3){
        List<Monstruo> listaVacia = new ArrayList<> ();
        if(turno % 2 == 0){
            if(campo.getMagicasYTrampasEnCampoJugador1()[cartaAActivar] != null && campo.getMagicasYTrampasEnCampoJugador1()[cartaAActivar] instanceof Trampa && campo.getMagicasYTrampasEnCampoJugador1()[cartaAActivar].isVisible()){
                campo.getMagicasYTrampasEnCampoJugador1()[cartaAActivar].jugar(campo, turno, cartaAActivar, byteAux, stringAux, stringAux2, stringAux3, listaVacia);
            }
        }
        else{
            if(campo.getMagicasYTrampasEnCampoJugador2()[cartaAActivar] != null && campo.getMagicasYTrampasEnCampoJugador2()[cartaAActivar] instanceof Trampa && campo.getMagicasYTrampasEnCampoJugador2()[cartaAActivar].isVisible()){
                campo.getMagicasYTrampasEnCampoJugador2()[cartaAActivar].jugar(campo, turno, cartaAActivar, byteAux, stringAux, stringAux2, stringAux3, listaVacia);
            }
        }
    }
    //Descartar cartas al final del turno
    protected void descartarCarta(byte indiceMano){
        if(turno % 2 == 0){
            campo.getCementerioJugador1().add(campo.getJugador1().getMano().remove(indiceMano));
        }
        else{
            campo.getCementerioJugador2().add(campo.getJugador2().getMano().remove(indiceMano));
        }
    }
    protected boolean muchasCartasEnMano(){
        if(turno % 2 == 0){
            if(campo.getJugador1().getMano().size() > 6){
                return true;
            }
            else{
                return false;
            }
        }
        else{
            if(campo.getJugador2().getMano().size() > 6){
                return true;
            }
            else{
                return false;
            }
        }
    }
    //Condiciones para terminar partida
    protected boolean hayUnGanador(){
        if(campo.getJugador1().getLP() == 0 || campo.getJugador1().getMazo().size() == 0){
            return true;
        }
        else if(campo.getJugador2().getLP() == 0 || campo.getJugador2().getMazo().size() == 0){
            return true;
        }
        else{
            return false;
        }
    }
    protected Jugador getGanador(){
        if(campo.getJugador1().getLP() == 0 || campo.getJugador1().getMazo().size() == 0){
            return campo.getJugador2();
        }
        else if(campo.getJugador2().getLP() == 0 || campo.getJugador2().getMazo().size() == 0){
            return campo.getJugador1();
        }
        else{
            return null;
        }
    }
    
}