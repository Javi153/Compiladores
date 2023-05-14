package ast;

public class Return extends Statement implements ASTNode{
    private E ret;
    private Tipo t;
    //Aplica todas las funciones basicas sobre ret

    public Return(E ret){
        this.ret = ret;
    }

    @Override
    public boolean type() { //Comprueba que el tipo de ret es el esperado
        boolean aux = ret.type();
        t = ret.isType();
        return aux;
    }

    public Tipo isType(){
        return ret.isType();
    }

    public Tipo getTipo(){
        return t;
    }

    @Override
    public boolean bind() {
        return ret.bind();
    }

    @Override
    public NodeKind nodeKind() {
        return NodeKind.RETURN;
    }

    public String toString(){
        return "return("+ret.toString()+")";
    }

    public String code(){
        return ret.code() + "\n";
    }
}
