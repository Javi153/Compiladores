package ast;

import java.util.ArrayList;
import java.util.HashMap;

public class DefaultCase extends Bloque<Statement> implements ASTNode {
    protected boolean breakPresence;

    public DefaultCase(ArrayList<Statement> stlist, boolean breakPresence){
        super(stlist);
        tipoBloque = "defaultCase";
	    this.breakPresence = breakPresence;
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
    public void setDelta(int prof){
        for(Statement s : stlist){
            s.setDelta(prof);
        }
    }
}
