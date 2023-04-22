package ast;

public class Tipo implements ASTNode{
    private TipoEnum t;

    public Tipo(TipoEnum t){
        this.t = t;
    }

    public TipoEnum getTipo(){
        return t;
    }

    @Override
    public boolean bind() {
        return true;
    }

    @Override
    public NodeKind nodeKind() {
        return NodeKind.TIPO;
    }

    public String toString(){
        return t.toString();
    }
}
