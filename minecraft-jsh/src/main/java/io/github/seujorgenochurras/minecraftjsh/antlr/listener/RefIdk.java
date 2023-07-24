package io.github.seujorgenochurras.minecraftjsh.antlr.listener;

import io.github.seujorgenochurras.minecraftjsh.antlr.parser.JavaParser;
import io.github.seujorgenochurras.minecraftjsh.antlr.parser.JavaParserBaseListener;
import io.github.seujorgenochurras.minecraftjsh.antlr.scope.GlobalScope;
import io.github.seujorgenochurras.minecraftjsh.antlr.scope.Scope;
import io.github.seujorgenochurras.minecraftjsh.antlr.symbol.MethodSymbol;
import io.github.seujorgenochurras.minecraftjsh.antlr.symbol.Symbol;
import org.antlr.v4.runtime.tree.ParseTreeProperty;

public class RefIdk extends JavaParserBaseListener {
    private final ParseTreeProperty<Scope> scopes;
    private final GlobalScope globals;

    Scope currentScope;

    public RefIdk(ParseTreeProperty<Scope> scopes, GlobalScope globals) {
        this.scopes = scopes;
        this.globals = globals;
    }

    @Override
    public void enterCompilationUnit(JavaParser.CompilationUnitContext ctx) {
        currentScope = globals;
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

    @Override
    public void exitVariableInitializer(JavaParser.VariableInitializerContext ctx) {
        String varName = ctx.start.getText();
        Symbol vara = currentScope.resolve(varName);
        if ( vara==null ) {
            System.err.println("var doesn't exists");
        }
        if ( vara instanceof MethodSymbol) {
            System.err.println("is not a method");
        }
    }
}
