package sajax_lex;
import constructorast.ClaseLexica;

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

  public UnidadLexica unidadEnt() {
    return new UnidadLexica(sjx.fila(),sjx.columna(),ClaseLexica.ENT,sjx.lexema());
  }

  public UnidadLexica unidadReal() {
    return new UnidadLexica(sjx.fila(),sjx.columna(),ClaseLexica.REAL,sjx.lexema());
  }

  public UnidadLexica unidadTrue() {
    return new UnidadLexica(sjx.fila(),sjx.columna(),ClaseLexica.TRUE,sjx.lexema());
  }

  public UnidadLexica unidadFalse() {
    return new UnidadLexica(sjx.fila(),sjx.columna(),ClaseLexica.FALSE,sjx.lexema());
  }

  public UnidadLexica unidadSuma() {
    return new UnidadLexica(sjx.fila(),sjx.columna(),ClaseLexica.MAS,sjx.lexema());
  }

  public UnidadLexica unidadResta() {
    return new UnidadLexica(sjx.fila(),sjx.columna(),ClaseLexica.MENOS,sjx.lexema());
  }

  public UnidadLexica unidadMul() {
    return new UnidadLexica(sjx.fila(),sjx.columna(),ClaseLexica.POR,sjx.lexema());
  }

  public UnidadLexica unidadDiv() {
    return new UnidadLexica(sjx.fila(),sjx.columna(),ClaseLexica.DIV,sjx.lexema());
  }

  public UnidadLexica unidadMod() {
    return new UnidadLexica(sjx.fila(),sjx.columna(),ClaseLexica.MOD,sjx.lexema());
  }

  public UnidadLexica unidadExp() {
    return new UnidadLexica(sjx.fila(),sjx.columna(),ClaseLexica.POT,sjx.lexema());
  }

  public UnidadLexica unidadY() {
    return new UnidadLexica(sjx.fila(),sjx.columna(),ClaseLexica.AND,sjx.lexema());
  }

  public UnidadLexica unidadO() {
    return new UnidadLexica(sjx.fila(),sjx.columna(),ClaseLexica.OR,sjx.lexema());
  }

  public UnidadLexica unidadNot() {
    return new UnidadLexica(sjx.fila(),sjx.columna(),ClaseLexica.NOT,sjx.lexema());
  }

  public UnidadLexica unidadMenor() {
    return new UnidadLexica(sjx.fila(),sjx.columna(),ClaseLexica.MENOR,sjx.lexema());
  }

  public UnidadLexica unidadMayor() {
    return new UnidadLexica(sjx.fila(),sjx.columna(),ClaseLexica.MAYOR,sjx.lexema());
  }

  public UnidadLexica unidadMenorIg() {
    return new UnidadLexica(sjx.fila(),sjx.columna(),ClaseLexica.MENIGUAL,sjx.lexema());
  }

  public UnidadLexica unidadMayorIg() {
    return new UnidadLexica(sjx.fila(),sjx.columna(),ClaseLexica.MAYIGUAL,sjx.lexema());
  }

  public UnidadLexica unidadIgual() {
    return new UnidadLexica(sjx.fila(),sjx.columna(),ClaseLexica.IGUAL,sjx.lexema());
  }

  public UnidadLexica unidadIdentidad() {
    return new UnidadLexica(sjx.fila(),sjx.columna(),ClaseLexica.IGDOBLE,sjx.lexema());
  }

  public UnidadLexica unidadDistinto() {
    return new UnidadLexica(sjx.fila(),sjx.columna(),ClaseLexica.DISTINTO,sjx.lexema());
  }

  public UnidadLexica unidadPAp() {
    return new UnidadLexica(sjx.fila(),sjx.columna(),ClaseLexica.PAP,sjx.lexema());
  }

  public UnidadLexica unidadPCierre() {
    return new UnidadLexica(sjx.fila(),sjx.columna(),ClaseLexica.PCIERRE,sjx.lexema());
  }

  public UnidadLexica unidadCAp() {
    return new UnidadLexica(sjx.fila(),sjx.columna(),ClaseLexica.CAP,sjx.lexema());
  }

  public UnidadLexica unidadCCierre() {
    return new UnidadLexica(sjx.fila(),sjx.columna(),ClaseLexica.CCIERRE,sjx.lexema());
  }

  public UnidadLexica unidadLAp() {
    return new UnidadLexica(sjx.fila(),sjx.columna(),ClaseLexica.LLAP,sjx.lexema());
  }

  public UnidadLexica unidadLCierre() {
    return new UnidadLexica(sjx.fila(),sjx.columna(),ClaseLexica.LLCIERRE,sjx.lexema());
  }

  public UnidadLexica unidadComa() {
    return new UnidadLexica(sjx.fila(),sjx.columna(),ClaseLexica.COMA,sjx.lexema());
  }

  public UnidadLexica unidadPtoComa() {
    return new UnidadLexica(sjx.fila(),sjx.columna(),ClaseLexica.PTOCOMA,sjx.lexema());
  }

  public UnidadLexica unidadPto() {
    return new UnidadLexica(sjx.fila(),sjx.columna(),ClaseLexica.PUNTO,sjx.lexema());
  }

  public UnidadLexica unidadDosPtos() {
    return new UnidadLexica(sjx.fila(),sjx.columna(),ClaseLexica.DOSPTOS,sjx.lexema());
  }

  public UnidadLexica unidadFl() {
    return new UnidadLexica(sjx.fila(),sjx.columna(),ClaseLexica.FLECHA,sjx.lexema());
  }

  public UnidadLexica unidadEof() {
    return new UnidadLexica(sjx.fila(),sjx.columna(),ClaseLexica.EOF,sjx.lexema());
  }
  public void error() {
    System.err.println("***"+sjx.fila()+", "+sjx.columna()+" Caracter inesperado: "+sjx.lexema());
  }
}
