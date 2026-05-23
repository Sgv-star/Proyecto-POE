import modelo.Campo;
import vista.VistaDuelo;
import controlador.ControladorDuelo;

import javax.swing.SwingUtilities;

public class Principal {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Campo modeloCampo = new Campo();

            VistaDuelo vista = new VistaDuelo();

            ControladorDuelo controlador = new ControladorDuelo(modeloCampo, vista);

            vista.setVisible(true);
        });
    }
}
