package io.github.seujorgenochurras.minecraftjsh.java.antlr.context.validation.chain;

import org.antlr.v4.runtime.RuleContext;

public interface AntlrValidatorChain<T extends RuleContext> {
    AntlrValidatorChain<T> addValidator(ContextValidator<T> contextValidator);

    boolean validate(T thingToValidate);

}
