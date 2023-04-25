package ast;

import java.util.ArrayList;

public class MainFun implements ASTNode {

    // private ArrayList<Parametro> args;
    // En principio el main no tiene argumentos no?
    private Bloque bloque;

    private Return ret;

    public MainFun(/*ArrayList<Parametro> args, */ Bloque bloque, Return ret) {
        //this.args = args;
        this.bloque = bloque;
        this.ret = ret;
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
}
