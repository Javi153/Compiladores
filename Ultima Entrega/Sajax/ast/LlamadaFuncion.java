package ast;

import java.util.ArrayList;

public class LlamadaFuncion extends E implements ASTNode{
    private String name;
    private ArrayList<E> parlist;
    private ASTNode def;

    public LlamadaFuncion(String name, ArrayList<E> parlist){
        this.name = name;
        this.parlist = parlist;
        def = null;
    }

    public ArrayList<E> getParlist() {
        return parlist;
    }

    @Override
    public KindE kind() {
        return KindE.CALLFUN;
    }

    /*@Override
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
    }*/

    @Override
    public Tipo isType() {
        return buscaTipo(name);
    }

    @Override
    public boolean type() {
        boolean aux = buscaTipo(name) != null;
        if(!aux){
            System.out.println("Error: Función " + name + " no está definida");
        }
        else{
            for(int i = 0; i < parlist.size(); i++){
                KindE naux = parlist.get(i).kind();
                boolean paraux = parlist.get(i).isType().getTipo().equals(((TipoParam)buscaTipo(name + "." + (i+1))).getTipoParam().getTipo());
                if(((TipoParam)buscaTipo(name + "." + (i+1))).isRef()){
                    if(!naux.equals(KindE.IDEN) && !naux.equals(KindE.FLECHA) && !naux.equals(KindE.PUNTO) && !naux.equals(KindE.CORCHETES) && !naux.equals(KindE.ASTERISCO)){
                        System.out.println("Error: Se esperaba un designador en el parámetro por referencia " + parlist.get(i).num() + " de la función " + name);
                        paraux = false;
                    }
                }
                if(!paraux){
                    System.out.println("Error: se esperaba tipo " + buscaTipo(name + "." + (i+1)).getTipo().toString() + " pero se ha recibido tipo " + parlist.get(i).isType().getTipo().toString() + " en el parámetro " + parlist.get(i).num() + " de la función " + name);
                }
                aux = aux & paraux;
            }
        }
        return aux;
    }

    @Override
    public boolean bind() {boolean res = true;
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
        def = aux;
        for(E elem : parlist){
            res = res & elem.bind();
        }
        return res;
    }

    @Override
    public NodeKind nodeKind(){
        return NodeKind.FUNCIONCALL;
    }

    public String getName(){
        return name;
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

    @Override
    public ASTNode getDef(){
        return def;
    }

    @Override
    public void setDef(ASTNode def) {
        this.def = def;
    }
}