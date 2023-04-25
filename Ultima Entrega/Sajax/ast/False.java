package ast;

public class False extends E{
    public False() {}
    public KindE kind(){
        return KindE.FALSE;
    }

    public String num(){
        return "false";
    }

    @Override
    public boolean isBound() {
        return true;
    }

    @Override
    public Tipo isType() {
        return new Tipo(TipoEnum.BOOL);
    }

    @Override
    public boolean type() {
        return true;
    }

    @Override
    public boolean bind() {
        return true;
    }

    public String toString(){
        return "false";
    }
}
