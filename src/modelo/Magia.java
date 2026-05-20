package modelo;

import java.util.*;

public class Magia extends Carta {
    private TipoHabilidadMagia tipoHabilidad;
    private byte turnosActiva;

    public Magia(String nombre, String descripcion, TipoHabilidadMagia tipoHabilidad) {
        super(nombre, descripcion, true);
        this.tipoHabilidad = tipoHabilidad;
        this.turnosActiva = 0;
    }

    public TipoHabilidadMagia obtenerTipoHabilidad() { return tipoHabilidad; }
    public byte obtenerTurnosActiva() { return turnosActiva; }
    public void establecerTurnosActiva(byte turnos) { this.turnosActiva = turnos; }

    @Override
    public boolean jugar(Campo campo, byte turno, byte cartaAActivar, byte byteAux, String stringAux, String stringAux2, String stringAux3, List<Monstruo> lista) {
        return true;
    }
}
