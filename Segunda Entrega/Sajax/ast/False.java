package ast;

public class False extends E{
    public False(){

    }
    public KindE kind(){
        return KindE.FALSE;
    }
    public String toString(){
        return "false";
    }
}
