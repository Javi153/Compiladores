package ast;

import java.util.HashMap;

public class MainFun implements ASTNode {
    private BloqueIns bloque;

    private Return ret;
    private int size;

    //El main funciona como un bloque terminado en return, por lo que sus funciones son similares a las de bloque

    public MainFun(/*ArrayList<Parametro> args, */ BloqueIns bloque, Return ret) {
        //this.args = args;
        this.bloque = bloque;
        this.ret = ret;
    }

    @Override
    public boolean type() {
        boolean aux =  bloque.type() & ret.type();
        if(!ret.getTipo().getTipo().equals(TipoEnum.INT)){
            aux = false;
            System.out.println("Error: El tipo de retorno de la funcion main debe ser de tipo int");
        }
        return aux;
    }

    @Override
    public boolean bind() {
        s.push(new HashMap<String, ASTNode>());
        boolean res = bloque.bind() & ret.bind();
        s.pop();
        return res;
    }

    @Override
    public NodeKind nodeKind() {
        return NodeKind.MAIN;
    }

    public String toString(){
        String s;
        s = "main(";
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
    public String code(){ //Usamos las funciones proporcionadas por el profesor para reservar la memoria necesaria y liberarla
        String s ="(func $main\n"+"(local $temp i32)\n   (local $localsStart i32)\n"+ "   i32.const " + (size + 8) + "\n" +
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
                "   set_local $localsStart\n";
        s = s.concat(bloque.code() + "\n");
        s = s.concat("   call $freeStack\n");
        s = s.concat(ret.code() + "\n");
        s = s.concat("   call $print\n"); //El return del main se imprime por pantalla para comprobar que t0d0 ha ido bien
        s = s.concat("return\n)\n");
        return s;
    }
}
