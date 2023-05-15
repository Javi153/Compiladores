package ast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.Collectors;

public class DecArray extends Statement implements ASTNode {

    Tipo tipo;
    Ident iden;
    ArrayList<Ent> dims; //Guardamos los limites de las dimensiones

    public DecArray(Tipo tipo, String iden, ArrayList<Ent> dims) {
        this.tipo = tipo;
        this.iden = new Ident(iden);
        this.dims = dims;
    }

    public DecArray(String name, DecArray d){ //Constructor de copia
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
    public boolean bind() { //metemos en la pila el identificador y esta definicion comprobamos que todas las dimensiones esten vinculadas
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
                aux = aux & e.type() && e.isType().getTipo().equals(TipoEnum.INT); //Todas las dimensiones deben ser de tipo entero
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
                sTipo.peek().put(new String(s), new TipoArray(tipo, dims.size() - i - 1)); //Añadimos iden con cada numero posible de [], cada uno como un array con una dimension menos
            }
        }
        sTipo.peek().put(new String(s), tipo); //Tambien añadimos el ultimo como el tipo basico del array
        return aux;
    }
    public void setDelta(int prof) { //Decidimos la profundidad y los deltas como en el resto de calses
        this.prof = prof;
        delta = sDeltaCont.pop();
        sDeltaCont.push(delta + size());
        iden.setDelta(prof);
    }

    public int size() { //El tamaño del array es el tamaño del tipo por cada maximo de dimension
        int s = tipo.size();
        for (Ent d : dims) {
            s = s * d.getInt();
        }
        return s;
    }

    public Tipo getTipo() {return tipo;}

    public int dimSize(){
        return dims.size();
    }

    public ArrayList<Ent> getDims(){
        return dims;
    }
}
