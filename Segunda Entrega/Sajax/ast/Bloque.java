package ast;

import java.util.ArrayList;

public class Bloque implements ASTNode{
    private ArrayList<Statement> stlist;

    public Bloque(ArrayList<Statement> stlist){
        this.stlist = stlist;
    }

    @Override
    public NodeKind nodeKind() {
        return NodeKind.BLOQUE;
    }

    public String toString(){
        if(stlist.size() == 0){
            return "bloque()";
        }
        else {
            String s = "bloque(";
            for (int i = 0; i < stlist.size() - 1; ++i) {
                s = s.concat(stlist.get(i).toString() + ",");
            }
            s = s.concat(stlist.get(stlist.size() - 1).toString() + ")");
            return s;
        }
    }
}
