package io.github.seujorgenochurras.minecraftjsh.java.antlr.syntax.listener.literal;

public class CharLiteral implements Literal{
    @Override
    public boolean isCompatibleWith(LiteralType literalType) {
        return literalType.equals(LiteralType.CHAR);
    }
}
