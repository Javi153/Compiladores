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
    } //Devuelve el tipo envuelto por el puntero, por ejemplo para *int devolvera int

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
