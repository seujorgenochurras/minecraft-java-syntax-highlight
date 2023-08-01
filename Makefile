

all :
	antlr4-parse minecraft-jsh/src/main/java/io/github/seujorgenochurras/minecraftjsh/antlr/JavaParser.g4 \
	minecraft-jsh/src/main/java/io/github/seujorgenochurras/minecraftjsh/antlr/JavaLexer.g4 \
	compilationUnit -gui minecraft-jsh/src/test/java/io/github/seujorgenochurras/antlr/subject/testclass