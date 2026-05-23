package modelo;

import java.util.*;

public class Trampa extends Carta {
    private TipoHabilidadTrampa tipoHabilidad;
    private byte turnosActiva;
    private String monstruoRobado;
    private TipoHabilidadTrampa momentoActivacion;
    private boolean estaActivada;

    public Trampa(String nombre, String descripcion, TipoHabilidadTrampa tipoHabilidad, TipoHabilidadTrampa momentoActivacion) {
        super(nombre, descripcion, false);
        this.tipoHabilidad = tipoHabilidad;
        this.turnosActiva = 0;
        this.momentoActivacion = momentoActivacion;
        this.estaActivada = false;
    }

    public TipoHabilidadTrampa obtenerTipoHabilidad() { return tipoHabilidad; }
    public byte obtenerTurnosActiva() { return turnosActiva; }
    public String obtenerMonstruoRobado() { return monstruoRobado; }
    public TipoHabilidadTrampa obtenerMomentoActivacion() { return momentoActivacion; }
    public boolean estaActivada() { return estaActivada; }

    public void establecerTurnosActiva(byte turnos) { this.turnosActiva = turnos; }
    public void establecerMonstruoRobado(String nombre) { this.monstruoRobado = nombre; }
    public void establecerEstaActivada(boolean activada) { this.estaActivada = activada; }

    @Override
    public boolean jugar(Campo campo, byte turno, byte cartaAActivar, byte byteAux, String stringAux, String stringAux2, String stringAux3, List<Monstruo> lista) {
        return true;
    }
}
