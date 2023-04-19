package ast;

import java.util.ArrayList;

public class BloqueDef extends Bloque<Definicion> implements ASTNode{
    public BloqueDef(ArrayList<Definicion> stlist) {
        super(stlist);
        super.tipoBloque = "bloqueDefs";
    }

    @Override
    public boolean bind(){
        boolean aux = true;
        for(Definicion d : stlist){
            aux = aux & d.bind();
        }
        return aux;
    }
}
