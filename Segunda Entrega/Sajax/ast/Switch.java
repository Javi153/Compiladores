package ast;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class Switch extends Statement implements ASTNode {

    private E exp;
    private ArrayList<Case> cases;
    private DefaultCase defaultCase = null;

    public Switch(E exp, ArrayList<Case> cases) {
        this.exp = exp;
        this.cases = cases;
    }

    public Switch(E exp, ArrayList<Case> cases, DefaultCase defaultCase) {
        this.exp = exp;
        this.cases = cases;
        this.defaultCase = defaultCase;
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
}
