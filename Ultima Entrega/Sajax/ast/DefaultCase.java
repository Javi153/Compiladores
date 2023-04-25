package ast;

import java.util.ArrayList;
import java.util.HashMap;

public class DefaultCase extends Bloque<Statement> implements ASTNode {

    public DefaultCase(ArrayList<Statement> stlist) {
        super(stlist);
        tipoBloque = "defaultCase";
    }

    @Override
    public boolean type(){
        boolean aux = true;
        s.push(new HashMap<>());
        for(Statement s : stlist){
            aux = aux & s.type();
        }
        s.pop();
        return aux;
    }

    @Override
    public boolean bind() {
        boolean aux = true;
        s.push(new HashMap<>());
        for(Statement s : stlist){
            aux = aux & s.bind();
        }
        s.pop();
        return aux;
    }

    @Override
    public NodeKind nodeKind() {
        return NodeKind.DEFCASE;
    }
}
