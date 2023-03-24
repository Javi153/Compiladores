package ast;

public class Print extends Statement implements ASTNode{
    private E expresion;
    private boolean ln;

    public Print(E expresion, boolean ln){
        this.expresion = expresion;
        this.ln = ln;
    }

    @Override
    public NodeKind nodeKind() {
        return NodeKind.PRINT;
    }

    @Override
    public String toString() {
        if(ln){
            return "println("+expresion.toString()+")";
        }
        else{
            return "print("+expresion.toString()+")";
        }
    }
}
