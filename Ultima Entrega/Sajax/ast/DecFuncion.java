package ast;

import java.util.ArrayList;
import java.util.HashMap;

public class DecFuncion extends Definicion implements ASTNode{
    private Tipo tipo;
    private String name;
    private ArrayList<Parametro> parlist;
    private BloqueIns cuerpo;
    private Return ret;

    public DecFuncion(Tipo tipo, String name, ArrayList<Parametro> parlist, BloqueIns cuerpo, Return ret){
        this.tipo = tipo;
        this.name = name;
        this.parlist = parlist;
        this.cuerpo = cuerpo;
        this.ret = ret;
    }

    @Override
    public boolean bind() {
        if(cuerpo == null){
            HashMap<String, ASTNode> m = s.peek();
            if(m.containsKey(name)){
                return false;
            }
            else{
                boolean aux = true;
                m.put(name, this);
                s.push(new HashMap<>());
                for(Parametro p : parlist){
                    aux = aux && p.bind();
                }
                s.pop();
                return aux;
            }
        }
        else{
            
        }
    }

    @Override
    public NodeKind nodeKind(){
        return NodeKind.FUNCIONDEC;
    }

    public String toString(){
        String s;
        if(cuerpo == null) {
            s = new String("decFuncionInline(" + tipo.toString() + "," + name);
            for (Parametro p : parlist) {
                s = s.concat("," + p.toString());
            }
            s = s.concat(")");
        }
        else{
            s = new String("decFuncion(" + tipo.toString() + "," + name);
            for (Parametro p : parlist) {
                s = s.concat("," + p.toString());
            }
            s = s.concat(","+cuerpo.toString());
            if(ret != null){
                s= s.concat(","+ret.toString()+")");
            }
            else{
                s=s.concat(")");
            }
        }
        return s;
    }
}
