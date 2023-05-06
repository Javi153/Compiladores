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
    public boolean type() {
        if(arg.isType().getTipo() == TipoEnum.PUNTERO){
            return true;
        }
        else{
            System.out.println("Error: se esperaba un puntero en free " + arg.num());
            return false;
        }
    }

    @Override
    public boolean bind() {
        return arg.bind();
    }

    @Override
    public Tipo isType() {
        return new Tipo(TipoEnum.VOID);
    }

    @Override
    public NodeKind nodeKind() {
        return NodeKind.FREE;
    }

    public String toString(){
        return "free("+arg.toString()+")";
    }

    @Override
    public ASTNode getDef() {
        return null;
    }

    public void setDelta(int prof){
        arg.setDelta(prof);
    }
}
