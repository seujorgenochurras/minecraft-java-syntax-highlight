package io.github.seujorgenochurras.minecraftjsh.antlr.context;

import io.github.seujorgenochurras.minecraftjsh.antlr.context.validation.chain.DefaultValidator;
import io.github.seujorgenochurras.minecraftjsh.antlr.context.validation.chain.ValidatorChainFactory;
import io.github.seujorgenochurras.minecraftjsh.antlr.context.validation.exception.InvalidTypeException;
import io.github.seujorgenochurras.minecraftjsh.antlr.context.validation.validator.ContextInstanceOf;
import io.github.seujorgenochurras.minecraftjsh.antlr.generated.JavaParser;
import org.antlr.v4.runtime.RuleContext;
import org.jetbrains.annotations.Nullable;

//TODO make this work
public class GenericVariableContext extends RuleContext implements VariableContext {
    private RuleContext ruleContext;
    private JavaParser.TypeTypeContext typeTypeContext;

    public GenericVariableContext(RuleContext ruleContext) {
        validateRuleContext(ruleContext);

        this.ruleContext = ruleContext;
    }

    private static final DefaultValidator<RuleContext> varContextValidator = ValidatorChainFactory.
            onlyOneValid(new ContextInstanceOf(new JavaParser.VariableInitializerContext(null, 0)))
            .addValidator(new ContextInstanceOf(new JavaParser.FormalParameterContext(null, 0)))
            .addValidator(new ContextInstanceOf(new JavaParser.FieldDeclarationContext(null, 0)));

    private void validateRuleContext(RuleContext ruleContext) {
        if(!varContextValidator.validate(ruleContext)){
            throw new InvalidTypeException("Rule context type is not a variable context");
        }
    }

    @Override
    public JavaParser.TypeTypeContext typeType() {
        return null;
    }

    @Nullable
    @Override
    public JavaParser.VariableDeclaratorsContext variableDeclarators() {
        return null;
    }
}
