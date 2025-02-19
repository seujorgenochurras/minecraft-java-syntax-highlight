package io.github.seujorgenochurras.minecraftjsh.java.antlr.syntax.symbol;

import io.github.seujorgenochurras.minecraftjsh.java.antlr.syntax.scope.Scope;

public class Symbol { // A generic programming language symbol
    String name;      // All symbols at least have a name
    Type type;
    private Scope scope;      // All symbols know what scope contains them.

    public Symbol(String name) { this.name = name; }
    public Symbol(String name, Type type) { this(name); this.type = type; }
    public String getName() { return name; }

    @Override
    public String toString() {
        return "Symbol{" +
                "name='" + name + '\'' +
                ", type=" + type
                + "scope=" +
                scope.getScopeName()+
                '}';
    }

    public Scope getScope() {
        return scope;
    }

    public void setScope(Scope scope) {
        this.scope = scope;
    }

    public Type getType() {
        return type;
    }


}