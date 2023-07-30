package io.github.seujorgenochurras.minecraftjsh.antlr.context.validation.chain;

import org.antlr.v4.runtime.RuleContext;

public interface ContextValidator<T extends RuleContext> {
    boolean validate(T t);
}
