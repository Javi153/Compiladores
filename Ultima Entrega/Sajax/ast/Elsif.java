package ast;

import java.util.HashMap;

public class Elsif extends Statement implements ASTNode{
    private E cond;
    private BloqueIns St;

    //En general t0d0 funciona muy parecido a bloque, pero comprobando la condicion del elsif en cada caso

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
        sTipo.pop();
        return aux;
    }

    @Override
    public boolean bind() {
        boolean aux = cond.bind();
        s.push(new HashMap<>());
        aux = aux & St.bind();
        s.pop();
        return aux;
    }

    public String code() {
        return "else\n" + cond.code() + "\nif\n" + St.code() + "\n";
    }

    @Override
    public NodeKind nodeKind() {
        return NodeKind.ELSIF;
    }

    public String toString(){
        return "elsif("+cond.toString()+","+St.toString()+")";
    }

    public void setDelta(int prof){
        St.setDelta(prof);
    }
}
