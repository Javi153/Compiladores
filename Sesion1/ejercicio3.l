package alex2;

%%
%char
%line
%column
%class AnalizadorLexicoTiny
%type  UnidadLexica
%unicode

%{
  private ALexOperations ops;
  public String lexema() {return yytext();}
  public int fila() {return yyline+1;}
  public int columna() {return yycolumn+1;}
%}

%eofval{
  return ops.unidadEof();
%eofval}

%init{
  ops = new ALexOperations(this);
%init}

letra = ([A-Z]|[a-z])
asterisco = \*
palabra = {letra}+
patron = {letra}*({asterisco}{letra}|{letra}{asterisco}){letra}*
separador = [ \t\r\b\n]
otro = (.)+
comentario = #[^\n]* 
%%
{separador}               {}
{comentario}              {}
{palabra}		  {return ops.unidadPalabra();}
{patron}		  {return ops.unidadPatron();}
{otro}			  {return ops.unidadOtro();}
[^]                       {ops.error();}  
