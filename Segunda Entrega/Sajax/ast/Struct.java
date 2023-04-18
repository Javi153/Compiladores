package ast;

public class Struct extends Definicion implements ASTNode{
    private String name;
    private Bloque atributos;

    public Struct(String name, Bloque atributos){
        this.name = name;
        this.atributos = atributos;
    }

    @Override
    public NodeKind nodeKind() {
        return NodeKind.STRUCT;
    }

    public String toString(){
        return "struct("+name+","+atributos.toString()+")";
    }
}
