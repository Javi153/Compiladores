package ast;

import java.util.ArrayList;
import java.util.Arrays;

public class Print extends LlamadaFuncion implements ASTNode{
    private E expresion;
    private boolean ln;
    private Tipo tipo;

    public Print(E expresion, boolean ln){
        super("print", new ArrayList<E>(Arrays.asList(expresion)));
        this.expresion = expresion;
        this.ln = ln;
    }

    @Override
    public boolean bind() {
        return expresion.bind();
    }

    @Override
    public NodeKind nodeKind() {
        return NodeKind.PRINT;
    }

    @Override
    public boolean type() {
        boolean aux = expresion.type();
        Tipo tipaux = expresion.isType();
        if(!aux || (!tipaux.getTipo().equals(TipoEnum.INT) && !tipaux.getTipo().equals(TipoEnum.FLOAT) && !tipaux.getTipo().equals(TipoEnum.BOOL))){
            System.out.println("Error: print/println solo acepta enteros, floats y bools en la expresion " + expresion.num());
            return false;
        }
        tipo = tipaux;
        return true;
    }

    @Override
    public Tipo isType() {
        return new Tipo(TipoEnum.VOID);
    }

    @Override
    public String toString() {
        if(ln){
            return "println("+expresion.toString()+")";
        }
        else{
            return "print("+expresion.toString()+")";
        }
    }

    @Override
    public String code(){
        String s = expresion.code() + "\n";
        if(tipo.getTipo().equals(TipoEnum.INT) || tipo.getTipo().equals(TipoEnum.BOOL)) {
            s = s.concat("call $print\n");
        }
        else{
            s = s.concat("call $print2\n");
        }
        return s;
    }
}
