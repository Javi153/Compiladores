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
    public boolean bind() {
        return arg.isBound();
    }

    @Override
    public NodeKind nodeKind() {
        return NodeKind.MEMSPACE;
    }

    public String toString(){
        return "memspace("+arg.toString()+")";
    }

    @Override
    public boolean type() {
        if(arg.isType().getTipo() == TipoEnum.INT){
            return true;
        }
        else{
            System.out.println("Error: se esperaba un entero en memspace " + arg.num());
            return false;
        }
    }

    @Override
    public Tipo isType() {
        return new Tipo(TipoEnum.PUNTERO);
    }
}
