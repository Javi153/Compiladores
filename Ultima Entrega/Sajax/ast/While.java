package ast;

import java.util.HashMap;

public class While extends Statement implements ASTNode{
    private E cond;
    private BloqueIns st;

    public While(E cond, BloqueIns st){
        this.cond = cond;
        this.st = st;
    }

    @Override
    public boolean type() {
        boolean aux = cond.isType().getTipo().equals(TipoEnum.BOOL);
        if(!aux){
            System.out.println("Error: la condicion del while " + cond.num() + " no es de tipo booleano");
        }
        s.push(new HashMap<>());
        aux = aux & st.type();
        s.pop();
        return aux;
    }

    @Override
    public boolean bind() {
        boolean aux = cond.isBound();
        s.push(new HashMap<>());
        aux = aux & st.bind();
        s.pop();
        return aux;
    }

    @Override
    public String code() {
        return "block\nloop\n" + cond.code() + "\ni32.eqz\nbr_if 1\n" + st.code() + "\nbr 0\nend";
    }

    @Override
    public NodeKind nodeKind() {
        return NodeKind.WHILE;
    }

    public String toString(){
        return "while("+cond.toString()+","+st.toString()+")";
    }
}
