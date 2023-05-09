package ast;

public class Return extends Statement implements ASTNode{
    private E ret;
    private Tipo t;

    public Return(E ret){
        this.ret = ret;
    }

    @Override
    public boolean type() {
        boolean aux = ret.type();
        t = ret.isType();
        return aux;
    }

    public Tipo isType(){
        return ret.isType();
    }

    public Tipo getTipo(){
        return t;
    }

    @Override
    public boolean bind() {
        return ret.bind();
    }

    @Override
    public NodeKind nodeKind() {
        return NodeKind.RETURN;
    }

    public String toString(){
        return "return("+ret.toString()+")";
    }

    public String code(){
        return ret.code() + "\n";
    }
}
