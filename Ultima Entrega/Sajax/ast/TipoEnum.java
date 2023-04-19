package ast;

public enum TipoEnum {
    INT("int"), FLOAT("float"), BOOL("bool"), STRUCT("struct"), VOID("void");

    private String name;
    TipoEnum(String name){
        this.name = name;
    }

    public String toString(){
        return name;
    }
}
