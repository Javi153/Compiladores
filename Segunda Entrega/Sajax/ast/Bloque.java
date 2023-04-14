package ast;

import java.util.ArrayList;

public class Bloque implements ASTNode {
    protected ArrayList<Statement> stlist;
    protected String tipoBloque = "bloque";

    public Bloque(ArrayList<Statement> stlist){
        this.stlist = stlist;
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
