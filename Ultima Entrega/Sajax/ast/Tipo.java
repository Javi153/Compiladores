package ast;

public class Tipo implements ASTNode{
    protected TipoEnum t;
    protected boolean isPointer;
    protected boolean isParam;

    public Tipo(TipoEnum t){
        this.t = t;
        isPointer = false;
        isParam = false;
    }

    public boolean isPointer(){
        return isPointer;
    }

    public boolean isParam(){return isParam;}

    public TipoEnum getTipo(){
        return t;
    }

    @Override
    public boolean type() {
        return true;
    }

    @Override
    public boolean bind() {
        return true;
    }

    public int size() { return t.size(); }

    @Override
    public NodeKind nodeKind() {
        return NodeKind.TIPO;
    }

    public String toString(){
        return t.toString();
    }
}
