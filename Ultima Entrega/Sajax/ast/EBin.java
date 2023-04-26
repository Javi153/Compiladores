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
        boolean aux = true;
        switch (k){

            case SUMA, RESTA, MUL, DIV, MOD, POT -> {
                aux = opnd1.type() & opnd2.type();
                aux = aux & (opnd1.isType().getTipo().equals(TipoEnum.INT) & opnd2.isType().getTipo().equals(TipoEnum.INT))
                        || (opnd1.isType().getTipo().equals(TipoEnum.FLOAT) & opnd2.isType().getTipo().equals(TipoEnum.FLOAT));
                if(!aux){
                    System.out.println("Error: se esperaba un tipo entero o float en la operacion " + num() + " pero se encontro " + opnd1.isType().getTipo() + " y " + opnd2.isType().getTipo());
                }
            }
            case OR, AND -> {
                aux = opnd1.type() & opnd2.type() & opnd1.isType().getTipo().equals(TipoEnum.BOOL) & opnd2.isType().getTipo().equals(TipoEnum.BOOL);
                if(!aux){
                    System.out.println("Error: se esperaba un tipo booleano en la operacion " + num() + " pero se encontro " + opnd1.isType().getTipo() + " y " + opnd2.isType().getTipo());
                }
            }
            case MENOR, MAYOR, MENIGUAL, MAYIGUAL, ID, DISTINTO -> {
                aux = opnd1.type() & opnd2.type();
                aux = aux & (opnd1.isType().getTipo().equals(TipoEnum.INT) & opnd2.isType().getTipo().equals(TipoEnum.INT))
                        || (opnd1.isType().getTipo().equals(TipoEnum.FLOAT) & opnd2.isType().getTipo().equals(TipoEnum.FLOAT))
                        || (opnd1.isType().getTipo().equals(TipoEnum.BOOL) & opnd2.isType().getTipo().equals(TipoEnum.BOOL));
                if(!aux){
                    System.out.println("Error: se esperaba un tipo entero, float o booleano en la operacion " + num() + " pero se encontro " + opnd1.isType().getTipo() + " y " + opnd2.isType().getTipo());
                }
            }
            case PUNTO -> {
                Tipo t = buscaTipo(opnd1.num());
                if(t == null || t.getTipo() != TipoEnum.STRUCT){
                    System.out.println("Error: el operando izquierdo de un punto debe ser un struct");
                    return false;
                }
                String auxname = ((TipoStruct)t).getId();
                if(opnd2.kind().equals(KindE.CALLFUN)){
                    LlamadaFuncion f = new LlamadaFuncion(auxname + "." + ((LlamadaFuncion)opnd2).getName(), ((LlamadaFuncion)opnd2).getParlist());
                    return f.type();
                }
                else if(opnd2.kind().equals(KindE.CORCHETES)){
                    //Tipo = buscaTipo(auxname + "." + )
                }
                else{
                    Ident Id = new Ident(auxname + "." + ((Ident)opnd2).num());
                    return Id.type();
                }
            }
            case FLECHA -> {
                Tipo t = buscaTipo(opnd1.num());
                if(t == null || t.getTipo() != TipoEnum.PUNTERO || ((Puntero)t).getTipoPointer().getTipo() != TipoEnum.STRUCT) {
                    System.out.println("Error: el operando izquierdo de una flecha debe ser un puntero a struct en " + num());
                    return false;
                }
                String auxname = ((TipoStruct)((Puntero)t).getTipoPointer()).getId();
                if(opnd2.kind().equals(KindE.CALLFUN)){
                    LlamadaFuncion f = new LlamadaFuncion(auxname + "." + ((LlamadaFuncion)opnd2).getName(), ((LlamadaFuncion)opnd2).getParlist());
                    return f.type();
                }
                else{
                    return opnd2.type();
                }
            }
            case CORCHETES -> {

            }
        }
        return aux;
    }

    @Override
    public Tipo isType(){
        boolean aux = true;
        switch(k){
            case SUMA, RESTA, MUL, DIV, MOD, POT, MENOR, MAYOR, MENIGUAL, MAYIGUAL, ID, DISTINTO -> {
                return opnd1.isType();
            }
            case OR, AND -> {
                return new Tipo(TipoEnum.BOOL);
            }
            //TODO AUN NO ESTA TERMINADO, REVISAR TIPOS PARA STRUCTS
            case PUNTO -> {
                Tipo t = buscaTipo(opnd1.num());
                if(t == null || t.getTipo() != TipoEnum.STRUCT){
                    return new Tipo(TipoEnum.VOID);
                }
                String auxname = ((TipoStruct)t).getId();
                if(opnd2.kind().equals(KindE.CALLFUN)){
                    LlamadaFuncion f = new LlamadaFuncion(auxname + "." + ((LlamadaFuncion)opnd2).getName(), ((LlamadaFuncion)opnd2).getParlist());
                    Tipo taux = buscaTipo(f.getName());
                    if(taux != null){
                        return taux;
                    }
                    else{
                        return new Tipo(TipoEnum.VOID);
                    }
                }
                else{
                    Tipo taux = buscaTipo(auxname + "." + opnd2.num());
                    if(taux != null){
                        return taux;
                    }
                    else{
                        return new Tipo(TipoEnum.VOID);
                    }
                }
            }
            case FLECHA -> {
                Tipo t = buscaTipo(opnd1.num());
                if(t == null || t.getTipo() != TipoEnum.PUNTERO || ((Puntero)t).getTipoPointer().getTipo() != TipoEnum.STRUCT){
                    return new Tipo(TipoEnum.VOID);
                }
                String auxname = ((TipoStruct)((Puntero)t).getTipoPointer()).getId();
                if(opnd2.kind().equals(KindE.CALLFUN)){
                    LlamadaFuncion f = new LlamadaFuncion(auxname + "." + ((LlamadaFuncion)opnd2).getName(), ((LlamadaFuncion)opnd2).getParlist());
                    Tipo taux = buscaTipo(f.getName());
                    if(taux != null){
                        return taux;
                    }
                    else{
                        return new Tipo(TipoEnum.VOID);
                    }
                }
                else{
                    return buscaTipo(auxname + "." + opnd2.num());
                }
            }
            case CORCHETES -> {
                if(opnd2.isType().getTipo() != TipoEnum.INT){
                    System.out.println("Error: el acceso a un array debe hacerse con una expresión de tipo int");
                }
                return buscaTipo(num());
            }
        }
        //TODO QUITA ESTO DE AQUI ERA SOLO PARA QUE NO SALIESE EL ERROR
        return new Tipo(TipoEnum.VOID);
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