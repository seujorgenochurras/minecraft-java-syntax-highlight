package io.github.seujorgenochurras.minecraftjsh.java.antlr.context.validation.validator;

import io.github.seujorgenochurras.minecraftjsh.java.antlr.context.validation.chain.ContextValidator;
import org.antlr.v4.runtime.RuleContext;

public class ContextInstanceOf implements ContextValidator<RuleContext> {
    private final RuleContext instanceToCheck;

    public <T extends RuleContext> ContextInstanceOf(T instanceToCheck ) {
        this.instanceToCheck = instanceToCheck;
    }

    @Override
    public boolean validate(RuleContext ruleContext) {
        return ruleContext.getClass().isInstance(instanceToCheck);

    }

}
