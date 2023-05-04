package ast;

public class Puntero extends Tipo implements ASTNode{ //Igual hay que rehacerlo
    private Tipo t;

    public Puntero(Tipo t){
        super(t.getTipo());
        this.t = t;
        isPointer = true;
    }

    public Tipo getTipoPointer(){
        return t;
    }

    @Override
    public TipoEnum getTipo(){
        return TipoEnum.PUNTERO;
    }

    @Override
    public boolean bind() {
        return t.bind();
    }

    public String toString(){
        return "puntero("+t.toString()+")";
    }

    @Override
    public int size(){
        return 4;
    }
}
