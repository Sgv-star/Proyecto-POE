import modelo.*;
import vista.*;
import controlador.*;

public class App {
    public static void main(String[] args) {
        DueloLogica modelo = new DueloLogica();
        NewJFrame vista = new NewJFrame();
        DueloControlador controlador = new DueloControlador(vista);
        vista.actualizarControlador(controlador);
        vista.setVisible(true);
        vista.setLocationRelativeTo(null);
    }
}