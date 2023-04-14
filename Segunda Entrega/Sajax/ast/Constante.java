package ast;

public class Constante extends Definiciones implements ASTNode{
    private Tipo tipo;
    private Ident iden;
    private E valor;

    public Constante(Tipo tipo, Ident iden, E valor){
        this.tipo = tipo;
        this.iden = iden;
        this.valor = valor;
    }

    @Override
    public NodeKind nodeKind() {
        return NodeKind.CONSTANTE;
    }

    public String toString(){
        return "constante("+tipo.toString()+","+iden.toString()+","+valor.toString()+")";
    }
}
