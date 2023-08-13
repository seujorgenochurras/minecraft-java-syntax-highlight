package io.github.seujorgenochurras.minecraftjsh.java.antlr.context;


import io.github.seujorgenochurras.minecraftjsh.java.antlr.generated.JavaParser;

import javax.annotation.Nullable;

public interface VariableContext {
    JavaParser.TypeTypeContext typeType();

    @Nullable
    default JavaParser.VariableDeclaratorsContext variableDeclarators(){
        return null;
    }
}
