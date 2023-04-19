package ast;

public class TipoStruct extends Tipo implements ASTNode{
    private String id;

    public TipoStruct(String s){
        super(TipoEnum.STRUCT);
        this.id = s;
    }

    public TipoEnum getTipo(){
        return TipoEnum.STRUCT;
    }

    public String toString(){
        return id;
    }
}
