# Especificaciones del Lenguaje
por Javier Jimenez Arenas y Sara Vicente Arroyo.

Nuestro trabajo consiste en el desarrollo del lenguaje *SAJAX*, cuya extensión de archivo de código fuente será .sjx. 
Nuestro lenguaje se basará en la claridad, siendo similar a lenguajes como C++ o Java con ligeras diferencias que no alterarán la intuición propia del programador.
Debe ser un lenguaje sencillo de aprender pero no por ello carente de expresividad.

## Tipos
En primer lugar hablaremos de los tipos básicos de nuestro lenguaje. Constaremos de 4 tipos predefinidos: enteros, booleanos, caracteres y numeros en coma flotante.
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
