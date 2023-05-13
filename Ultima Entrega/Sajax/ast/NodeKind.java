package ast;

public enum NodeKind { //Tipos distintos de nodos, principalmente asociados a las instrucciones
  EXPRESSION, ASIGN, DEC, DECARRAY, INPUT,PRINT, IFELSE, ELSIF, WHILE, MEMSPACE, FREE,
  FOR, TIPO, BLOQUE, FUNCIONCALL, FUNCIONDEC, PARAM, STRUCT, RETURN,
  CASE, DEFCASE, SWITCH, MAIN, PROGRAMA, GLOBVAR
}
