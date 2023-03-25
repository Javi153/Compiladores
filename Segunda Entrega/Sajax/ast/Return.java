package ast;

public class Return extends Statement implements ASTNode{
    private E ret;

    public Return(E ret){
        this.ret = ret;
    }

    @Override
    public NodeKind nodeKind() {
        return NodeKind.RETURN;
    }

    public String toString(){
        return "return("+ret.toString()+")";
    }
}
