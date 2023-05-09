package ast;

import java.util.ArrayList;
import java.util.HashMap;

public class DecFuncion extends Definicion implements ASTNode{
    private Tipo tipo;
    private Ident name;
    private ArrayList<Parametro> parlist;
    private BloqueIns cuerpo;
    private Return ret;
    private int size;

    public DecFuncion(Tipo tipo, String name, ArrayList<Parametro> parlist, BloqueIns cuerpo, Return ret){
        this.tipo = tipo;
        this.name = new Ident(name);
        this.parlist = parlist;
        this.cuerpo = cuerpo;
        this.ret = ret;
    }

    public DecFuncion(String name, DecFuncion d){
        this.name = new Ident(name);
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
        if(cuerpo != null && sTipo.peek().get(name.toString()) != null){
            if(!sTipo.peek().get(name.toString()).getTipo().equals(tipo.getTipo())){
                aux = false;
                System.out.println("El tipo de la funcion " + name + " no coincide con el tipo de la declaracion");
            }
            int count = 1;
            for(Parametro p : parlist){
                if(!sTipo.peek().get(name.toString() + "." + count).equals(p.getTipo())){
                    aux = false;
                    System.out.println("El tipo del parametro " + p.getName() + " en " + name + " no coincide con el tipo de la declaracion");
                }
            }
        }
        else {
            sTipo.peek().put(name.toString(), tipo);
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
                aux = aux & ret.type();
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
            if (m.containsKey(name.toString())) {
                System.out.println("Error: Funcion " + name + " ya declarada");
                return false;
            } else {
                boolean aux;
                m.put(name.toString(), this);
                aux = name.bind();
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
            if(m.containsKey(name.toString())){
                ASTNode original = m.get(name.toString());
                if(original.nodeKind() == NodeKind.FUNCIONDEC && ((DecFuncion)original).sinCuerpo()){
                    boolean aux = ((DecFuncion)original).numParams() == parlist.size();
                    if(!aux) {
                        System.out.println("Error: Funcion " + name + " no coincide en el número de parámetros");
                    }
                    m.replace(name.toString(), this); //Nos quedaremos siempre con la declaracion con cuerpo que nos da mas informacion
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
                m.put(name.toString(), this);
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

    public ArrayList<Parametro> getParams(){
        return parlist;
    }

    public String getName(){
        return name.toString();
    }

    public void setDelta(int prof){
        this.prof = prof + 1;
        sDeltaCont.push(0);
        for(Parametro p : parlist){
            p.setDelta(this.prof);
        }
        if(cuerpo != null){
            cuerpo.setDelta(this.prof);
        }
        size = sDeltaCont.pop();
    }

    public String code(){
        if(cuerpo == null){
            return "";
        }
        else {
            String s = "(func $" + name.num() + '\n';
            //for (Parametro p : parlist) {
            //    s = s.concat("(param i32)\n"); //Aqui falta por copiar los structs que sean por valor
            //}                                       //Tb queda por ingeniarselas para hacer un load de los numeritos por referencia y trabajar con eso
            if (ret != null) {                      //Y aun no tengo ni idea de como abordar ninguno de los dos
                if(ret.getTipo().getTipo().equals(TipoEnum.FLOAT)){
                    s = s.concat("(result f32)\n");
                }
                else{
                    s = s.concat("(result i32)\n");
                }
            } // TODO esto igual hay que quitarlo también aunque las diapos dicen que no
            // porque al parecer el profe dice que sí

            s = s.concat("(local $temp i32)\n");
            s = s.concat("(local $localsStart i32)\n");


            s = s.concat("   i32.const " + (size + 8) + "\n" +
                    "   call $reserveStack  ;; returns old MP (dynamic link)\n" +
                    "   set_local $temp\n" +
                    "   get_global $MP\n" +
                    "   get_local $temp\n" +
                    "   i32.store\n" +
                    "   get_global $MP\n" +
                    "   get_global $SP\n" +
                    "   i32.store offset=4\n" +
                    "   get_global $MP\n" +
                    "   i32.const 8\n" +
                    "   i32.add\n" +
                    "   set_local $localsStart\n");

            /*
            s = s.concat("i32.const "+(size+8)+"\n");
            s = s.concat("call $reserveStack\n");
            s = s.concat("i32.const 8\ni32.add\n");
            s = s.concat("set_local $localsStart\n");
            */



            s = s.concat(cuerpo.code());
            if(ret != null){
                s = s.concat(ret.code());
            }
            s = s.concat("call $freeStack\n");
            s = s.concat(")\n");
            return s;
        }
    }

    public int getSize(){
        return size;
    }
}
