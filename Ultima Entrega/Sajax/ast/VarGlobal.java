package ast;

public class VarGlobal extends Definicion implements ASTNode{
    private Statement var;

    public VarGlobal(Statement var){
        this.var = var;
    }

    @Override
    public boolean type() {
        return var.type();
    }

    @Override
    public boolean bind() {
        return var.bind();
    }

    //@Override
    //public String code() { return  }

    @Override
    public NodeKind nodeKind() {
        return NodeKind.GLOBVAR;
    }

    public String toString(){
            return "variableGlobal("+var.toString()+")";
    }

    @Override
    public void setDelta(int prof){
        var.setDelta(prof);
    }

    public String code(){
        return var.code();
    }

    public int size(){
        if(var.nodeKind().equals(NodeKind.DEC)){
            return ((Dec)var).size();
        }
        else{
            return ((DecArray)var).size();
        }
    }
}
