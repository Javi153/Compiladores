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
        switch(k){
            case ASTERISCO -> {
                if(opnd.isType().getTipo().equals(TipoEnum.PUNTERO)){
                    return ((Puntero)opnd.isType()).getTipoPointer();
                }
                else{
                    return new Tipo(TipoEnum.VOID);
                }
            }
            case NOT -> {
                return new Tipo(TipoEnum.BOOL);
            }
        }
        return opnd.isType();
    }

    @Override
    public boolean type() {
        switch(k){
            case ASTERISCO -> {
                if(opnd.isType().getTipo().equals(TipoEnum.PUNTERO)){
                    return opnd.type();
                }
                else{
                    System.out.println("Error: Se esperaba un puntero en la expresion " + num());
                    return false;
                }
            }
            case NOT -> {
                if(opnd.isType().getTipo().equals(TipoEnum.BOOL)){
                    return opnd.type();
                }
                else{
                    System.out.println("Error: Se esperaba una expresion booleana en la expresion " + num());
                    return false;
                }
            }
        }
        return opnd.type();
    }

    @Override
    public boolean bind() {
        return opnd.bind();
    }

    @Override
    public String code() {
        switch(k) {
            case ASTERISCO -> {}
            case NOT -> {return opnd.code() + "\ni32.eqz"; }
        }
    }

    public String toString() {
        return k.toString()+"(" + opnd().toString() + ")";
    }
}
