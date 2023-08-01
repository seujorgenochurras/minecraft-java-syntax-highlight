package io.github.seujorgenochurras.minecraftjsh.antlr.syntax.listener.literal;

public class BooleanLiteral implements Literal{
    @Override
    public boolean isCompatibleWith(LiteralType literalType) {
        return literalType.equals(LiteralType.BOOL);
    }
}
