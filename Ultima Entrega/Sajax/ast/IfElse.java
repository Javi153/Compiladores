package ast;

import java.util.ArrayList;
import java.util.HashMap;

public class IfElse extends Statement implements ASTNode{
    private E cond;
    private BloqueIns S1, S2;
    private ArrayList<Elsif> Elsifs;
    private boolean hayelse;

    public IfElse(E cond, BloqueIns S1, ArrayList<Elsif> Elsifs){
        this.cond = cond;
        this.S1 = S1;
        hayelse = false;
        this.Elsifs = Elsifs;
        this.S2 = null;
    }

    public IfElse(E cond, BloqueIns S1, ArrayList<Elsif> Elsifs, BloqueIns S2){
        this.cond = cond;
        this.S1 = S1;
        this.S2 = S2;
        hayelse = true;
        this.Elsifs = Elsifs;
    }

    @Override
    public boolean type() {
        boolean aux = cond.type() && cond.isType().getTipo().equals(TipoEnum.BOOL);
        if(!aux){
            System.out.println("Error: se esperaba tipo bool en la condicion de if " + cond.num());
        }
        aux = aux & S1.type();
        for(Elsif es : Elsifs){
            aux = aux & es.type();
        }
        if(hayelse){
            aux = aux & S2.type();
        }
        return aux;
    }

    @Override
    public boolean bind() {
        boolean aux = cond.isBound();
        s.push(new HashMap<>());
        aux = aux & S1.bind();
        s.pop();
        for(Elsif es : Elsifs){
            aux = aux & es.bind();
        }
        s.push(new HashMap<>());
        if(hayelse){
            aux = aux & S2.bind();
        }
        s.pop();
        return aux;
    }

    @Override
    public String code() {
        String c = cond.code() + "\nif\n" + S1.code() + "\n";
        for (Elsif es : Elsifs)
            c = c.concat(es.code());
        if (hayelse)
            c = c.concat("\nelse\n" + S2.code());
        for (Elsif es : Elsifs)
            c = c.concat("\nend");
        c = c.concat("\nend");
        return c;
    }

    @Override
    public NodeKind nodeKind() {
        return NodeKind.IFELSE;
    }

    public String toString(){
        String s;
        if(hayelse){
            s = new String("ifElse("+cond.toString()+","+S1.toString()+",");
            for(Elsif es : Elsifs){
                s = s.concat(es.toString()+",");
            }
            s = s.concat(S2.toString() + ")");
        }
        else{
            s = new String("if("+cond.toString()+","+S1.toString());
            for(Elsif es : Elsifs){
                s = s.concat(","+es.toString());
            }
            s = s.concat(")");
        }
        return s;
    }
}