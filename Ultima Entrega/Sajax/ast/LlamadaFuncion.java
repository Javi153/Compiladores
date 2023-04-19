package ast;

import java.util.ArrayList;

public class LlamadaFuncion extends E implements ASTNode{
    private String name;
    private ArrayList<E> parlist;

    public LlamadaFuncion(String name, ArrayList<E> parlist){
        this.name = name;
        this.parlist = parlist;
    }

    @Override
    public KindE kind() {
        return KindE.CALLFUN;
    }

    @Override
    public boolean isBound() {
        boolean res = true;
        if(buscaId(name) == null){
            res = false;
            System.out.println("La función " + name + " no está definida");
        }
        for(E elem : parlist){
            res = res && elem.isBound();
        }
        return res;
    }

    @Override
    public boolean bind() {
        return true;
    }

    @Override
    public NodeKind nodeKind(){
        return NodeKind.FUNCIONCALL;
    }

    public String toString(){
        String s;
        s = new String("llamadaFuncion(" + name);
        for (E p : parlist) {
            s = s.concat("," + p.toString());
        }
        s = s.concat(")");
        return s;
    }
}