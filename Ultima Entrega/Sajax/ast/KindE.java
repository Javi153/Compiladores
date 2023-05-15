package ast;

public enum KindE { //Clases para las expresiones, con valores predeterminados y alias para ahorrar codigo
  SUMA("suma"),RESTA("resta"),MUL("mult"),DIV("div"),MOD("mod"),POT("pot"),ENT("ent"),REAL("real"),
  IDEN("iden"),ID("igualdad"),DISTINTO("desigualdad"),OR("or"),AND("and"),NOT("not"), MENOR("menor"),
  MAYOR("mayor"),MENIGUAL("menorOIgual"), MAYIGUAL("mayorOIgual"),TRUE("true"),FALSE("false"), CALLFUN("llamadaFuncion"),
  PUNTO("accesoACampo"), FLECHA("accesoADireccion"), ASTERISCO("asterisco"), CORCHETES("accesoAPosicion"), NULL("null");

  private String name;
  KindE(String s){
    name = s;
  }

  public String alias() {
    switch (this) {
      case SUMA -> {return "add";}
      case RESTA -> {return "sub";}
      case MUL -> {return "mul";}
      case DIV -> {return "div";} // Para float, división entera es div_s
      case MOD -> {return "rem_s";}
      case ID -> {return "eq";}
      case DISTINTO -> {return "ne";}
      case MENOR -> {return "lt";} // Para float, comparación entera es lt_s
      case MAYOR -> {return "gt";} // Para float, comparación entera es gt_s
      case MENIGUAL -> {return "le";} // Para float, comparación entera es le_s
      case MAYIGUAL -> {return "ge";} // Para float, comparación entera es ge_s
      case NOT -> {return "eqz";}
      case AND -> {return "and";}
      case OR -> {return "or";}
    }
    return null;
  }

  public String toString(){
    return name;
  }
}
