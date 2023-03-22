package ast;

public class Ident extends E {
    private String v;
    public Ident(String v) {
        this.v = v;
    }
    public String num() {return v;}
    public KindE kind() {return KindE.IDEN;}
    public String toString() {return v;}
}