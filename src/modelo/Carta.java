package modelo;

import java.util.*;

public abstract class Carta {

    private String nombre;
    private String descripcion;
    private boolean esVisible;

    protected Carta(String nombre, String descripcion, boolean esVisible){
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.esVisible = esVisible;
    }

    public String getNombre() {
        return nombre;
    }
    public String getDescripcion() {
        return descripcion;
    }
    public boolean esVisible() {
        return esVisible;
    }

    public void setVisible(boolean visible) {
        this.esVisible = visible;
    }

    public abstract boolean jugar(Campo campo, byte turno, byte cartaAActivar, byte byteAux, String stringAux, String stringAux2, String stringAux3, List<Monstruo> lista);

}
