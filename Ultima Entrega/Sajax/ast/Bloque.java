package ast;

import java.util.ArrayList;

public class Bloque<T extends ASTNode> implements ASTNode {
    protected ArrayList<T> stlist;
    protected String tipoBloque;

    public Bloque(ArrayList<T> stlist){
        this.stlist = stlist;
        this.tipoBloque = "bloque";
    }

    @Override
    public boolean bind() {
        boolean aux = true;
        for(T elem : stlist){
            aux = aux && elem.bind();
        }
        return aux;
    }

    @Override
    public NodeKind nodeKind() {
        return NodeKind.BLOQUE;
    }

    public String toString() {
        if(stlist.size() == 0) {
            return tipoBloque + "()";
        }
        else {
            String s = tipoBloque + "(";
            for (int i = 0; i < stlist.size() - 1; ++i) {
                s = s.concat(stlist.get(i).toString() + ",");
            }
            s = s.concat(stlist.get(stlist.size() - 1).toString() + ")");
            return s;
        }
    }
}
