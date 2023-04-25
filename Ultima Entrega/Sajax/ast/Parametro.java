package ast;

public class Parametro implements ASTNode{
    private String name;
    private Tipo tipo;
    private boolean ref;

    public Parametro(Tipo tipo, boolean ref, String name){
        this.tipo = tipo;
        this.name = name;
        this.ref = ref;
    }

    @Override
    public boolean bind() {
        if(s.peek().containsKey(name)){
            System.out.println("Nombre de parámetro duplicado: " + name);
            return false;
        }
        else{
            insertaId(name, this);
            return true;
        }
    }

    @Override
    public NodeKind nodeKind() {
        return NodeKind.PARAM;
    }

    public String toString(){
        return "param("+tipo.toString()+","+ (ref ? "referencia" : "valor") +","+name+")";
    }
}
