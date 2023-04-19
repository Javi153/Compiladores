package ast;

import java.util.ArrayList;

public class DefaultCase extends Bloque<Statement> implements ASTNode {

    public DefaultCase(ArrayList<Statement> stlist) {
        super(stlist);
        tipoBloque = "defaultCase";
    }

    @Override
    public NodeKind nodeKind() {
        return NodeKind.DEFCASE;
    }
}
