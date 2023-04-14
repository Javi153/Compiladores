package alex2;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Vector;

public class Main {
   public static void main(String[] args) throws FileNotFoundException, IOException {
       Vector<String> palabras = new Vector<String>();
       Vector<String> patrones = new Vector<String>();
       Reader input = new InputStreamReader(new FileInputStream(args[0]));
     AnalizadorLexicoTiny al = new AnalizadorLexicoTiny(input);
     UnidadLexica unidad;
     do {
       unidad = al.yylex();
         if(unidad.clase() == 2){
            palabras.add(unidad.lexema());
         }
         else if(unidad.clase() == 3){
             patrones.add(unidad.lexema());
         }
       System.out.println(unidad);
     }
     while (unidad.clase() != ClaseLexica.EOF);
       System.out.println("PALABRAS:");
       for(String s : palabras){
           System.out.println(s);
       }
       System.out.println("PATRONES:");
       for(String s : patrones){
           System.out.println(s);
       }
    }        
} 
