package io.github.seujorgenochurras.minecraftjsh.antlr.syntax.listener.reference;

import io.github.seujorgenochurras.minecraftjsh.antlr.generated.JavaParser;
import io.github.seujorgenochurras.minecraftjsh.antlr.generated.JavaParserBaseListener;
import io.github.seujorgenochurras.minecraftjsh.antlr.syntax.symbol.MethodSymbol;
import io.github.seujorgenochurras.minecraftjsh.antlr.syntax.symbol.Symbol;
import io.github.seujorgenochurras.minecraftjsh.antlr.syntax.scope.GlobalScope;
import io.github.seujorgenochurras.minecraftjsh.antlr.syntax.scope.Scope;
import org.antlr.v4.runtime.tree.ParseTreeProperty;

public class OnUnknownReference extends JavaParserBaseListener {
    private final ParseTreeProperty<Scope> scopes;
    private final GlobalScope globalScope;

    private Scope currentScope;

    public OnUnknownReference(ParseTreeProperty<Scope> scopes, GlobalScope globalScope) {
        this.scopes = scopes;
        this.globalScope = globalScope;
    }

    @Override
    public void exitVar(JavaParser.VarContext ctx) {
        String varName = ctx.start.getText();
        Symbol varSymbol = currentScope.findSymbol(varName);
        if (varSymbol == null) {
            System.err.println("Variable '" + varName + "' is not defined");
        } else if (varSymbol instanceof MethodSymbol && (varSymbol.getType().typeName().equals("void"))) {
            System.err.println("Variable '" + varName + "' is a method");
        }
    }

    @Override
    public void enterCompilationUnit(JavaParser.CompilationUnitContext ctx) {
        this.currentScope = globalScope;
    }

    @Override
    public void enterMethodDeclaration(JavaParser.MethodDeclarationContext ctx) {
        currentScope = scopes.get(ctx);
    }

    @Override
    public void exitMethodDeclaration(JavaParser.MethodDeclarationContext ctx) {
        currentScope = currentScope.getEnclosingScope();
    }

    @Override
    public void enterBlock(JavaParser.BlockContext ctx) {
        currentScope = scopes.get(ctx);
    }

    @Override
    public void exitBlock(JavaParser.BlockContext ctx) {
        currentScope = currentScope.getEnclosingScope();
    }


}
