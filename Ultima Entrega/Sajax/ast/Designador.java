package ast;

public interface Designador { //Interfaz que implementan los designadores (variables, campos, funciones, operadores sobre identificadores, etc)
    public ASTNode getDef();

    public default void setDef(ASTNode def) {}

    public default String codeDesig() {return ""; };
}
