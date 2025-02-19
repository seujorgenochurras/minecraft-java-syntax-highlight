package io.github.seujorgenochurras.minecraftjsh.java.antlr.syntax.listener;


import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.ATNConfigSet;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.Interval;
import org.bukkit.entity.Player;

import java.util.BitSet;

public class OnSyntaxError extends BaseErrorListener {
    private final TokenStreamRewriter rewriter;

    private Player player;

    public OnSyntaxError(TokenStreamRewriter rewriter, Player player) {
        this.rewriter = rewriter;
        this.player = player;
    }

    public OnSyntaxError(TokenStreamRewriter rewriter) {
        this.rewriter = rewriter;
    }

    @Override
    public void syntaxError(Recognizer<?, ?> recognizer,
                            Object offendingSymbol,
                            int line, int charPositionInLine,
                            String msg, RecognitionException e) {
        recognizer.removeErrorListeners();
        Token symbol = (Token) offendingSymbol;
        System.out.println("Syntax error");
        rewriter.insertBefore(symbol.getTokenIndex()-1, "§c§n §r");
    }

    public TokenStreamRewriter getRewriter() {
        return rewriter;
    }

}
