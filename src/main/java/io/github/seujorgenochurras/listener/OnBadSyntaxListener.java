package io.github.seujorgenochurras.listener;

import io.github.seujorgenochurras.antlr.JavaParserBaseListener;
import org.antlr.v4.runtime.tree.ErrorNode;

public class OnBadSyntaxListener extends JavaParserBaseListener {
    @Override
    public void visitErrorNode(ErrorNode node) {
        System.out.println(node.getText());
        super.visitErrorNode(node);
    }
}
