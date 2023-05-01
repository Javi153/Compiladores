package ast;

public class False extends E {
    private String v = "0";

    public False() {}
    public KindE kind(){
        return KindE.FALSE;
    }

    public String num(){
        return "false";
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
        return "false";
    }

    @Override
    public ASTNode getDef() {
        return null;
    }
}
