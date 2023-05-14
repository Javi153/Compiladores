package ast;

import java.util.HashMap;

public class Struct extends Definicion implements ASTNode{
    private String name;
    private Bloque<ASTNode> atributos;

    public Struct(String name, Bloque<ASTNode> atributos){
        this.name = name;
        this.atributos = atributos;
    }

    public Struct(String name, Struct s){ //Constructor de copia
        this.name = name;
        this.atributos = s.getAtt();
    }

    @Override
    public boolean type() {
        boolean aux = true;
        sTipo.peek().put(name, new TipoStruct(name)); //Guardamos el tipo del struct con su nombre
        for(ASTNode n : atributos.getList()){
            switch(n.nodeKind()){
                case DEC -> {
                    Dec d = (Dec)n;
                    aux = aux & (new Dec(name + "." + d.getName(), d)).type(); //Para reconocer que el atributo forma parte de este strutc, lo guardaremos como nombrestruct.nombreatrib
                }                                                                   //De esta manera podemos buscarlo independientemente del operando izquierdo
                case DECARRAY -> {
                    DecArray d = (DecArray)n;
                    aux = aux & (new DecArray(name + "." + d.getName(), d)).type();
                }
                case FUNCIONDEC -> {
                    DecFuncion d = (DecFuncion) n;
                    aux = aux & (new DecFuncion(name + "." + d.getName(), d)).type();
                }
            }
        }
        return aux;
    }

    @Override
    public boolean bind() {
        if(buscaId(name) != null){ //Comprobamos que no hay un struct con el mismo nombre
            System.out.println("Error: ya existe una variable con el nombre "+name);
            return false;
        }
        else{
            insertaId(name, this); //Asociamos el struct a este nodo
            s.push(new HashMap<>());
            boolean aux = atributos.bind(); //Creamos los nodos de asociacion de los atributos
            s.pop();
            for(ASTNode a : atributos.getList()){ //Para hacer el binding, guardaremos estos atributos como .nombreatrib, paara comprobar que al menos existe un atributo de algun struct con ese nombre
                if(a.nodeKind() == NodeKind.DEC){
                    Dec d = (Dec)a;
                    insertaId("." + d.getName(), d);
                } else if (a.nodeKind() == NodeKind.DECARRAY) {
                    DecArray d = (DecArray)a;
                    insertaId("." + d.getName(), d);
                } else{
                    DecFuncion df = (DecFuncion)a;
                    insertaId("." + df.getName(), df);
                }
            }
            return aux;
        }
    }

    @Override
    public void setDelta(int prof) {
        this.prof = prof;
    }

    @Override
    public NodeKind nodeKind() {
        return NodeKind.STRUCT;
    }

    public String toString(){
        return "struct("+name+","+atributos.toString()+")";
    }

    public Bloque<ASTNode> getAtt(){
        return atributos;
    }

    public int size(){ //Calculamos el tamaño en bytes que necesitas para guardar todos los atributos del struct
        int s = 0;
        for(ASTNode atr : atributos.getList()){
            if(atr.nodeKind() == NodeKind.DECARRAY)
                s += ((DecArray)atr).size();
            else if(atr.nodeKind() == NodeKind.DEC)
                s += ((Dec)atr).getTipo().size();
        }
        return s;
    }

    public int getOffset(Ident a){ //Funcion que dice la posicion relativa del atributo dentro del struct en bytes
        int offset = 0;
        for(ASTNode atr : atributos.getList()){
            if(atr.nodeKind() == NodeKind.DECARRAY){
                DecArray d = (DecArray)atr;
                if(d.getName().equals(a.num()))
                    return offset;
                else
                    offset += d.size();
            }
            else if(atr.nodeKind() == NodeKind.DEC){
                Dec d = (Dec)atr;
                if(d.getName().equals(a.num()))
                    return offset;
                else
                    offset += d.getTipo().size();
            }
        }
        return offset;
    }
}
