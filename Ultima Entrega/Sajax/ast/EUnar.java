package ast;

public class EUnar extends E {
    private E opnd;
    private KindE k;

    public String num(){
        String simbolo = "";
        switch(k){

            case NOT -> {
                simbolo = "!";
            }
            case ASTERISCO -> {
                simbolo = "*";
            }
        }
        return simbolo + opnd.num();
    }

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

    @Override
    public boolean isBound() {
        return opnd.isBound();
    }

    @Override
    public Tipo isType() {
        return opnd.isType();
    }

    @Override
    public boolean type() {
        return opnd.type();
    }

    @Override
    public boolean bind() {
        return opnd.bind();
    }

    public String toString() {
        return k.toString()+"(" + opnd().toString() + ")";
    }
}
