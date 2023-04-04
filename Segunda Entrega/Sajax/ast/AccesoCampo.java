package ast;

public class AccesoCampo extends E implements ASTNode {

    private String iden;
    private String campo;

    public AccesoCampo(String iden, String campo) {
        this.iden = iden;
        this.campo = campo;
    }

    @Override
    public KindE kind() { return KindE.PUNTO; }
    public NodeKind nodeKind() { return NodeKind.ACCESOCAMPO; }
    public String toString() {
        return "accesoACampo(" + iden + "," + campo + ")";
    }
}
