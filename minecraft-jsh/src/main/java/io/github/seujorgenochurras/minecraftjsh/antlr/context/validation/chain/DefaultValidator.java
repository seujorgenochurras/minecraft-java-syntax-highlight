package io.github.seujorgenochurras.minecraftjsh.antlr.context.validation.chain;

import org.antlr.v4.runtime.RuleContext;

import java.util.ArrayList;

public class DefaultValidator<T extends RuleContext> implements AntlrValidatorChain<T> {
     DefaultValidator(ContextValidator<T> initialValidator){
        validators.add(initialValidator);
    }
    protected final ArrayList<ContextValidator<T>> validators = new ArrayList<>();

    @Override
    public DefaultValidator<T> addValidator(ContextValidator<T> validator){
        validators.add(validator);
        return this;
    }
    @Override
    public boolean validate(T contextToValidate){
        for (ContextValidator<T> validator : this.validators) {
            boolean isValid = validator.validate(contextToValidate);
            if(!isValid) return false;
        }
        return true;
    }


}
