package io.github.seujorgenochurras.minecraftjsh.antlr.context.validation.chain;

import org.antlr.v4.runtime.RuleContext;

public class ValidatorChainFactory {
    private ValidatorChainFactory(){}

    public static <T extends RuleContext> OnlyOneValidator<T> onlyOneValid(ContextValidator<T> contextValidator){
        return new OnlyOneValidator<>(contextValidator);
    }
    public static <K extends RuleContext> DefaultValidator<K> defaultValidationChain(ContextValidator<K> initialValidator){
        return new DefaultValidator<>(initialValidator);
    }
}
