package alex;

import errors.GestionErroresTiny;

%%
%cup
%line
%column
%class AnalizadorLexicoTiny
%unicode
%public

%{
  private ALexOperations ops;
  private GestionErroresTiny errores;
  public String lexema() {return yytext();}
  public int fila() {return yyline+1;}
  public int columna() {return yycolumn+1;}
  public void fijaGestionErrores(GestionErroresTiny errores) {
   this.errores = errores;
  }
%}

%eofval{
  return ops.unidadEof();
%eofval}

%init{
  ops = new ALexOperations(this);
%init}

letra  = ([A-Z]|[a-z])
digitoPositivo = [1-9]
digito = ({digitoPositivo}|0)
parteEntera = {digitoPositivo}{digito}*
parteDecimal = {digito}* {digitoPositivo}
separador = [ \t\r\b\n]
comentario = \/\/[^\n]*
evalua = evalua
donde = donde
lreduce = lreduce
lmap = lmap
lfilter = lfilter
print = print
identificador = {letra}({letra}|{digito})*
numeroEntero = [\+\-]?{parteEntera}
numeroReal = [\+\-]?{parteEntera}\.{parteDecimal}
operadorSuma = \+
operadorResta = \-
operadorMultiplicacion = \*
operadorDivision = \/
corcheteApertura = \[
corcheteCierre = \]
parentesisApertura = \(
parentesisCierre = \)
igual = \=
igualDoble = \=\=
distinto = \!\=
menor = \<
mayor = \>
almohadilla = \#
coma  = ,
%%
{separador}               {}
{comentario}              {}
{evalua}                  {return ops.unidadEvalua();}
{donde}                   {return ops.unidadDonde();}
{print}			  {return ops.unidadPrint();}
{lreduce}		  {return ops.unidadLReduce();}
{lmap}			  {return ops.unidadLMap();}
{lfilter}		  {return ops.unidadLFilter();}
{identificador}           {return ops.unidadId();}
{numeroEntero}            {return ops.unidadEnt();}
{numeroReal}              {return ops.unidadReal();}
{operadorSuma}            {return ops.unidadSuma();}
{operadorResta}           {return ops.unidadResta();}
{operadorMultiplicacion}  {return ops.unidadMul();}
{operadorDivision}        {return ops.unidadDiv();}
{parentesisApertura}      {return ops.unidadPAp();}
{parentesisCierre}        {return ops.unidadPCierre();} 
{corcheteApertura}        {return ops.unidadCAp();}
{corcheteCierre}          {return ops.unidadCCierre();}
{igual}                   {return ops.unidadIgual();} 
{igualDoble}              {return ops.unidadIgualDoble();} 
{distinto}                {return ops.unidadDistinto();} 
{menor}                   {return ops.unidadMenor();} 
{mayor}                   {return ops.unidadMayor();} 
{almohadilla}		  {return ops.unidadAlmohadilla();}
{coma}                    {return ops.unidadComa();}
[^]                       {errores.errorLexico(fila(),columna(),lexema());}  
