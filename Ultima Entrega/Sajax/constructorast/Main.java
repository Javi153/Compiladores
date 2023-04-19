package constructorast;

import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import sajax_lex.AnalizadorLexicoSajax;

public class Main {
   public static void main(String[] args) throws Exception {
     Reader input = new InputStreamReader(new FileInputStream(args[0]));
	 AnalizadorLexicoSajax sajax = new AnalizadorLexicoSajax(input);
	 ConstructorASTExp constructorast = new ConstructorASTExp(sajax);
     try {
         System.out.println(constructorast.parse().value);
     }
     catch (Exception e) {
         System.out.println("Error de parseo.");
     }
   }
}   
   
