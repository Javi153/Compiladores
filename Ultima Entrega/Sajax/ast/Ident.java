package ast;

public class Ident extends E {
    private String v;
    public Ident(String v) {
        this.v = v;
    }
    public String num() {return v;}
    public KindE kind() {return KindE.IDEN;}

    @Override
    public boolean isBound() {
        if(buscaId(v) == null){
            System.out.println("Error: el identificador " + v + " no está definido");
            return false;
        }
        else{
            return true;
        }
    }

    @Override
    public boolean bind() {
        if(s.peek().containsKey(v)){
            System.out.println("Error: el identificador " + v + " ya está definido en este ámbito");
            return false;
        }
        else{
            insertaId(v, this);
            return true;
        }
    }

    public String toString() {return v;}
}