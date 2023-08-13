package io.github.seujorgenochurras.minecraftjsh.java.antlr.syntax.listener.reference;

import io.github.seujorgenochurras.minecraftjsh.java.antlr.context.FormalVariable;
import io.github.seujorgenochurras.minecraftjsh.java.antlr.context.VariableContext;
import io.github.seujorgenochurras.minecraftjsh.java.antlr.generated.JavaParser;
import io.github.seujorgenochurras.minecraftjsh.java.antlr.generated.JavaParserBaseListener;
import io.github.seujorgenochurras.minecraftjsh.java.antlr.syntax.symbol.MethodSymbol;
import io.github.seujorgenochurras.minecraftjsh.java.antlr.syntax.symbol.Type;
import io.github.seujorgenochurras.minecraftjsh.java.antlr.syntax.symbol.VariableSymbol;
import io.github.seujorgenochurras.minecraftjsh.java.antlr.syntax.scope.GlobalScope;
import io.github.seujorgenochurras.minecraftjsh.java.antlr.syntax.scope.LocalScope;
import io.github.seujorgenochurras.minecraftjsh.java.antlr.syntax.scope.Scope;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ParseTreeProperty;
import org.jetbrains.annotations.Nullable;

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
        defineVar(new GenericVariableContext(ctx));
    }

    @Override
    public void exitLocalVariableDeclaration(JavaParser.LocalVariableDeclarationContext ctx) {
        defineVar(new GenericVariableContext(ctx));
    }

    private void defineVar(JavaParser.FormalParameterContext variableContext) {
        Type varType = getVarType(new GenericVariableContext(variableContext));

        String varName = variableContext.variableDeclaratorId().identifier().getText();
        defineVar(varName, varType);
    }

    private void defineVar(VariableContext variableContext) {
        Type varType = getVarType(variableContext);

        assert variableContext.variableDeclarators() != null : "Var context cannot be null";

        variableContext.variableDeclarators().variableDeclarator().forEach(declarationCtx -> {
            String varName = declarationCtx.variableDeclaratorId().start.getText();
            defineVar(varName, varType);
        });
    }

    private void defineVar(String varName, Type varType) {
        VariableSymbol variableSymbol = new VariableSymbol(varName, varType);
        currentScope.addSymbol(variableSymbol);
    }

    private Type getVarType(VariableContext variableContext) {
        String varTypeName = variableContext.typeType().getText();
        int varTypeNumber = variableContext.typeType().start.getType();
        return new Type(varTypeName, varTypeNumber);
    }

    public ParseTreeProperty<Scope> getScopes() {
        return scopes;
    }

    public GlobalScope getReferences() {
        return globalScope;
    }

    //TODO make better code
    private static final class GenericVariableContext implements FormalVariable {
        private JavaParser.FormalParameterContext formalParameterContext;
        private JavaParser.LocalVariableDeclarationContext localVariableDeclarationContext;
        private JavaParser.FieldDeclarationContext fieldDeclarationContext;

        public GenericVariableContext(JavaParser.FormalParameterContext formalParameterContext) {
            this.formalParameterContext = formalParameterContext;
        }

        public GenericVariableContext(JavaParser.LocalVariableDeclarationContext localVariableDeclarationContext) {
            this.localVariableDeclarationContext = localVariableDeclarationContext;
        }

        public GenericVariableContext(JavaParser.FieldDeclarationContext fieldDeclarationContext) {
            this.fieldDeclarationContext = fieldDeclarationContext;
        }

        @Override
        public JavaParser.TypeTypeContext typeType() {
            if(formalParameterContext != null) return formalParameterContext.typeType();
            if(localVariableDeclarationContext !=null ) return localVariableDeclarationContext.typeType();
            return fieldDeclarationContext.typeType();
        }

        @Nullable
        @Override
        public JavaParser.VariableDeclaratorsContext variableDeclarators() {
            if(formalParameterContext != null) return null;
            if(localVariableDeclarationContext !=null ) return localVariableDeclarationContext.variableDeclarators();
            return fieldDeclarationContext.variableDeclarators();
        }

        @Override
        public JavaParser.VariableDeclaratorIdContext variableDeclaratorId() {
            if(formalParameterContext == null) return null;
            return formalParameterContext.variableDeclaratorId();
        }
    }
}
