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
        if(exp != null) { aux = aux & exp.bind(); }
        HashMap<String, ASTNode> m = s.peek();
        if(m.containsKey(iden.toString())){
            System.out.println("Error: variable "+iden.num()+" ya declarada");
            return false;
        }
        else{
            m.put(iden.toString(), this);
            aux = aux & iden.bind();
            return aux;
        }
    }

    @Override
    public boolean type() {
        sTipo.peek().put(iden.num(), tipo);
        if(tipo.isPointer()) {
            int count = 0;
            Tipo t = tipo;
            while(t.isPointer()){
                count++;
                t = ((Puntero)t).getTipoPointer();
            }
            String s = iden.num();
            for (int i = 0; i < count; i++) {
                s = s.concat("[]");
                if (i != count - 1) {
                    sTipo.peek().put(new String(s), new TipoArray(tipo, count - i - 1));
                }
            }
            sTipo.peek().put(new String(s), t);
        }
        if(exp != null) {
            boolean aux = exp.type();
            if(tipo.isPointer() && exp.kind() == KindE.NULL){
                return true;
            }
            if(tipo.isPointer() && exp.nodeKind() == NodeKind.MEMSPACE){
                return true;
            }
            if(tipo.isPointer() && exp.isType().isPointer()){
                Tipo t1 = ((Puntero)tipo).getTipoPointer();
                Tipo t2 = ((Puntero)exp.isType()).getTipoPointer();
                while(t1.isPointer() && t2.isPointer()){
                    t1 = ((Puntero)t1).getTipoPointer();
                    t2 = ((Puntero)t2).getTipoPointer();
                }
                if(!t1.getTipo().equals(t2.getTipo())){
                    aux = false;
                    System.out.println("Error: Los punteros " + iden.num() + " y " + exp.num() + " son de tipos distintos");
                }
            }
            else if(!tipo.getTipo().equals(exp.isType().getTipo())){
                System.out.println("Error: se esperaba tipo "+tipo.getTipo().toString()+" pero se ha recibido tipo "+exp.isType().getTipo().toString() + " en la expresion " + tipo.getTipo().toString() +" " + iden.num() + " = " + exp.num());
                aux = false;
            }
            return aux;
        }
        return true;
    }

    public void setDelta() {
        sDelta.peek().put(iden.num(), sDeltaCont.peek());
        Integer cima = sDeltaCont.pop();
        sDeltaCont.push(cima + tipo.size());
    }

    public String getName(){
        return iden.num();
    }

    public Ident getIden() { return iden; }

    public Tipo getTipo() { return tipo; }
}
