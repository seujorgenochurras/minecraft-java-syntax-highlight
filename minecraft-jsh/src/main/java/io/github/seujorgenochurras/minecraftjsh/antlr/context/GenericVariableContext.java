package io.github.seujorgenochurras.minecraftjsh.antlr.context;

import io.github.seujorgenochurras.minecraftjsh.antlr.context.validation.chain.DefaultValidator;
import io.github.seujorgenochurras.minecraftjsh.antlr.context.validation.chain.ValidatorChainFactory;
import io.github.seujorgenochurras.minecraftjsh.antlr.context.validation.exception.InvalidTypeException;
import io.github.seujorgenochurras.minecraftjsh.antlr.context.validation.validator.ContextInstanceOf;
import io.github.seujorgenochurras.minecraftjsh.antlr.parser.JavaParser;
import org.antlr.v4.runtime.RuleContext;
import static io.github.seujorgenochurras.minecraftjsh.antlr.parser.JavaParser.*;
import org.jetbrains.annotations.Nullable;

//TODO make this work
public class GenericVariableContext extends RuleContext implements VariableContext {
    private RuleContext ruleContext;
    private TypeTypeContext typeTypeContext;

    public GenericVariableContext(RuleContext ruleContext) {
        validateRuleContext(ruleContext);

        this.ruleContext = ruleContext;
    }

    private static final DefaultValidator<RuleContext> varContextValidator = ValidatorChainFactory.
            onlyOneValid(new ContextInstanceOf(new VariableInitializerContext(null, 0)))
            .addValidator(new ContextInstanceOf(new FormalParameterContext(null, 0)))
            .addValidator(new ContextInstanceOf(new FieldDeclarationContext(null, 0)));

    private void validateRuleContext(RuleContext ruleContext) {
        if(!varContextValidator.validate(ruleContext)){
            throw new InvalidTypeException("Rule context type is not a variable context");
        }
    }

    @Override
    public JavaParser.TypeTypeContext typeType() {
        return null;
    }

    @Override
    public JavaParser.VariableDeclaratorIdContext variableDeclaratorId() {
        return null;
    }

    @Nullable
    @Override
    public JavaParser.VariableDeclaratorsContext variableDeclarators() {
        return null;
    }
}
