import java.util.Scanner;

public class Trampa extends Carta implements Activable{

    private TipoHabilidadEspecialTrampa tipoHabilidadEspecial;

    public Trampa(String nombre, String cuadroDeTexto, TipoHabilidadEspecialTrampa tipoHabilidadEspecial) {
        super(nombre, cuadroDeTexto, false);
        this.tipoHabilidadEspecial = tipoHabilidadEspecial;
    }

     public TipoHabilidadEspecialTrampa getTipoHabilidadEspecial() {
        return tipoHabilidadEspecial;
    }

    @Override
    public void ActivarEfecto(Campo campo, byte turno, Scanner scaner) {
       System.out.println("");
    }

    @Override
    public boolean jugar(Campo campo, byte turno, Scanner scaner) {
        return true;
    }

    
    
}
