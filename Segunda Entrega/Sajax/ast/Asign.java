package ast;

public class Asign extends Statement implements ASTNode {
    private Tipo tipo = null;
    private Ident iden;
    private E expresion;

    public Asign(String iden, E expresion) {
        this.iden = new Ident(iden);
        this.expresion = expresion;
    }

    public Asign(Tipo tipo, String iden, E expresion){
        this.tipo = tipo;
        this.iden = new Ident(iden);
        this.expresion = expresion;
    }

    @Override
    public NodeKind nodeKind() {
        return NodeKind.ASIGN;
    }

    @Override
    public String toString() {
        if (tipo == null)
            return "asign("+iden.toString()+","+expresion.toString()+")";
        else
            return "asign("+tipo.toString()+","+iden.toString()+","+expresion.toString()+")";
    }
}
