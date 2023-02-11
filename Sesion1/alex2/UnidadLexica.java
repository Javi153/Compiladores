package alex2;

public class UnidadLexica extends Symbol {
    public UnidadLexica(int fila, int columna, int clase) {
	super(clase,new TokenValue(fila,columna));
    }
    public UnidadLexica(int fila, int columna, int clase, String lexema) {
	super(clase,new TokenValue(lexema,fila,columna));
    }
    public int clase () {return sym;}
    public String lexema() {return ((TokenValue)value).lexema;}    
    public int fila() {return ((TokenValue)value).fila;}
    public int columna() {return ((TokenValue)value).columna;}
    public String toString() {
        String clase = "";
        if(clase() == 0){
            clase = "EOF";
        }
        else if(clase() == 2){
            clase = "PALABRA";
        }
        else if(clase() == 3){
            clase = "PATRON";
        }
        else{
            clase = "OTRO";
        }
	if (lexema() == null) {
	    return "[clase:"+clase+",fila:"+fila()+",col:"+columna()+"]";
	} else {
	    return "[clase:"+clase+",fila:"+fila()+",col:"+columna()+",lexema:"+lexema()+"]";
	}
    }
}
