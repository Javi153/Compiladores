package ast;

public class Asign extends Statement implements ASTNode {
    private E designador;
    private E expresion;

    public Asign(E designador, E expresion) {
        this.designador = designador;
        this.expresion = expresion;
    }

    @Override
    public boolean type() {
        if(designador.isType().getTipo().equals(TipoEnum.ARRAY)){
            System.out.println("Error: No se puede asignar a un array");
            return false;
        }
        boolean aux = expresion.type() & designador.type();
        if(designador.isType().isPointer() && expresion.kind() == KindE.NULL){
            return true;
        }
        if(designador.isType().isPointer() && expresion.nodeKind() == NodeKind.MEMSPACE){
            return true;
        }
        if(designador.isType().isPointer() && expresion.isType().isPointer()){
            Tipo t1 = designador.isType();
            Tipo t2 = expresion.isType();
            while(t1.isPointer() && t2.isPointer()){
                t1 = ((Puntero)t1).getTipoPointer();
                t2 = ((Puntero)t2).getTipoPointer();
            }
            if(!t1.getTipo().equals(t2.getTipo())){
                aux = false;
                System.out.println("Error: Los punteros " + designador.num() + " y " + expresion.num() + " son de tipos distintos");
            }
        }
        else if(!designador.isType().getTipo().equals(expresion.isType().getTipo())){
            aux = false;
            System.out.println("Error: Se esperaba tipo " + designador.isType().toString() + " pero se ha recibido tipo " + expresion.isType().toString() + " en la expresion " + expresion.num());
        }
        return aux;
    }

    @Override
    public boolean bind() {
        return designador.bind() & expresion.bind();
    }

    @Override
    public String code() {

        switch(designador.getDef().nodeKind()) {
            case DEC -> {
                TipoEnum t = ((Dec) designador.getDef()).getTipo().getTipo();
                switch (t) {
                    case INT, BOOL, FLOAT -> {return designador.codeDesig() + "\n" + expresion.code() + "\n" + t.alias() + ".store\n"; }
                }
            }
            case STRUCT -> {}
            default -> {}
        }

        return "";
    }

    @Override
    public NodeKind nodeKind() {
        return NodeKind.ASIGN;
    }

    @Override
    public String toString() {
            return "asign("+designador.toString()+","+expresion.toString()+")";
    }
}
