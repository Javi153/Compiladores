package ast;

public class Dec extends Statement implements ASTNode{
    private TipoEnum tipo;
    private Ident iden;

    public Dec(TipoEnum tipo, String iden){
        this.tipo = tipo;
        this.iden = new Ident(iden);
    }
    @Override
    public NodeKind nodeKind() {
        return NodeKind.DEC;
    }
    @Override
    public String toString(){
        String stipo = new String();
        switch(tipo){
            case INT:
                stipo = "int";
                break;
            case BOOL:
                stipo = "bool";
                break;
            case FLOAT:
                stipo = "float";
                break;
        }
        return "dec("+stipo+","+iden.toString()+")";
    }
}
