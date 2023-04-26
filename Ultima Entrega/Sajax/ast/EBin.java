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

    public String num(){
        String simbolo = "";
        switch(k){
            case SUMA -> {
                simbolo = "+";
            }
            case RESTA -> {
                simbolo = "-";
            }
            case MUL -> {
                simbolo = "*";
            }
            case DIV -> {
                simbolo = "/";
            }
            case MOD -> {
                simbolo = "%";
            }
            case POT -> {
                simbolo = "^";
            }
            case DISTINTO -> {
                simbolo = "/=";
            }
            case OR -> {
                simbolo = "||";
            }
            case AND -> {
                simbolo = "&&";
            }
            case MENOR -> {
                simbolo = "<";
            }
            case MAYOR -> {
                simbolo = ">";
            }
            case MENIGUAL -> {
                simbolo = "<=";
            }
            case MAYIGUAL -> {
                simbolo = ">=";
            }
            case PUNTO -> {
                simbolo = ".";
            }
            case FLECHA -> {
                simbolo = "->";
            }
            case CORCHETES -> {
                return opnd1.num() + "[]";
            }
        }
        return opnd1.num() + simbolo + opnd2.num();
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
    public boolean type(){
        return opnd1.type() & opnd2.type();
    }

    @Override
    public Tipo isType(){
        boolean aux = true;
        switch(k){
            case SUMA, RESTA, MUL, DIV, MOD, POT -> {
                aux = opnd1.isType().getTipo().equals(TipoEnum.INT) && opnd2.isType().getTipo().equals(TipoEnum.INT);
                if(!aux){
                    System.out.println("Error: se esperaba tipo int en "+ this.num() + " pero se ha recibido tipo " + opnd1.isType().toString() + " y tipo " + opnd2.isType().toString());
                }
                return new Tipo(TipoEnum.INT);
            }
            case OR, AND -> {
                aux = opnd1.isType().getTipo().equals(TipoEnum.BOOL) && opnd2.isType().getTipo().equals(TipoEnum.BOOL);
                if(!aux){
                    System.out.println("Error: se esperaba tipo bool en la expresion " + this.num() + " pero se ha recibido tipo " + opnd1.isType().toString() + " y tipo " + opnd2.isType().toString());
                }
                return new Tipo(TipoEnum.BOOL);
            }
            case MENOR, MAYOR, MENIGUAL, MAYIGUAL, ID, DISTINTO -> {
                aux = opnd1.isType().getTipo().equals(TipoEnum.INT) && opnd2.isType().getTipo().equals(TipoEnum.INT) || opnd1.isType().getTipo().equals(TipoEnum.BOOL) && opnd2.isType().getTipo().equals(TipoEnum.BOOL);
                if(!aux){
                    System.out.println("Error: los tipos de los operandos " + opnd1.num() + " y " + opnd2.num() + " no son compatibles");
                }
                return new Tipo(TipoEnum.BOOL);
            }
            //TODO AUN NO ESTA TERMINADO, REVISAR TIPOS PARA STRUCTS
            case PUNTO -> {
                Tipo t = buscaTipo(opnd1.num());
                if(t.getTipo() != TipoEnum.STRUCT){
                    System.out.println("Error: el operando izquierdo de un punto debe ser un struct");
                }
                String auxname = ((TipoStruct)t).getId();
                if(opnd2.kind().equals(KindE.CALLFUN)){
                    return buscaTipo(auxname + "." + ((LlamadaFuncion)opnd2).getName());
                }
                else{
                    return buscaTipo(auxname + "." + opnd2.num());
                }
            }
            case FLECHA -> {
            }
            case CORCHETES -> {
                if(opnd2.isType().getTipo() != TipoEnum.INT){
                    System.out.println("Error: el acceso a un array debe hacerse con una expresión de tipo int");
                }
                return buscaTipo(num());
            }
        }
        //TODO QUITA ESTO DE AQUI ERA SOLO PARA QUE NO SALIESE EL ERROR
        return new Tipo(TipoEnum.NULL);
    }

    @Override
    public boolean isBound() {
        switch(k){
            case PUNTO, FLECHA -> {
                if(opnd2.kind().equals(KindE.CALLFUN)){
                    boolean res = opnd1.isBound();
                    return res & (new LlamadaFuncion("."+((LlamadaFuncion)opnd2).getName(), ((LlamadaFuncion)opnd2).getParlist()).isBound());

                }
                else if(opnd2.kind().equals(KindE.IDEN)){
                    boolean res = opnd1.isBound();
                    ASTNode aux = buscaId("."+opnd2.num());
                    if(aux == null){
                        System.out.println("Error: el identificador " + opnd2.num() + " no está definido en el struct " + opnd1.num());
                        return false;
                    } else{
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