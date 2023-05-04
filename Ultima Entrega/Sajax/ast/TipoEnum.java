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

    public String alias() {
        switch (this) {
            case INT, BOOL -> {return "i32";}
            case FLOAT -> {return "f32";}
            default -> {return null;}
        }
    }

    public int size() {     //TODO esto no se puede hacer aqui
        switch(this) {
            case INT, BOOL, FLOAT -> {return 4;} //Aqui no es 4? Al final son los bytes, no los bits
            default -> {return 0;}
        }
    }
}
