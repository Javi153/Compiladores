package ast;

public class True extends E{
    public True(){

    }
    public KindE kind(){
        return KindE.TRUE;
    }
    public String toString(){
        return "true";
    }
}
