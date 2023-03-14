package Sajax;

%%
%char
%line
%column
%class AnalizadorLexicoSajax
%type  UnidadLexica
%unicode

%{
  private SajaxOperations ops;
  public String lexema() {return yytext();}
  public int fila() {return yyline+1;}
  public int columna() {return yycolumn+1;}
%}

%eofval{
  return ops.unidadEof();
%eofval}

%init{
  ops = new SajaxOperations(this);
%init}

struct = struct
tipoEnt = int
tipoDec = float
tipoBool = bool
print = print
println = println
input = input
main = main
return = return
for = for
while = while
if = if
elsif = elsif
else = else
switch = switch
break = break
case = case
default = default
memspace = memspace
free = free
sizeof = sizeof
letra  = ([A-Z]|[a-z])
digitoPositivo = [1-9]
digito = ({digitoPositivo}|0)
parteEntera = {digitoPositivo}{digito}*
parteDecimal = {digito}* {digitoPositivo}
separador = [ \t\r\b\n]
comentario = \/\/[^\n]*
comentarioLargo = \/\*[^]*\*\/
barraBaja = \_
identificador = {letra}({letra}|{digito}|{barraBaja})*
numeroEntero = [\+\-]?{parteEntera}
numeroReal = [\+\-]?{parteEntera}\.{parteDecimal}
operadorSuma = \+
operadorResta = \-
operadorMultiplicacion = \*
operadorDivision = \/
operadorModulo = %
operadorExp = \^
operadorY = &&
operadorO = \|\|
operadorNot = \!
operadorMenor = <
operadorMayor = >
operadorMenorIg = <\=
operadorMayorIg = >\=
operadorIdentidad = \=\=
operadorDistinto = \/\=
parentesisApertura = \(
parentesisCierre = \)
llaveApertura = \{
llaveCierre = \}
corcheteApertura = \[
corcheteCierre = \]
igual = \=
coma  = ,
puntocoma = ;
flechita = ->
%%
{separador}               {}
{comentario}              {}
{comentarioLargo}         {}
{struct}                  {return ops.unidadStruct();}
{tipoEnt}                 {return ops.unidadInt();}
{tipoDec}                 {return ops.unidadFloat();}
{tipoBool}                {return ops.unidadBool();}
{print}                   {return ops.unidadPrint();}
{println}                 {return ops.unidadPrintln();}
{input}                   {return ops.unidadInput();}
{main}                    {return ops.unidadMain();}
{return}                  {return ops.unidadReturn();}
{for}                     {return ops.unidadFor();}
{while}                   {return ops.unidadWhile();}
{if}                      {return ops.unidadIf();}
{elsif}                  {return ops.unidadElsif();}
{else}                    {return ops.unidadElse();}
{switch}                  {return ops.unidadSwitch();}
{break}                   {return ops.unidadBreak();}
{case}                    {return ops.unidadCase();}
{default}                 {return ops.unidadDefault();}
{memspace}                {return ops.unidadMemspace();}
{free}                    {return ops.unidadFree();}
{sizeof}                  {return ops.unidadSizeof();}
{barraBaja}               {return ops.unidadBB();}
{identificador}           {return ops.unidadId();}
{numeroEntero}            {return ops.unidadEnt();}
{numeroReal}              {return ops.unidadReal();}
{operadorSuma}            {return ops.unidadSuma();}
{operadorResta}           {return ops.unidadResta();}
{operadorMultiplicacion}  {return ops.unidadMul();}
{operadorDivision}        {return ops.unidadDiv();}
{operadorModulo}          {return ops.unidadMod();}
{operadorExp}             {return ops.unidadExp();}
{operadorY}               {return ops.unidadY();}
{operadorO}               {return ops.unidadO();}
{operadorNot}             {return ops.unidadNot();}
{operadorMenor}           {return ops.unidadMenor();}
{operadorMayor}           {return ops.unidadMayor();}
{operadorMenorIg}         {return ops.unidadMenorIg();}
{operadorMayorIg}         {return ops.unidadMayorIg();}
{operadorIdentidad}       {return ops.unidadIdentidad();}
{operadorDistinto}        {return ops.unidadDistinto();}
{parentesisApertura}      {return ops.unidadPAp();}
{parentesisCierre}        {return ops.unidadPCierre();} 
{corcheteApertura}        {return ops.unidadCAp();}
{corcheteCierre}          {return ops.unidadCCierre();}
{llaveApertura}           {return ops.unidadLAp();}
{llaveCierre}             {return ops.unidadLCierre();}
{igual}                   {return ops.unidadIgual();} 
{coma}                    {return ops.unidadComa();}
{puntocoma}               {return ops.unidadPtoComa();}
{flechita}                {return ops.unidadFl();}
[^]                       {ops.error();}  
