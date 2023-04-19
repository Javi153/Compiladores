package ast;

public class Ent extends E {
  private String v;
  public Ent(String v) {
   this.v = v;   
  }
  public String num() {return v;}
  public KindE kind() {return KindE.ENT;}

  @Override
  public boolean isBound() {
    return true;
  }

  @Override
  public boolean bind() {
    return true;
  }

  public String toString() {return v;}
}
