import modelo.Campo;
import vista.VistaDuelo;
import controlador.ControladorDuelo;

import javax.swing.SwingUtilities;

public class Principal {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // 1. Crear el Modelo
            Campo modeloCampo = new Campo();

            // 2. Crear la Vista
            VistaDuelo vista = new VistaDuelo();

            // 3. Crear el Controlador principal (que orquestará a los demás)
            ControladorDuelo controlador = new ControladorDuelo(modeloCampo, vista);

            // 4. Mostrar la aplicación
            vista.setVisible(true);
        });
    }
}
