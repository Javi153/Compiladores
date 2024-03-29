package ast;

public class EUnar extends E { //Clase de expresiones formadas con un operador unario
    private E opnd;
    private KindE k;
    private Tipo tipoOp;

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

    /*@Override
    public boolean isBound() {
        return opnd.isBound();
    }*/

    @Override
    public Tipo isType() {
        switch(k){
            case ASTERISCO -> {
                if(opnd.isType().getTipo().equals(TipoEnum.PUNTERO)){ //Si el operando es un puntero, el tipo del unario sera el tipo del puntero
                    Tipo t = opnd.isType();
                    if(t.isParam()){
                        t = ((TipoParam) t).getTipoParam();
                    }
                    return ((Puntero)t).getTipoPointer();
                }
                else{
                    return new Tipo(TipoEnum.VOID);
                }
            }
            case NOT -> { //El tipo del not sera el mismo que el de su operador, que deberia ser bool
                return opnd.isType();
            }
        }
        return opnd.isType();
    }

    @Override
    public boolean type() {
        switch(k){
            case ASTERISCO -> {
                if(opnd.isType().getTipo().equals(TipoEnum.PUNTERO)){
                    tipoOp = opnd.isType();
                    if (tipoOp.isParam()) {
                        tipoOp = ((TipoParam) tipoOp).getTipoParam();
                    }
                    return opnd.type();
                }
                else{
                    System.out.println("Error: Se esperaba un puntero en la expresion " + num());
                    return false;
                }
            }
            case NOT -> {
                if(opnd.isType().getTipo().equals(TipoEnum.BOOL)){
                    tipoOp = opnd.isType();
                    if (tipoOp.isParam()) {
                        tipoOp = ((TipoParam) tipoOp).getTipoParam();
                    }
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
            case ASTERISCO -> {
                Tipo t = ((Puntero) tipoOp).getTipoPointer();
                return opnd.code() + "\n" + t.getTipo().alias() + ".load";
            }
            case NOT -> {return opnd.code() + "\ni32.eqz"; }
        }
        return null;
    }

    public String codeDesig() {
        if (k.equals(KindE.ASTERISCO)) {  // Solo se debería invocar a codeDesig en este caso (*p = exp;)
            return opnd.code();
        }
        else return "";
    }

    public String toString() {
        return k.toString()+"(" + opnd().toString() + ")";
    }

    @Override
    public ASTNode getDef() {
        return opnd.getDef();
    }

    @Override
    public void setDef(ASTNode def) {
        opnd.setDef(def);
    }

    public void setDelta(int prof){
        this.prof = prof;
        opnd.setDelta(prof);
    }
}
