package ast;

public class Real extends E {
    private String v;
    public Real(String v) {
        this.v = v;
    }
    public String num() {return v;}
    public KindE kind() {return KindE.REAL;}

    @Override
    public boolean isBound() {
        return true;
    }

    @Override
    public boolean bind() {
        return true;
    }

    public String toString() {return v;}
}