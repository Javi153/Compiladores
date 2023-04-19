package ast;

public class EBin extends E {
    private E opnd1;
    private E opnd2;
    private KindE k;

    public EBin(E opnd1, E opnd2, KindE k) {
        this.opnd1 = opnd1;
        this.opnd2 = opnd2;
        this.k = k;
    }

    public E opnd1() {
        return opnd1;
    }

    public E opnd2() {
        return opnd2;
    }

    public KindE kind() {
        return k;
    }

    @Override
    public boolean isBound() {
        return opnd1.isBound() && opnd2.isBound();
    }

    @Override
    public boolean bind() {
        return opnd1.bind() && opnd2.bind();
    }

    public String toString() {
        return k.toString()+"(" + opnd1().toString() + "," + opnd2().toString() + ")";
    }
}