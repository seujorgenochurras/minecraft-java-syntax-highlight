package io.github.seujorgenochurras.minecraftjsh.antlr.syntax.listener;

import io.github.seujorgenochurras.minecraftjsh.antlr.generated.JavaParser;
import io.github.seujorgenochurras.minecraftjsh.antlr.generated.JavaParserBaseListener;
import io.github.seujorgenochurras.minecraftjsh.antlr.syntax.listener.literal.*;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStreamRewriter;

public class OnIncorrectLiteral extends JavaParserBaseListener {

    private Literal expectedLiteral;

    private final TokenStreamRewriter rewriter;

    public OnIncorrectLiteral(TokenStreamRewriter rewriter) {
        this.rewriter = rewriter;
    }

    @Override
    public void enterTypeType(JavaParser.TypeTypeContext ctx) {
        String typeName = ctx.getText();
        switch (typeName) {
            case "int", "short", "long", "byte" -> this.expectedLiteral = new IntegerLiteral();
            case "float", "double" -> this.expectedLiteral = new FloatLiteral();
            case "char" -> this.expectedLiteral = new CharLiteral();
            case "boolean" -> this.expectedLiteral = new BooleanLiteral();
            case "String" -> this.expectedLiteral = new StringLiteral();
            default -> this.expectedLiteral = null;
        }
    }

    @Override
    public void enterLiteral(JavaParser.LiteralContext ctx) {
        if (expectedLiteral == null) return;

        LiteralType contextType = getLiteralTypeFromContext(ctx);
        if (!expectedLiteral.isCompatibleWith(contextType)) {
            Token contextToken = ctx.start;
            rewriter.insertBefore(contextToken, "§c§n");
            rewriter.insertAfter(contextToken, "§r");
        }

    }

    private LiteralType getLiteralTypeFromContext(JavaParser.LiteralContext literalContext) {
        if (literalContext.floatLiteral() != null) return LiteralType.FLOAT;
        if (literalContext.NULL_LITERAL() != null) return LiteralType.NULL;
        if (literalContext.BOOL_LITERAL() != null) return LiteralType.BOOL;
        if (literalContext.STRING_LITERAL() != null) return LiteralType.STRING;
        if (literalContext.integerLiteral() != null) return LiteralType.INTEGER;
        if (literalContext.TEXT_BLOCK() != null) return LiteralType.TEXT_BLOCK;
        if (literalContext.CHAR_LITERAL() != null) return LiteralType.CHAR;
        return null;
    }

    public Literal getExpectedLiteral() {
        return expectedLiteral;
    }
}
