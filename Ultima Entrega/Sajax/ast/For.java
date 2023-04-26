package ast;

import java.util.HashMap;

public class For extends Statement implements ASTNode{
    private Dec inicio;
    private E fin, paso;
    private BloqueIns st;

    public For(Dec inicio, E fin, BloqueIns st){
        this.inicio = inicio;
        this.fin = fin;
        this.paso = null;
        this.st = st;
    }

    public For(Dec inicio, E fin, E paso, BloqueIns st){
        this.inicio = inicio;
        this.fin = fin;
        this.paso = paso;
        this.st = st;
    }

    @Override
    public boolean type() {
        s.push(new HashMap<>());
        boolean aux = inicio.type();
        if(!fin.isType().getTipo().equals(TipoEnum.INT)){
            aux = false;
            System.out.println("Error: la expresion del for " + fin.num() + " no es de tipo entero");
        }
        if(paso != null){
            if(!paso.isType().getTipo().equals(TipoEnum.INT)){
                aux = false;
                System.out.println("Error: la expresion del for " + paso.num() + " no es de tipo entero");
            }
        }
        aux = aux & st.type();
        s.pop();
        return aux;
    }

    @Override
    public boolean bind() {
        boolean aux = true;
        s.push(new HashMap<>());
        aux = aux & inicio.bind() & fin.isBound();
        if(paso != null){
            aux = aux & paso.isBound();
        }
        aux = aux & st.bind();
        s.pop();
        return aux;
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
