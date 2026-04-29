# Yu-Gi-Oh! — Simulador de Duelos

Simulación de duelos de Yu-Gi-Oh! con interfaz gráfica, desarrollada en Java 21 con Swing.

---

## Descripción

Dos jugadores se enfrentan en un duelo por turnos. Cada uno recibe 25 cartas aleatorias de un mazo compartido de 50 (30 monstruos, 10 mágicas y 10 trampas), empiezan con 5 cartas en mano y 8000 puntos de vida. Gana quien deje al oponente sin puntos de vida o sin cartas en el mazo.

---

## Cómo jugar

Al iniciar el juego se pide el nombre de los dos jugadores y se hace clic en **Iniciar Duelo**. A partir de ahí cada turno se divide en fases, y los botones disponibles en pantalla cambian automáticamente según la fase activa:

1. **Draw Phase** — se roba una carta del mazo automáticamente al inicio de cada turno
2. **Standby Phase** — se resuelven efectos de magias o trampas
3. **Main Phase 1** — se puede colocar un monstruo, una magia o una trampa al campo, o cambiar la posición de una carta ya colocada
4. **Battle Phase** — se puede atacar con los monstruos en campo. Si el oponente no tiene monstruos, el daño va directamente a sus puntos de vida
5. **Main Phase 2** — igual que la Main Phase 1, se pueden seguir jugando cartas después de la batalla
6. **End Phase** — si se tienen más de 6 cartas en mano se debe descartar hasta quedarse con 6; luego el turno pasa al oponente, igualmente se validan si se cumplen las condiciones de victoria

Toda entrada de datos (elegir cartas, escribir nombres de monstruos para efectos, confirmar acciones) se maneja mediante ventanas emergentes que aparecen según la acción que se realice. El campo de juego se actualiza en tiempo real.

---
