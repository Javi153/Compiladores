package ast;

public class Asign extends Statement implements ASTNode {
    private E designador;
    private E expresion;
    private Tipo tipoAsig;

    public Asign(E designador, E expresion) {
        this.designador = designador;
        this.expresion = expresion;
    }

    @Override
    public boolean type() {
        tipoAsig = designador.isType();
        if(designador.isType().getTipo().equals(TipoEnum.ARRAY)){ //No permitimos copia de arrays
            System.out.println("Error: No se puede asignar a un array");
            return false;
        }
        if(designador.getDef() != null && designador.getDef().nodeKind().equals(NodeKind.FUNCIONDEC)){ //No puede haber una llamada a funcion a la izquierda de la asignacion
            System.out.println("Error: No se puede asignar valores a una funcion");
            return false;
        }
        boolean aux = expresion.type() & designador.type(); //Comprobamos que los tipos de ambos terminos son correctos con su definicion
        if(designador.isType().isPointer() && expresion.kind() == KindE.NULL){ //Null puede tiparse con cualquier clase de puntero
            return true;
        }
        if(designador.isType().isPointer() && expresion.nodeKind() == NodeKind.MEMSPACE){ //Tambien memspace puede adaptarse a cualquier tipo de puntero
            ((Memspace) expresion).setTipoDatos(((Puntero) designador.isType()).getTipoPointer()); //Elegimos el tipo especifico de memspace en esta asignacion
            return true;
        }
        if(designador.isType().isPointer() && expresion.isType().isPointer()){
            Tipo t1 = designador.isType(); //Si ambos son punteros comprobamos el tipo al que apuntan
            Tipo t2 = expresion.isType();
            while(t1.isPointer() && t2.isPointer()){ //Puede haber punteros multiples, aunque en codigo esto no se reflejara
                t1 = ((Puntero)t1).getTipoPointer();
                t2 = ((Puntero)t2).getTipoPointer();
            }
            if(!t1.getTipo().equals(t2.getTipo())){
                aux = false;
                System.out.println("Error: Los punteros " + designador.num() + " y " + expresion.num() + " son de tipos distintos");
            }
        }
        else if(!designador.isType().getTipo().equals(expresion.isType().getTipo())){ //Si no son punteros comprobamos la igualdad de tipos normales
            aux = false;
            System.out.println("Error: Se esperaba tipo " + designador.isType().toString() + " pero se ha recibido tipo " + expresion.isType().toString() + " en la expresion " + expresion.num());
        }
        return aux;
    }

    @Override
    public boolean bind() {
        return designador.bind() & expresion.bind();
    } //La vinculacion es simplemente la vinculacion de cada uno de los terminos

    @Override
    public String code() {
        /*
        switch(designador.getDef().nodeKind()) { //Comprobamos la clase del nodo de la definicion del termino a la izquierda del igual
            case DEC -> {
                TipoEnum t = ((Dec) designador.getDef()).getTipo().getTipo();
                switch (t) { //Para las declaraciones tomamos la direcion del termino izquierdo con codeDesig y el valor del termino derecho con code
                    case INT, BOOL, FLOAT -> {return designador.codeDesig() + "\n" + expresion.code() + "\n" + t.alias() + ".store\n"; }
                    case STRUCT ->{
                        int s = ((Dec) designador.getDef()).getTipo().size();
                        return expresion.codeDesig() + "\n" + designador.codeDesig() + "\ni32.const " + s/4 + "\n call $copyn\n";
                    }
                    case PUNTERO -> {
                        Tipo tBas = ((Puntero) ((Dec) designador.getDef()).getTipo()).getTipoPointer();
                        return designador.codeDesig() + "\n" + expresion.code() + "\n" + tBas.getTipo().alias() + ".store\n";
                        //return designador.codeDesig() + "\n" + expresion.code() + "\n" + t.alias() + ".store\n";
                    }
                } //En cada caso hacemos un casting a la clase correspondiente para obtener el tipo de la declaracion
            }
            case DECARRAY -> {
                TipoEnum t = ((DecArray) designador.getDef()).getTipo().getTipo();
                switch (t) {
                    case INT, BOOL, FLOAT -> {return designador.codeDesig() + "\n" + expresion.code() + "\n" + t.alias() + ".store\n"; }
                    case STRUCT ->{
                        int s = ((DecArray) designador.getDef()).getTipo().size();
                        return expresion.codeDesig() + "\n" + designador.codeDesig() + "\ni32.const " + s/4 + "\n call $copyn\n";
                    }
                    case PUNTERO -> {
                        Tipo tBas = ((Puntero) ((DecArray) designador.getDef()).getTipo()).getTipoPointer();
                        return designador.codeDesig() + "\n" + expresion.code() + "\n" + tBas.getTipo().alias() + ".store\n";
                    }
                }
            }
            case PARAM -> { //Puede que la declaracion este en un parametro de una funcion, en ese caso el tipo sera TipoParam
                TipoEnum t = ((TipoParam)((Parametro) designador.getDef()).getTipo()).getTipoParam().getTipo();
                switch (t) {
                    case INT, BOOL, FLOAT -> {return designador.codeDesig() + "\n" + expresion.code() + "\n" + t.alias() + ".store\n"; }
                    case STRUCT ->{
                        int s = ((TipoParam)((Parametro) designador.getDef()).getTipo()).getTipoParam().size();
                        return expresion.codeDesig() + "\n" + designador.codeDesig() + "\ni32.const " + s/4 + "\n call $copyn\n";
                    }
                    case PUNTERO -> {
                        Tipo tBas = ((Puntero)((TipoParam)((Parametro) designador.getDef()).getTipo()).getTipoParam()).getTipoPointer();
                        return designador.codeDesig() + "\n" + expresion.code() + "\n" + tBas.getTipo().alias() + ".store\n";
                    }
                }
            }
            default -> {}
        }
        */

        if (tipoAsig.getTipo().equals(TipoEnum.STRUCT)) {
            int s = tipoAsig.size();
            return expresion.codeDesig() + "\n" + designador.codeDesig() + "\ni32.const " + s/4 + "\n call $copyn\n";
        }
        else { // Tipo INT, BOOL, FLOAT, PUNTERO
            return designador.codeDesig() + "\n" + expresion.code() + "\n" + tipoAsig.getTipo().alias() + ".store\n";
        }
    }

    @Override
    public NodeKind nodeKind() {
        return NodeKind.ASIGN;
    }

    @Override
    public String toString() {
            return "asign("+designador.toString()+","+expresion.toString()+")";
    }

    public void setDelta(int prof){ //Elegimos la profundidad y asignamos los deltas a los terminos, principalmente para la profundidad de los mismos, pues no son declaraciones y no tendran deltas
        this.prof = prof;
        designador.setDelta(prof);
        expresion.setDelta(prof);
    }
}
