package io.github.seujorgenochurras.minecraftjsh.antlr.listener;

import io.github.seujorgenochurras.minecraftjsh.antlr.parser.JavaParser;
import io.github.seujorgenochurras.minecraftjsh.antlr.parser.JavaParserBaseListener;
import io.github.seujorgenochurras.minecraftjsh.antlr.scope.GlobalScope;
import io.github.seujorgenochurras.minecraftjsh.antlr.scope.LocalScope;
import io.github.seujorgenochurras.minecraftjsh.antlr.scope.Scope;
import io.github.seujorgenochurras.minecraftjsh.antlr.symbol.MethodSymbol;
import io.github.seujorgenochurras.minecraftjsh.antlr.symbol.Type;
import io.github.seujorgenochurras.minecraftjsh.antlr.symbol.VariableSymbol;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.ParseTreeProperty;

public class OnInvalidReference extends JavaParserBaseListener {
    public ParseTreeProperty<Scope> scopes = new ParseTreeProperty<>();
    public GlobalScope globals;
    public Scope currentScope;

    @Override
    public void enterCompilationUnit(JavaParser.CompilationUnitContext ctx) {
        globals = new GlobalScope(null);
        currentScope = globals;
    }

    @Override
    public void exitCompilationUnit(JavaParser.CompilationUnitContext ctx) {
        System.out.println(globals);
    }

    @Override
    public void enterMethodDeclaration(JavaParser.MethodDeclarationContext ctx) {
        String typeName = ctx.start.getText();
        String methodName = ctx.identifier().getText();
        int typeNumber = ctx.start.getType();
        Type methodType = new Type(typeName, typeNumber);

        MethodSymbol methodSymbol = new MethodSymbol(methodName, methodType, currentScope);

        currentScope.define(methodSymbol);
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


    public void enterBlock(JavaParser.BlockContext ctx) {
        currentScope = new LocalScope(currentScope);
        saveScope(ctx, currentScope);
    }

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
        currentScope.define(variableSymbol);
    }

}
