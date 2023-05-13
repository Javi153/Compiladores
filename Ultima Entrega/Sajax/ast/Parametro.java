package ast;

public class Parametro extends Statement implements ASTNode{
    private Ident name;
    private TipoParam tipo; //Tipo del parametro incluyendo si es o no por referencia

    public Parametro(Tipo tipo, boolean ref, String name){
        this.tipo = new TipoParam(tipo, ref);
        this.name = new Ident(name);
    }

    public String getName(){
        return name.toString();
    }

    public Tipo getTipo(){
        return tipo;
    } //Devuelve algo de tipo TipoParam

    @Override
    public boolean type() {
        return true;
    }

    public Tipo isType(){
        return tipo.getTipoParam(); //Devuelve el tipo basico
    }

    @Override
    public boolean bind() {
        boolean aux = tipo.bind();
        if(s.peek().containsKey(name.toString())){
            System.out.println("Nombre de parámetro duplicado: " + name);
            return false;
        }
        else{
            insertaId(name.toString(), this); //Inserta el parámetro en la tabla de símbolos
            aux = aux & name.bind() & tipo.bind(); //Comprueba que el nombre y el tipo estan definidos
            return aux;
        }
    }

    @Override
    public NodeKind nodeKind() {
        return NodeKind.PARAM;
    }

    public String toString(){
        return "param("+tipo.toString()+","+ (tipo.isRef() ? "referencia" : "valor") +","+name+")";
    }

    public void setDelta(int prof){
        this.prof = prof;
        delta = sDeltaCont.peek();
        if(tipo.isRef()){
            sDeltaCont.push(delta + 4); //Los punteros ocupan solo 4 bytes, igual que los enteros
        }
        else{
            sDeltaCont.push(delta + tipo.getTipoParam().size()); //En otro caso buscamos el tamaño del tipo basico
        }
    }

    public int getDelta(){
        return delta;
    }

    public int getProf(){
        return prof;
    }
}
