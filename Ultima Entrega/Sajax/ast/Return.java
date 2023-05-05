package ast;

public class Return extends Statement implements ASTNode{
    private E ret;

    public Return(E ret){
        this.ret = ret;
    }

    @Override
    public boolean type() {
        return true;
    }

    public Tipo getTipo(){
        return ret.isType();
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
