package ast;
public class EBin extends E{ //Superclase de las expresiones que usan operadores binarios
    private E opnd1;
    private E opnd2;
    private KindE k; //Clase de operador
    private ASTNode def; //Definicion de la expresion para los operadores sobre identificadores principalmente
    private Tipo tipoOp; //Tipo asociado a la operacion

    public EBin(E opnd1, E opnd2, KindE k) {
        this.opnd1 = opnd1;
        this.opnd2 = opnd2;
        this.k = k;
        def = null;
        tipoOp = null;
    }

    public String num(){ //Expresion mas visual del operador
        String simbolo = "";
        switch(k){
            case SUMA -> {
                simbolo = "+";
            }
            case RESTA -> {
                simbolo = "-";
            }
            case MUL -> {
                simbolo = "*";
            }
            case DIV -> {
                simbolo = "/";
            }
            case MOD -> {
                simbolo = "%";
            }
            case POT -> {
                simbolo = "^";
            }
            case ID -> {
                simbolo = "==";
            }
            case DISTINTO -> {
                simbolo = "/=";
            }
            case OR -> {
                simbolo = "||";
            }
            case AND -> {
                simbolo = "&&";
            }
            case MENOR -> {
                simbolo = "<";
            }
            case MAYOR -> {
                simbolo = ">";
            }
            case MENIGUAL -> {
                simbolo = "<=";
            }
            case MAYIGUAL -> {
                simbolo = ">=";
            }
            case PUNTO -> {
                simbolo = ".";
            }
            case FLECHA -> {
                simbolo = "->";
            }
            case CORCHETES -> {
                return opnd1.num() + "["+opnd2.num()+"]";
            }
        }
        return opnd1.num() + simbolo + opnd2.num();
    }

    public E opnd1() {
        return opnd1;
    }

    public E opnd2() {
        return opnd2;
    }

    public KindE kind() {
        return k;
    }

    @Override
    public boolean type(){ //En general se basa en tipar los operandos y comprobar que sean del tipo esperado en cada caso
        boolean aux = true;
        switch (k){

            case SUMA, RESTA, MUL, DIV, MOD -> {
                aux = opnd1.type() & opnd2.type();
                aux = aux & (opnd1.isType().getTipo().equals(TipoEnum.INT) & opnd2.isType().getTipo().equals(TipoEnum.INT))
                        || (opnd1.isType().getTipo().equals(TipoEnum.FLOAT) & opnd2.isType().getTipo().equals(TipoEnum.FLOAT));
                if(!aux){
                    System.out.println("Error: se esperaba un tipo entero o float en la operacion " + num() + " pero se encontro " + opnd1.isType().getTipo() + " y " + opnd2.isType().getTipo());
                }
                else{
                    tipoOp = opnd1.isType();
                }
            }
            case POT -> {
                aux = opnd1.type() & opnd2.type();
                aux = aux & (opnd1.isType().getTipo().equals(TipoEnum.INT) & opnd2.isType().getTipo().equals(TipoEnum.INT))
                        || (opnd1.isType().getTipo().equals(TipoEnum.FLOAT) & opnd2.isType().getTipo().equals(TipoEnum.INT));
                if(!aux){
                    System.out.println("Error: se esperaba un tipo entero o float en la base y un entero en el exponente en la operacion " + num() + " pero se encontro " + opnd1.isType().getTipo() + " y " + opnd2.isType().getTipo());
                }
                else{
                    tipoOp = opnd1.isType();
                }
            }
            case OR, AND -> {
                aux = opnd1.type() & opnd2.type() & opnd1.isType().getTipo().equals(TipoEnum.BOOL) & opnd2.isType().getTipo().equals(TipoEnum.BOOL);
                if(!aux){
                    System.out.println("Error: se esperaba un tipo booleano en la operacion " + num() + " pero se encontro " + opnd1.isType().getTipo() + " y " + opnd2.isType().getTipo());
                }
                tipoOp = new Tipo(TipoEnum.BOOL);
            }
            case MENOR, MAYOR, MENIGUAL, MAYIGUAL, ID, DISTINTO -> {
                aux = opnd1.type() & opnd2.type();
                aux = aux & (opnd1.isType().getTipo().equals(TipoEnum.INT) & opnd2.isType().getTipo().equals(TipoEnum.INT))
                        || (opnd1.isType().getTipo().equals(TipoEnum.FLOAT) & opnd2.isType().getTipo().equals(TipoEnum.FLOAT))
                        || (opnd1.isType().getTipo().equals(TipoEnum.BOOL) & opnd2.isType().getTipo().equals(TipoEnum.BOOL));
                if(!aux){
                    System.out.println("Error: se esperaba un tipo entero, float o booleano en la operacion " + num() + " pero se encontro " + opnd1.isType().getTipo() + " y " + opnd2.isType().getTipo());
                }
                else {
                    tipoOp = opnd1.isType();
                }
            }
            case PUNTO -> { //Punto, flecha y corchetes son mas complicados
                if(opnd2.kind().equals(KindE.CORCHETES)){ //En el caso de opnd1.(opnd2[]) cambiamos la estructura del arbol por (opnd1.opnd2)[] porque resulta mas facil
                    EBin eb = new EBin(new EBin(opnd1, opnd2.opnd1(), KindE.PUNTO), opnd2.opnd2(), KindE.CORCHETES);
                    boolean res = eb.type();
                    tipoOp = eb.isType();
                    return res;
                }
                if(!opnd1.type() || !opnd2.type()){
                    System.out.println("Error: los tipos de " + opnd1.num() + " y " + opnd2.num() + " no coinciden");
                }
                Tipo t = opnd1.isType();
                if(t == null || t.getTipo() != TipoEnum.STRUCT){
                    System.out.println("Error: el operando izquierdo de un punto debe ser un struct: " + opnd1.num());
                    return false;
                }
                String auxname;
                TipoStruct t2;
                if(!t.isParam()) { //Buscamos el nombre del struct al que se refiere opnd1
                        auxname=((TipoStruct) t).getId();
                        t2 = (TipoStruct) t;
                }
                else{
                        auxname = ((TipoStruct)((TipoParam)t).getTipoParam()).getId();
                        t2 = (TipoStruct)((TipoParam)t).getTipoParam();
                }
                if(opnd2.kind().equals(KindE.CALLFUN)){ //Para las funciones haremos el tipado sobre nombreDelStruct.funcion() pues asi esta guardado en la tabla de tipos
                    for(ASTNode a : ((Struct)t2.getDef()).getAtt().getList()){
                        if(a.nodeKind().equals(NodeKind.FUNCIONDEC) && ((DecFuncion)a).getName().equals(((LlamadaFuncion)opnd2).getName())){
                            opnd2.setDef(a); //Reemplazamos la defincion para evitar mal asociamiento en binding por nombres repetidos de atributos de distintos structs
                        }
                    }
                    LlamadaFuncion f = new LlamadaFuncion(auxname + "." + ((LlamadaFuncion)opnd2).getName(), ((LlamadaFuncion)opnd2).getParlist());
                    boolean res = f.type();
                    tipoOp = f.isType();
                    return res;
                }
                else{ //Para los atributos del struct hacemos lo mismo, cambiamos el opnd1 por el nombre del struct para tipar
                    for(ASTNode a : ((Struct)t2.getDef()).getAtt().getList()){
                        if((a.nodeKind().equals(NodeKind.DEC) && ((Dec)a).getName().equals((opnd2.num()))) ||
                                (a.nodeKind().equals(NodeKind.DECARRAY) && ((DecArray)a).getName().equals((opnd2).num()))){
                            opnd2.setDef(a); //Reemplazamos la defincion para evitar mal asociamiento en binding por nombres repetidos de atributos de distintos structs
                        }
                    }
                    Ident Id = new Ident(auxname + "." + ((Ident)opnd2).num());
                    boolean res = Id.type();
                    tipoOp = Id.isType();
                    return res;
                }
            }
            case FLECHA -> { //La flecha funciona igual que el punto, solo que el operando izquierdo debe ser un puntero
                if(!opnd1.type() || !opnd2.type()){
                    System.out.println("Error: los tipos de " + opnd1.num() + " y " + opnd2.num() + " no coinciden");
                }
                Tipo t = opnd1.isType();
                if(t.isParam()){
                    t = ((TipoParam)t).getTipoParam();
                }
                if(!t.isPointer()){
                    System.out.println("Error: el operando izquierdo de una flecha debe ser un puntero en " + num());
                    return false;
                }
                EBin eb = new EBin(new EUnar(opnd1, KindE.ASTERISCO), opnd2, KindE.PUNTO);
                boolean res = eb.type();
                tipoOp = eb.isType();
                return res;
            }
            case CORCHETES -> { //Para los corchetes comprobamos que el operando izquierdo sea un array y que el derecho sea un entero
                if(!opnd1.type() || !opnd2.type() || opnd2.isType().getTipo() != TipoEnum.INT){
                    System.out.println("Error: el operando derecho de corchetes debe ser un entero en " + num());
                    aux = false;
                }
                Tipo t = opnd1.isType();
                if(t.getTipo() != TipoEnum.ARRAY && t.getTipo() != TipoEnum.PUNTERO){
                    System.out.println("Error: el operando izquierdo de corchetes debe ser un array en " + num());
                    return false;
                }
                tipoOp = isType();
            }
        }
        return aux;
    }

    @Override
    public Tipo isType(){ //Esta funcion devuelve el tipo de la operacion
        switch(k){
            case SUMA, RESTA, MUL, DIV, MOD, POT, OR, AND -> {
                return opnd1.isType();
            }
            case MENOR, MAYOR, MENIGUAL, MAYIGUAL, ID, DISTINTO -> {
                return new Tipo(TipoEnum.BOOL);
            }
            case PUNTO -> {//Procedemos igual que en type
                if(opnd2.kind().equals(KindE.CORCHETES)){
                    return new EBin(new EBin(opnd1, opnd2.opnd1(), KindE.PUNTO),opnd2.opnd2(), KindE.CORCHETES).isType();
                }
                Tipo t = opnd1.isType();
                if(t == null || t.getTipo() != TipoEnum.STRUCT){
                    return new Tipo(TipoEnum.VOID);
                }
                String auxname;
                if(!t.isParam()) {
                    auxname=((TipoStruct) t).getId();
                }
                else{
                    auxname = ((TipoStruct)((TipoParam)t).getTipoParam()).getId();
                }
                if(opnd2.kind().equals(KindE.CALLFUN)){
                    LlamadaFuncion f = new LlamadaFuncion(auxname + "." + ((LlamadaFuncion)opnd2).getName(), ((LlamadaFuncion)opnd2).getParlist());
                    Tipo taux = buscaTipo(f.getName());
                    if(taux != null){
                        return taux;
                    }
                    else{
                        return new Tipo(TipoEnum.VOID);
                    }
                }
                else{
                    return buscaTipo(auxname + "." + opnd2.num());
                }
            }
            case FLECHA -> { //Flecha es azucar sintactico de (*struct).atributo
                Tipo t = opnd1.isType();
                if(t.isParam()){
                    t = ((TipoParam)t).getTipoParam();
                }
                if(!t.isPointer()){
                    return new Tipo(TipoEnum.VOID);
                }
                return new EBin(new EUnar(opnd1, KindE.ASTERISCO), opnd2, KindE.PUNTO).isType();
            }
            case CORCHETES -> {
                Tipo taux = opnd1.isType();
                if(taux.isParam()){
                    taux = ((TipoParam)taux).getTipoParam();
                }
                if(taux.getTipo() != TipoEnum.ARRAY && taux.getTipo() != TipoEnum.PUNTERO){
                    return new Tipo(TipoEnum.VOID);
                }
                else if(taux.getTipo() == TipoEnum.PUNTERO){
                    return ((Puntero)taux).getTipoPointer();
                }
                else if(((TipoArray)taux).getTam() == 1){
                    return ((TipoArray)taux).getTipoBasico();
                }
                else{
                    return new TipoArray(((TipoArray)taux).getTipoBasico(), ((TipoArray)taux).getTam()-1);
                }
            }
        }
        return new Tipo(TipoEnum.VOID);
    }

    @Override
    public boolean bind() {
        switch(k){
            case PUNTO, FLECHA -> {
                if(opnd2.kind().equals(KindE.CALLFUN)){
                    boolean res = opnd1.bind();
                    if(res & (new LlamadaFuncion("."+((LlamadaFuncion)opnd2).getName(), ((LlamadaFuncion)opnd2).getParlist()).bind())){
                        opnd2.setDef(buscaId("."+((LlamadaFuncion)opnd2).getName()));
                    }
                    def = opnd2.getDef();
                    return res;
                }
                else if(opnd2.kind().equals(KindE.IDEN)){
                    boolean res = opnd1.bind();
                    ASTNode aux = buscaId("."+opnd2.num());
                    if(aux == null){
                        System.out.println("Error: el identificador " + opnd2.num() + " no está definido en el struct " + opnd1.num());
                        return false;
                    } else{
                        opnd2.setDef(aux);
                        def = opnd2.getDef();
                        return res;
                    }
                }
                else if(opnd2.kind() == KindE.CORCHETES){
                    boolean res = true;
                    EBin aux = new EBin(opnd1, opnd2.opnd1(), KindE.PUNTO);
                    if(aux.bind()){
                        def = aux.getDef();
                        opnd2.setDef(def);
                        E opndaux = opnd2.opnd1();
                        while(opndaux.kind() == KindE.CORCHETES){
                            opndaux.setDef(def);
                            opndaux = ((EBin)opndaux).opnd1();
                        }
                    }
                    else{
                        res = false;
                    }
                    res = res & opnd2.opnd2().bind();
                    return res;
                }
                else{
                    System.out.println("Error: el operando derecho de un punto o flecha debe ser un identificador o un array");
                    return false;
                }
            }
            default ->{
                boolean res = opnd1.bind() & opnd2.bind();
                def = opnd1.getDef();
                return res;
            }
        }
    }

    @Override
    public String code() {
        String c = "";
        switch(k) {
            case SUMA, RESTA, MUL, MOD, ID, DISTINTO -> { //Valores de los operando y la operacion guardada en el alias
                return opnd1.code() + "\n" + opnd2.code() + "\n" + tipoOp.getTipo().alias() + "." + k.alias();
            }
            case DIV -> {
                c = opnd1.code() + "\n" + opnd2.code() + "\n" + tipoOp.getTipo().alias() + "." + k.alias();
                return tipoOp.getTipo() == TipoEnum.FLOAT ? c : c + "_s";
            }
            case POT -> { //Para las potencias hemos desarrollado una funcion especifica, la llamamos
                c = opnd1.code() + "\n" + opnd2.code() + "\n";
                return tipoOp.getTipo() == TipoEnum.INT ? c + "call $potInt" : c + "call $potFloat";
            }
            case OR, AND -> {
                return opnd1.code() + "\n" + opnd2.code() + "\n" + TipoEnum.BOOL.alias() + "." + k.alias() + "\n";
            }
            case MENOR, MAYOR, MENIGUAL, MAYIGUAL -> {
                c = opnd1.code() + "\n" + opnd2.code() + "\n" + tipoOp.getTipo().alias() + "." + k.alias();
                return tipoOp.getTipo() == TipoEnum.FLOAT ? c + "\n" : c + "_s\n";
            }
            case CORCHETES -> { //Para los corchetes buscamos la direccion con codeDesig y hacemos un load
                c = codeDesig();
                if(!tipoOp.getTipo().equals(TipoEnum.STRUCT)){
                    c = c.concat(tipoOp.getTipo().alias() + ".load");
                }
            }
            case PUNTO, FLECHA -> { //Igual con estos dos
                c = codeDesig();
                if(!tipoOp.getTipo().equals(TipoEnum.STRUCT)) {
                    c = c.concat(tipoOp.getTipo().alias() + ".load");
                }
                return c;
            }
            default -> {}
        }
        return c;
    }

    public String codeDesig() { //Codigo para obtener la direccion de memoria de un designador
        String c = "";
        switch(k) {
            case PUNTO -> {
                if(opnd2.kind().equals(KindE.CORCHETES)){ //Hacemos igual que en tipado de punto
                    EBin eb2 = new EBin(opnd1, opnd2.opnd1(), KindE.PUNTO);
                    EBin eb = new EBin(eb2, opnd2.opnd2(), KindE.CORCHETES);
                    eb.setDef(def); //Hacemos que las clases nuevas tengan las mismas definciones pues lo necesitamos en otro punto
                    eb2.setDef(def);
                    eb.setTipoOp(tipoOp);
                    TipoArray aux;
                    if(tipoOp.getTipo().equals(TipoEnum.ARRAY)) { //Si hay corchetes debemos ver si estan en el tipo mas basico o no
                        aux = new TipoArray(((TipoArray) tipoOp).getTipoBasico(), ((TipoArray) tipoOp).getTam() + 1);
                    }
                    else{
                        aux = new TipoArray(tipoOp, 1);
                    }
                    eb2.setTipoOp(aux);
                    return eb.codeDesig();
                }
                int offset;
                if(opnd1.getDef().nodeKind().equals(NodeKind.DEC)) { //Dependiendo de la definicion, buscamos el atributo en el struct y veremos cual es su delta asociado dentro del struct, pues esa es la direccion especifica que queremos
                    Tipo auxt = ((Dec) opnd1.getDef()).getTipo();
                    if(auxt.getTipo().equals(TipoEnum.PUNTERO)){
                        auxt = ((Puntero)auxt).getTipoPointer();
                    }
                    offset = ((Struct) ((TipoStruct) auxt).getDef()).getOffset((Ident) opnd2);
                }
                else if (opnd1.getDef().nodeKind().equals(NodeKind.DECARRAY)){
                    offset = ((Struct) ((TipoStruct) ((DecArray) opnd1.getDef()).getTipo()).getDef()).getOffset((Ident) opnd2);
                }
                else if(opnd1.getDef().nodeKind().equals(NodeKind.FUNCIONDEC)){
                    offset = ((Struct) ((TipoStruct) ((DecFuncion)opnd1.getDef()).getTipo()).getDef()).getOffset((Ident) opnd2);
                }
                else{
                    offset = ((Struct) ((TipoStruct) ((TipoParam)((Parametro)opnd1.getDef()).getTipo()).getTipoParam()).getDef()).getOffset((Ident) opnd2);
                }
                return opnd1.codeDesig() + "\ni32.const " + offset + "\ni32.add\n";
            }
            case FLECHA -> {
                // d->id es azúcar sintáctico de (*d).id, se procede practicamente igual que en punto
                if(opnd2.kind().equals(KindE.CORCHETES)){
                    EBin eb2 = new EBin(opnd1, opnd2.opnd1(), KindE.PUNTO);
                    EBin eb = new EBin(eb2, opnd2.opnd2(), KindE.CORCHETES);
                    eb.setDef(def);
                    eb2.setDef(def);
                    eb.setTipoOp(tipoOp);
                    return eb.codeDesig();
                }
                int offset;
                if(opnd1.getDef().nodeKind().equals(NodeKind.DEC)) {
                    Tipo auxt = ((Dec) opnd1.getDef()).getTipo();
                    if(auxt.getTipo().equals(TipoEnum.PUNTERO)){
                        auxt = ((Puntero)auxt).getTipoPointer();
                    }
                    offset = ((Struct) ((TipoStruct) auxt).getDef()).getOffset((Ident) opnd2);
                }
                else if (opnd1.getDef().nodeKind().equals(NodeKind.DECARRAY)){
                    offset = ((Struct) ((TipoStruct) ((DecArray) opnd1.getDef()).getTipo()).getDef()).getOffset((Ident) opnd2);
                }
                else if(opnd1.getDef().nodeKind().equals(NodeKind.FUNCIONDEC)){
                    offset = ((Struct) ((TipoStruct) ((DecFuncion)opnd1.getDef()).getTipo()).getDef()).getOffset((Ident) opnd2);
                }
                else{
                    Tipo auxt = ((TipoParam)((Parametro)opnd1.getDef()).getTipo()).getTipoParam();
                    if(auxt.isPointer()){
                        auxt = ((Puntero)auxt).getTipoPointer();
                    }
                    offset = ((Struct) ((TipoStruct) auxt).getDef()).getOffset((Ident) opnd2);
                }
                return opnd1.codeDesig() + "\ni32.load\ni32.const " + offset + "\ni32.add\n";
            }
            case CORCHETES -> {
                Tipo tOp = tipoOp;
                if (tOp.isParam())
                    tOp = ((TipoParam) tOp).getTipoParam();
                if(!tOp.getTipo().equals(TipoEnum.ARRAY)) {
                    if (opnd1.getDef().nodeKind().equals(NodeKind.DEC) || opnd1.getDef().nodeKind().equals(NodeKind.PARAM)) { //Si la declaracion es DEC y no DECARRAY es que en vd es un puntero usando corchetes
                        return opnd1.codeDesig() + "\ni32.load\ni32.const " + tOp.size() + "\n"
                                + opnd2.code() + "\ni32.mul\ni32.add\n";
                    }
                    else {
                        return opnd1.codeDesig() + "\ni32.const " + tOp.size() + "\n"
                                + opnd2.code() + "\ni32.mul\ni32.add\n";
                    }
                }
                else {
                    TipoArray aux = (TipoArray) tOp;
                    int tam = aux.getTam();
                    DecArray auxdef = (DecArray) def;
                    int mult = 1;
                    for(int i = auxdef.dimSize() - 1; i > auxdef.dimSize() - tam - 1; --i){
                        mult = mult * auxdef.getDims().get(i).getInt(); //Iterativamente buscamos la direccion en cada dimension
                    }
                    return opnd1.codeDesig() + "\ni32.const " + ((TipoArray) tOp).getTipoBasico().size() * mult + "\n"
                            + opnd2.code() + "\ni32.mul\ni32.add\n"; //Ahora opnd1 nos da la direccion basica del array y tenemos calculado el offset, añadimos el valor de opnd2 ahora
                }
            }
            default -> {}
        }
        return c;
    }

    public String toString() {
        return k.toString()+"(" + opnd1().toString() + "," + opnd2().toString() + ")";
    }

    @Override
    public ASTNode getDef() {
        return def;
    }

    @Override
    public void setDef(ASTNode n) {
        def = n;
    }

    public Tipo getTipoOp() {
        return tipoOp;
    }

    public void setTipoOp(Tipo t) {
        tipoOp = t;
    }

    public void setDelta(int prof){
        this.prof = prof;
        opnd1.setDelta(prof);
        opnd2.setDelta(prof);
    }
}