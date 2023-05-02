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
        sDelta.push(new HashMap<>());
        sDeltaCont.push(0);
        for (Statement s : stlist){
            c = c.concat(s.code());
        }
        sDeltaCont.pop();
        sDelta.pop();
        return c;
    }
}