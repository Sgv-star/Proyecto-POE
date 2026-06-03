package modelo;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class PersistenciaPartida {

    private static String RUTA_PARTIDA = "src/persistencia/partida_guardada.json";
    private Gson gson = new GsonBuilder().setPrettyPrinting().create();

    static class DatosArchivo {
        List<DatosGuardado> partidas = new ArrayList<>();
    }
    static class DatosGuardado {
        String nombreGuardado, fase;
        byte turno, jugadorActual;
        DatosJugador jugador1, jugador2;
    }
    static class DatosJugador {
        String nombre;
        short vida;
        List<String> mazo = new ArrayList<>(), mano = new ArrayList<>(), cementerio = new ArrayList<>();
        DatosCarta[] monstruos = new DatosCarta[5], magicasOTrampas = new DatosCarta[5];
    }
    static class DatosCarta {
        String nombre;
        String posicion; 
        boolean bocaArriba;
    }

    public void guardar(Campo campo, int turno, String fase, int jugadorActivo) throws IOException {
        DatosArchivo archivo = leerArchivo();
        DatosGuardado partida = construirPartida(campo, turno, fase, jugadorActivo);
        int posicion = buscarPartida(archivo.partidas, partida.nombreGuardado);
        if (posicion >= 0) {
            archivo.partidas.set(posicion, partida);
        } 
        else{
            archivo.partidas.add(partida);
        }
        try (FileWriter fw = new FileWriter(RUTA_PARTIDA)) {
            gson.toJson(archivo, fw);
        }
    }

    public EstadoPartida cargar(Campo campo, String nombreGuardado) throws IOException {
        DatosArchivo archivo = leerArchivo();
        DatosGuardado datos = null;
        for (DatosGuardado p : archivo.partidas) {
            if (p.nombreGuardado.equals(nombreGuardado)) {
                datos = p;
                break;
            }
        }
        if (datos == null) {
            throw new IOException("No se encontró la partida: " + nombreGuardado);
        }
        cargarJugador(campo, datos.jugador1, true);
        cargarJugador(campo, datos.jugador2, false);
        campo.reconstruirControlCampo();
        return new EstadoPartida(datos.turno, datos.fase, datos.jugadorActual);
    }

    public EstadoPartida cargar(Campo campo) throws IOException {
        List<String> nombres = getNombresPartidas();
        if (nombres.isEmpty()) {
            throw new IOException("No hay partidas guardadas.");
        }
        return cargar(campo, nombres.get(0));
    }

    public List<String> getNombresPartidas() throws IOException {
        List<String> nombres = new ArrayList<>();
        DatosArchivo archivo = leerArchivo();
        for (DatosGuardado partida : archivo.partidas) {
            nombres.add(partida.nombreGuardado);
        }
        return nombres;
    }

    private DatosArchivo leerArchivo() {
        File archivo = new File(RUTA_PARTIDA);
        if (!archivo.exists()) {
            return new DatosArchivo();
        }
        try {
            DatosArchivo datos;
            try (FileReader fr = new FileReader(archivo)) {
                datos = gson.fromJson(fr, DatosArchivo.class);
            }
            if (datos == null) {
                return new DatosArchivo();
            }
            return datos;
        } catch (IOException e) {
            System.out.println("Error al leer el archivo: " + e.getMessage());
            return new DatosArchivo();
        }
    }

    private DatosGuardado construirPartida(Campo campo, int turno, String fase, int jugadorActivo) {
        DatosGuardado partida = new DatosGuardado();
        partida.nombreGuardado = campo.getJugador1().getNombre() + " vs " + campo.getJugador2().getNombre();
        partida.turno = (byte) turno;
        partida.fase = fase;
        partida.jugadorActual = (byte) jugadorActivo;
        partida.jugador1 = construirJugador(campo.getJugador1(), campo.getMonstruosJugador1(), campo.getMagicasYTrampasJugador1(), campo.getCementerioJugador1());
        partida.jugador2 = construirJugador(campo.getJugador2(), campo.getMonstruosJugador2(), campo.getMagicasYTrampasJugador2(), campo.getCementerioJugador2());
        return partida;
    }

    private DatosJugador construirJugador(Jugador jugador, Monstruo[] monstruos, Carta[] magicasYTrampas, List<Carta> cementerio) {
        DatosJugador datos = new DatosJugador();
        datos.nombre = jugador.getNombre();
        datos.vida = jugador.getPuntosVida();
        for (Carta carta : jugador.getMazo()) {
            datos.mazo.add(carta.getNombre());
        }
        for (Carta carta : jugador.getMano()) {
            datos.mano.add(carta.getNombre());
        }
        for (Carta carta : cementerio) {
            datos.cementerio.add(carta.getNombre());
        }
        for (int i = 0; i < 5; i++) {
            if (monstruos[i] != null) {
                DatosCarta dc = new DatosCarta();
                dc.nombre = monstruos[i].getNombre();
                if (monstruos[i].estaEnPosicionAtaque()) {
                    dc.posicion = "ataque";
                } 
                else {
                    dc.posicion = "defensa";
                }
                dc.bocaArriba = monstruos[i].esVisible();
                datos.monstruos[i] = dc;
            }
        }
        for (int i = 0; i < 5; i++) {
            if (magicasYTrampas[i] != null) {
                DatosCarta dc = new DatosCarta();
                dc.nombre = magicasYTrampas[i].getNombre();
                dc.bocaArriba = magicasYTrampas[i].esVisible();
                datos.magicasOTrampas[i] = dc;
            }
        }
        return datos;
    }

    private void cargarJugador(Campo campo, DatosJugador datos, boolean esJugador1) throws IOException {
        Jugador jugador = new Jugador();
        jugador.setNombre(datos.nombre);
        jugador.setPuntosVida((short) datos.vida);
        for (String nombre : datos.mazo) {
            jugador.getMazo().add(crearCarta(nombre));
        }
        for (String nombre : datos.mano) {
            jugador.getMano().add(crearCarta(nombre));
        }
        List<Carta> cementerioDestino;
        if (esJugador1) {
            cementerioDestino = campo.getCementerioJugador1();
        } 
        else {
            cementerioDestino = campo.getCementerioJugador2();
        }
        cementerioDestino.clear();
        jugador.getMapaCementerio().clear();
        for (String nombre : datos.cementerio) {
            Carta carta = crearCarta(nombre);
            cementerioDestino.add(carta);
            jugador.getMapaCementerio().put(carta.getNombre(), carta);
        }
        if (esJugador1) {
            campo.setJugador1(jugador);
            cargarMonstruos(campo.getMonstruosJugador1(), datos.monstruos);
            cargarCampo(campo.getMagicasYTrampasJugador1(), datos.magicasOTrampas);
        } else {
            campo.setJugador2(jugador);
            cargarMonstruos(campo.getMonstruosJugador2(), datos.monstruos);
            cargarCampo(campo.getMagicasYTrampasJugador2(), datos.magicasOTrampas);
        }
    }

    private void cargarMonstruos(Monstruo[] destino, DatosCarta[] cartas) throws IOException {
        for (int i = 0; i < destino.length; i++) {
            if (cartas[i] == null) {
                destino[i] = null;
            } 
            else {
                Carta carta = crearCarta(cartas[i].nombre);
                if (!(carta instanceof Monstruo)) {
                    throw new IOException("La carta " + carta.getNombre() + " no es un monstruo.");
                }
                Monstruo monstruo = (Monstruo) carta;
                if (cartas[i].posicion.equals("defensa")) {
                    monstruo.setEnPosicionAtaque(false);
                } else {
                    monstruo.setEnPosicionAtaque(true);
                }
                monstruo.setVisible(cartas[i].bocaArriba);
                destino[i] = monstruo;
            }
        }
    }

    private void cargarCampo(Carta[] destino, DatosCarta[] cartas) throws IOException {
        for (int i = 0; i < destino.length; i++) {
            if (cartas[i] == null) {
                destino[i] = null;
            } else {
                Carta carta = crearCarta(cartas[i].nombre);
                carta.setVisible(cartas[i].bocaArriba);
                destino[i] = carta;
            }
        }
    }

    private Carta crearCarta(String nombre) throws IOException {
        Mazo mazo = new Mazo();
        for (Carta carta : mazo.getCartas()) {
            if (carta.getNombre().equals(nombre)) {
                return carta;
            }
        }
        throw new IOException("No se encontró la carta: " + nombre);
    }

    private int buscarPartida(List<DatosGuardado> partidas, String nombreGuardado) {
        for (int i = 0; i < partidas.size(); i++) {
            if (partidas.get(i).nombreGuardado.equals(nombreGuardado)) {
                return i;
            }
        }
        return -1;
    }

    public static class EstadoPartida {
        private byte turno, jugadorActivo;
        private String fase;
        public EstadoPartida(int turno, String fase, int jugadorActivo) {
            this.turno = (byte) turno;
            this.fase = fase;
            this.jugadorActivo = (byte) jugadorActivo;
        }
        public byte getTurno(){ 
            return turno; 
        }
        public String getFase(){ 
            return fase; 
        }
        public byte getJugadorActivo(){ 
            return jugadorActivo; 
        }
    }
}
