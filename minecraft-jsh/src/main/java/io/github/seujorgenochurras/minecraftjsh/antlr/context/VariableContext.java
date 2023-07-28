package io.github.seujorgenochurras.minecraftjsh.antlr.context;

import io.github.seujorgenochurras.minecraftjsh.antlr.parser.JavaParser;

import javax.annotation.Nullable;

public interface VariableContext {
    JavaParser.TypeTypeContext typeType();

    JavaParser.VariableDeclaratorIdContext variableDeclaratorId();

    @Nullable
    JavaParser.VariableDeclaratorsContext variableDeclarators();
}
