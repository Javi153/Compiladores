package ast;

public interface Designador{
    public ASTNode getDef();

    public default void setDef(ASTNode def) {}
}
