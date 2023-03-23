package ast;

public class Asign extends Statement implements ASTNode{
    private TipoEnum tipo;
    private Ident iden;
    private E expresion;

    public Asign(TipoEnum tipo, String iden, E expresion){
        this.tipo = tipo;
        this.iden = new Ident(iden);
        this.expresion = expresion;
    }
    @Override
    public NodeKind nodeKind() {
        return NodeKind.ASIGN;
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
        return "asign("+stipo+","+iden.toString()+","+expresion.toString()+")";
    }
}
