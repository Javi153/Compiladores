package ast;

public class Elsif extends Statement implements ASTNode{
    private E cond;
    private Statement St;

    public Elsif(E cond, Statement St){
        this.cond = cond;
        this.St = St;
    }
    @Override
    public NodeKind nodeKind() {
        return NodeKind.ELSIF;
    }

    public String toString(){
        return "elsif("+cond.toString()+","+St.toString()+")";
    }
}
