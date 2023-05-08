package ast;

public class Parametro extends Statement implements ASTNode{
    private Ident name;
    private TipoParam tipo;

    public Parametro(Tipo tipo, boolean ref, String name){
        this.tipo = new TipoParam(tipo, ref);
        this.name = new Ident(name);
    }

    public String getName(){
        return name.toString();
    }

    public Tipo getTipo(){
        return tipo;
    }

    @Override
    public boolean type() {
        return true;
    }

    @Override
    public boolean bind() {
        boolean aux = tipo.bind();
        if(s.peek().containsKey(name.toString())){
            System.out.println("Nombre de parámetro duplicado: " + name);
            return false;
        }
        else{
            insertaId(name.toString(), this);
            aux = aux & name.bind();
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
            sDeltaCont.push(delta);
        }
        else{
            sDeltaCont.push(delta + tipo.getTipo().size());
        }
    }

    public int getDelta(){
        return delta;
    }

    public int getProf(){
        return prof;
    }
}
