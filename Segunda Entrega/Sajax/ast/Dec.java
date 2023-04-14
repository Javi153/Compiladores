package ast;

public class Dec extends Statement implements ASTNode{
    private Tipo tipo;
    private Ident iden;

    public Dec(Tipo tipo, String iden){
        this.tipo = tipo;
        this.iden = new Ident(iden);
    }
    @Override
    public NodeKind nodeKind() {
        return NodeKind.DEC;
    }
    @Override
    public String toString(){
        return "dec("+tipo.toString()+","+iden.toString()+")";
    }
}
