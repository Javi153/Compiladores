package ast;

import java.util.HashMap;

public class While extends Statement implements ASTNode{
    private E cond;
    private BloqueIns st;

    public While(E cond, BloqueIns st){
        this.cond = cond;
        this.st = st;
    }

    @Override
    public boolean bind() {
        boolean aux = cond.isBound();
        s.push(new HashMap<>());
        aux = aux & st.bind();
        s.pop();
        return aux;
    }

    @Override
    public NodeKind nodeKind() {
        return NodeKind.WHILE;
    }

    public String toString(){
        return "while("+cond.toString()+","+st.toString()+")";
    }
}
