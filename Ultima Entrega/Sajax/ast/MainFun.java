package ast;

import java.util.ArrayList;

public class MainFun implements ASTNode {

    // private ArrayList<Parametro> args;
    // En principio el main no tiene argumentos no?
    private BloqueIns bloque;

    private Return ret;
    private int size;

    public MainFun(/*ArrayList<Parametro> args, */ BloqueIns bloque, Return ret) {
        //this.args = args;
        this.bloque = bloque;
        this.ret = ret;
    }

    @Override
    public boolean type() {
        boolean aux =  bloque.type();
        if(!ret.getTipo().getTipo().equals(TipoEnum.INT)){
            aux = false;
            System.out.println("Error: El tipo de retorno de la funcion main debe ser de tipo int");
        }
        return aux;
    }

    @Override
    public boolean bind() {
        return bloque.bind() & ret.bind();
    }

    @Override
    public NodeKind nodeKind() {
        return NodeKind.MAIN;
    }

    public String toString(){
        String s;
        s = "main(";
        /*if(!args.isEmpty()) {
            s = s.concat(args.get(0).toString());
            for (int i = 1; i < args.size(); ++i) {
                s = s.concat("," + args.get(i).toString());
            }
        }*/
        s = s.concat(bloque.toString() + "," + ret.toString());
        s = s.concat(")");
        return s;
    }

    @Override
    public void setDelta(int prof){
        sDeltaCont.push(0);
        bloque.setDelta(1);
        ret.setDelta(1);
        size = sDeltaCont.pop();
    }

    @Override
    public String code(){
        String s = "(func $main\n";
        s = s.concat("(local $temp i32)\n" +
                "   (local $localsStart i32)\n" +
                "   i32.const " + (size + 8) + "\n" +
                "   call $reserveStack  ;; returns old MP (dynamic link)\n" +
                "   set_local $temp\n" +
                "   get_global $MP\n" +
                "   get_local $temp\n" +
                "   i32.store\n" +
                "   get_global $MP\n" +
                "   get_global $SP\n" +
                "   i32.store offset=4\n" +
                "   get_global $MP\n" +
                "   i32.const 8\n" +
                "   i32.add\n" +
                "   set_local $localsStart\n");
        s = s.concat(bloque.code());
        s = s.concat("   call $freeStack\n");
        s = s.concat(ret.code());
        s = s.concat("   call $print\n");
        s = s.concat(")\n");
        return s;
    }
}
