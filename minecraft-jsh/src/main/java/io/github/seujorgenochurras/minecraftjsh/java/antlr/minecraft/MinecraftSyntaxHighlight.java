package io.github.seujorgenochurras.minecraftjsh.java.antlr.minecraft;

import io.github.seujorgenochurras.minecraftjsh.java.antlr.generated.JavaLexer;
import io.github.seujorgenochurras.minecraftjsh.java.antlr.generated.JavaParser;
import io.github.seujorgenochurras.minecraftjsh.java.antlr.syntax.listener.OnIncorrectLiteral;
import io.github.seujorgenochurras.minecraftjsh.java.antlr.syntax.listener.OnSyntaxError;
import io.github.seujorgenochurras.minecraftjsh.java.antlr.syntax.listener.reference.OnUnknownReference;
import io.github.seujorgenochurras.minecraftjsh.java.antlr.syntax.listener.reference.ReferenceTreeDefiner;
import io.github.seujorgenochurras.minecraftjsh.java.antlr.syntax.scope.GlobalScope;
import io.github.seujorgenochurras.minecraftjsh.java.antlr.syntax.scope.Scope;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.TokenStreamRewriter;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeProperty;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.bukkit.entity.Player;

public class MinecraftSyntaxHighlight {
    private MinecraftSyntaxHighlight() {
    }

    public static String handle(String javaCode, Player player) {
        JavaLexer javaLexer = new JavaLexer(CharStreams.fromString(javaCode));
        CommonTokenStream tokens = new CommonTokenStream(javaLexer);

        JavaParser parser = new JavaParser(tokens);
        ParseTreeWalker walker = new ParseTreeWalker();
        TokenStreamRewriter rewriter = new TokenStreamRewriter(tokens);
        OnSyntaxError onSyntaxError = new OnSyntaxError(rewriter, player);

        parser.removeErrorListeners();
        parser.addErrorListener(onSyntaxError);

        ParseTree tree = parser.compilationUnit();
        ReferenceTreeDefiner referenceTreeDefiner = new ReferenceTreeDefiner();

        walker.walk(referenceTreeDefiner, tree);

        ParseTreeProperty<Scope> scopes = referenceTreeDefiner.getScopes();
        GlobalScope globalScope = referenceTreeDefiner.getReferences();

        OnUnknownReference onUnknownReference = new OnUnknownReference(scopes, globalScope, rewriter);
        OnIncorrectLiteral onIncorrectLiteral = new OnIncorrectLiteral(rewriter);

        walker.walk(onUnknownReference, tree);

        walker.walk(onIncorrectLiteral, tree);

        return rewriter.getText();
    }
}
