package ast;

import java.util.ArrayList;

public class IfElse extends Statement implements ASTNode{
    private E cond;
    private Bloque S1, S2;
    private ArrayList<Elsif> Elsifs;
    private boolean hayelse;

    public IfElse(E cond, Bloque S1, ArrayList<Elsif> Elsifs){
        this.cond = cond;
        this.S1 = S1;
        hayelse = false;
        this.Elsifs = Elsifs;
        this.S2 = null;
    }

    public IfElse(E cond, Bloque S1, ArrayList<Elsif> Elsifs, Bloque S2){
        this.cond = cond;
        this.S1 = S1;
        this.S2 = S2;
        hayelse = true;
        this.Elsifs = Elsifs;
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