package ast;

public enum KindE {
  SUMA("suma"),RESTA("resta"),MUL("mult"),DIV("div"),MOD("mod"),POT("pot"),ENT("ent"),REAL("real"),
  IDEN("iden"),ID("igualdad"),DISTINTO("desigualdad"),OR("or"),AND("and"),NOT("not"), MENOR("menor"),
  MAYOR("mayor"),MENIGUAL("menorOIgual"), MAYIGUAL("mayorOIgual"),TRUE("true"),FALSE("false"), CALLFUN("llamadaFuncion"),
  PUNTO("accesoACampo"), FLECHA("accesoADireccion"), ASTERISCO("asterisco"), CORCHETES("accesoAPosicion"), NULL("null");

  private String name;
  KindE(String s){
    name = s;
  }

  public String toString(){
    return name;
  }
}
