# SIGAC-UTA

Prototipo Java de gestión académica, atención de laboratorio, conectividad y recomendaciones. Se desarrolló a partir del examen práctico integrador de Estructura de Datos de la UTA. Funciona sin dependencias externas con **JDK 17 o superior**.

## Compilar y ejecutar

Desde la raíz del proyecto:

**PowerShell (Windows)**

```powershell
$archivos = rg --files src -g '*.java'
javac -encoding UTF-8 -d build $archivos
java -cp build app.Main
```

Si no tienes `rg`, reemplaza la primera línea por:

```powershell
$archivos = Get-ChildItem src -Recurse -Filter *.java | ForEach-Object FullName
```

**Bash (Linux/macOS)**

```bash
mkdir -p build
javac -encoding UTF-8 -d build $(find src -name '*.java')
java -cp build app.Main
```

En el menú, la opción **1** carga el caso integrador. La carga reemplaza los datos actuales. Las demás opciones permiten ejecutar CRUD, recorridos, cola, deshacer, red y recomendaciones.

Para ejecutar las pruebas y la demostración:

```powershell
java -cp build pruebas.Pruebas
java -cp build pruebas.CasoIntegrador
```

## Estructura

- `src/modelo`: estudiantes y solicitudes.
- `src/estructuras`: vector dinámico, lista enlazada simple, pila, cola, árbol general, ABB y grafo de listas de adyacencia enlazadas.
- `src/servicios`: reglas de negocio y caso integrador.
- `src/app`: menú de consola.
- `src/pruebas`: pruebas automatizadas y demostración completa.
- `docs`: matriz de pruebas, salidas de ejecución y capturas.

## Decisiones reproducibles

- Las matrículas son enteros positivos y únicos. El vector y la lista conservan el orden de inserción; el ABB se ordena por matrícula. La búsqueda secuencial recorre el vector y la búsqueda del ABB compara claves.
- El ABB elimina un nodo con dos hijos usando el **sucesor inorden**, mínimo del subárbol derecho. Los recorridos visitan izquierda antes que derecha.
- La jerarquía es `Universidad Técnica de Ambato -> FISEI -> Ingeniería en Software -> estudiantes`.
- La red de equipos es **dirigida**. `A -> B` permite llegar de A a B, no al revés. Las aristas tienen peso positivo y el orden de vecinos es el **orden de inserción**. BFS y DFS respetan ese orden. El peso se conserva, aunque los recorridos solicitados no calculan rutas mínimas.
- La red social es **no dirigida**. Una amistad agrega las dos adyacencias. Se excluye al usuario y a sus amigos directos. Se ordena por número descendente de amigos en común y, en caso de empate, por nombre ascendente.
- El menú valida la matrícula contra el índice académico y el equipo contra el grafo antes de encolar una solicitud. Registrar una persona en la red social también requiere una matrícula existente. El caso integrador precarga los nombres sociales dados por el enunciado.
- Las operaciones reversibles son crear, actualizar y eliminar estudiante. Deshacer revierte la última de ellas. Las solicitudes atendidas no se revierten. Al restaurar un estudiante eliminado, este reaparece al final del vector, lista y jerarquía; su posición en el ABB depende siempre de la matrícula.
- La cola de solicitudes y la cola interna de BFS son implementaciones propias. No se usan `TreeMap`, `TreeSet` ni bibliotecas de grafos.

## Complejidad temporal

| Operación | Tiempo | Motivo |
|---|---:|---|
| Buscar matrícula en vector | O(n) | Compara cada posición hasta hallar la clave. |
| Buscar matrícula en ABB | O(h), peor O(n) | Sigue una rama; `h` es la altura. No es balanceado. |
| Insertar en ABB | O(h), peor O(n) | Sigue una rama. |
| Eliminar en ABB | O(h), peor O(n) | Busca el nodo y, si procede, el sucesor. |
| Encolar / desencolar | O(1) | Mantiene referencias al inicio y fin. |
| Apilar / desapilar | O(1) | Opera sobre la cima. |
| BFS / DFS | O(V² + E) en esta implementación | Las listas de adyacencia recorren V+E, pero la comprobación de visitados es lineal en un vector. |
| Buscar en lista enlazada | O(n) | Avanza desde la cabeza. |
| Recomendar | O(S(F + R) + R²) | Recorre S adyacencias de segundo grado, compara con F amigos directos y hasta R candidatos; ordena R resultados por selección. |

## Caso obligatorio

Se cargan las matrículas `50, 30, 70, 20, 40, 60, 80` con nombres Ana, Beto, Carla, Diego, Elena, Eva y Felipe, respectivamente; equipos `PC1, SW1, SRV1, PC2, PC3, PC4`; y amistades indicadas en el examen. Las conexiones de laboratorio son `PC1 -> SW1 (1)`, `SW1 -> SRV1 (2)`, `SRV1 -> PC2 (3)`, `PC1 -> PC3 (4)`. `PC4` permanece aislado.

Resultados esperados: inorden `20,30,40,50,60,70,80`; tras eliminar 30, `20,40,50,60,70,80`; BFS de PC1 `[PC1, SW1, PC3, SRV1, PC2]`; no alcanzable `PC4`; recomendaciones de Ana: Diego (2), Elena (1). Al eliminar `SW1 -> SRV1`, no alcanzables: `[SRV1, PC2, PC4]`.

La [matriz de pruebas](docs/matriz-pruebas.md) detalla los casos normales, extremos y erróneos. Las capturas de ejecución están en `docs/capturas/`.
