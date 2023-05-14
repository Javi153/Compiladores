package ast;

public enum TipoEnum {
    INT("int"), FLOAT("float"), BOOL("bool"), STRUCT("struct"), VOID("void"), PUNTERO("puntero"), ARRAY("array");

    private String name;
    TipoEnum(String name){
        this.name = name;
    }

    public String toString(){
        return name;
    }

    public String alias() { //Alias para facilitar la generacion de codigo
        switch (this) {
            case INT, BOOL, PUNTERO -> {return "i32";}
            case FLOAT -> {return "f32";}
            default -> {return null;}
        }
    }

    public int size() { //Tamaño del tipo
        switch(this) {
            case INT, BOOL, FLOAT -> {return 4;}
            default -> {return 0;}
        }
    }
}
