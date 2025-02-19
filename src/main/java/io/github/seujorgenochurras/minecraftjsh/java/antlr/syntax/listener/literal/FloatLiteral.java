package io.github.seujorgenochurras.minecraftjsh.java.antlr.syntax.listener.literal;

import java.util.Set;

public class FloatLiteral implements Literal{
    private static final Set<LiteralType> compatibleTypes = Set.of(LiteralType.FLOAT, LiteralType.INTEGER);
    @Override
    public boolean isCompatibleWith(LiteralType literalType) {
        return compatibleTypes.contains(literalType);
    }
}
