package ast;

public class TipoArray extends Tipo{
    private Tipo t;
    private int tam;
    public TipoArray(Tipo t, int tam) {
        super(TipoEnum.ARRAY);
        this.t = t;
        this.tam = tam;
    }

    public int getTam(){
        return tam;
    }

    public Tipo getTipoBasico(){
        return t;
    }
}
