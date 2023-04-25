package ast;

import java.util.ArrayList;

public class DefaultCase extends Bloque implements ASTNode {

    protected boolean breakPresence;

    public DefaultCase(ArrayList<Statement> stlist, boolean breakPresence) {
        super(stlist);
        tipoBloque = "defaultCase";
        this.breakPresence = breakPresence;
    }

    @Override
    public String toString() {
        String s = tipoBloque + "(";
        for (int i = 0; i < stlist.size() ; ++i) {
            s = s.concat(stlist.get(i).toString() + ",");
        }
        s = s.concat(breakPresence + ")");
        return s;
    }

    @Override
    public NodeKind nodeKind() {
        return NodeKind.DEFCASE;
    }
}
