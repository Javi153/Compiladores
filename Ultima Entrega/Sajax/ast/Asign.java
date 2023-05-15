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
        Tipo tDesig = designador.isType();
        Tipo tExp = expresion.isType();
        if (tDesig.isParam()) tDesig = ((TipoParam) tDesig).getTipoParam();
        if (tExp.isParam()) tExp = ((TipoParam) tExp).getTipoParam();
        tipoAsig = tDesig;
        if(tDesig.getTipo().equals(TipoEnum.ARRAY)){ //No permitimos copia de arrays
            System.out.println("Error: No se puede asignar a un array: " + designador.num());
            return false;
        }
        if(designador.getDef() != null && (designador.nodeKind().equals(NodeKind.FUNCIONCALL) || designador.getDef().nodeKind().equals(NodeKind.FUNCIONDEC))){ //No puede haber una llamada a funcion a la izquierda de la asignacion
            System.out.println("Error: No se puede asignar valores a una funcion: " + designador.num());
            return false;
        }
        boolean aux = expresion.type() & designador.type(); //Comprobamos que los tipos de ambos terminos son correctos con su definicion
        if(tDesig.isPointer() && expresion.kind() == KindE.NULL){ //Null puede tiparse con cualquier clase de puntero
            return true;
        }
        if(tDesig.isPointer() && expresion.nodeKind() == NodeKind.MEMSPACE){ //Tambien memspace puede adaptarse a cualquier tipo de puntero
            ((Memspace) expresion).setTipoDatos(((Puntero) tDesig).getTipoPointer()); //Elegimos el tipo especifico de memspace en esta asignacion
            return true;
        }
        if(tDesig.isPointer() && expresion.isType().isPointer()){
            Tipo t1 = tDesig; //Si ambos son punteros comprobamos el tipo al que apuntan
            Tipo t2 = tExp;
            while(t1.isPointer() && t2.isPointer()){ //Puede haber punteros multiples, aunque en codigo esto no se reflejara
                t1 = ((Puntero)t1).getTipoPointer();
                t2 = ((Puntero)t2).getTipoPointer();
            }
            if(!t1.getTipo().equals(t2.getTipo())){
                aux = false;
                System.out.println("Error: Los punteros " + designador.num() + " y " + expresion.num() + " son de tipos distintos");
            }
        }
        else if(!tDesig.getTipo().equals(tExp.getTipo())){ //Si no son punteros comprobamos la igualdad de tipos normales
            aux = false;
            System.out.println("Error: Se esperaba tipo " + tDesig.toString() + " pero se ha recibido tipo " + tExp.toString() + " en la expresion " + expresion.num());
        }
        return aux;
    }

    @Override
    public boolean bind() {
        return designador.bind() & expresion.bind();
    } //La vinculacion es simplemente la vinculacion de cada uno de los terminos

    @Override
    public String code() {
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
