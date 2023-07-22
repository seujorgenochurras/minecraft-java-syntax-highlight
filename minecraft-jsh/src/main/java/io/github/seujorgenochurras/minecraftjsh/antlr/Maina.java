package io.github.seujorgenochurras.minecraftjsh.antlr;

import io.github.seujorgenochurras.minecraftjsh.antlr.lexer.JavaLexer;
import io.github.seujorgenochurras.minecraftjsh.antlr.listener.OnBadSyntaxListener;
import io.github.seujorgenochurras.minecraftjsh.antlr.parser.JavaParser;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Maina {
    public static void main(String[] args) throws FileNotFoundException {
        String code = readFile(new File("minecraft-jsh/src/main/java/io/github/seujorgenochurras/minecraftjsh/antlr/javaCode"));

        JavaLexer javaLexer = new JavaLexer(CharStreams.fromString(code));
        CommonTokenStream tokens = new CommonTokenStream(javaLexer);


        JavaParser parser = new JavaParser(tokens);

        JavaParser.CompilationUnitContext tree = parser.compilationUnit();

        ParseTreeWalker walker = new ParseTreeWalker();
        OnBadSyntaxListener listener = new OnBadSyntaxListener(tokens);
        walker.walk(listener, tree);
        System.out.println(listener.rewriter.getText());

    }
    private static String readFile(File file) throws FileNotFoundException {
        StringBuilder fileContents = new StringBuilder();
        Scanner scanner = new Scanner(file);
        while(scanner.hasNextLine()){
            fileContents.append(scanner.nextLine());
        }
        scanner.close();
        return fileContents.toString();
    }
}
