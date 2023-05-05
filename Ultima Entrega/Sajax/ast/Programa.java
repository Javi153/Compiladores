package ast;

import java.util.ArrayList;
import java.util.HashMap;

public class Programa implements ASTNode{
    private BloqueDef defs;
    private MainFun main;

    private final String funciones = "(func $reserveStack (param $size i32) ;;funcion que reserva memoria para la pila para entrar en un ambito nuevo\n" +
            "   (result i32)\n" +
            "   get_global $MP\n" +
            "   get_global $SP\n" +
            "   set_global $MP\n" +
            "   get_global $SP\n" +
            "   get_local $size\n" +
            "   i32.add\n" +
            "   set_global $SP\n" +
            "   get_global $SP\n" +
            "   get_global $NP\n" +
            "   i32.gt_u\n" +
            "   if\n" +
            "   i32.const 3\n" +
            "   call $exception\n" +
            "   end\n" +
            ")\n" +
            "(func $freeStack ;;funcion que libera la memoria del ultimo ambito en el que se haya entrado\n" +
            "   get_global $MP\n" +
            "   i32.load\n" +
            "   i32.load offset=4\n" +
            "   set_global $SP\n" +
            "   get_global $MP\n" +
            "   i32.load\n" +
            "   set_global $MP   \n" +
            ")\n" +
            "(func $reserveHeap ;;funcion que reserva memoria en el heap para un nuevo ambito\n" +
            "   (param $size i32)\n" +
            "   get_global $NP\n" +
            "   set_global $MP\n" +
            "   get_global $NP\n" +
            "   get_local $size\n" +
            "   i32.add\n" +
            "   set_global $NP\n" +
            ")\n" +
            "(func $freeHeap ;;funcion que libera memoria del heap del ultimo ambito en el que nos encontrasemos\n" +
            "   get_global $MP\n" +
            "   i32.load\n" +
            "   i32.load offset=4\n" +
            "   set_global $NP\n" +
            "   get_global $MP\n" +
            "   i32.load\n" +
            "   set_global $MP   \n" +
            ")\n" +
            "(func $copyn ;; copy $n i32 slots from $src to $dest\n" +
            "   (param $src i32)\n" +
            "   (param $dest i32)\n" +
            "   (param $n i32)\n" +
            "   block\n" +
            "     loop\n" +
            "       get_local $n\n" +
            "       i32.eqz\n" +
            "       br_if 1\n" +
            "       get_local $n\n" +
            "       i32.const 1\n" +
            "       i32.sub\n" +
            "       set_local $n\n" +
            "       get_local $dest\n" +
            "       get_local $src\n" +
            "       i32.load\n" +
            "       i32.store\n" +
            "       get_local $dest\n" +
            "       i32.const 4\n" +
            "       i32.add\n" +
            "       set_local $dest\n" +
            "       get_local $src\n" +
            "       i32.const 4\n" +
            "       i32.add\n" +
            "       set_local $src\n" +
            "       br 0\n" +
            "     end\n" +
            "   end\n" +
            ")\n";
    
    public Programa(BloqueDef defs, MainFun main){
        this.defs = defs;
        this.main = main;
    }

    @Override
    public boolean type() {
        sTipo.push(new HashMap<>());
        if(!(defs.type() & main.type())){
            sTipo.pop();
            System.out.println("La comprobación de tipos falló. Compilación abortada");
            return false;
        }
        else{
            sTipo.pop();
            System.out.println("La comprobación de tipos fue exitosa");
            return true;
        }
    }

    @Override
    public boolean bind() {
        s.push(new HashMap<>());
        boolean aux = defs.bind() & main.bind();
        for(String name : s.peek().keySet()){
            if(s.peek().get(name).nodeKind() == NodeKind.FUNCIONDEC && ((DecFuncion)s.peek().get(name)).sinCuerpo()){
                System.out.println("Funcion declarada pero cuerpo no definido: " + name);
                aux = false;
            }
        }
        s.pop();
        if(!aux){
            System.out.println("La vinculación falló. Compilación abortada");
            return false;
        }
        else{
            System.out.println("La vinculación fue exitosa");
            return true;
        }
    }

    public NodeKind nodeKind(){
        return NodeKind.PROGRAMA;
    }

    public String code(){
        String s = "(module\n";
        s = s.concat("(import \"runtime\" \"print\" (func $print (param i32)))\n" +
                "(import \"runtime\" \"print\" (func $print2 (param f32)))\n" +
                "(import \"runtime\" \"read\" (func $read (result i32)))\n" +
                "(import \"runtime\" \"read\" (func $read2 (result f32)))\n");
        s = s.concat("(import \"runtime\" \"exceptionHandler\" (func $exception (param i32)))\n");
        s = s.concat("(memory 2000)\n");
        s = s.concat("(global $SP (mut i32) (i32.const 0)) ;; start of stack\n");
        s = s.concat("(global $MP (mut i32) (i32.const 0)) ;; mark pointer\n");
        s = s.concat("(global $NP (mut i32) (i32.const 131071996)) ;; heap 2000*64*1024-4\n");
        s = s.concat("(start $main)\n");
        s = s.concat(defs.code() + main.code());
        s = s.concat(funciones);
        s = s.concat(")\n");
        return s;
    }
    
    public String toString(){
        return "programa(" + defs.toString() + "," + main.toString() + ")";
    }

    @Override
    public void setDelta(int prof){
        sDeltaCont.push(0);
        defs.setDelta(0);
        main.setDelta(0);
        sDeltaCont.pop();
    }

}
