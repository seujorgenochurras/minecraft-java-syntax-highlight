package io.github.seujorgenochurras;

import io.github.seujorgenochurras.antlr.JavaLexer;
import io.github.seujorgenochurras.antlr.JavaParser;
import io.github.seujorgenochurras.listener.OnBadSyntaxListener;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

public class Main {


    public static void main(String[] args) {
        String code = "public class Subject {\n" +
                "    public static void main(String[] args) {\n" +
                "        System.out.println(\"BUAHDIUAWHDIUWAH\");\n" +
                "    }\n" +
                "}\n";
        JavaLexer javaLexer = new JavaLexer(CharStreams.fromString(code));
        CommonTokenStream tokens = new CommonTokenStream(javaLexer);


        JavaParser parser = new JavaParser(tokens);

        JavaParser.CompilationUnitContext tree = parser.compilationUnit();

        ParseTreeWalker walker = new ParseTreeWalker();
        walker.walk(new OnBadSyntaxListener(), tree);


    }
}