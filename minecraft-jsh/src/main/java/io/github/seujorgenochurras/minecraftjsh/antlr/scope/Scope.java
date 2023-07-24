package io.github.seujorgenochurras.minecraftjsh.antlr.scope;

import io.github.seujorgenochurras.minecraftjsh.antlr.symbol.Symbol;

public interface Scope {
    String getScopeName();

    /**
     * Where to look next for symbols
     */
    Scope getEnclosingScope();

    /**
     * Define a symbol in the current scope
     */
    void define(Symbol sym);

    /**
     * Look up name in this scope or in enclosing scope if not here
     */
    Symbol resolve(String name);
}