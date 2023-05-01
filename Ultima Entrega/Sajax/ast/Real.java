package ast;

public class Real extends E {
    private String v;
    public Real(String v) {
        this.v = v;
    }
    public String num() {return v;}
    public KindE kind() {return KindE.REAL;}

    /*@Override
    public boolean isBound() {
        return true;
    }*/

    @Override
    public Tipo isType() {
        return new Tipo(TipoEnum.FLOAT);
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
    public String code() {return "f32.const " + v; }

    public String toString() {return v;}

    @Override
    public ASTNode getDef() {
        return null;
    }
}
