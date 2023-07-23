package io.github.seujorgenochurras.minecraftjsh.antlr.listener;


import org.antlr.v4.runtime.*;
import org.bukkit.entity.Player;

public class OnSyntaxError extends BaseErrorListener {
    private final TokenStreamRewriter rewriter;

    private final Player player;

    public OnSyntaxError(TokenStream tokenStream, Player player) {
        this.rewriter = new TokenStreamRewriter(tokenStream);
        this.player = player;
    }

    @Override
    public void syntaxError(Recognizer<?, ?> recognizer,
                            Object offendingSymbol,
                            int line, int charPositionInLine,
                            String msg, RecognitionException e) {
        String errorMsg = "invalid syntax at " + line + ":" + charPositionInLine + "\n " + msg;
        System.err.println(errorMsg);

        player.sendRawMessage(errorMsg);
        rewriter.insertBefore(((Token) offendingSymbol), "§c§n §r");

    }

    public TokenStreamRewriter getRewriter() {
        return rewriter;
    }

}
