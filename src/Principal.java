import controlador.ControladorDuelo;
import javax.swing.SwingUtilities;
import modelo.Campo;
import vista.VistaDuelo;

public class Principal {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Campo modeloCampo = new Campo();

            VistaDuelo vistaGUI = new VistaDuelo();
            //VistaConsola vistaTerminal = new VistaConsola();

            ControladorDuelo controlador = new ControladorDuelo(modeloCampo, vistaGUI);

            vistaGUI.setVisible(true);
        });
    }
}
