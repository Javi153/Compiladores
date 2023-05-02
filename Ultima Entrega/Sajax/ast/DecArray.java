package ast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.Collectors;

public class DecArray extends Statement implements ASTNode {

    Tipo tipo;
    Ident iden;
    ArrayList<E> dims;

    public DecArray(Tipo tipo, String iden, ArrayList<E> dims) {
        this.tipo = tipo;
        this.iden = new Ident(iden);
        this.dims = dims;
    }

    public DecArray(String name, DecArray d){
        this.iden = new Ident(name);
        this.tipo = d.tipo;
        this.dims = d.dims;
    }

    @Override
    public NodeKind nodeKind() {
        return NodeKind.DECARRAY;
    }

    public String getName(){
        return iden.toString();
    }

    @Override
    public String toString() {
        return "decArray("+tipo.toString()+","+iden+",dimensiones" +
                dims.stream().map(E::toString).collect(Collectors.joining(",", "(", ")")) + ")";
    }

    @Override
    public boolean bind() {
        boolean aux = tipo.bind();
        HashMap<String, ASTNode> m = s.peek();
        if(m.containsKey(iden.toString())){
            System.out.println("Error: variable "+iden+" ya declarada");
            aux = false;
        }
        else{
            m.put(iden.toString(), this);
            aux = aux & iden.bind();
        }
        for(E e : dims){
            aux = aux & e.bind();
        }
        return aux;
    }

    @Override
    public boolean type(){
        sTipo.peek().put(iden.toString(), new TipoArray(tipo, dims.size()));
        boolean aux = true;
        for(E e : dims){
                aux = aux & e.type() && e.isType().getTipo().equals(TipoEnum.INT);
                if (!aux) {
                    String s = "Error: la dimensión de un array debe ser de tipo entero en la expresion " + tipo.getTipo().toString() + " " + iden;
                    for (E e2 : dims) {
                        s = s.concat("[" + e2.num() + "]");
                    }
                    System.out.println(s);
                }
        }
        String s = iden.toString();
        for(int i = 0; i < dims.size(); i++){
            s = s.concat("[]");
            if(i != dims.size() - 1) {
                sTipo.peek().put(new String(s), new TipoArray(tipo, dims.size() - i - 1));
            }
        }
        sTipo.peek().put(new String(s), tipo);
        return aux;
    }

    @Override
    public void setDelta() {
        sDelta.peek().put(iden.num(), sDeltaCont.peek());
        Integer cima = sDeltaCont.pop();
        sDeltaCont.push(cima + size());
    }

    public int size() {
        int s = tipo.size();
        // TODO malasunto no sé evaluar las dims
        // Habría que ir multiplicando s por cada dim
        return s;
    }

    public Tipo getTipo() {return tipo;}
}
