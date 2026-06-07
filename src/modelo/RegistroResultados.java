package modelo;

import java.io.*;
import java.time.LocalDate;
import java.util.*;

public class RegistroResultados {

    private static final String RUTA_RESULTADOS = "src/persistencia/resultados.txt";

    public void guardarResultado(Jugador ganador, Jugador perdedor, int turnos) throws IOException {
        try (FileWriter fw = new FileWriter(RUTA_RESULTADOS, true)) {
            fw.write("=== RESULTADO ===\n");
            fw.write("GANADOR:" + ganador.getNombre() + "\n");
            fw.write("PERDEDOR:" + perdedor.getNombre() + "\n");
            fw.write("TURNOS:" + turnos + "\n");
            fw.write("FECHA:" + LocalDate.now() + "\n");
            fw.write("LP_GANADOR:" + ganador.getPuntosVida() + "\n");
            fw.write("LP_PERDEDOR:" + perdedor.getPuntosVida() + "\n\n");
        }
    }

    public String getResumen() {
        Map<String, Byte> victorias = new HashMap<>();
        byte total = 0, turnosActuales = 0, turnosMax = 0;
        String partidaMasLarga = "Sin datos", ultimoGanador = "Sin datos", ultimaFecha = "Sin datos", ganadorActual = "", perdedorActual = "", fechaActual = "";

        try {
            try (BufferedReader br = new BufferedReader(new FileReader(RUTA_RESULTADOS))) {
                String linea;
                
                while ((linea = br.readLine()) != null) {
                    if (linea.equals("=== RESULTADO ===")) {
                        if (!ganadorActual.isEmpty()) {
                            total++;
                            if (victorias.containsKey(ganadorActual)) {
                                victorias.put(ganadorActual, (byte) (victorias.get(ganadorActual) + 1));
                            } 
                            else {
                                victorias.put(ganadorActual, (byte) 1);
                            }
                            if (turnosActuales > turnosMax) {
                                turnosMax = turnosActuales;
                                partidaMasLarga = ganadorActual + " vs " + perdedorActual + " (" + turnosActuales + " turnos)";
                            }
                            ultimoGanador = ganadorActual;
                            ultimaFecha = fechaActual;
                        }
                        ganadorActual = "";
                        perdedorActual = "";
                        fechaActual = "";
                        turnosActuales = 0;
                    } 
                    else if (linea.startsWith("GANADOR:")) {
                        ganadorActual = linea.substring("GANADOR:".length());
                    } 
                    else if (linea.startsWith("PERDEDOR:")) {
                        perdedorActual = linea.substring("PERDEDOR:".length());
                    } 
                    else if (linea.startsWith("TURNOS:")) {
                        turnosActuales = Byte.parseByte(linea.substring("TURNOS:".length()));
                    } 
                    else if (linea.startsWith("FECHA:")) {
                        fechaActual = linea.substring("FECHA:".length());
                    }
                }
            }

            if (!ganadorActual.isEmpty()) {
                total++;
                if (victorias.containsKey(ganadorActual)) {
                    victorias.put(ganadorActual, (byte) (victorias.get(ganadorActual) + 1));
                } 
                else {
                    victorias.put(ganadorActual, (byte) 1);
                }
                if (turnosActuales > turnosMax) {
                    turnosMax = turnosActuales;
                    partidaMasLarga = ganadorActual + " vs " + perdedorActual + " (" + turnosActuales + " turnos)";
                }
                ultimoGanador = ganadorActual;
                ultimaFecha = fechaActual;
            }

        } catch (IOException | NumberFormatException e) {
            return "=== ESTADISTICAS ===\nNo se pudieron leer los resultados.";
        }

        String jugadorMasVictorias = "Sin datos";
        int maxVictorias = 0;
        for (String nombre : victorias.keySet()) {
            if (victorias.get(nombre) > maxVictorias) {
                jugadorMasVictorias = nombre;
                maxVictorias = victorias.get(nombre);
            }
        }

        String resumen = "";
        resumen += "=== ESTADISTICAS ===\n";
        resumen += "Partidas registradas: " + total + "\n";
        resumen += "Jugador con mas victorias: " + jugadorMasVictorias;
        if (maxVictorias > 0) {
            resumen += " (" + maxVictorias + ")";
        }
        resumen += "\n";
        resumen += "Partida mas larga: " + partidaMasLarga + "\n";
        resumen += "Ultimo ganador: " + ultimoGanador + "\n";
        resumen += "Ultima fecha: " + ultimaFecha;
        return resumen;
    }
}