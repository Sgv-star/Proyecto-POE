package modelo;

import java.util.*;

public class Campo {

    private Monstruo[] monstruosJugador1 = new Monstruo[5];
    private Monstruo[] monstruosJugador2 = new Monstruo[5];
    private Carta[] magicasYTrampasJugador1 = new Carta[5];
    private Carta[] magicasYTrampasJugador2 = new Carta[5];
    private HashMap<String, Carta> cementerioJugador1 = new LinkedHashMap<>();
    private HashMap<String, Carta> cementerioJugador2 = new LinkedHashMap<>();
    private Set<String> cartasEnCampoJ1 = new HashSet<>();
    private Set<String> cartasEnCampoJ2 = new HashSet<>();
    private Jugador jugador1;
    private Jugador jugador2;

    public Campo(Jugador jugador1, Jugador jugador2) {
        this.jugador1 = jugador1;
        this.jugador2 = jugador2;
    }

    public Campo() {}

    public Monstruo[] getMonstruosJugador1() {
        return monstruosJugador1;
    }
    public Monstruo[] getMonstruosJugador2() {
        return monstruosJugador2;
    }
    public Carta[] getMagicasYTrampasJugador1() {
        return magicasYTrampasJugador1;
    }
    public Carta[] getMagicasYTrampasJugador2() {
        return magicasYTrampasJugador2;
    }
    public List<Carta> getCementerioJugador1() {
        return new CementerioLista(cementerioJugador1);
    }
    public List<Carta> getCementerioJugador2() {
        return new CementerioLista(cementerioJugador2);
    }
    public HashMap<String, Carta> getMapaCementerioJugador1() {
        return cementerioJugador1;
    }
    public HashMap<String, Carta> getMapaCementerioJugador2() {
        return cementerioJugador2;
    }
    public Jugador getJugador1() {
        return jugador1;
    }
    public Jugador getJugador2() {
        return jugador2;
    }

    public void setJugador1(Jugador jugador) {
        this.jugador1 = jugador;
    }
    public void setJugador2(Jugador jugador) {
        this.jugador2 = jugador;
    }

    public boolean colocarCarta(Carta carta, byte turno, byte posicion) {
        boolean esTurnoJugador1 = (turno % 2 == 0);
        if (carta instanceof Monstruo) {
            Monstruo[] campo = esTurnoJugador1 ? monstruosJugador1 : monstruosJugador2;
            if (campo[posicion] == null) {
                campo[posicion] = (Monstruo) carta;
                agregarControlCampo(carta, esTurnoJugador1 ? 1 : 2);
                return true;
            }
        } else {
            Carta[] campo = esTurnoJugador1 ? magicasYTrampasJugador1 : magicasYTrampasJugador2;
            if (campo[posicion] == null) {
                campo[posicion] = carta;
                agregarControlCampo(carta, esTurnoJugador1 ? 1 : 2);
                return true;
            }
        }
        return false;
    }

    public boolean estaEnCampo(String nombre, int jugador) {
        return jugador == 1 ? cartasEnCampoJ1.contains(nombre) : cartasEnCampoJ2.contains(nombre);
    }

    public void removerDelCampo(String nombre, int jugador) {
        if (jugador == 1) {
            cartasEnCampoJ1.remove(nombre);
        } else {
            cartasEnCampoJ2.remove(nombre);
        }
    }

    public void agregarAlCementerio(Carta carta, int jugador) {
        if (carta == null) return;
        if (jugador == 1) {
            cementerioJugador1.put(carta.getNombre(), carta);
        } else {
            cementerioJugador2.put(carta.getNombre(), carta);
        }
    }

    public void limpiarControlCampo() {
        cartasEnCampoJ1.clear();
        cartasEnCampoJ2.clear();
    }

    public void reconstruirControlCampo() {
        limpiarControlCampo();
        for (Carta carta : monstruosJugador1) agregarControlCampo(carta, 1);
        for (Carta carta : magicasYTrampasJugador1) agregarControlCampo(carta, 1);
        for (Carta carta : monstruosJugador2) agregarControlCampo(carta, 2);
        for (Carta carta : magicasYTrampasJugador2) agregarControlCampo(carta, 2);
    }

    private void agregarControlCampo(Carta carta, int jugador) {
        if (carta == null) return;
        if (jugador == 1) {
            cartasEnCampoJ1.add(carta.getNombre());
        } else {
            cartasEnCampoJ2.add(carta.getNombre());
        }
    }

    private static class CementerioLista extends AbstractList<Carta> {
        private HashMap<String, Carta> mapa;

        public CementerioLista(HashMap<String, Carta> mapa) {
            this.mapa = mapa;
        }

        @Override
        public Carta get(int index) {
            return new ArrayList<>(mapa.values()).get(index);
        }

        @Override
        public int size() {
            return mapa.size();
        }

        @Override
        public boolean add(Carta carta) {
            if (carta == null) return false;
            mapa.put(carta.getNombre(), carta);
            return true;
        }

        @Override
        public Carta remove(int index) {
            String clave = new ArrayList<>(mapa.keySet()).get(index);
            return mapa.remove(clave);
        }

        @Override
        public void clear() {
            mapa.clear();
        }
    }
}
