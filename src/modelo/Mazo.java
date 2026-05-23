package modelo;

import java.util.*;

public class Mazo {
    private List<Carta> cartas = new ArrayList<>();

    public Mazo() {
        // Añadimos suficientes cartas para que ambos jugadores tengan un mazo completo (25 c/u)
        for (int i = 0; i < 10; i++) {
            this.cartas.add(new Monstruo("Mago Oscuro", "El mago definitivo.", (byte) 7, (short) 2500, (short) 2100));
            this.cartas.add(new Monstruo("Dragón Blanco", "Poderosa máquina de destrucción.", (byte) 8, (short) 3000, (short) 2500));
            this.cartas.add(new Monstruo("Cráneo Convocado", "Demonio con poderes oscuros.", (byte) 6, (short) 2500, (short) 1200));
            this.cartas.add(new Monstruo("Bebé Dragón", "Dragón pequeño con potencial.", (byte) 3, (short) 1200, (short) 700));
            this.cartas.add(new Monstruo("Elfo Gemelo", "Hermanas elfas.", (byte) 4, (short) 1900, (short) 900));
            
            this.cartas.add(new Magia("Olla de la Codicia", "Roba 2 cartas.", TipoHabilidadMagia.OLLA_CODICIA));
            this.cartas.add(new Magia("Raigeki", "Destruye monstruos oponente.", TipoHabilidadMagia.RAIGEKI));
            
            this.cartas.add(new Trampa("Fuerza Espejo", "Destruye atacantes.", TipoHabilidadTrampa.FUERZA_ESPEJO, TipoHabilidadTrampa.BATALLA));
            this.cartas.add(new Trampa("Cilindro Mágico", "Refleja daño.", TipoHabilidadTrampa.CILINDRO_MAGICO, TipoHabilidadTrampa.BATALLA));
        }

        barajar();
    }

    public List<Carta> obtenerCartas() {
        return cartas;
    }
    
    public void barajar() {
        Collections.shuffle(cartas);
    }
}
