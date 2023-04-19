package ast;

import java.util.HashMap;

public class Struct extends Definicion implements ASTNode{
    private String name;
    private Bloque atributos;

    public Struct(String name, Bloque atributos){
        this.name = name;
        this.atributos = atributos;
    }

    @Override
    public boolean bind() {
        if(buscaId(name) != null){
            System.out.println("Error: ya existe una variable con el nombre "+name);
            return false;
        }
        else{
            insertaId(name, this);
            s.push(new HashMap<>());
            boolean aux = atributos.bind();
            s.pop();
            return aux;
        }
    }

    @Override
    public NodeKind nodeKind() {
        return NodeKind.STRUCT;
    }

    public String toString(){
        return "struct("+name+","+atributos.toString()+")";
    }
}
