package io.github.seujorgenochurras.minecraftjsh.antlr.symbol;

import io.github.seujorgenochurras.minecraftjsh.antlr.scope.Scope;

import java.util.LinkedHashMap;
import java.util.Map;

public class MethodSymbol extends Symbol implements Scope {
    Map<String, Symbol> arguments = new LinkedHashMap<>();
    Scope enclosingScope;

    public MethodSymbol(String name, Type type, Scope enclosingScope) {
        super(name, type);
        this.enclosingScope = enclosingScope;
    }

    public Symbol resolve(String name) {
        Symbol s = arguments.get(name);
        if ( s!=null ) return s;
        // if not here, check any enclosing scope
        if ( getEnclosingScope() != null ) {
            return getEnclosingScope().resolve(name);
        }
        return null; // not found
    }

    public void define(Symbol sym) {
        arguments.put(sym.name, sym);
        sym.setScope(this); // track the scope in each symbol
    }

    public Scope getEnclosingScope() { return enclosingScope; }
    public String getScopeName() { return name; }

    public String toString() { return "method"+super.toString()+":"+arguments.values(); }
}