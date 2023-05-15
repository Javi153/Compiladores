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

    @Override
    public Tipo isType() { //Busca el tipo de la funcion en la tabla
        return buscaTipo(name);
    }

    @Override
    public boolean type() {
        boolean aux = buscaTipo(name) != null; //Comprueba que la funcion tiene el tipo esperado en su definicion
        if(!aux){
            System.out.println("Error: Función " + name + " no está definida");
        }
        else{
            for(int i = 0; i < parlist.size(); i++){
                parlist.get(i).type(); //Comprueba que los tipos en los parametros son coherentes
                KindE naux = parlist.get(i).kind();
                boolean paraux = parlist.get(i).isType().getTipo().equals(((TipoParam)buscaTipo(name + "." + (i+1))).getTipoParam().getTipo()); //Compruebas que el tipo del ma¡¡parametro coincide con el de la definicion
                if(((TipoParam)buscaTipo(name + "." + (i+1))).isRef()){
                    if(!naux.equals(KindE.IDEN) && !naux.equals(KindE.FLECHA) && !naux.equals(KindE.PUNTO) && !naux.equals(KindE.CORCHETES) && !naux.equals(KindE.ASTERISCO)){ //Si es parametro por referencia debe ser un designador
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
        ASTNode aux = buscaId(name); //Comprueba que la funcion este definida
        if(aux == null){
            res = false;
            System.out.println("Error: Función " + name + " no está definida");
        }
        else if(aux.nodeKind() != NodeKind.FUNCIONDEC){ //Comprueba que lo que ha sido definido es efectivamente una funcion
            res = false;
            System.out.println("Error: " + name + " no es una función");
        }
        else if(((DecFuncion)aux).numParams() != parlist.size()){ //Comprueba que coincide el numero de parametros
            res = false;
            System.out.println("Error: Función " + name + " no tiene el número de parámetros correcto");
        }
        def = aux;
        for(E elem : parlist){
            res = res & elem.bind(); //Comprueba que los parametros estan asociados a variables existentes
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
        if(parlist.size() != 0) {
            s = s.concat(parlist.get(parlist.size() - 1).num());
        }
        s = s.concat(")");
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

    public String codeDesig(){
        return code();
    }

    @Override
    public String code(){
        String s = "";
        DecFuncion dec = (DecFuncion) def; // Con esto se puede acceder a los deltas de los parámetros del marco nuevo
        for(int i = 0; i < parlist.size(); i++) {

            TipoParam t = ((TipoParam)dec.getParams().get(i).getTipo());
            if(t.isRef()) {
                s = s.concat("get_global $SP\ni32.const 8\ni32.add\n"); // pila <- SP + 8
                s = s.concat("i32.const " + dec.getParams().get(i).getDelta() + "\ni32.add\n"); // pila <- delta(param, marco nuevo) + SP + 8
                s = s.concat(parlist.get(i).codeDesig() + "\n"); // Son direcciones de memoria (pila <- mem_dir)
                s = s.concat("i32.store\n"); // mem(SP+8+delta) = mem_dir
            }
            else {
                TipoEnum tipo = t.getTipoParam().getTipo();
                switch(tipo) {
                    case INT, BOOL, FLOAT, PUNTERO -> {
                        s = s.concat("get_global $SP\ni32.const 8\ni32.add\n"); // pila <- SP + 8
                        s = s.concat("i32.const " + dec.getParams().get(i).getDelta() + "\ni32.add\n"); // pila <- delta(param, marco nuevo) + SP + 8
                        s = s.concat(parlist.get(i).code() + "\n"); // pila <- evaluación de expresión (parámetro por valor)
                        s = s.concat(tipo.alias() + ".store\n"); // mem(SP+8+delta) = valor
                    }
                    case STRUCT -> {
                        s = s.concat(parlist.get(i).codeDesig() + "\n");
                        s = s.concat("get_global $SP\ni32.const 8\ni32.add\n"); // pila <- SP + 8
                        s = s.concat("i32.const " + dec.getParams().get(i).getDelta() + "\ni32.add\n"); // pila <- delta(param, marco nuevo) + SP + 8
                        s = s.concat("i32.const " + ((TipoStruct)t.getTipoParam()).size()/4 + "\n");
                        s = s.concat("call $copyn\n");
                    }
                    default -> {}
                }
            }
        }
        s = s.concat("call $" + name + "\n");
        return s;
    }
}