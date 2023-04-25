package ast;

public class Null extends E {
    private Tipo tipo;
    public Null() {
        tipo = new Tipo(TipoEnum.NULL);
    }
    public KindE kind(){
        return KindE.NULL;
    }

    @Override
    public boolean isBound() {
        return true;
    }

    @Override
    public Tipo isType() { //Probablemente le metere puntero
        return tipo;
    }

    @Override
    public boolean type() { //Ya veremos mas adelante
        return true;
    }

    public String toString(){
        return "null";
    }

    @Override
    public boolean bind() {
        return true;
    }

    public String num(){
        return "null";
    }
}
