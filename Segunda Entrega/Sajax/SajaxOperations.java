package Sajax;

public class SajaxOperations {
  private AnalizadorLexicoSajax sjx;
  public SajaxOperations(AnalizadorLexicoSajax sjx) {
   this.sjx = sjx;   
  }
  public UnidadLexica unidadId() {
     return new UnidadLexica(sjx.fila(),sjx.columna(),ClaseLexica.IDEN,
                                         sjx.lexema()); 
  }
    public UnidadLexica unidadEnt() {
        return new UnidadLexica(sjx.fila(),sjx.columna(),ClaseLexica.ENT,sjx.lexema());
    }

    public UnidadLexica unidadEnt() {
     return new UnidadLexica(sjx.fila(),sjx.columna(),ClaseLexica.ENT,sjx.lexema()); 
  } 
  public UnidadLexica unidadReal() {
     return new UnidadLexica(sjx.fila(),sjx.columna(),ClaseLexica.REAL,sjx.lexema()); 
  } 
  public UnidadLexica unidadSuma() {
     return new UnidadLexica(sjx.fila(),sjx.columna(),ClaseLexica.MAS); 
  } 
  public UnidadLexica unidadResta() {
     return new UnidadLexica(sjx.fila(),sjx.columna(),ClaseLexica.MENOS); 
  } 
  public UnidadLexica unidadMul() {
     return new UnidadLexica(sjx.fila(),sjx.columna(),ClaseLexica.POR); 
  } 
  public UnidadLexica unidadDiv() {
     return new UnidadLexica(sjx.fila(),sjx.columna(),ClaseLexica.DIV); 
  } 
  public UnidadLexica unidadPAp() {
     return new UnidadLexica(sjx.fila(),sjx.columna(),ClaseLexica.PAP); 
  } 
  public UnidadLexica unidadPCierre() {
     return new UnidadLexica(sjx.fila(),sjx.columna(),ClaseLexica.PCIERRE); 
  } 
  public UnidadLexica unidadIgual() {
     return new UnidadLexica(sjx.fila(),sjx.columna(),ClaseLexica.IGUAL); 
  } 
  public UnidadLexica unidadComa() {
     return new UnidadLexica(sjx.fila(),sjx.columna(),ClaseLexica.COMA); 
  } 
  public UnidadLexica unidadEof() {
     return new UnidadLexica(sjx.fila(),sjx.columna(),ClaseLexica.EOF); 
  }
  public void error() {
    System.err.println("***"+sjx.fila()+", "+sjx.columna()+" Caracter inesperado: "+sjx.lexema());
  }
}
