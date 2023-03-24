package ast;

public class Input extends Statement implements ASTNode{
    private E expresion;

    public Input(E expresion){
        this.expresion = expresion;
    }

    @Override
    public NodeKind nodeKind() {
        return NodeKind.INPUT;
    }

    @Override
    public String toString() {
        return "input("+expresion.toString()+")";
    }
}
