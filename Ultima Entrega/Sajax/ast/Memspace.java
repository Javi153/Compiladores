package ast;

import java.util.ArrayList;
import java.util.Arrays;

public class Memspace extends LlamadaFuncion implements ASTNode{
    private E arg;
    private Tipo tipoDatos;

    public Memspace(E arg){
        super("memspace", new ArrayList<E>(Arrays.asList(arg)));
        this.arg = arg;
    }

    @Override
    public boolean bind() {
        return arg.bind();
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

    @Override
    public String code() {
        //return "get_global $NP\n" + sizeCode() + "\ncall $reserveHeap";
        return sizeCode() + "\ncall $reserveHeap\nget_global $NP"; // Le asigna al puntero la dirección de memoria de la
                                                                    // última posición del heap
    }

    private String sizeCode() {
        return "i32.const " + tipoDatos.size() + "\n" + arg.code() + "\ni32.mul";
    }

    public void setTipoDatos(Tipo t) {
        tipoDatos = t;
    }
}
