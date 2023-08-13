package io.github.seujorgenochurras.minecraftjsh.java.antlr.syntax.scope;

import io.github.seujorgenochurras.minecraftjsh.java.antlr.syntax.symbol.Symbol;

public interface Scope {
    String getScopeName();

    /**
     * Where to look next for symbols
     */
    Scope getEnclosingScope();

    /**
     * Define a symbol in the current scope
     */
    void addSymbol(Symbol sym);

    /**
     * Look up name in this scope or in enclosing scope if not here
     */
    Symbol findSymbol(String name);
}