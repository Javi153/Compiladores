package ast;

public abstract class Statement implements ASTNode {
    //Superclase para las instrucciones
    protected int prof;
    protected int delta;
    public abstract boolean bind();

    public int getDelta() {
        return delta;
    }

    public int getProf() {
        return prof;
    }
}
