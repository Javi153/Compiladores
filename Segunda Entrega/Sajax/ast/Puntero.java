package ast;

public class Puntero extends Tipo implements ASTNode{
    private Tipo t;

    public Puntero(Tipo t){
        super(t.getTipo());
        this.t = t;
    }

    public String toString(){
        return "puntero("+t.toString()+")";
    }
}
