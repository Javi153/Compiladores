package ast;

public class TipoStruct extends Tipo implements ASTNode, Designador{
    private String id;
    private ASTNode def;

    public TipoStruct(String s){
        super(TipoEnum.STRUCT);
        this.id = s;
        def = null;
    }

    public String getId(){
        return id;
    }

    @Override
    public boolean bind() {
        ASTNode aux = buscaId(id);
        if(aux == null){
            System.out.println("Error: tipo "+id+" no declarado");
            return false;
        }
        else if(aux.nodeKind() != NodeKind.STRUCT){
            System.out.println("Error: tipo "+id+" no es un struct");
            return false;
        }
        else{
            def = aux;
            return true;
        }
    }

    public TipoEnum getTipo(){
        return TipoEnum.STRUCT;
    }

    public String toString(){
        return id;
    }

    @Override
    public ASTNode getDef() {
        return def;
    }

    @Override
    public void setDef(ASTNode def) {
        this.def = def;
    }
}
