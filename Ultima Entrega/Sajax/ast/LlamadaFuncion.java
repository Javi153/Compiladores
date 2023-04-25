package ast;

import java.util.ArrayList;

public class LlamadaFuncion extends E implements ASTNode{
    private String name;
    private ArrayList<E> parlist;

    public LlamadaFuncion(String name, ArrayList<E> parlist){
        this.name = name;
        this.parlist = parlist;
    }

    @Override
    public KindE kind() {
        return KindE.CALLFUN;
    }

    @Override
    public boolean isBound() {
        boolean res = true;
        ASTNode aux = buscaId(name);
        if(aux == null){
            res = false;
            System.out.println("Error: Función " + name + " no está definida");
        }
        else if(aux.nodeKind() != NodeKind.FUNCIONDEC){
            res = false;
            System.out.println("Error: " + name + " no es una función");
        }
        else if(((DecFuncion)aux).numParams() != parlist.size()){
            res = false;
            System.out.println("Error: Función " + name + " no tiene el número de parámetros correcto");
        }
        for(E elem : parlist){
            res = res & elem.isBound();
        }
        return res;
    }

    @Override
    public boolean bind() {
        return true;
    }

    @Override
    public NodeKind nodeKind(){
        return NodeKind.FUNCIONCALL;
    }

    public String toString(){
        String s;
        s = new String("llamadaFuncion(" + name);
        for (E p : parlist) {
            s = s.concat("," + p.toString());
        }
        s = s.concat(")");
        return s;
    }

    public String num(){
        String s = name + "(";
        for (int i = 0; i < parlist.size() - 1; i++) {
            s = s.concat(parlist.get(i).num() + ",");
        }
        s = s.concat(parlist.get(parlist.size() - 1).num() + ")");
        return s;
    }
}