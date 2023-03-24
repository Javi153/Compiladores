package ast;

public class While extends Statement implements ASTNode{
    private E cond;
    private Statement st;

    public While(E cond, Statement st){
        this.cond = cond;
        this.st = st;
    }

    @Override
    public NodeKind nodeKind() {
        return NodeKind.WHILE;
    }

    public String toString(){
        return "while("+cond.toString()+","+st.toString()+")";
    }
}
