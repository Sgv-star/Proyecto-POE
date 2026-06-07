# Yu-Gi-Oh! — Simulador de Duelos

Simulación de duelos de Yu-Gi-Oh! con interfaz gráfica, desarrollada en Java 21 con Swing y Maven.

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

## Tablero KanbanFlow
https://kanbanflow.com/board/WKV9vja

---

## Estrcuturas de datos usadas
**Pila (Stack)**: Para el mazo, debido a que conceptualmente el mazo es perfecto para ser tratado como una pila (LIFO), se ponen cartas cartas "arriba" y se sacan las de "abajo".\
**Lista enlazada (LinkedList)**: Para la mano del jugador, debido a que su funcionamiento es similar al de un array, y es más eficiente para insertar o eliminar su primera o última posición.\
**Tabla Hash (HashMap)**: Se usa para los variables de los cementerios de los jugadores, ya que de esta forma, cartas que requieran buscar una carta específica en algún cementerio, puede hacerlo de forma óptima simplemente con el nombre de la carta, ya que el nombre de la carta es la clave y la carta en sí, el valor.\
**Conjunto (set)**: Se añadió un set en el campo, de forma que al necesitar verificar si cierta carta existe en el campo, debido al efecto especial de una magia o trampa, con el set se puede lograr fácilmente al llevar un control de cartas en campo.\

---

## Persistencia
En ambas vistas al inicio de cada partida encontraremos un log con algunos datos de estadísticas históricas de partidas anteriores jugadas, en este mismo momento es posible seleccionar la opción de "cargar partida" que desplegará una serie de nombres (nombreJugador1 vs nombreJugador2), cada uno de estos representa una de las partidas guardadas, de forma que es posible seleccionar cualquiera de estos y así cargar cualquiera de las partidas en memoria (en el archivo .json encargado del almacenamiento de las partidas guardadas) con el mismo estado en que se guardó. En cualquier momento de la partida es posible guardar partida para cargarla posteriormente, almacenándose con el nombre de ambos duelistas con un " vs " en medio de ambos, como restricción, dos jugadores no pueden tener un partida guardada, comenzar una totalmente nueva y guardar esta última, pues la partida anteriormente guardada en memoria se sobreescribirá.\
El juego usa la dependencia Gson para gestionar el flujo de información desde y hacia el archivo de registro de partidas guardadas partida_guardada.json (mediante los métodos propios de la dependencia (.toJson y .fromJson), así como librerías propias de Java SE.
