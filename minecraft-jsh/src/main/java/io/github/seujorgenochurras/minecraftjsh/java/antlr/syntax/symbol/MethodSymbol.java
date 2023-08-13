package io.github.seujorgenochurras.minecraftjsh.java.antlr.syntax.symbol;

import io.github.seujorgenochurras.minecraftjsh.java.antlr.syntax.scope.Scope;

import javax.annotation.Nullable;
import java.util.LinkedHashMap;
import java.util.Map;

public class MethodSymbol extends Symbol implements Scope {
    Map<String, Symbol> arguments = new LinkedHashMap<>();
    Scope enclosingScope;

    public MethodSymbol(String name, Type type, Scope enclosingScope) {
        super(name, type);
        this.enclosingScope = enclosingScope;
    }

    /***
     * @param name symbol name
     * @return symbol found or null if not found
     */
    @Nullable
    public Symbol findSymbol(String name) {
        Symbol symbolFound = arguments.get(name);
        if (symbolFound != null) return symbolFound;

        // if not here, check for enclosing scopes
        if (getEnclosingScope() != null) {
            return getEnclosingScope().findSymbol(name);
        }
        return null; // not found
    }

    public void addSymbol(Symbol sym) {
        arguments.put(sym.name, sym);
        sym.setScope(this); // track the scope in each symbol
    }

    public Scope getEnclosingScope() {
        return enclosingScope;
    }

    public String getScopeName() {
        return name;
    }

    @Override
    public String toString() {
        return "method" + super.toString() + ":" + arguments.values();
    }
}