package io.github.seujorgenochurras.antlr;

import io.github.seujorgenochurras.minecraftjsh.antlr.lexer.JavaLexer;
import io.github.seujorgenochurras.minecraftjsh.antlr.parser.JavaParser;
import io.github.seujorgenochurras.minecraftjsh.antlr.syntax.ReferenceTreeDefiner;
import io.github.seujorgenochurras.minecraftjsh.antlr.syntax.listener.reference.OnUnknownReference;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.junit.jupiter.api.Test;

import java.sql.Ref;

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
                int a = onDsable();
                public void onDisable(int h, int p, String s) {
                    }
                    public int onDsable() {
                    return 0;
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
        ReferenceTreeDefiner referenceTree = new ReferenceTreeDefiner();

        ParseTree tree = parser.compilationUnit();

        walker.walk(referenceTree, tree);

        OnUnknownReference unknownReference = new OnUnknownReference(referenceTree.getScopes(),
                referenceTree.getReferences());
        walker.walk(unknownReference, tree);


    }

    private static final class OnBadSyntax extends BaseErrorListener {
        @Override
        public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol, int line, int charPositionInLine, String msg, RecognitionException e) {
            System.err.println("Syntax Error at line " + line + ", position " + charPositionInLine + ": " + msg);

        }
    }
}
