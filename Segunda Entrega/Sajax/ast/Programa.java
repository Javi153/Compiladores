package ast;

import java.util.ArrayList;

public class Programa implements ASTNode{
    private ArrayList<Bloque> bloques;
    
    public Programa(ArrayList<Bloque> bloques){
        this.bloques = bloques;
    }

    public NodeKind nodeKind(){
        return NodeKind.PROGRAMA;
    }
    
    public String toString(){
        String s = "programa(" + bloques.get(0).toString();
        for(int i = 1; i < bloques.size(); ++i){
            s = s.concat(","+bloques.get(i).toString());
        }
        s = s.concat(")");
        return s;
    }
}
