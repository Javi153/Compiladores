package ast;

public class VarGlobal extends Definiciones implements ASTNode{
    private Tipo tipo;
    private Ident iden;
    private E valor;

    public VarGlobal(Tipo tipo, Ident iden, E valor){
        this.tipo = tipo;
        this.iden = iden;
        this.valor = valor;
    }

    @Override
    public NodeKind nodeKind() {
        return NodeKind.GLOBVAR;
    }

    public String toString(){
        if(valor == null){
            return "variableGlobal("+tipo.toString()+","+iden.toString()+")";
        }
        else{
            return "variableGlobal("+tipo.toString()+","+iden.toString()+","+valor.toString()+")";
        }
    }
}
