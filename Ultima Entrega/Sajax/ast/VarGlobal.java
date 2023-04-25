package ast;

public class VarGlobal extends Definicion implements ASTNode{
    private Statement var;

    public VarGlobal(Statement var){
        this.var = var;
    }

    @Override
    public boolean bind() {
        return var.bind();
    }

    @Override
    public NodeKind nodeKind() {
        return NodeKind.GLOBVAR;
    }

    public String toString(){
            return "variableGlobal("+var.toString()+")";
    }
}