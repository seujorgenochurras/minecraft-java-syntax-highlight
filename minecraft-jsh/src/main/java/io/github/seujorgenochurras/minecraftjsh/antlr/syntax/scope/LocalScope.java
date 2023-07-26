package io.github.seujorgenochurras.minecraftjsh.antlr.syntax.scope;

public class LocalScope extends BaseScope{
    public LocalScope(Scope enclosingScope) {
        super(enclosingScope);
    }

    @Override
    public String getScopeName() {
        return "local";
    }
}
