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

    public String toString() {
        String s = new String();
        switch(k){
            case SUMA:
                s = "suma";
                break;
            case RESTA:
                s = "resta";
                break;
            case MUL:
                s = "mult";
                break;
            case DIV:
                s = "div";
                break;
            case MOD:
                s = "mod";
                break;
            case POT:
                s = "pot";
                break;
        }
        return s+"(" + opnd1().toString() + "," + opnd2().toString() + ")";
    }
}