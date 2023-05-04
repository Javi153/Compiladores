package ast;

public abstract class Statement implements ASTNode {
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
