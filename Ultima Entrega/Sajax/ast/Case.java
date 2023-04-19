package ast;

import java.util.ArrayList;

public class Case extends Bloque<Statement> implements ASTNode {

    private Ent ent;

    public Case(Ent ent, ArrayList<Statement> stlist) {
        super(stlist);
        this.ent = ent;
        tipoBloque = "case";
    }

    @Override
    public NodeKind nodeKind() {
        return NodeKind.CASE;
    }

    @Override
    public String toString(){
        if (stlist.size() == 0) {
            return tipoBloque + "(" + ent.toString() + ")";
        }
        else {
            String s = tipoBloque + "(" + ent.toString();
            for (int i = 0; i < stlist.size(); ++i) {
                s = s.concat("," + stlist.get(i).toString());
            }
            s = s.concat(")");
            return s;
        }
    }

    @Override
    public boolean bind() {
        boolean aux = true;
        for(Statement s : stlist){
            aux = aux && s.bind();
        }
        return aux;
    }

}
