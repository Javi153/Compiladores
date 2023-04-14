package ast;

public class EUnar extends E {
    private E opnd;
    private KindE k;

    public EUnar(E opnd, KindE k) {
        this.opnd = opnd;
        this.k = k;
    }

    public E opnd() {
        return opnd;
    }

    public KindE kind() {
        return k;
    }

    public String toString() {
        return k.toString()+"(" + opnd().toString() + ")";
    }
}
