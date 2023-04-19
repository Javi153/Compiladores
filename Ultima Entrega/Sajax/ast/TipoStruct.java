package ast;

public class TipoStruct extends Tipo implements ASTNode{
    private String id;

    public TipoStruct(String s){
        super(TipoEnum.STRUCT);
        this.id = s;
    }

    public String getId(){
        return id;
    }

    @Override
    public boolean bind() {
        return buscaId(id) != null;
    }

    public TipoEnum getTipo(){
        return TipoEnum.STRUCT;
    }

    public String toString(){
        return id;
    }
}
