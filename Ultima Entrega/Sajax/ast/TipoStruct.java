package ast;

public class TipoStruct extends Tipo implements ASTNode, Designador{
    private String id; //Identificador asociado al struct
    private ASTNode def; //Declaracion del struct


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
            def = aux; //Simplemente guardamos la declaracion del struct si existe
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

    public int size(){ //Solo
        int s = 0;
        for(ASTNode atr : ((Struct)def).getAtt().getList()){ //El tamaño del array, las funciones no ocupan espacio
            switch(atr.nodeKind()){
                case DEC:
                    s += ((Dec)atr).getTipo().size();
                    break;
                case DECARRAY:
                    s += ((DecArray)atr).size();
                    break;
                case FUNCIONDEC: //Finalmente los metodos de struct no se implmentaran a nivel de codiogo
                    break;
            }
        }
        return s;
    }

    @Override
    public void setDef(ASTNode def) {
        this.def = def;
    }
}
