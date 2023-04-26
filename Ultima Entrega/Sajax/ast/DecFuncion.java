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

    public DecFuncion(String name, DecFuncion d){
        this.name = name;
        this.tipo = d.tipo;
        this.parlist = d.parlist;
        this.cuerpo = d.cuerpo;
        this.ret = d.ret;
    }

    public boolean sinCuerpo(){
        return cuerpo == null;
    }

    @Override
    public boolean type() {
        boolean aux = true;
        if(cuerpo != null && sTipo.peek().get(name) != null){
            if(!sTipo.peek().get(name).getTipo().equals(tipo.getTipo())){
                aux = false;
                System.out.println("El tipo de la funcion " + name + " no coincide con el tipo de la declaracion");
            }
            int count = 1;
            for(Parametro p : parlist){
                if(!sTipo.peek().get(name + "." + count).equals(p.getTipo())){
                    aux = false;
                    System.out.println("El tipo del parametro " + p.getName() + " en " + name + " no coincide con el tipo de la declaracion");
                }
            }
        }
        else {
            sTipo.peek().put(name, tipo);
            int count = 1;
            for (Parametro p : parlist) {
                sTipo.peek().put(name + "." + count, p.getTipo());
                count++;
            }
        }
        if(cuerpo != null){
            sTipo.push(new HashMap<>());
            for(Parametro p : parlist){
                sTipo.peek().put(p.getName(), p.getTipo());
                aux = cuerpo.type();
            }
            if(ret != null){
                if(!ret.getTipo().getTipo().equals(tipo.getTipo())){
                    aux = false;
                    System.out.println("Error: El tipo de retorno de la funcion " + name + " no coincide con el tipo de la declaracion");
                }
            }
            sTipo.pop();
        }
        return aux;
    }

    @Override
    public boolean bind() {
        //Esta funcion es bastante liosa asi que la explico. En primer lugar hay dos tipos de declaracion
        //de funciones, la declaracion de una funcion sin cuerpo y la declaracion de una funcion con cuerpo.
        //Cuando no tenga cuerpo, solo hay que comporbar por un lado que nunca haya sido definida y por otro
        //que los parametros estan bien definidos, es decir, no se repiten
        if(cuerpo == null) {
            HashMap<String, ASTNode> m = s.peek();
            if (m.containsKey(name)) {
                System.out.println("Error: Funcion " + name + " ya declarada");
                return false;
            } else {
                boolean aux = true;
                m.put(name, this);
                s.push(new HashMap<>());
                for (Parametro p : parlist) {
                    aux = aux & p.bind();
                }
                s.pop();
                return aux;
            }
        }
        //Si por el contrario tiene cuerpo, ahora podemos aceptar que la funcion ya estuviese declarada siempre
        //y cuando la anterior declaracion no tuviese cuerpo y coincida el numero de parametros (si el nombre cambia da igual)
        //Ademas, debemos encargarnos de vincular las instrucciones dentro del cuerpo
        else{
            HashMap<String, ASTNode> m = s.peek();
            if(m.containsKey(name)){
                ASTNode original = m.get(name);
                if(original.nodeKind() == NodeKind.FUNCIONDEC && ((DecFuncion)original).sinCuerpo()){
                    boolean aux = ((DecFuncion)original).numParams() == parlist.size();
                    if(!aux) {
                        System.out.println("Error: Funcion " + name + " no coincide en el número de parámetros");
                    }
                    m.replace(name, this); //Nos quedaremos siempre con la declaracion con cuerpo que nos da mas informacion
                    s.push(new HashMap<>());
                    for(Parametro p : parlist){
                        aux = aux & p.bind();
                    }
                    aux = aux & cuerpo.bind();
                    if(ret != null){
                        aux = aux & ret.bind();
                    }
                    s.pop();
                    return aux;
                }
                else{
                    System.out.println("Error: Funcion " + name + " ya declarada");
                    return false;
                }
            }
            else{
                boolean aux = true;
                m.put(name, this);
                s.push(new HashMap<>());
                for(Parametro p : parlist){
                    aux = aux & p.bind();
                }
                aux = aux & cuerpo.bind();
                if(ret != null){
                    aux = aux & ret.bind();
                }
                s.pop();
                return aux;
            }
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

    public int numParams(){
        return parlist.size();
    }

    public String getName(){
        return name;
    }
}
