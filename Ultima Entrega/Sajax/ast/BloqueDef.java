package ast;

import java.util.ArrayList;

public class BloqueDef extends Bloque<Definicion> implements ASTNode{
    public BloqueDef(ArrayList<Definicion> stlist) {
        super(stlist);
        super.tipoBloque = "bloqueDefs";
    }
}
