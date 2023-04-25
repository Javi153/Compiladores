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
        if(designador.isType().equals(expresion.isType())){
            return true;
        }
        else if(expresion.isType() != null){
            System.out.println("Error: Se esperaba tipo " + designador.isType().toString() + " pero se ha recibido tipo " + expresion.isType().toString());
            return true;
        }
        else{
            return false;
        }
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
