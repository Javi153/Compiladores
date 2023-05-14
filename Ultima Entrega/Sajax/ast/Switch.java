package ast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.stream.Collectors;

public class Switch extends Statement implements ASTNode {

    private E exp;
    private ArrayList<Case> cases;
    private DefaultCase defaultCase = null;
    private int minimo;
    private int maximo;
    private int n;
    private ArrayList<Integer> br_table;

    public Switch(E exp, ArrayList<Case> cases) {
        this.exp = exp;
        this.cases = cases;
        minimo = buscaMinimo();
        maximo = buscaMaximo();
        n = maximo - minimo + 1;
        br_table_init();
    }

    public Switch(E exp, ArrayList<Case> cases, DefaultCase defaultCase) { //Diferenciamos el caso en que hay default
        this.exp = exp;
        this.cases = cases;
        this.defaultCase = defaultCase;
        minimo = buscaMinimo();
        maximo = buscaMaximo();
        n = maximo - minimo + 1;
        br_table_init();
    }

    @Override
    public boolean type() { //Para el tipado funciona como dos bloques, solo comprobamos que la expresion es un entero
        boolean aux = exp.type() & exp.isType().getTipo().equals(TipoEnum.INT);
        if(!aux){
            System.out.println("Error: la expresion del switch " + exp.num() + " no es de tipo entero");
        }
        for (Case c : cases) {
            aux = aux & c.type();
        }
        if (defaultCase != null) {
            aux = aux & defaultCase.type();
        }
        return aux;
    }

    @Override
    public boolean bind() { //Para le binding asociamos cada una de sus partes
        boolean aux = exp.bind();
        for (Case c : cases) {
            aux = aux & c.bind();
        }
        if (defaultCase != null) {
            aux = aux & defaultCase.bind();
        }
        return aux;
    }

    @Override
    public NodeKind nodeKind() {
        return NodeKind.SWITCH;
    }

    @Override
    public String toString() {
        String s = "switch(" + exp.toString() + ",cases" + cases.stream().map(Case::toString).collect(Collectors.joining(",", "(", ")"));

        if (defaultCase != null) {
            s = s.concat("," + defaultCase.toString());
        }

        return s + ")";
    }

    @Override
    public void setDelta(int prof){
        for(Case c : cases){
            c.setDelta(prof);
        }
        if(defaultCase != null){
            defaultCase.setDelta(prof);
        }
    }

    @Override
    public String code() {
        String c = "";

        for (int i = 0; i < n + 2; ++i)
            c = c.concat("block\n");

        c = c.concat(exp.code() + "\ni32.const " + minimo + "\ni32.sub\n");

        c = c.concat("br_table"); //Se codifican todos los saltos

        for (Integer num : br_table)
            c = c.concat(" " + num.toString());

        c = c.concat("\nend\n");

        int tam = cases.size();
        for (int i = 0; i < tam; ++i) {
            Case caso = cases.get(i);
            c = c.concat(caso.code() + "\n"); //se añade el codigo de cada caso
            if (caso.getBreakPresence())
                c = c.concat("br " + (n - i) + "\n");
            c = c.concat("end\n");
        }

        for (int i = 0; i < n - tam; ++i)
            c = c.concat("end\n");

        if (defaultCase != null) { //Si hay default se añade
            c = c.concat(defaultCase.code() + "\n");
            if (defaultCase.getBreakPresence())
                c = c.concat("br 0\n");
        }

        c = c.concat("end");

        return c;
    }

    private int buscaMinimo() { //Busca el minimo de los cases
        int min = cases.get(0).getEnt().getInt();
        for (Case c : cases) {
            int aux = c.getEnt().getInt();
            if (aux < min)
                min = aux;
        }
        return min;
    }

    private int buscaMaximo() { //Busca el maximo de los cases
        int max = cases.get(0).getEnt().getInt();
        for (Case c : cases) {
            int aux = c.getEnt().getInt();
            if (aux > max)
                max = aux;
        }
        return max;
    }

    private void br_table_init() { //Inicializa la tabla de saltos poniendo los cases en orden desde el minimo siendo el 0 en adelante
        br_table = new ArrayList<Integer>(Collections.nCopies(n + 1, n));

        int num = cases.size();
        for (int i = 0; i < num; ++i) {
            int caso = cases.get(i).getEnt().getInt() - minimo;
            br_table.set(caso, i);
            System.out.println(caso);
        }
    }
}
