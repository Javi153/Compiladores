package ast;

public class TipoParam extends Tipo{
    private boolean ref; //Dice si el parametro se pasa por referencia o no
    private Tipo tOrig; //El tipo del parametro
    public TipoParam(Tipo t, boolean ref) {
        super(t.getTipo());
        this.ref = ref;
        isParam = true;
        tOrig = t;
    }

    @Override
    public boolean bind(){ //Nos interesa aociar el tipo verdadero
        return tOrig.bind();
    }

    @Override
    public boolean isParam(){return true;} //Dice si el tipo es de un parametro

    public Tipo getTipoParam(){
        return tOrig;
    }

    public boolean isRef(){
        return ref;
    } //Dice si el parametro se pasa por referencia
}
