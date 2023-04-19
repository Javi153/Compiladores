package ast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.Collectors;

public class DecArray extends Statement implements ASTNode {

    Tipo tipo;
    String iden;
    ArrayList<E> dims;

    public DecArray(Tipo tipo, String iden, ArrayList<E> dims) {
        this.tipo = tipo;
        this.iden = iden;
        this.dims = dims;
    }

    @Override
    public NodeKind nodeKind() {
        return NodeKind.DECARRAY;
    }

    @Override
    public String toString() {
        return "decArray("+tipo.toString()+","+iden+",dimensiones" +
                dims.stream().map(E::toString).collect(Collectors.joining(",", "(", ")")) + ")";
    }

    @Override
    public boolean bind() {
        boolean aux = true;
        HashMap<String, ASTNode> m = s.peek();
        if(m.containsKey(iden)){
            System.out.println("Error: variable "+iden+" ya declarada");
            aux = false;
        }
        else{
            m.put(iden, this);
        }
        for(E e : dims){
            aux = aux & e.isBound();
        }
        return aux;
    }
}
