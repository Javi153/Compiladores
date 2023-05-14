package ast;

import java.util.HashMap;

public class Ident extends E implements ASTNode, Designador {
    private String v;
    private ASTNode def; //Declaracion asociada al identificador
    public Ident(String v) {
        this.v = v;
        this.def = null;
    }
    public String num() {return v;}
    public KindE kind() {return KindE.IDEN;}

    /*@Override
    public boolean isBound() {
        if(buscaId(v) == null){
            System.out.println("Error: el identificador " + v + " no está definido");
            return false;
        }
        else{
            return true;
        }
    }*/

    @Override
    public Tipo isType() { //En teoria nunca se llamará a este método, sino que se verá en la tabla cual es el tipo
        return buscaTipo(v);
    }

    @Override
    public boolean type() { //Si t0d0 sale bien, se deberían guardar los identificadores en sus definicoines
        return true;
    }

    @Override
    public boolean bind() {
        ASTNode aux = buscaId(v);
        if(aux == null){
            System.out.println("Error: el identificador " + v + " no está definido");
            return false;
        }
        else{
            def = aux;
            return true;
        }
    }

    @Override
    public String code() {
        String c = codeDesig();
        switch (def.nodeKind()) { //Buscamos el tipo y lo cargamos dependiendo si es int o float
            case DEC -> {
                switch(((Dec) def).getTipo().getTipo()) {
                    case INT, BOOL, PUNTERO -> { c = c.concat("\ni32.load"); }
                    case FLOAT -> { c = c.concat("\nf32.load"); }
                    default -> {}
                }
            }
            case PARAM -> {
                switch(((Parametro) def).getTipo().getTipo()) {
                    case INT, BOOL -> { c = c.concat("\ni32.load"); }
                    case FLOAT -> { c = c.concat("\nf32.load"); }
                    default -> {}
                }
            }
            case DECARRAY -> {} //En estos casos no se llamara directamente a su code, sino que se aplicara algo mas sobre el iden
            case FUNCIONDEC -> {}
            case STRUCT -> {}
            case GLOBVAR -> {}
        }
        return c;
    }

    public String toString() {return v;}

    @Override
    public ASTNode getDef() {
        return def;
    }

    @Override
    public void setDef(ASTNode def) {this.def = def;}

    @Override
    public String codeDesig() { //Buscmos la direccion del ident dependienndo de la profundidad
        String s = "i32.const " + def.getDelta();
        if(((Statement)def).getProf() == 1){
            s = s.concat("\nget_local $localsStart\ni32.add\n");
        }
        if(def.nodeKind().equals(NodeKind.PARAM) && ((TipoParam)((Parametro) def).getTipo()).isRef()){ //Tambien debemos hacer una segunda carga si es un parametro pasado por referencia
            s = s.concat("i32.load\n"); //Esto es asi porque un parametro por referencia es una direccion que apunta a otra direccion donde esta el contenido real de las varibales, asiq cargamos y tenemos la direccion real
        }
        //if (def.nodeKind().equals(NodeKind.DEC) && ((Dec) def).getTipo().getTipo().equals(TipoEnum.PUNTERO))
        //    s = s.concat("\ni32.load aqui no entro oke");
        return s;
    }

    public void setDelta(int prof){
        this.prof = prof;
    }
}