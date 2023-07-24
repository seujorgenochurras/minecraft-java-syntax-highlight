package io.github.seujorgenochurras.antlr;

import io.github.seujorgenochurras.minecraftjsh.antlr.lexer.JavaLexer;
import io.github.seujorgenochurras.minecraftjsh.antlr.listener.OnInvalidReference;
import io.github.seujorgenochurras.minecraftjsh.antlr.listener.RefIdk;
import io.github.seujorgenochurras.minecraftjsh.antlr.parser.JavaParser;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.junit.jupiter.api.Test;

class ParserTest {

    @Test
    void symbolNotFoundTest() {
        String code = "public class MeuPau {" +
                "int a = j;" +
                "}";
        JavaLexer javaLexer = new JavaLexer(CharStreams.fromString(code));

        javaLexer.removeErrorListeners();
        javaLexer.addErrorListener(new OnBadSyntax());

        CommonTokenStream commonTokenStream = new CommonTokenStream(javaLexer);

        JavaParser parser = new JavaParser(commonTokenStream);


        parser.removeParseListeners();
        parser.addErrorListener(new OnBadSyntax());

        parser.compilationUnit();
    }

    @Test
    void methodTypeTest() {
        String code = """
                public class MeuPau {
                int a = j;
                public void onDisable() {
                    }
                    public int onDsable() {
                    }
                    public boolean onDisabe() {
                    }
                    public MeuPau onDisble() {
                    }
                    }""";
        JavaLexer javaLexer = new JavaLexer(CharStreams.fromString(code));
        CommonTokenStream commonTokenStream = new CommonTokenStream(javaLexer);
        JavaParser parser = new JavaParser(commonTokenStream);

        ParseTreeWalker walker = new ParseTreeWalker();
        OnInvalidReference onInvalidReference = new OnInvalidReference();
        ParseTree tree = parser.compilationUnit();

        walker.walk(onInvalidReference, tree);
        RefIdk refIdk = new RefIdk(onInvalidReference.scopes, onInvalidReference.globals);
        walker.walk(refIdk, tree);


    }

    private static final class OnBadSyntax extends BaseErrorListener {
        @Override
        public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol, int line, int charPositionInLine, String msg, RecognitionException e) {
            System.err.println("Syntax Error at line " + line + ", position " + charPositionInLine + ": " + msg);

        }
    }
}
