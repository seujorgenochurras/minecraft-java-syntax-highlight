package io.github.seujorgenochurras.minecraftjsh.java.antlr.syntax.listener.reference;

import io.github.seujorgenochurras.minecraftjsh.java.antlr.generated.JavaParser;
import io.github.seujorgenochurras.minecraftjsh.java.antlr.generated.JavaParserBaseListener;
import io.github.seujorgenochurras.minecraftjsh.java.antlr.syntax.symbol.MethodSymbol;
import io.github.seujorgenochurras.minecraftjsh.java.antlr.syntax.symbol.Symbol;
import io.github.seujorgenochurras.minecraftjsh.java.antlr.syntax.scope.GlobalScope;
import io.github.seujorgenochurras.minecraftjsh.java.antlr.syntax.scope.Scope;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStreamRewriter;
import org.antlr.v4.runtime.tree.ParseTreeProperty;

public class OnUnknownReference extends JavaParserBaseListener {
    private final ParseTreeProperty<Scope> scopes;
    private final GlobalScope globalScope;

    private Scope currentScope;

    private final TokenStreamRewriter rewriter;

    private boolean isInsideVarInitialization = false;

    public OnUnknownReference(ParseTreeProperty<Scope> scopes, GlobalScope globalScope, TokenStreamRewriter rewriter) {
        this.scopes = scopes;
        this.globalScope = globalScope;
        this.rewriter = rewriter;
    }

    @Override
    public void exitVar(JavaParser.VarContext ctx) {
        if(!isInsideVarInitialization) return;
        Token varToken = ctx.start;
        String varName = varToken.getText();
        Symbol varSymbol = currentScope.findSymbol(varName);

        if (varSymbol == null ||
                varSymbol instanceof MethodSymbol && (varSymbol.getType().typeName().equals("void"))) {
            rewriter.insertBefore(varToken, "§c§n");
            rewriter.insertAfter(varToken, "§r");
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

    @Override
    public void enterVariableInitializer(JavaParser.VariableInitializerContext ctx) {
        this.isInsideVarInitialization = true;
    }

    @Override
    public void exitVariableInitializer(JavaParser.VariableInitializerContext ctx) {
        this.isInsideVarInitialization = false;
    }
}
