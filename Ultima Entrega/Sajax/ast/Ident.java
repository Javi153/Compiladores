package ast;

import java.util.HashMap;

public class Ident extends E {
    private String v;
    public Ident(String v) {
        this.v = v;
    }
    public String num() {return v;}
    public KindE kind() {return KindE.IDEN;}

    @Override
    public boolean isBound() {
        if(buscaId(v) == null){
            System.out.println("Error: el identificador " + v + " no está definido");
            return false;
        }
        else{
            return true;
        }
    }

    @Override
    public Tipo isType() { //En teoria nunca se llamará a este método, sino que se verá en la tabla cual es el tipo
        return buscaTipo(v);
    }

    @Override
    public boolean type() { //Si t0d0 sale bien, se deberían guardar los identificadores en sus definicoines
        return true;
    }

    @Override
    public boolean bind() {
        if(s.peek().containsKey(v)){
            System.out.println("Error: el identificador " + v + " ya está definido en este ámbito");
            return false;
        }
        else{
            insertaId(v, this);
            return true;
        }
    }

    public String toString() {return v;}
}