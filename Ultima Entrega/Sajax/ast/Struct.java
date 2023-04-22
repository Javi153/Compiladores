package ast;

import java.util.HashMap;

public class Struct extends Definicion implements ASTNode{
    private String name;
    private Bloque<ASTNode> atributos;

    public Struct(String name, Bloque<ASTNode> atributos){
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
            for(ASTNode a : atributos.getList()){
                if(a.nodeKind() == NodeKind.DEC){
                    Dec d = (Dec)a;
                    insertaId("." + d.getName(), d);
                }
                else{
                    DecFuncion df = (DecFuncion)a;
                    insertaId("." + df.getName(), df);
                }
            }
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

    public Bloque<ASTNode> getAtt(){
        return atributos;
    }
}
