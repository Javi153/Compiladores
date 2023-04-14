package ast;

public class While extends Statement implements ASTNode{
    private E cond;
    private Bloque st;

    public While(E cond, Bloque st){
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
