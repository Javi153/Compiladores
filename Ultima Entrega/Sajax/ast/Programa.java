package ast;

import java.util.ArrayList;
import java.util.HashMap;

public class Programa implements ASTNode{
    private BloqueDef defs;
    private MainFun main;
    
    public Programa(BloqueDef defs, MainFun main){
        this.defs = defs;
        this.main = main;
    }

    @Override
    public boolean type() {
        sTipo.push(new HashMap<>());
        if(!(defs.type() & main.type())){
            sTipo.pop();
            System.out.println("La comprobación de tipos falló. Compilación abortada");
            return false;
        }
        else{
            sTipo.pop();
            System.out.println("La comprobación de tipos fue exitosa");
            return true;
        }
    }

    @Override
    public boolean bind() {
        s.push(new HashMap<>());
        if(!(defs.bind() & main.bind())){
            s.pop();
            System.out.println("La vinculación falló. Compilación abortada");
            return false;
        }
        else{
            s.pop();
            System.out.println("La vinculación fue exitosa");
            return true;
        }
    }

    public NodeKind nodeKind(){
        return NodeKind.PROGRAMA;
    }
    
    public String toString(){
        return "programa(" + defs.toString() + "," + main.toString() + ")";
    }
}