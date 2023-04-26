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
    public boolean type() {
        boolean aux = cond.type() && cond.isType().getTipo().equals(TipoEnum.BOOL);
        if(!aux){
            System.out.println("Error: Se esperaba condicion booleana en la clausula if " + cond.num());
        }
        sTipo.push(new HashMap<>());
        for(Statement s : St.getList()){
            aux = aux & s.type();
        }
        s.pop();
        return aux;
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
