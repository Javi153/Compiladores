package ast;

import java.util.ArrayList;
import java.util.HashMap;

public class Case extends DefaultCase implements ASTNode {

    private Ent ent;

    public Case(Ent ent, ArrayList<Statement> stlist, boolean breakPresence) {
        super(stlist, breakPresence);
        this.ent = ent;
        tipoBloque = "case";
    }

    @Override
    public NodeKind nodeKind() {
        return NodeKind.CASE;
    }

    @Override
    public String toString() {
        String s = tipoBloque + "(" + ent.toString();
        for (int i = 0; i < stlist.size(); ++i) {
            s = s.concat("," + stlist.get(i).toString());
        }
        s = s.concat("," + breakPresence + ")");
        return s;
    }

    //No implementes bind y type, coge el de default

}
