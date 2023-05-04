package ast;

import java.util.ArrayList;

public class MainFun implements ASTNode {

    // private ArrayList<Parametro> args;
    // En principio el main no tiene argumentos no?
    private BloqueIns bloque;

    private Return ret;

    public MainFun(/*ArrayList<Parametro> args, */ BloqueIns bloque, Return ret) {
        //this.args = args;
        this.bloque = bloque;
        this.ret = ret;
    }

    @Override
    public boolean type() {
        boolean aux =  bloque.type();
        if(!ret.getTipo().getTipo().equals(TipoEnum.INT)){
            aux = false;
            System.out.println("Error: El tipo de retorno de la funcion main debe ser de tipo int");
        }
        return aux;
    }

    @Override
    public boolean bind() {
        return bloque.bind() & ret.bind();
    }

    @Override
    public NodeKind nodeKind() {
        return NodeKind.MAIN;
    }

    public String toString(){
        String s;
        s = "main(";
        /*if(!args.isEmpty()) {
            s = s.concat(args.get(0).toString());
            for (int i = 1; i < args.size(); ++i) {
                s = s.concat("," + args.get(i).toString());
            }
        }*/
        s = s.concat(bloque.toString() + "," + ret.toString());
        s = s.concat(")");
        return s;
    }

    @Override
    public void setDelta(){
        bloque.setDelta();
        ret.setDelta();
    }

    @Override
    public String code(){
        String s = bloque.code();
        s = s.concat(ret.code());
        return s;
    }
}
