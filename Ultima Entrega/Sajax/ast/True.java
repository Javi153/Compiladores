package ast;

public class True extends E {

    private String v = "1";

    public True() {}
    public KindE kind(){
        return KindE.TRUE;
    }

    public String num(){
        return "true";
    }

    /*@Override
    public boolean isBound() {
        return true;
    }*/

    @Override
    public Tipo isType() {
        return new Tipo(TipoEnum.BOOL);
    }

    @Override
    public boolean type() {
        return true;
    }

    @Override
    public boolean bind() {
        return true;
    }

    @Override
    public String code() {return "i32.const " + v; }

    public String toString(){
        return "true";
    }

    @Override
    public ASTNode getDef() {
        return null;
    }
}
