package ast;

import java.util.ArrayList;
import java.util.Arrays;

public class Print extends LlamadaFuncion implements ASTNode{
    private E expresion;
    private boolean ln;

    public Print(E expresion, boolean ln){
        super("print", new ArrayList<E>(Arrays.asList(expresion)));
        this.expresion = expresion;
        this.ln = ln;
    }

    @Override
    public NodeKind nodeKind() {
        return NodeKind.PRINT;
    }

    @Override
    public String toString() {
        if(ln){
            return "println("+expresion.toString()+")";
        }
        else{
            return "print("+expresion.toString()+")";
        }
    }
}
