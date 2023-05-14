package ast;

public class TipoParam extends Tipo{
    private boolean ref; //Dice si el parametro se pasa por referencia o no
    private Tipo tOrig;
    public TipoParam(Tipo t, boolean ref) {
        super(t.getTipo());
        this.ref = ref;
        isParam = true;
        tOrig = t;
    }

    @Override
    public boolean bind(){
        return tOrig.bind();
    }

    @Override
    public boolean isParam(){return true;}

    public Tipo getTipoParam(){
        return tOrig;
    }

    public boolean isRef(){
        return ref;
    }
}
