package ast;

public class For extends Statement implements ASTNode{
    private Dec inicio;
    private E fin, paso;
    private Bloque st;

    public For(Dec inicio, E fin, Bloque st){
        this.inicio = inicio;
        this.fin = fin;
        this.paso = null;
        this.st = st;
    }

    public For(Dec inicio, E fin, E paso, Bloque st){
        this.inicio = inicio;
        this.fin = fin;
        this.paso = paso;
        this.st = st;
    }

    @Override
    public NodeKind nodeKind() {
        return NodeKind.FOR;
    }

    public String toString(){
        if(paso == null){
            return "for(" + inicio.toString() + "," + fin.toString() + "," + st.toString() + ")";
        }
        else {
            return "for(" + inicio.toString() + "," + fin.toString() + "," + paso.toString() + "," + st.toString() + ")";
        }
    }
}
