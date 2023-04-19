package ast;

import java.util.ArrayList;
import java.util.Arrays;

public class Input extends LlamadaFuncion implements ASTNode{
    private E expresion;

    public Input(E expresion){
        super("input", new ArrayList<E>(Arrays.asList(expresion)));
        this.expresion = expresion;
    }

    @Override
    public NodeKind nodeKind() {
        return NodeKind.INPUT;
    }

    @Override
    public String toString() {
        return "input("+expresion.toString()+")";
    }
}
