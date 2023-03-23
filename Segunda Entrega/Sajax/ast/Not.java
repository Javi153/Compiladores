package ast;

public class Not extends E{
    private E opnd1;
    public Not(E opnd1){
        this.opnd1 = opnd1;
    }
    public KindE kind(){
        return KindE.NOT;
    }
    public E opnd1() {return opnd1;}
    public String toString() {return "not("+opnd1().toString()+")";}
}
