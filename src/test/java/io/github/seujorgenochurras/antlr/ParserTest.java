package io.github.seujorgenochurras.antlr;

import io.github.seujorgenochurras.minecraftjsh.java.antlr.generated.JavaLexer;
import io.github.seujorgenochurras.minecraftjsh.java.antlr.generated.JavaParser;
import io.github.seujorgenochurras.minecraftjsh.java.antlr.syntax.listener.OnIncorrectLiteral;
import io.github.seujorgenochurras.minecraftjsh.java.antlr.syntax.listener.OnSyntaxError;
import io.github.seujorgenochurras.minecraftjsh.java.antlr.syntax.listener.reference.ReferenceTreeDefiner;
import io.github.seujorgenochurras.minecraftjsh.java.antlr.syntax.listener.reference.OnUnknownReference;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

class ParserTest {
    private static final String testFileCode;

    private static final String massiveTestFileCode;

    static {
        try {
            testFileCode = Files.readString(Path.of("src/test/java/io/github/seujorgenochurras/antlr/subject/testclass"));
            massiveTestFileCode = Files.readString(Path.of("src/test/java/io/github/seujorgenochurras/antlr/subject/massiveTestClass"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


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
    void referenceTest() {
        String code = """
                public class MeuPau {
                final int a = onDisable();
                int j = 23;
                int k = 12;
            
                
                public static void main(String[] args) {
                      System.out.println("hello worl);
                  }
                
                public void onDisable(int h, int p, String s) {
                    }
                    public int onDsable() {
                    return 0;
                    }
                    public boolean onDisabe() {
                      int aoiwd = 23;
                    }
                    public MeuPau onDisble() {
                    }
                    }""";

        JavaLexer javaLexer = new JavaLexer(CharStreams.fromString(code));
        CommonTokenStream commonTokenStream = new CommonTokenStream(javaLexer);
        JavaParser parser = new JavaParser(commonTokenStream);

        ParseTreeWalker walker = new ParseTreeWalker();
        ReferenceTreeDefiner referenceTree = new ReferenceTreeDefiner();

        parser.removeParseListeners();
        TokenStreamRewriter rewriter = new TokenStreamRewriter(commonTokenStream);
        parser.addErrorListener(new OnSyntaxError(rewriter));
        ParseTree tree = parser.compilationUnit();

        walker.walk(referenceTree, tree);

        OnUnknownReference unknownReference = new OnUnknownReference(referenceTree.getScopes(),
                referenceTree.getReferences(), new TokenStreamRewriter(commonTokenStream));
        walker.walk(unknownReference, tree);

    }

    @Test
    void literalTest(){
        String code = massiveTestFileCode;

        JavaLexer javaLexer = new JavaLexer(CharStreams.fromString(code));
        CommonTokenStream commonTokenStream = new CommonTokenStream(javaLexer);
        JavaParser parser = new JavaParser(commonTokenStream);

        ParseTreeWalker walker = new ParseTreeWalker();

        ParseTree tree = parser.compilationUnit();

        OnIncorrectLiteral incorrectLiteral = new OnIncorrectLiteral(new TokenStreamRewriter(parser.getTokenStream()));

        walker.walk(incorrectLiteral, tree);


    }

    private static final class OnBadSyntax extends BaseErrorListener {
        @Override
        public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol, int line, int charPositionInLine, String msg, RecognitionException e) {
            System.err.println("Syntax Error at line " + line + ", position " + charPositionInLine + ": " + msg);

        }
    }
}
