package ast;

import java.util.ArrayList;
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
        return "decArray("+tipo.toString()+","+iden.toString()+",dimensiones" +
                dims.stream().map(E::toString).collect(Collectors.joining(",", "(", ")")) + ")";
    }

}
