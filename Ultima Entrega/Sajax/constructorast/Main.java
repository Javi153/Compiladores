package constructorast;

import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.text.ParseException;

import ast.ASTNode;
import sajax_lex.AnalizadorLexicoSajax;

public class Main {
   public static void main(String[] args) throws Exception {
     Reader input = new InputStreamReader(new FileInputStream(args[0]));
       AnalizadorLexicoSajax sajax = new AnalizadorLexicoSajax(input);
       ConstructorASTExp constructorast = new ConstructorASTExp(sajax);
     ASTNode result = null;
     try {
         result = (ASTNode)constructorast.parse().value;
         System.out.println(result);
     }
     catch (Exception e) {
         System.out.println("Error de parseo.");
     }
     boolean success = true;
     if(result != null) {
         success = result.bind();
     }
     if(result != null && success){
         success = result.type();
     }
     if(result != null && success){
         result.setDelta(0);
         System.out.println(result.code());
     }
   }
}   
   
