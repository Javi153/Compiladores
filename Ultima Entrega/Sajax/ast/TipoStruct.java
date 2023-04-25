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
            return true;
        }
    }

    public TipoEnum getTipo(){
        return TipoEnum.STRUCT;
    }

    public String toString(){
        return id;
    }
}
