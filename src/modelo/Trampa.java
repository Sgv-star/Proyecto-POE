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

    public TipoHabilidadTrampa getTipoHabilidad() {
        return tipoHabilidad;
    }
    public byte getTurnosActiva() {
        return turnosActiva;
    }
    public String getMonstruoRobado() {
        return monstruoRobado;
    }
    public TipoHabilidadTrampa getMomentoActivacion() {
        return momentoActivacion;
    }
    public boolean estaActivada() {
        return estaActivada;
    }

    public void setTurnosActiva(byte turnos) {
        this.turnosActiva = turnos;
    }
    public void setMonstruoRobado(String nombre) {
        this.monstruoRobado = nombre;
    }
    public void setEstaActivada(boolean activada) {
        this.estaActivada = activada;
    }

    @Override
    public boolean jugar(Campo campo, byte turno, byte cartaAActivar, byte byteAux, String stringAux, String stringAux2, String stringAux3, List<Monstruo> lista) {
        return true;
    }
}
