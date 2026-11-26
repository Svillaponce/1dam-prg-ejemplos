# Renovando el DNI — versión básica

Diseño mediante métodos:

| Método | Responsabilidad | Parámetros | Retorno |
| --- | --- | --- | --- |
| main | Leer la entrada y coordinar el programa | Argumentos del programa | void |
| esValido | Comprobar el formato de la versión básica | DNI deteriorado (String) | boolean |
| localizarDanio | Localizar el primer carácter desconocido | DNI (String) | int, posición o -1 |
| calcularLetra | Obtener la letra mediante el resto de dividir entre 23 | Número de DNI (int) | char |
| generarCandidato | Sustituir los daños conservando los ceros iniciales | DNI, posición inicial, cantidad de daños, valor | String |
| esCompatible | Comparar la letra calculada con la visible | Candidato (String) | boolean |
| buscarSoluciones | Generar, comprobar y almacenar los candidatos válidos | DNI validado, array de 20 soluciones | int, cantidad almacenada |
| mostrarResultados | Mostrar ninguna, una o varias soluciones | Array de soluciones, cantidad | void |

Se admiten uno o dos dígitos desconocidos; si son dos, deben ser consecutivos.
La letra final debe ser conocida y puede introducirse en mayúscula o minúscula.
