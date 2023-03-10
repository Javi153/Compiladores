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
parentesisApertura = \(
parentesisCierre = \)
llaveApertura = \{
llaveCierre = \}
igual = \=
distinto = \/\=
coma  = ,
%%
{separador}               {}
{comentario}              {}
{comentarioLargo}         {}
{barraBaja}               {return ops.unidadBB();}
{identificador}           {return ops.unidadId();}
{numeroEntero}            {return ops.unidadEnt();}
{numeroReal}              {return ops.unidadReal();}
{operadorSuma}            {return ops.unidadSuma();}
{operadorResta}           {return ops.unidadResta();}
{operadorMultiplicacion}  {return ops.unidadMul();}
{operadorDivision}        {return ops.unidadDiv();}
{parentesisApertura}      {return ops.unidadPAp();}
{parentesisCierre}        {return ops.unidadPCierre();} 
{llaveApertura}           {return ops.unidadLAp();}
{llaveCierre}             {return ops.unidadLCierre();}
{igual}                   {return ops.unidadIgual();} 
{distinto}                {return ops.unidadDist();}
{coma}                    {return ops.unidadComa();}
[^]                       {ops.error();}  
