package ast;

public abstract class Definicion implements ASTNode{ //Superclase de las definiciones (structs, funciones, variables globales)
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
