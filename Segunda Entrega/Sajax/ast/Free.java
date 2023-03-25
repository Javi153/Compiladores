package ast;

import java.util.ArrayList;
import java.util.Arrays;

public class Free extends LlamadaFuncion implements ASTNode{
    private E arg;

    public Free(E arg){
        super("free", new ArrayList<E>(Arrays.asList(arg)));
        this.arg = arg;
    }

    @Override
    public NodeKind nodeKind() {
        return NodeKind.FREE;
    }

    public String toString(){
        return "free("+arg.toString()+")";
    }
}
