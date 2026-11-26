# Matriz de pruebas

Ejecutar `java -cp build pruebas.Pruebas` después de compilar. El programa termina con `OK: 43 verificaciones.` si todas pasan; cualquier fallo produce `AssertionError`. Ejecutar `java -cp build pruebas.CasoIntegrador` para ver la secuencia exigida por el examen.

| ID | Tipo | Acción / entrada | Resultado esperado | Verificación |
|---|---|---|---|---|
| E01 | Normal | Insertar 50,30,70,20,40,60,80 | Inorden 20,30,40,50,60,70,80 | Automática |
| E02 | Normal | Buscar 40 en vector y ABB | Misma instancia | Automática |
| E03 | Error | Insertar matrícula 50 repetida | Rechazo sin alterar índices | Automática |
| E04 | Extremo | Matrícula 0; nombre vacío | Rechazo | Automática |
| E05 | Normal | Eliminar 30, nodo con dos hijos | Inorden 20,40,50,60,70,80; vector y lista sincronizados | Automática |
| E06 | Normal | Deshacer eliminación, actualización y creación | Estado anterior restaurado | Automática |
| E07 | Error | Eliminar 999; deshacer sin historial | Excepción controlada | Automática |
| A01 | Normal | Encolar 3 solicitudes y atender 3 | Ana, Beto, Carla | Automática |
| A02 | Extremo | Atender cola vacía; desapilar pila vacía | Excepción controlada | Automática |
| A03 | Normal | Apilar 1 y 2; desapilar 2 veces | 2, 1 | Automática |
| A04 | Integración | Solicitar PC1 con matrícula 50 | Solicitud de Ana [50] | Automática |
| A05 | Error | Solicitar con matrícula o equipo ausente | Rechazo | Automática |
| G01 | Normal | BFS desde PC1 | PC1, SW1, PC3, SRV1, PC2 | Automática |
| G02 | Normal | DFS desde PC1 | PC1, SW1, SRV1, PC2, PC3 | Automática |
| G03 | Normal | Detectar no alcanzables | PC4 | Automática |
| G04 | Normal | Eliminar SW1 -> SRV1 | SRV1, PC2, PC4 no alcanzables | Automática |
| G05 | Error | BFS origen ausente; conexión a destino ausente; peso 0 | Rechazo | Automática |
| G06 | Extremo | Eliminar vértice B con A -> B y C -> B | Ninguna arista entrante residual | Automática |
| S01 | Normal | Recomendar para Ana | Diego (2), Elena (1) | Automática |
| S02 | Error | Recomendar usuario ausente | Rechazo | Automática |
| M01 | Integración | Ejecutar `pruebas.CasoIntegrador` | Inorden, FIFO, LIFO, BFS/DFS, recomendaciones y conectividad visibles | Captura 03 |
| M02 | Error | Cargar caso en el menú e intentar crear otra vez la matrícula 50 | Rechazo de duplicado | Captura 01 |

## Evidencia

Las salidas reales de ejecución están en `docs/salida-caso.txt` y `docs/salida-pruebas.txt`. Las capturas de la validación, las 43 verificaciones y el caso integrador están en `docs/capturas/`.
