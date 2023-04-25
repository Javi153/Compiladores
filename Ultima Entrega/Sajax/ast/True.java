package ast;

public class True extends E{
    public True() {}
    public KindE kind(){
        return KindE.TRUE;
    }

    @Override
    public boolean isBound() {
        return true;
    }

    @Override
    public boolean bind() {
        return true;
    }

    public String toString(){
        return "true";
    }
}
