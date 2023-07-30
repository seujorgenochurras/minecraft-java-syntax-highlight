package io.github.seujorgenochurras.minecraftjsh.antlr.syntax.scope;

import io.github.seujorgenochurras.minecraftjsh.antlr.syntax.symbol.Symbol;

import java.util.LinkedHashMap;
import java.util.Map;

public abstract class BaseScope implements Scope {
    Scope enclosingScope; // null if global (outermost) scope
    Map<String, Symbol> symbols = new LinkedHashMap<>();

    protected BaseScope(Scope enclosingScope) { this.enclosingScope = enclosingScope;  }

    public Symbol findSymbol(String name) {
        Symbol s = symbols.get(name);
        if ( s!=null ) return s;
        // if not here, check any enclosing scope
        if ( enclosingScope != null ) return enclosingScope.findSymbol(name);
        return null; // not found
    }

    public void addSymbol(Symbol sym) {
        symbols.put(sym.getName(), sym);
        sym.setScope(this); // track the scope in each symbol
    }

    public Scope getEnclosingScope() {
        return enclosingScope;
    }

    public String toString() {
        return getScopeName()+":"+ symbols.keySet();
    }

}
