package ast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.Collectors;

public class DecArray extends Statement implements ASTNode {

    Tipo tipo;
    String iden;
    ArrayList<E> dims;

    public DecArray(Tipo tipo, String iden, ArrayList<E> dims) {
        this.tipo = tipo;
        this.iden = iden;
        this.dims = dims;
    }

    public DecArray(String name, DecArray d){
        this.iden = name;
        this.tipo = d.tipo;
        this.dims = d.dims;
    }

    @Override
    public NodeKind nodeKind() {
        return NodeKind.DECARRAY;
    }

    public String getName(){
        return iden;
    }

    @Override
    public String toString() {
        return "decArray("+tipo.toString()+","+iden+",dimensiones" +
                dims.stream().map(E::toString).collect(Collectors.joining(",", "(", ")")) + ")";
    }

    @Override
    public boolean bind() {
        boolean aux = true;
        HashMap<String, ASTNode> m = s.peek();
        if(m.containsKey(iden)){
            System.out.println("Error: variable "+iden+" ya declarada");
            aux = false;
        }
        else{
            m.put(iden, this);
        }
        for(E e : dims){
            aux = aux & e.isBound();
        }
        return aux;
    }

    @Override
    public boolean type(){
        sTipo.peek().put(iden, tipo);
        boolean aux = true;
        for(E e : dims){
            aux = aux & e.isType().getTipo().equals(TipoEnum.INT);
            if(!aux){
                String s = "Error: la dimensión de un array debe ser de tipo entero en la expresion " + tipo.getTipo().toString() + " " + iden;
                for(E e2 : dims){
                    s = s.concat("[" + e2.num() + "]");
                }
                System.out.println(s);
            }
        }
        String s = iden;
        for(int i = 0; i < dims.size(); i++){
            s = s.concat("[]");
            sTipo.peek().put(new String(s), new TipoArray(tipo, dims.size() - i - 1));
        }
        sTipo.peek().put(new String(s), tipo);
        return aux;
    }
}
