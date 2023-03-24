package ast;

public class IfElse extends Statement implements ASTNode{
    private E cond;
    private Statement S1, S2;
    private boolean hayelse;

    public IfElse(E cond, Statement S1){
        this.cond = cond;
        this.S1 = S1;
        hayelse = false;
        this.S2 = null;
    }

    public IfElse(E cond, Statement S1, Statement S2){
        this.cond = cond;
        this.S1 = S1;
        this.S2 = S2;
        hayelse = true;
    }

    @Override
    public NodeKind nodeKind() {
        return NodeKind.IFELSE;
    }
}