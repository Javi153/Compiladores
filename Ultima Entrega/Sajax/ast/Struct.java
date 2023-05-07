package ast;

import java.util.HashMap;

public class Struct extends Definicion implements ASTNode{
    private String name;
    private Bloque<ASTNode> atributos;

    public Struct(String name, Bloque<ASTNode> atributos){
        this.name = name;
        this.atributos = atributos;
    }

    public Struct(String name, Struct s){
        this.name = name;
        this.atributos = s.getAtt();
    }

    @Override
    public boolean type() {
        /*Javi del pasado igual no te acuerdas pero ya pensamos en eso. Si, en las funciones de structs
        La clave sera guardar todo como struct.funcion.1 etc. Para ello quiza lo mejor es que escribas las cosas
        de funcion desde aqui, sin llamar a su type*/
        // supongo que querrías decir Javi del futuro :)
        boolean aux = true;
        sTipo.peek().put(name, new TipoStruct(name));
        for(ASTNode n : atributos.getList()){
            switch(n.nodeKind()){
                case DEC -> {
                    Dec d = (Dec)n;
                    aux = aux & (new Dec(name + "." + d.getName(), d)).type();
                }
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
        if(buscaId(name) != null){
            System.out.println("Error: ya existe una variable con el nombre "+name);
            return false;
        }
        else{
            insertaId(name, this);
            s.push(new HashMap<>());
            boolean aux = atributos.bind();
            s.pop();
            for(ASTNode a : atributos.getList()){
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

    /*@Override
    public void setDelta() {
        sDelta.push(new HashMap<>());
        sDeltaCont.push(0);
        for (ASTNode a : atributos.getList())
            a.setDelta(); // Esto tal vez no me hace el casting sino que llama a la setDelta() default de ASTNode
        sDeltaCont.pop(); // Cosas de TP1
        sDelta.pop();
    }*/

    @Override
    public void setDelta(int prof) {
        this.prof = prof;
        //for (ASTNode a : atributos.getList())
        //    a.setDelta(prof);
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

    public int size(){
        int s = 0;
        for(ASTNode atr : atributos.getList()){
            if(atr.nodeKind() == NodeKind.DECARRAY)
                s += ((DecArray)atr).size();
            else if(atr.nodeKind() == NodeKind.DEC)
                s += ((Dec)atr).getTipo().size();
        }
        return s;
    }

    public int getOffset(Ident a){
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
