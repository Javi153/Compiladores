package ast;

import java.util.ArrayList;

public class Case extends DefaultCase implements ASTNode { //Vemos que funciona como un caso especifico del default

    private Ent ent; //el numero entero asociado al case de un switch

    public Case(Ent ent, ArrayList<Statement> stlist, boolean breakPresence) {
        super(stlist, breakPresence);
        this.ent = ent;
        tipoBloque = "case";
    }


    @Override
    public NodeKind nodeKind() {
        return NodeKind.CASE;
    }

    @Override
    public String toString() {
        String s = tipoBloque + "(" + ent.toString();
        for (int i = 0; i < stlist.size(); ++i) {
            s = s.concat("," + stlist.get(i).toString());
        }
        s = s.concat("," + breakPresence + ")");
        return s;
    }

    //No se implementa bind, type ni code, coge el de default

    public Ent getEnt() { return ent; }

}
