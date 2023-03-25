package ast;

import java.util.ArrayList;

public class DecFuncion extends Statement implements ASTNode{
    private Tipo tipo;
    private String name;
    private ArrayList<Parametro> parlist;
    private Bloque cuerpo;

    public DecFuncion(Tipo tipo, String name, ArrayList<Parametro> parlist, Bloque cuerpo){
        this.tipo = tipo;
        this.name = name;
        this.parlist = parlist;
        this.cuerpo = cuerpo;
    }

    @Override
    public NodeKind nodeKind(){
        return NodeKind.FUNCIONDEC;
    }

    public String toString(){
        String s;
        if(cuerpo == null) {
            s = new String("decFuncionInline(" + tipo.toString() + "," + name);
            for (Parametro p : parlist) {
                s = s.concat("," + p.toString());
            }
            s = s.concat(")");
        }
        else{
            s = new String("decFuncion(" + tipo.toString() + "," + name);
            for (Parametro p : parlist) {
                s = s.concat("," + p.toString());
            }
            s = s.concat(","+cuerpo.toString()+")");
        }
        return s;
    }
}
