package ast;

import java.util.ArrayList;

public class Programa implements ASTNode{
    private BloqueDef defs;
    private MainFun main;
    
    public Programa(BloqueDef defs, MainFun main){
        this.defs = defs;
        this.main = main;
    }

    @Override
    public boolean bind() {
        if(!(defs.bind() && main.bind())){
            System.out.println("La vinculación falló. Compilación abortada");
            return false;
        }
        return true;
    }

    public NodeKind nodeKind(){
        return NodeKind.PROGRAMA;
    }
    
    public String toString(){
        return "programa(" + defs.toString() + "," + main.toString() + ")";
    }
}
