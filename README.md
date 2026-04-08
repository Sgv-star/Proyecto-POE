# Yu-Gi-Oh! — Simulador de Duelos
 
Simulación de duelos de Yu-Gi-Oh! que corre por consola, desarrollada en Java 21.
 
---
 
## Descripción
 
Dos jugadores se enfrentan en un duelo por turnos. Cada uno recibe 20 cartas aleatorias de un mazo compartido de 40 (30 monstruos y 10 mágicas), empieza con 5 cartas en mano y 8000 puntos de vida. Gana quien deje al oponente sin puntos de vida o sin cartas en el mazo.
 
---
 
## Cómo jugar
 
Al iniciar el juego se pide el nombre de los dos jugadores. A partir de ahí cada turno se divide en fases:
 
1. **Draw Phase** — robas una carta de tu mazo automáticamente
2. **Main Phase 1** — puedes colocar una carta al campo o cambiar la posición de un monstruo entre ataque y defensa
3. **Battle Phase** — activas tus cartas mágicas y atacas con tus monstruos. Puedes atacar a los monstruos del oponente o directamente a sus puntos de vida si no tiene ninguno en campo
4. **Main Phase 2** — puedes seguir jugando cartas después de haber atacado
5. **End Phase** — si tienes más de 6 cartas en mano debes descartar hasta quedarte con 6
 
Todo se controla mediante menús numerados. Cuando el juego te pida elegir una carta, escribe el número que aparece al lado de ella en pantalla.
