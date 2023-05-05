package ast;

import java.util.HashMap;

public class Ident extends E implements ASTNode, Designador {
    private String v;
    private ASTNode def;
    public Ident(String v) {
        this.v = v;
        this.def = null;
    }
    public String num() {return v;}
    public KindE kind() {return KindE.IDEN;}

    /*@Override
    public boolean isBound() {
        if(buscaId(v) == null){
            System.out.println("Error: el identificador " + v + " no está definido");
            return false;
        }
        else{
            return true;
        }
    }*/

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
        ASTNode aux = buscaId(v);
        if(aux == null){
            System.out.println("Error: el identificador " + v + " no está definido");
            return false;
        }
        else{
            def = aux;
            return true;
        }
    }

    @Override
    public String code() {
        String c = codeDesig();
        switch (def.nodeKind()) {
            case DEC -> {
                switch(((Dec) def).getTipo().getTipo()) {
                    case INT, BOOL -> { c = c.concat("\ni32.load"); }
                    case FLOAT -> { c = c.concat("\nf32.load"); }
                    default -> {}
                }
            }
            case DECARRAY -> {}
            case FUNCIONDEC -> {}
            case STRUCT -> {}
            case GLOBVAR -> {}
        }
        return c;
    }

    public String toString() {return v;}

    @Override
    public ASTNode getDef() {
        return def;
    }

    @Override
    public void setDef(ASTNode def) {this.def = def;}

    @Override
    public String codeDesig() {
        return "i32.const " + def.getDelta() + "\nget_local $localsStart\ni32.add";
    }
}