package ast;

import java.util.ArrayList;
import java.util.Arrays;

public class Memspace extends LlamadaFuncion implements ASTNode{
    private E arg;

    public Memspace(E arg){
        super("memspace", new ArrayList<E>(Arrays.asList(arg)));
        this.arg = arg;
    }

    @Override
    public NodeKind nodeKind() {
        return NodeKind.MEMSPACE;
    }

    public String toString(){
        return "memspace("+arg.toString()+")";
    }
}
