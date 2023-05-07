package ast;

public class TipoArray extends Tipo{
    private Tipo t; //Encapsula el tipo mas basico del array
    private int tam; //Numero de dimensiones del array, es decir, las que quedan hasta el basico
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

    @Override
    public boolean bind() {
        return t.bind();
    }
}
