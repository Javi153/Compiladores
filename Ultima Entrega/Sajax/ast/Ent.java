package ast;

public class Ent extends E { //Clase de los numeros enteros
  private String v;
  public Ent(String v) {
   this.v = v;   
  }
  public String num() {return v;}
  public KindE kind() {return KindE.ENT;}

  @Override
  public Tipo isType() {
    return new Tipo(TipoEnum.INT);
  }

  @Override
  public boolean type() {
    return true;
  }

  @Override
  public boolean bind() {
    return true;
  }

  @Override
  public String code() {return "i32.const " + v; }

  public String toString() {return v;}

  @Override
  public ASTNode getDef() {
    return null;
  }

  public int getInt() {
    return Integer.parseInt(v);
  }
}
