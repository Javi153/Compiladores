package ast;

public class Free extends Statement implements ASTNode{
    private E arg;

    public Free(E arg){
        this.arg = arg;
    }

    @Override
    public NodeKind nodeKind() {
        return NodeKind.FREE;
    }

    public String toString(){
        return "free("+arg.toString()+")";
    }
}
