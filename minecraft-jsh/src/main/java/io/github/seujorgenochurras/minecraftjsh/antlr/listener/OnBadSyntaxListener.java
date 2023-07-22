package io.github.seujorgenochurras.minecraftjsh.antlr.listener;


import io.github.seujorgenochurras.minecraftjsh.antlr.parser.JavaParser;
import io.github.seujorgenochurras.minecraftjsh.antlr.parser.JavaParserBaseListener;
import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.TokenStreamRewriter;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.TerminalNode;

public class OnBadSyntaxListener extends BaseErrorListener {
    public TokenStreamRewriter rewriter;
    @Override
    public void visitErrorNode(ErrorNode node) {
//        System.out.println(node.getSymbol().getLine());
//        System.out.println(node.getSymbol().getCharPositionInLine());
    }

    public OnBadSyntaxListener(TokenStream tokenStream) {
        this.rewriter = new TokenStreamRewriter(tokenStream);
    }

    @Override
    public void enterMethodDeclaration(JavaParser.MethodDeclarationContext ctx) {
        TerminalNode terminalNode = ctx.identifier().IDENTIFIER();
        String methodName = terminalNode.getText();
//
//            System.out.println(terminalNode.getSourceInterval().a);
//            System.out.println(terminalNode.getSourceInterval().b);
    }

    @Override
    public void enterFieldDeclaration(JavaParser.FieldDeclarationContext ctx) {
        String comment = "\n// meu pau";
        rewriter.insertAfter(ctx.start, comment);
    }

}
