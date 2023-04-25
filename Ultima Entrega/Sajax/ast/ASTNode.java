package ast;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Stack;

public interface ASTNode {
    public static Stack<HashMap<String, ASTNode>> s = new Stack<>();
    public static Stack<HashMap<String, Tipo>> sTipo = new Stack<>();

    public default void insertaId(String id, ASTNode nodo){
        if(!s.empty()){
            s.peek().put(id, nodo);
        }
    }

    public default ASTNode buscaId(String id){
        boolean encontrado = false;
        ASTNode result = null;
        Stack<HashMap<String, ASTNode>> saux = new Stack<>();
        while(!encontrado && !s.empty()){
            if(s.peek().containsKey(id)){
                encontrado = true;
                result = s.peek().get(id);
            }
            else{
                saux.add(s.pop());
            }
        }
        while(!saux.empty()){
            s.add(saux.pop());
        }
        return result;
    }

    public boolean type(); // for the future
    public boolean bind();  // for the future
    // public ?? generateCode() // for the future
    public NodeKind nodeKind();
    public String toString();
}