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
        switch(k){
            case PUNTO, FLECHA -> {
                if(opnd2.kind() == KindE.IDEN){
                    boolean res = opnd1.isBound();
                    Ident aux = (Ident)buscaId("."+opnd2.num());
                    if(aux == null){
                        System.out.println("Error: el identificador " + opnd2.num() + " no está definido en el struct");
                        return false;
                    }
                    else{
                        return res;
                    }
                }
                else if(opnd2.kind() == KindE.CORCHETES){
                    return (new EBin(opnd1, opnd2.opnd1(), KindE.PUNTO)).isBound() & opnd2.opnd2().isBound();
                }
                else{
                    System.out.println("Error: el operando derecho de un punto o flecha debe ser un identificador o un array");
                    return false;
                }
            }
            default ->{
                return opnd1.isBound() & opnd2.isBound();
            }
        }
    }

    @Override
    public boolean bind() {
        return opnd1.bind() & opnd2.bind();
    }

    public String toString() {
        return k.toString()+"(" + opnd1().toString() + "," + opnd2().toString() + ")";
    }
}