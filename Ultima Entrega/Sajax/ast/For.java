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
        //sDelta.push(new HashMap<>());
        //sDeltaCont.push(0);
        //inicio.setDelta();

        // Importante:
        // Primero se hace inicio.code() para que se genere el código de la asigación del valor a la variable inicio,
        // ya que, como no es un statement como tal del bloque en el que esté, no se invoca de otra forma.

        // Luego se generan inicio.getExp().code() y fin.code() para evaluar dichas expresiones y averiguar cuál es mayor.

        c = c.concat(
                inicio.code() + "\n"
                + inicio.getExp().code() + "\n" + fin.code() + "\ni32.le_s\nif\n"
        );

        c = c.concat(
                "block\nloop\n"
                + inicio.getIden().code() + "\n" + fin.code() + "\n"
                + "i32.ge_s\nbr_if 1\n"
                + st.code() + "\n"
                + inicio.getIden().codeDesig() + "\n"
                + inicio.getIden().code() + "\n"
        );

        if (paso == null)
            c = c.concat("i32.const 1\n");
        else
            c = c.concat(paso.code() + "\n");

        c = c.concat(
                "i32.add\ni32.store\nbr 0\nend\nend\nelse\n"
        );

        c = c.concat(
                "block\nloop\n"
                        + inicio.getIden().code() + "\n" + fin.code() + "\n"
                        + "i32.le_s\nbr_if 1\n"
                        + st.code() + "\n"
                        + inicio.getIden().codeDesig() + "\n"
                        + inicio.getIden().code() + "\n"
        );

        if (paso == null)
            c = c.concat("i32.const 1\n");
        else
            c = c.concat(paso.code() + "\n");

        c = c.concat(
                "i32.add\ni32.store\nbr 0\nend\nend\nend\n"
        );
        
        //sDeltaCont.pop();
        //sDelta.pop();
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

    public void setDelta(int prof){
        inicio.setDelta(prof);
        fin.setDelta(prof);
        if(paso != null){
            paso.setDelta(prof);
        }
        st.setDelta(prof);
    }
}
