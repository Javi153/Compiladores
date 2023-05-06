package ast;

public abstract class E extends Statement implements ASTNode, Designador {
    private Tipo tipo;
    public abstract KindE kind();

    public abstract Tipo isType();
    public E opnd1() {throw new UnsupportedOperationException("opnd1");} 
    public E opnd2() {throw new UnsupportedOperationException("opnd2");} 
    public String num() {throw new UnsupportedOperationException("num");}
    public NodeKind nodeKind() {return NodeKind.EXPRESSION;}
    public String toString() {return "";}

    public void setDelta(int prof) {
        this.prof = prof;
    }

}
