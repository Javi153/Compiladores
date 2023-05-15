package ast;

import java.util.HashMap;

public class Dec extends Statement implements ASTNode{
    private Tipo tipo;
    private Ident iden;
    private E exp; //Puede ser null, pues puedes declarar variables sin valor inicial

    public Dec(Tipo tipo, String iden){
        this.tipo = tipo;
        this.iden = new Ident(iden);
        exp = null;
    }

    public Dec(Tipo tipo, String iden, E exp){
        this.tipo = tipo;
        this.iden = new Ident(iden);
        this.exp = exp;
    }

    public Dec(String name, Dec d){ //Constructor de copia
        this.iden = new Ident(name);
        this.tipo = d.tipo;
        this.exp = d.exp;
    }

    @Override
    public NodeKind nodeKind() {
        return NodeKind.DEC;
    }
    @Override
    public String toString(){
        if(exp == null) {
            return "dec("+tipo.toString()+","+iden.toString()+")";
        }
        else{
            return "dec("+tipo.toString()+","+iden.toString()+","+exp.toString()+")";
        }
    }

    @Override
    public boolean bind() { //Buscamos que la variable declarada no estuviese declarada previamente
        boolean aux = tipo.bind();
        if(exp != null) { aux = aux & exp.bind(); } //Si hay valor inicial vinculamos para ver que existe
        HashMap<String, ASTNode> m = s.peek();
        if(m.containsKey(iden.toString())){
            System.out.println("Error: variable "+iden.num()+" ya declarada");
            return false;
        }
        else{
            m.put(iden.toString(), this); //Si no lo esta este nodo sera la vinculacion de esta variable
            aux = aux & iden.bind();
            return aux;
        }
    }

    @Override
    public boolean type() {
        sTipo.peek().put(iden.num(), tipo); //Añadimos el tipo de la declaracion a la pila, asignado al identificador
        if(tipo.isPointer()) {
            int count = 0;
            Tipo t = tipo;
            while(t.isPointer()){
                count++;
                t = ((Puntero)t).getTipoPointer(); //En caso de ser puntero, contamos el numero de punteros del tipo
            }
            String s = iden.num();
            for (int i = 0; i < count; i++) {
                s = s.concat("[]"); //En cada nivel de puntero añadimos un nuevo tipo
                if (i != count - 1) {
                    sTipo.peek().put(new String(s), new TipoArray(tipo, count - i - 1)); //Lo guardaremos como un tipo array pues estos punteros seran accedido con []
                }
            }
            sTipo.peek().put(new String(s), t); //Tambien incluimos iden[][]...[] como el tipo basico detras de todos los punteros
        }
        if(exp != null) { //Si hay valor inicial usamos el codigo de las asignaciones
            boolean aux = exp.type();
            Tipo tExp = exp.isType();
            if (tExp.isParam())
                tExp = ((TipoParam) tExp).getTipoParam();
            if(tipo.isPointer() && exp.kind() == KindE.NULL){
                return true;
            }
            if(tipo.isPointer() && exp.nodeKind() == NodeKind.MEMSPACE){
                ((Memspace) exp).setTipoDatos(((Puntero) tipo).getTipoPointer());
                return true;
            }
            if(tipo.isPointer() && tExp.isPointer()){
                Tipo t1 = ((Puntero)tipo).getTipoPointer();
                Tipo t2 = ((Puntero)tExp).getTipoPointer();
                while(t1.isPointer() && t2.isPointer()){
                    t1 = ((Puntero)t1).getTipoPointer();
                    t2 = ((Puntero)t2).getTipoPointer();
                }
                if(!t1.getTipo().equals(t2.getTipo())){
                    aux = false;
                    System.out.println("Error: Los punteros " + iden.num() + " y " + exp.num() + " son de tipos distintos");
                }
            }
            else if(!tipo.getTipo().equals(tExp.getTipo())){
                System.out.println("Error: se esperaba tipo "+tipo.getTipo().toString()+" pero se ha recibido tipo "+tExp.getTipo().toString() + " en la expresion " + tipo.getTipo().toString() +" " + iden.num() + " = " + exp.num());
                aux = false;
            }
            return aux;
        }
        return true;
    }

    public String getName(){
        return iden.num();
    }

    public Ident getIden() { return iden; }

    public Tipo getTipo() { return tipo; }

    public E getExp() { return exp; }

    public String code(){
        String s = "";
        if(exp == null){
            if(tipo.isPointer()){
                s = iden.codeDesig() + "\n";
                s = s.concat(new Null().code()) + "\n";
                s = s.concat("i32.store\n");
                return s;
            }
            return "";
        }
        else{
            if(!tipo.getTipo().equals(TipoEnum.STRUCT)){ //Si tenemos valores iniciales usamos el valor de exp y la direccion de iden
                s = iden.codeDesig() + "\n";
                s = s.concat(exp.code()) + "\n";
                s = s.concat(tipo.getTipo().alias() + ".store\n");
            }
            else{ //Sin embargo, para structs hay que copiar grandes direcciones de memoria, asi que reusamos el codigo de asignacion
                s = new Asign(iden, exp).code() + "\n";
            }
            return s;
        }
    }

    @Override
    public void setDelta(int prof) { //Asignamos la profundidad
        this.prof = prof;
        delta = sDeltaCont.pop(); //Recogemos el ultimo delta hasta el momento
        sDeltaCont.push(delta+tipo.size()); //sumamos el size y lo colocamos de nuevo
        iden.setDelta(prof);
        if(exp != null) {
            exp.setDelta(prof);
        }
    }

    public int size(){
        return tipo.size();
    }
}
