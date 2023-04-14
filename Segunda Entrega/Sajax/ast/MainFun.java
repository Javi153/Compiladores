package ast;

import java.util.ArrayList;

public class MainFun implements ASTNode {

    private ArrayList<Parametro> args;
    private Bloque bloque;

    public MainFun(ArrayList<Parametro> args, Bloque bloque) {
        this.args = args;
        this.bloque = bloque;
    }

    @Override
    public NodeKind nodeKind() {
        return NodeKind.MAIN;
    }
}
