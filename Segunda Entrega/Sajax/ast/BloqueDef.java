package ast;

import java.util.ArrayList;

public class BloqueDef implements ASTNode {
    protected ArrayList<Definiciones> stlist;
    protected String tipoBloque = "definiciones";

    public BloqueDef(ArrayList<Definiciones> stlist){
        this.stlist = stlist;
    }

    @Override
    public NodeKind nodeKind() {
        return NodeKind.BLOQUEDEF;
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
