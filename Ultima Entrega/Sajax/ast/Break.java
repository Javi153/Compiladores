package ast;

public class Break extends Statement implements ASTNode{
    public Break(){}

    @Override
    public boolean type() {
        return true;
    }

    @Override
    public boolean bind() {
        return true;
    }

    @Override
    public NodeKind nodeKind() {
        return NodeKind.BREAK;
    }

    public String toString(){
        return "break";
    }
}
