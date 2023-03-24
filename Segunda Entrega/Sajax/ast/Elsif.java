package ast;

public class Elsif extends Statement implements ASTNode{
    private E cond;
    private Statement St;

    public Elsif(E cond, Statement S){
        this.cond = ;
    }
    @Override
    public NodeKind nodeKind() {
        return NodeKind.ELSIF;
    }

    public String toString(){

    }
}
