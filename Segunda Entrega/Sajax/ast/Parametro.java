package ast;

public class Parametro implements ASTNode{
    private String name;
    private Tipo tipo;

    public Parametro(Tipo tipo, String name){
        this.tipo = tipo;
        this.name = name;
    }

    @Override
    public NodeKind nodeKind() {
        return NodeKind.PARAM;
    }

    public String toString(){
        return "param("+tipo.toString()+","+name+")";
    }
}
