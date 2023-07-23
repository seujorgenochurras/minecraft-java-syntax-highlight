package io.github.seujorgenochurras.minecraftjsh.antlr.minecraft;

import io.github.seujorgenochurras.minecraftjsh.antlr.lexer.JavaLexer;
import io.github.seujorgenochurras.minecraftjsh.antlr.listener.OnSyntaxError;
import io.github.seujorgenochurras.minecraftjsh.antlr.parser.JavaParser;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.bukkit.entity.Player;

public class MinecraftSyntaxHighlight {
    private MinecraftSyntaxHighlight(){}
    public static String handle(String javaCode, Player player){
        JavaLexer javaLexer = new JavaLexer(CharStreams.fromString(javaCode));
        CommonTokenStream tokens = new CommonTokenStream(javaLexer);

        JavaParser parser = new JavaParser(tokens);

        OnSyntaxError listener = new OnSyntaxError(tokens, player);

        parser.removeErrorListeners();
        parser.addErrorListener(listener);
        parser.compilationUnit();

      return listener.getRewriter().getText();
    }
}
