package ast;

public class Puntero extends Tipo implements ASTNode{
    private Tipo t;

    public Puntero(Tipo t){
        super(t.getTipo());
        this.t = t;
    }

    @Override
    public boolean bind() {
        if(t.getTipo() == TipoEnum.STRUCT){
            return buscaId(((TipoStruct)t).getId()) != null;
        }
        else{
            return true;
        }
    }

    public String toString(){
        return "puntero("+t.toString()+")";
    }
}
