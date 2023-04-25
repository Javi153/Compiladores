package ast;

import java.util.HashMap;

public class Elsif extends Statement implements ASTNode{
    private E cond;
    private BloqueIns St;

    public Elsif(E cond, BloqueIns St){
        this.cond = cond;
        this.St = St;
    }

    @Override
    public boolean bind() {
        boolean aux = cond.isBound();
        s.push(new HashMap<>());
        aux = aux & St.bind();
        s.pop();
        return aux;
    }

    @Override
    public NodeKind nodeKind() {
        return NodeKind.ELSIF;
    }

    public String toString(){
        return "elsif("+cond.toString()+","+St.toString()+")";
    }
}