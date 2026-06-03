package modelo;

import java.io.IOException;
import java.nio.charset.*;
import java.nio.file.*;
import java.time.LocalDate;
import java.util.*;

public class RegistroResultados {
    private static final Path RUTA_RESULTADOS = Path.of("src/persistencia", "resultados.txt");

    public void guardarResultado(Jugador ganador, Jugador perdedor, int turnos) throws IOException {
        StringBuilder string = new StringBuilder();
        string.append("=== RESULTADO ===\n");
        string.append("GANADOR:").append(ganador.getNombre()).append("\n");
        string.append("PERDEDOR:").append(perdedor.getNombre()).append("\n");
        string.append("TURNOS:").append(turnos).append("\n");
        string.append("FECHA:").append(LocalDate.now()).append("\n");
        string.append("LP_GANADOR:").append(ganador.getPuntosVida()).append("\n");
        string.append("LP_PERDEDOR:").append(perdedor.getPuntosVida()).append("\n\n");
        Files.writeString(RUTA_RESULTADOS, string.toString(), StandardCharsets.UTF_8, StandardOpenOption.APPEND);
    }

    public String getResumen() {
        try {
            List<String> lineas = Files.readAllLines(RUTA_RESULTADOS, StandardCharsets.UTF_8);
            Map<String, Integer> victorias = new HashMap<>();
            byte total = 0, turnosMax = 0, turnosActuales = 0;
            String partidaMasLarga = "Sin datos", ultimoGanador = "Sin datos", ultimaFecha = "Sin datos", ganadorActual = "", perdedorActual = "", fechaActual = "";

            for (String linea : lineas) {
                if (linea.equals("=== RESULTADO ===")) {
                    if (!ganadorActual.isEmpty()) {
                        total++;
                        victorias.put(ganadorActual, victorias.getOrDefault(ganadorActual, 0) + 1);
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
                else if(linea.startsWith("GANADOR:")) {
                    ganadorActual = linea.substring("GANADOR:".length());
                }
                else if(linea.startsWith("PERDEDOR:")) {
                    perdedorActual = linea.substring("PERDEDOR:".length());
                }
                else if(linea.startsWith("TURNOS:")) {
                    turnosActuales = (byte) Integer.parseInt(linea.substring("TURNOS:".length()));
                }
                else if(linea.startsWith("FECHA:")) {
                    fechaActual = linea.substring("FECHA:".length());
                }
            }

            if (!ganadorActual.isEmpty()) {
                total++;
                victorias.put(ganadorActual, victorias.getOrDefault(ganadorActual, 0) + 1);
                if (turnosActuales > turnosMax) {
                    turnosMax = turnosActuales;
                    partidaMasLarga = ganadorActual + " vs " + perdedorActual + " (" + turnosActuales + " turnos)";
                }
                ultimoGanador = ganadorActual;
                ultimaFecha = fechaActual;
            }

            String jugadorMasVictorias = "Sin datos";
            int maxVictorias = 0;
            for (Map.Entry<String, Integer> entrada : victorias.entrySet()) {
                if (entrada.getValue() > maxVictorias) {
                    jugadorMasVictorias = entrada.getKey();
                    maxVictorias = entrada.getValue();
                }
            }

            StringBuilder resumen = new StringBuilder();
            resumen.append("=== ESTADISTICAS ===\n");
            resumen.append("Partidas registradas: ").append(total).append("\n");
            resumen.append("Jugador con mas victorias: ").append(jugadorMasVictorias);
            if (maxVictorias > 0) {
                resumen.append(" (").append(maxVictorias).append(")");
            }
            resumen.append("\n");
            resumen.append("Partida mas larga: ").append(partidaMasLarga).append("\n");
            resumen.append("Ultimo ganador: ").append(ultimoGanador).append("\n");
            resumen.append("Ultima fecha: ").append(ultimaFecha);
            return resumen.toString();
        }
        catch (IOException | NumberFormatException e){
            return "=== ESTADISTICAS ===\nNo se pudieron leer los resultados.";
        }
    }
}
