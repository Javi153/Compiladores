# Especificaciones del Lenguaje
por Javier Jimenez Arenas y Sara Vicente Arroyo.

Nuestro trabajo consiste en el desarrollo del lenguaje *SAJAX*, cuya extensión de archivo de código fuente será .sjx. 
Nuestro lenguaje se basará en la claridad, siendo similar a lenguajes como C++ o Java con ligeras diferencias que no alterarán la intuición propia del programador.
Debe ser un lenguaje sencillo de aprender pero no por ello carente de expresividad.

## Tipos
En primer lugar hablaremos de los tipos básicos de nuestro lenguaje. Constaremos de 4 tipos predefinidos: enteros, numeros en coma flotante, booleanos y caracteres.
El tipado será estático y no habrá casting automático entre tipos a la hora de hacer operaciones entre ellos o llamar a funciones.

El tipo entero se representará mediante la palabra reservada int. Los operadores aritméticos para enteros serán binarios infijos y se corresponden con la suma (+), la resta (-),
la multiplicación (\*), la división entera (/), el módulo de la división entera (%) y la potencia (^). Todos ellos asocian por la izquierda, salvo el operador de potencia
que asocia por la derecha.

También incluiremos operadores binarios infijos entre enteros para operaciones de comparación: == para la igualdad, /= para la desigualdad, <= para menor o igual, 
\>= para mayor o igual, < para menor estricto y > para mayor estricto. Todos ellos asociarán una vez más por la izquierda

Estos operadores tendrán distintos niveles de prioridad, siendo los del nivel 0 los de prioridad más alta y desde ahí hacia los de prioridad más baja. Para el caso de los operadores entre enteros tendremos los siguientes niveles:
* Nivel 0: ^
* Nivel 1: *, /, %
* Nivel 2: +, -
* Nivel 3: <=, >=, <, >
* Nivel 4: ==, /=

Este orden podrá ser modificado mediante el uso de paréntesis, que tienen prioridad sobre cualquier operador.

Con esto, la expresión 3 + 4 / 8 ^ 9 ^ 4 - 5 * 7 < 6 sería semánticamente equivalente a ((3 + (4 / ((8 ^ 9) ^ 4))) - (5 * 7)) < 6 

Por otro lado, el tipo de los números en coma flotante vendrá representado por la palabra reservada float. Sus operadores serán los mismos que los operadores enteros salvo por el módulo, que carece de sentido en números con decimales. Al escribir un número literalmente deberá añadirse .0 si es un entero para que sea reconocido en su lugar como tipo float. Esto se debe tener en cuenta para evitar error al comparar enteros y flotantes.

El tipo booleano tendrá como palabra reservada bool. Tendrá un operador unario not (!) prefijo y el resto serán operadores binarios infijos que asocian por la izquierda. Estos serán la conjunción (&&), la disyunción (||) y los operadores de comparación de igualdad (==) y desigualdad (/=).

Sus niveles de precedencia serán los siguientes:
* Nivel 0: !
* Nivel 1: &&
* Nivel 2: ||
* Nivel 3: ==, /=

De nuevo, podremos variar la precedencia con paréntesis.

La expresión a && b || ! c && d == v || ! d será equivalente a ((a && b) || ((! c) && d)) == (v || (! d)) 
