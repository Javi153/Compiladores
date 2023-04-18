package ast;

public class Dec extends Statement implements ASTNode{
    private Tipo tipo;
    private Ident iden;
    private E exp;

    public Dec(Tipo tipo, String iden){
        this.tipo = tipo;
        this.iden = new Ident(iden);
        exp = null;
    }

    public Dec(Tipo tipo, String iden, E exp){
        this.tipo = tipo;
        this.iden = new Ident(iden);
        this.exp = exp;
    }
    @Override
    public NodeKind nodeKind() {
        return NodeKind.DEC;
    }
    @Override
    public String toString(){
        if(exp == null) {
            return "dec("+tipo.toString()+","+iden.toString()+")";
        }
        else{
            return "dec("+tipo.toString()+","+iden.toString()+","+exp.toString()+")";
        }
    }
}
