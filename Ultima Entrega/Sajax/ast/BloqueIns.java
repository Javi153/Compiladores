package ast;

import java.util.ArrayList;
import java.util.HashMap;

public class BloqueIns extends Bloque<Statement> implements ASTNode {
    public BloqueIns(ArrayList<Statement> stlist) {
        super(stlist);
        super.tipoBloque = "bloqueInstrucciones";
    }

    @Override
    public boolean type() {
        boolean aux = true;
        for(Statement s : stlist){
            aux = aux & s.type();
        }
        return aux;
    }

    @Override
    public boolean bind(){
        boolean aux = true;
        for(Statement s : stlist){
            aux = aux & s.bind();
        }
        return aux;
    }

    @Override
    public String code() {
        String c = "";
        for (Statement s : stlist){
            c = c.concat(s.code() + "\n");
        }
        return c;
    }

    @Override
    public void setDelta(int prof){
        for(Statement s : stlist){
            s.setDelta(prof);
        }
    }
}