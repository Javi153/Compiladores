package asint;

import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import sajax_lex.AnalizadorLexicoSajax;

public class Main {
   public static void main(String[] args) throws Exception {
     Reader input = new InputStreamReader(new FileInputStream(args[0]));
	 AnalizadorLexicoSajax alex = new AnalizadorLexicoSajax(input);
	 AnalizadorSintacticoSajax asint = new AnalizadorSintacticoSajax(alex);
	 asint.parse();
 }
}   
   
