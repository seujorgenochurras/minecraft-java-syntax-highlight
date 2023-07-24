package io.github.seujorgenochurras.minecraftjsh.antlr.symbol;

import io.github.seujorgenochurras.minecraftjsh.antlr.scope.Scope;

public class Symbol { // A generic programming language symbol
    String name;      // All symbols at least have a name
    Type type;
    private Scope scope;      // All symbols know what scope contains them.

    public Symbol(String name) { this.name = name; }
    public Symbol(String name, Type type) { this(name); this.type = type; }
    public String getName() { return name; }

    public String toString() {
        return getName();
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