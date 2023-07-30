package io.github.seujorgenochurras.minecraftjsh.antlr.context;

import io.github.seujorgenochurras.minecraftjsh.antlr.parser.JavaParser;

public interface FormalVariable extends VariableContext {
    JavaParser.VariableDeclaratorIdContext variableDeclaratorId();
}
