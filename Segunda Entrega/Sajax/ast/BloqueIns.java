package ast;

import java.util.ArrayList;

public class BloqueIns extends Bloque implements ASTNode{
    public BloqueIns(ArrayList<Statement> stlist) {
        super(stlist);
        super.tipoBloque = "bloqueInstrucciones";
    }
}
