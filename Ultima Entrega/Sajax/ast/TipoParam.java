package ast;

public class TipoParam extends Tipo{
    private boolean ref; //Dice si el parametro se pasa por referencia o no
    public TipoParam(Tipo t, boolean ref) {
        super(t.getTipo());
        this.ref = ref;
    }

    public Tipo getTipoParam(){
        return new Tipo(t);
    }

    public boolean isRef(){
        return ref;
    }
}
