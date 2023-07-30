package io.github.seujorgenochurras.minecraftjsh.antlr.context.validation.chain;

import org.antlr.v4.runtime.RuleContext;

public class OnlyOneValidator<T extends RuleContext> extends DefaultValidator<T> {

    OnlyOneValidator(ContextValidator<T> initialValidator) {
        super(initialValidator);
    }

    @Override
        public boolean validate(T contextToValidate){
            for (ContextValidator<T> validator : validators) {
                boolean isValid = validator.validate(contextToValidate);
                if(isValid) return true;
            }
            return false;
        }
}
