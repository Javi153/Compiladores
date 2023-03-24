package ast;

public class Memspace extends Statement implements ASTNode{
    private E arg;

    public Memspace(E arg){
        this.arg = arg;
    }

    @Override
    public NodeKind nodeKind() {
        return NodeKind.MEMSPACE;
    }

    public String toString(){
        return "memspace("+arg.toString()+")";
    }
}
