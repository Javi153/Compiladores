package sajax_lex;
import asint.ClaseLexica;

public class SajaxOperations {
  private AnalizadorLexicoSajax sjx;
  public SajaxOperations(AnalizadorLexicoSajax sjx) {
   this.sjx = sjx;   
  }

  public UnidadLexica unidadStruct() {
    return new UnidadLexica(sjx.fila(),sjx.columna(),ClaseLexica.STRUCT,sjx.lexema());
  }
  public UnidadLexica unidadInt() {
    return new UnidadLexica(sjx.fila(),sjx.columna(),ClaseLexica.INT,sjx.lexema());
  }
  public UnidadLexica unidadFloat() {
    return new UnidadLexica(sjx.fila(),sjx.columna(),ClaseLexica.FLOAT,sjx.lexema());
  }
  public UnidadLexica unidadBool() {
    return new UnidadLexica(sjx.fila(),sjx.columna(),ClaseLexica.BOOL,sjx.lexema());
  }
  public UnidadLexica unidadPrint() {
    return new UnidadLexica(sjx.fila(),sjx.columna(),ClaseLexica.PRINT,sjx.lexema());
  }
  public UnidadLexica unidadPrintln() {
    return new UnidadLexica(sjx.fila(),sjx.columna(),ClaseLexica.PRINTLN,sjx.lexema());
  }

  public UnidadLexica unidadInput() {
    return new UnidadLexica(sjx.fila(),sjx.columna(),ClaseLexica.INPUT,sjx.lexema());
  }
  public UnidadLexica unidadMain() {
    return new UnidadLexica(sjx.fila(),sjx.columna(),ClaseLexica.MAIN,sjx.lexema());
  }

  public UnidadLexica unidadVoid() {
    return new UnidadLexica(sjx.fila(),sjx.columna(),ClaseLexica.VOID,sjx.lexema());
  }

  public UnidadLexica unidadReturn() {
    return new UnidadLexica(sjx.fila(),sjx.columna(),ClaseLexica.RETURN,sjx.lexema());
  }

  public UnidadLexica unidadFor() {
    return new UnidadLexica(sjx.fila(),sjx.columna(),ClaseLexica.FOR,sjx.lexema());
  }

  public UnidadLexica unidadWhile() {
    return new UnidadLexica(sjx.fila(),sjx.columna(),ClaseLexica.WHILE,sjx.lexema());
  }

  public UnidadLexica unidadIf() {
    return new UnidadLexica(sjx.fila(),sjx.columna(),ClaseLexica.IF,sjx.lexema());
  }

  public UnidadLexica unidadElsif() {
    return new UnidadLexica(sjx.fila(),sjx.columna(),ClaseLexica.ELSIF,sjx.lexema());
  }

  public UnidadLexica unidadElse() {
    return new UnidadLexica(sjx.fila(),sjx.columna(),ClaseLexica.ELSE,sjx.lexema());
  }

  public UnidadLexica unidadSwitch() {
    return new UnidadLexica(sjx.fila(),sjx.columna(),ClaseLexica.SWITCH,sjx.lexema());
  }

  public UnidadLexica unidadBreak() {
    return new UnidadLexica(sjx.fila(),sjx.columna(),ClaseLexica.BREAK,sjx.lexema());
  }

  public UnidadLexica unidadCase() {
    return new UnidadLexica(sjx.fila(),sjx.columna(),ClaseLexica.CASE,sjx.lexema());
  }

  public UnidadLexica unidadDefault() {
    return new UnidadLexica(sjx.fila(),sjx.columna(),ClaseLexica.DEFAULT,sjx.lexema());
  }

  public UnidadLexica unidadMemspace() {
    return new UnidadLexica(sjx.fila(),sjx.columna(),ClaseLexica.MEMSPACE,sjx.lexema());
  }

  public UnidadLexica unidadFree() {
    return new UnidadLexica(sjx.fila(),sjx.columna(),ClaseLexica.FREE,sjx.lexema());
  }

  public UnidadLexica unidadBB() {
    return new UnidadLexica(sjx.fila(),sjx.columna(),ClaseLexica.BB,sjx.lexema());
  }

  public UnidadLexica unidadId() {
    return new UnidadLexica(sjx.fila(),sjx.columna(),ClaseLexica.IDEN,sjx.lexema());
  }

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

  public void error() {
    System.err.println("***"+sjx.fila()+", "+sjx.columna()+" Caracter inesperado: "+sjx.lexema());
  }
}
