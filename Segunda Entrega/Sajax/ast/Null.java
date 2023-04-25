package ast;

public class Null extends E {

    public Null() {}
    public KindE kind(){
        return KindE.NULL;
    }
    public String toString(){
        return "null";
    }

}
