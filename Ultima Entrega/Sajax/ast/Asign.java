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
        boolean aux = designador.isType().getTipo().equals(expresion.isType().getTipo());
        if(!aux){
            System.out.println("Error: Se esperaba tipo " + designador.isType().toString() + " pero se ha recibido tipo " + expresion.isType().toString() + " en la expresion " + expresion.num());
        }
        aux = aux & designador.isType().getTipo().equals(buscaTipo(designador.num()).getTipo());
        if(!aux){
            System.out.println("Error: el designador " + designador.num() + " no coincide con el tipo de su defincion");
        }
        return aux;
    }

    @Override
    public boolean bind() {
        return designador.isBound() & expresion.isBound();
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
