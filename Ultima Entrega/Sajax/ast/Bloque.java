package ast;

import java.util.ArrayList;

public class Bloque<T extends ASTNode> implements ASTNode {
    protected ArrayList<T> stlist;
    protected String tipoBloque;

    public Bloque(ArrayList<T> stlist){
        this.stlist = stlist;
        this.tipoBloque = "bloque";
    }

    @Override
    public boolean type() {
        boolean aux = true;
        for(T elem : stlist){
            aux = aux & elem.type();
        }
        return aux;
    }

    @Override
    public boolean bind() {
        boolean aux = true;
        for(T elem : stlist){
            aux = aux & elem.bind();
        }
        return aux;
    }

    @Override
    public NodeKind nodeKind() {
        return NodeKind.BLOQUE;
    }

    public String toString() {
        if(stlist.size() == 0) {
            return tipoBloque + "()";
        }
        else {
            String s = tipoBloque + "(";
            for (int i = 0; i < stlist.size() - 1; ++i) {
                s = s.concat(stlist.get(i).toString() + ",");
            }
            s = s.concat(stlist.get(stlist.size() - 1).toString() + ")");
            return s;
        }
    }

    public String code(){
        String s = "";
        for(T elem : stlist){
            s = s.concat(elem.code());
        }
        return s;
    }

    @Override
    public void setDelta(int prof){
        for(T elem : stlist){
            elem.setDelta(prof);
        }
    }

    public ArrayList<T> getList(){
        return stlist;
    }
}
