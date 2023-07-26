package io.github.seujorgenochurras.minecraftjsh.antlr.syntax;

import io.github.seujorgenochurras.minecraftjsh.antlr.parser.JavaParser;
import io.github.seujorgenochurras.minecraftjsh.antlr.parser.JavaParserBaseListener;
import io.github.seujorgenochurras.minecraftjsh.antlr.syntax.symbol.MethodSymbol;
import io.github.seujorgenochurras.minecraftjsh.antlr.syntax.symbol.Type;
import io.github.seujorgenochurras.minecraftjsh.antlr.syntax.symbol.VariableSymbol;
import io.github.seujorgenochurras.minecraftjsh.antlr.syntax.scope.GlobalScope;
import io.github.seujorgenochurras.minecraftjsh.antlr.syntax.scope.LocalScope;
import io.github.seujorgenochurras.minecraftjsh.antlr.syntax.scope.Scope;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.ParseTreeProperty;

public class ReferenceTreeDefiner extends JavaParserBaseListener {
    private final ParseTreeProperty<Scope> scopes = new ParseTreeProperty<>();
    private GlobalScope globalScope;
    private Scope currentScope;

    @Override
    public void enterCompilationUnit(JavaParser.CompilationUnitContext ctx) {
        globalScope = new GlobalScope(null);
        currentScope = globalScope;
    }

    @Override
    public void enterMethodDeclaration(JavaParser.MethodDeclarationContext ctx) {
        String typeName = ctx.start.getText();
        String methodName = ctx.identifier().getText();
        int typeNumber = ctx.start.getType();
        Type methodType = new Type(typeName, typeNumber);

        MethodSymbol methodSymbol = new MethodSymbol(methodName, methodType, currentScope);

        currentScope.addSymbol(methodSymbol);
        saveScope(ctx, methodSymbol);
        currentScope = methodSymbol;
    }

    @Override
    public void exitVariableDeclarator(JavaParser.VariableDeclaratorContext ctx) {
        defineVar(ctx.variableDeclaratorId(), ctx.start);
    }

    private void saveScope(ParserRuleContext context, Scope scope) {
        scopes.put(context, scope);
    }

    @Override
    public void exitMethodDeclaration(JavaParser.MethodDeclarationContext ctx) {
        currentScope = currentScope.getEnclosingScope();
    }


    @Override
    public void enterBlock(JavaParser.BlockContext ctx) {
        currentScope = new LocalScope(currentScope);
        saveScope(ctx, currentScope);
    }

    @Override
    public void exitBlock(JavaParser.BlockContext ctx) {
        currentScope = currentScope.getEnclosingScope(); // pop scope
    }

    @Override
    public void exitFormalParameter(JavaParser.FormalParameterContext ctx) {
        defineVar(ctx.variableDeclaratorId(), ctx.start);
    }

    private void defineVar(JavaParser.VariableDeclaratorIdContext varDeclarationCtx, Token nameToken) {
        int typeNumber = varDeclarationCtx.start.getType();
        String typeName = varDeclarationCtx.start.getText();
        String variableName = nameToken.getText();

        Type type = new Type(typeName, typeNumber);

        VariableSymbol variableSymbol = new VariableSymbol(variableName, type);
        currentScope.addSymbol(variableSymbol);
    }

    public ParseTreeProperty<Scope> getScopes() {
        return scopes;
    }

    public GlobalScope getReferences() {
        return globalScope;
    }

}
