package ast;

public class Elsif extends Statement implements ASTNode{
    private E cond;
    private BloqueIns St;

    public Elsif(E cond, BloqueIns St){
        this.cond = cond;
        this.St = St;
    }

    @Override
    public boolean bind() {
        return cond.isBound() && St.bind();
    }

    @Override
    public NodeKind nodeKind() {
        return NodeKind.ELSIF;
    }

    public String toString(){
        return "elsif("+cond.toString()+","+St.toString()+")";
    }
}
