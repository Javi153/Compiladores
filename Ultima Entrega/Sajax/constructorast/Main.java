package constructorast;

import java.io.*;
import java.text.ParseException;

import ast.ASTNode;
import sajax_lex.AnalizadorLexicoSajax;

public class Main {
   public static void main(String[] args) throws Exception {
       Reader input = new InputStreamReader(new FileInputStream(args[0]));
       AnalizadorLexicoSajax sajax = new AnalizadorLexicoSajax(input);
       ConstructorASTExp constructorast = new ConstructorASTExp(sajax);
       ASTNode result = null;
       boolean success = true;
       String s = "";
       try {
           result = (ASTNode) constructorast.parse().value;
           System.out.println(result);
       } catch (Exception e) {
           System.out.println("Error de parseo.");
           e.printStackTrace();
           success = false;
       }
       if (result != null && success) {
           success = result.bind();
       }
       if (result != null && success) {
           success = result.type();
       }
       if (result != null && success) {
           result.setDelta(0);
           s = result.code();
           System.out.println(s);
       }
       if(success) {
           try {
               String ruta = "result.wat";
               File file = new File(ruta);
               if (!file.exists()) {
                   file.createNewFile();
               }
               FileWriter fw = new FileWriter(file);
               BufferedWriter bw = new BufferedWriter(fw);
               bw.write(s);
               bw.close();
           } catch (Exception e) {
               e.printStackTrace();
           }
       }
   }
}   
   
