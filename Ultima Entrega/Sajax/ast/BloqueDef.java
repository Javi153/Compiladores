package ast;

import java.util.ArrayList;

public class BloqueDef extends Bloque<Definicion> implements ASTNode{
    private int size;
    public BloqueDef(ArrayList<Definicion> stlist) {
        super(stlist);
        super.tipoBloque = "bloqueDefs";
    }

    @Override
    public boolean type() {
        boolean aux = true;
        for(Definicion d : stlist){
            aux = aux & d.type();
        }
        return aux;
    }

    @Override
    public void setDelta(int prof){
        for(Definicion d : stlist){
            d.setDelta(prof);
        }
    }

    @Override
    public boolean bind(){
        boolean aux = true;
        for(Definicion d : stlist){
            aux = aux & d.bind();
        }
        return aux;
    }

    @Override
    public String code() {
        size = 0;
        String c = "";
        for (Definicion d : stlist) {
            if(d.nodeKind().equals(NodeKind.FUNCIONDEC)) {
                c = c.concat(d.code());
            }
            else if(!d.nodeKind().equals(NodeKind.STRUCT)){
                size += ((VarGlobal)d).size();
            }
        }
        c = "(func $init\n";
        c = c.concat("i32.const " + size + "\n");
        c = c.concat("set_global $SP\n");
        for (Definicion d : stlist) {
            if(d.nodeKind().equals(NodeKind.GLOBVAR)) {
                c = c.concat(d.code());
            }
        }
        c = c.concat("call $main\ni32.const 0\nset_global $SP\n)\n");
        return c;
    }
}
