package ast;

import java.util.HashMap;

public class Dec extends Statement implements ASTNode{
    private Tipo tipo;
    private Ident iden;
    private E exp;

    public Dec(Tipo tipo, String iden){
        this.tipo = tipo;
        this.iden = new Ident(iden);
        exp = null;
    }

    public Dec(Tipo tipo, String iden, E exp){
        this.tipo = tipo;
        this.iden = new Ident(iden);
        this.exp = exp;
    }

    public Dec(String name, Dec d){
        this.iden = new Ident(name);
        this.tipo = d.tipo;
        this.exp = d.exp;
    }

    @Override
    public NodeKind nodeKind() {
        return NodeKind.DEC;
    }
    @Override
    public String toString(){
        if(exp == null) {
            return "dec("+tipo.toString()+","+iden.toString()+")";
        }
        else{
            return "dec("+tipo.toString()+","+iden.toString()+","+exp.toString()+")";
        }
    }

    @Override
    public boolean bind() {
        boolean aux = tipo.bind();
        if(exp != null) { aux = exp.isBound(); }
        HashMap<String, ASTNode> m = s.peek();
        if(m.containsKey(iden.toString())){
            System.out.println("Error: variable "+iden.num()+" ya declarada");
            return false;
        }
        else{
            m.put(iden.toString(), this);
            return aux;
        }
    }

    @Override
    public boolean type() {
        sTipo.peek().put(iden.num(), tipo);
        if(exp != null) {
            boolean aux = exp.type();
            if(!tipo.getTipo().equals(exp.isType().getTipo())){
                System.out.println("Error: se esperaba tipo "+tipo.getTipo().toString()+" pero se ha recibido tipo "+exp.isType().getTipo().toString() + " en la expresion " + tipo.getTipo().toString() +" " + iden.num() + " = " + exp.num());
                aux = false;
            }
            return aux;
        }
        return true;
    }

    public String getName(){
        return iden.num();
    }
}
