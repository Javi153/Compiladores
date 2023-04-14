package ast;

public class Asign extends Statement implements ASTNode {
    private Tipo tipo = null;
    private E designador;
    private E expresion;

    public Asign(E designador, E expresion) {
        this.designador = designador;
        this.expresion = expresion;
    }

    public Asign(Tipo tipo, E designador, E expresion){
        this.tipo = tipo;
        this.designador = designador;
        this.expresion = expresion;
    }

    @Override
    public NodeKind nodeKind() {
        return NodeKind.ASIGN;
    }

    @Override
    public String toString() {
        if (tipo == null)
            return "asign("+designador.toString()+","+expresion.toString()+")";
        else
            return "asign("+tipo.toString()+","+designador.toString()+","+expresion.toString()+")";
    }
}
