package modelo;

import java.util.*;

public abstract class Carta {

    private String nombre;
    private String cuadroDeTexto;
    private boolean visible;

    protected Carta(String nombre, String cuadroDeTexto, boolean visible){
        this.nombre = nombre;
        this.cuadroDeTexto = cuadroDeTexto;
        this.visible = visible;
    }

    public String getNombre() {
        return nombre;
    }
    public String getCuadroDeTexto() {
        return cuadroDeTexto;
    }
    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public abstract boolean jugar(Campo campo, byte turno, byte cartaAActivar, byte byteAux, String stringAux, String stringAux2, String stringAux3, List<Monstruo> lista);

}
