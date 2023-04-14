# Especificaciones del Lenguaje
por Javier Jimenez Arenas y Sara Vicente Arroyo.

Nuestro trabajo consiste en el desarrollo del lenguaje *SAJAX*, cuya extensión de archivo de código fuente será .sjx. 
Nuestro lenguaje se basará en la claridad, siendo similar a lenguajes como C++ o Java con ligeras diferencias que no alterarán la intuición propia del programador.
Debe ser un lenguaje sencillo de aprender pero no por ello carente de expresividad.

## Tipos
En primer lugar hablaremos de los tipos básicos de nuestro lenguaje. Constaremos de 3 tipos predefinidos: enteros, numeros en coma flotante y booleanos.
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

Para realizar una asignación cualquiera de una variable de tipo t usaremos la sintaxis

t iden;

O inicializado con 

t iden = valor;

## Sintaxis y estructuras básica
Como dijimos el lenguaje tiene tipado estático por lo que para declarar cualquier función o variable será necesario escribir el tipo.
Para los tipos básicos ya hemos visto como.

Por otro lado, si queremos declarar una función debemos usar la sintaxis t fun(t1 par1, t2 par2) { return; }

Por ejemplo, si queremos hacer una función que sume dos números podríamos hacerlo de la siguiente manera:

int sum(int a, int b){
  int c = 0;
  c = a + b;
  return c;
}

Si queremos usar un procedimiento que no devuelva nada podemos usar el tipo especial void y no se exigirá el uso de un return.

Además, las funciones de forma predeterminada pasarán los parámetros por valor. Si quisiésemos pasarlos por parámetro deberemos usar el operador unario &.

int sum(int& a, int& b){
  int c = 0;
  c = a + b;
  return c;
}

También se podrán añadir comentarios por ejemplo para explicar el funcionamiento de las funciones. Para ello podemos escribir comentarios de una línea con el operador //. Todo lo que se escriba detrás de él no será tenido en cuenta por el compilador hasta el final de la línea.

También se podrán añadir comentarios de más de una línea usando /* para abrir el comentario y */ para cerrarlo.

Aparte de los tipos básicos, podremos también definir punteros usando el operador unario * mediante la sintaxis t* iden.

Esto debe aunarse en general con una operación de reserva de memoria, en nuestro caso esta operación será memspace t* iden = memspace(N);

Para liberar la memoria reservada se usará la función free de la siguiente forma: free(iden);

Otra estructura importante y necesaria serán los arrays, posiciones sucesivas de memoria de un mismo tipo. Para definirlos usaremos el operador [] para reservar tamaño a la vez que para acceder a una posición específica.

También se podrá inicializar el array usando llaves. Veámoslo con un ejemplo:

int num[6] = { 2, 6, 3, 2, 6, 3 };

Es importante tener en cuenta que las posiciones de memoria serán indexadas comenzando desde el 0.

Ahora hablaremos de los structs. Los structs contendrán una serie de atributos: variables, punteros o arrays que funcionan como sus propiedades. También dentro del bloque podrán definirse métodos asociados al struct. Para crear uno usaremos la sintaxis 

struct iden {
  atributos;
  metodos(){
  
  }
}

Para acceder a una parte del struct usaremos el operador ".". Por ejemplo:

struct Persona{
  int dni;
  char letra;
}

Persona p;
int carne = p.dni;

(METEMOS CLASES?????=?)

## Conjuntos de instrucciones
Comenzamos con la instrucción de asignación, que tendrá la siguiente estructura:
inden = exp;
donde iden es un identificador y exp es otro identificador o una expresión aritmética o booleana. Las siguientes tres instrucciones son ejemplos de asignaciones:

num = 2 + 9;
aux = num;
b = aux < 1;

A continuación, añadimos las instrucciones condicionales (IF-THEN e IF-THEN-ELSE), con la sintaxis habitual:

if (condición) {
  // cuerpo del "then"
}

if (condición) {
  // cuerpo del "then"
}
else {
  // cuerpo del "else"
}

También será posible añadir tantas cláusulas "elsif" como se desee (con o sin cláusula "else" al final):

if (condición 1) {
  // cuerpo del "then"
}
elsif (condición 2) {
  // cuerpo del "elsif"
}
...
elsif (condición n) {
  // cuerpo del "elsif"
}
else {
   // cuerpo del "else"
}

Además, tendremos una instrucción para la distinción de casos:

switch (iden) {
  case 0: // cuerpo del case 0;
  ...
  case n: // cuerpo del case n;
  default:
}

donde "iden" es el identificador de una variable de tipo entero. En cada caso se podrá incluir la instrucción "break;" de salida del bloque switch.

En cuanto a los bucles, contaremos con un "while" y un "for". Este último estará restringido a bucles con un número definido de iteraciones.
La sintaxis será la siguiente:

while (condición) {
  // cuerpo del while
}

for (int iden = inicio; fin; paso) {
  // cuerpo del for
}

La variable iden podrá utilizarse dentro del bucle "for". El bucle terminará cuando esta tome un valor igual o superior al entero "fin" en el caso "inicio" menor o igual que "fin", y cuando tome un valor igual o inferior que "fin" en el caso "inicio" mayor que "fin". El paso será opcional y por defecto valdrá 1, de forma que se admitirá también la siguiente sintaxis:

for (int iden = inicio; fin) {
  // cuerpo del for
}

Un ejemplo (suma de los números naturales de 0 a 9):

for (int i = 0; 10) {
    suma = suma + i;
}

Para terminar, incluimos las instrucciones de entrada-salida:

En primer lugar tenemos la instrucción print de salida estándar

print(exp);

donde exp es un identificador, una expresión aritmética o booleana o una cadena de caracteres constante entre comillas dobles.

Si quisiéramos incluir un salto de línea automático al final del texto usaríamos println.

Por otro lado tenemos la instrucción de entrada estándar por teclado.

input(iden);

donde iden es el identificador de una variable.

## Ejemplos
Por último daremos algunos ejemplos de programas que pueden desarrollarse en este lenguaje con el objetivo de que el usuario se familiarice con el entorno.

Un programa estilo hola mundo tendría la siguiente forma:

int main(){
  println("Hola mundo");
  return 0;
}

También podemos incluir un programa que sume los elementos de un array:

int main(){
  int nums[10];
  int suma = 0;
  for(int i = 0; 10){
    print("Inserte un valor entero: ");
    input(nums[i]); //Recibimos cada uno de los valores por la entrada
  }
  for(int i = 0; 10){
    suma = suma + nums[i];
  }
  println(suma); //Ahora suma contendrá la suma de los valores del array
  return 0;
}

(EJEMPLOS CON IF, CON PUNTEROS, CON SWITCH Y CON ALGUN STRUCT)
