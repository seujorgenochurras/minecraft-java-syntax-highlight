package io.github.seujorgenochurras.minecraftjsh.antlr.syntax.listener.reference;

import io.github.seujorgenochurras.minecraftjsh.antlr.parser.JavaParser;
import io.github.seujorgenochurras.minecraftjsh.antlr.parser.JavaParserBaseListener;
import io.github.seujorgenochurras.minecraftjsh.antlr.syntax.symbol.MethodSymbol;
import io.github.seujorgenochurras.minecraftjsh.antlr.syntax.symbol.Type;
import io.github.seujorgenochurras.minecraftjsh.antlr.syntax.symbol.VariableSymbol;
import io.github.seujorgenochurras.minecraftjsh.antlr.syntax.scope.GlobalScope;
import io.github.seujorgenochurras.minecraftjsh.antlr.syntax.scope.LocalScope;
import io.github.seujorgenochurras.minecraftjsh.antlr.syntax.scope.Scope;
import org.antlr.v4.runtime.ParserRuleContext;
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
        String methodName = ctx.identifier().getText() + "()";
        int typeNumber = ctx.start.getType();
        Type methodType = new Type(typeName, typeNumber);

        MethodSymbol methodSymbol = new MethodSymbol(methodName, methodType, currentScope);

        currentScope.addSymbol(methodSymbol);
        saveScope(ctx, methodSymbol);
        currentScope = methodSymbol;
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
        defineVar(ctx);
    }

    @Override
    public void exitFieldDeclaration(JavaParser.FieldDeclarationContext ctx) {
        defineVar(ctx);
    }

    @Override
    public void exitLocalVariableDeclaration(JavaParser.LocalVariableDeclarationContext ctx) {
        defineVar(ctx);
    }

    private void defineVar(JavaParser.LocalVariableDeclarationContext variableInitializerCtx) {
        String varTypeName = variableInitializerCtx.typeType().getText();
        int varTypeNumber = variableInitializerCtx.typeType().start.getType();
        Type varType = new Type(varTypeName, varTypeNumber);

        variableInitializerCtx.variableDeclarators().variableDeclarator().forEach(declarationCtx -> {
            String varName = declarationCtx.variableDeclaratorId().start.getText();
            defineVar(varName, varType);
        });
    }

    private void defineVar(JavaParser.FieldDeclarationContext declarationContext) {
        String varTypeName = declarationContext.typeType().getText();
        int varTypeNumber = declarationContext.typeType().start.getType();
        Type varType = new Type(varTypeName, varTypeNumber);

        declarationContext.variableDeclarators().variableDeclarator().forEach(declarationCtx -> {
            String varName = declarationCtx.variableDeclaratorId().start.getText();
            defineVar(varName, varType);
        });
    }
    private void defineVar(JavaParser.FormalParameterContext formalParameterContext) {
        String varTypeName = formalParameterContext.typeType().getText();
        int varTypeNumber = formalParameterContext.typeType().start.getType();
        Type varType = new Type(varTypeName, varTypeNumber);

        String varName = formalParameterContext.variableDeclaratorId().start.getText();
        defineVar(varName, varType);
    }

    private void defineVar(String varName, Type varType) {
        VariableSymbol variableSymbol = new VariableSymbol(varName, varType);
        currentScope.addSymbol(variableSymbol);
    }

    public ParseTreeProperty<Scope> getScopes() {
        return scopes;
    }

    public GlobalScope getReferences() {
        return globalScope;
    }

}
