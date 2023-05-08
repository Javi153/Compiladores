package ast;

import java.util.ArrayList;
import java.util.Arrays;

public class Input extends LlamadaFuncion implements ASTNode{
    private E expresion;
    private Tipo tipo;

    public Input(E expresion){
        super("input", new ArrayList<E>(Arrays.asList(expresion)));
        this.expresion = expresion;
    }

    @Override
    public boolean type() { //No veo como tipar esto sin strings
        boolean aux = expresion.type();
        Tipo tipaux = expresion.isType();
        if(!aux || (!tipaux.getTipo().equals(TipoEnum.INT) && !tipaux.getTipo().equals(TipoEnum.FLOAT))){
            System.out.println("Error: input solo acepta enteros y floats en la expresion " + expresion.num());
            return false;
        }
        tipo = tipaux;
        return true;
    }

    @Override
    public boolean bind() {
        return expresion.bind();
    }

    @Override
    public Tipo isType() { //No veo como tipar esto sin strings
        return new Tipo(TipoEnum.VOID);
    }

    @Override
    public NodeKind nodeKind() {
        return NodeKind.INPUT;
    }

    @Override
    public String toString() {
        return "input("+expresion.toString()+")";
    }

    @Override
    public ASTNode getDef() {
        return null;
    }

    @Override
    public String code() {
        if(tipo.getTipo().equals(TipoEnum.INT) || tipo.getTipo().equals(TipoEnum.BOOL)){
            return expresion.codeDesig() + "\n call $read\ni32.store\n";
        }
        else{
            return expresion.codeDesig() + "\n call $read2\nf32.store\n";
        }
    }
}
