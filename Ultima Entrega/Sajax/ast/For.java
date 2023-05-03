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
        boolean aux;
        s.push(new HashMap<>());
        aux = inicio.bind() & fin.bind();
        if(paso != null){
            aux = aux & paso.bind();
        }
        aux = aux & st.bind();
        s.pop();
        return aux;
    }
    
    @Override
    public String code() {
        String c = "";
        sDelta.push(new HashMap<>());
        sDeltaCont.push(0);
        inicio.setDelta();

        c = c.concat (
                inicio.getExp().code() + "\n" + fin.code() + "i32.le_s\nif\n"
        );

        c = c.concat (
                "block\nloop\n(br_if 1 (i32.gt_s (" + inicio.getIden().code() + ") (" + fin.code() + ")))\n"
                        + st.code() + "\n(i32.store (" + inicio.getIden().codeDesig() + ") (i32.add (" + inicio.getIden().code() + ") ("
        );

        if (paso == null)
            c = c.concat("i32.const 1)))\nbr 0\nend\nelse\n");
        else
            c = c.concat(paso.code() + ")))\nbr 0\nend\nelse\n");

        c = c.concat (
                "block\nloop\n(br_if 1 (i32.lt_s (" + inicio.getIden().code() + ") (" + fin.code() + ")))\n"
                        + st.code() + "\n(i32.store (" + inicio.getIden().codeDesig() + ") (i32.add (" + inicio.getIden().code() + ") ("
        );

        if (paso == null)
            c = c.concat("i32.const 1)))\nbr 0\nend\nend");
        else
            c = c.concat(paso.code() + ")))\nbr 0\nend\nend");

        sDeltaCont.pop();
        sDelta.pop();
        return c;
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
