package ast;

import java.util.ArrayList;

public class BloqueIns extends Bloque<Statement> implements ASTNode{
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
}