package ast;

public class Null extends E {
    private Tipo tipo;
    public Null() {
        tipo = new Tipo(TipoEnum.PUNTERO);
    }
    public KindE kind(){
        return KindE.NULL;
    }

    /*@Override
    public boolean isBound() {
        return true;
    }*/

    @Override
    public Tipo isType() {
        return tipo;
    }

    @Override
    public boolean type() {
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

    @Override
    public ASTNode getDef() {
        return null;
    }
}
