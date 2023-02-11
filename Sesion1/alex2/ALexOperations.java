package alex2;

public class ALexOperations {
  private AnalizadorLexicoTiny alex;
  public ALexOperations(AnalizadorLexicoTiny alex) {
   this.alex = alex;   
  }
  public UnidadLexica unidadEof() {
     return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.EOF); 
  }
  public UnidadLexica unidadPalabra() {
        return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.PALABRA, alex.lexema());
    }
  public UnidadLexica unidadPatron() {
        return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.PATRON, alex.lexema());
    }
  public UnidadLexica unidadOtro() {
        return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.OTRO, alex.lexema());
    }


    public void error() {
    System.err.println("***"+alex.fila()+", "+alex.columna()+" Caracter inesperado: "+alex.lexema());
  }
}
