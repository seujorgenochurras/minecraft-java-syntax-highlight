package io.github.seujorgenochurras.minecraftjsh.compile;

import io.github.seujorgenochurras.minecraftjsh.compile.shell.Shell;
import io.github.seujorgenochurras.minecraftjsh.compile.utils.FileUtils;

import java.io.File;

public class JavaCompiler {
    private JavaCompiler(){}

    public static CompiledJavaCode compile(String rawJavaCode){
        //THERE IS NO... ABSOLUTELY NO WAY THIS IS SAFE DON'T TRUST THIS
        //TODO bruh this is so scary
        final String classFile = "Main";
        Shell.execute("touch "+ classFile + ".java");
        Shell.execute("echo \"" + rawJavaCode.replace("\"", "\\\"") + "\"> " + classFile + ".java");
        Shell.execute( "/usr/bin/javac " + classFile + ".java");
        String javaHumanByteCode = Shell.execute("/usr/bin/javap -c " + classFile);
        File compiledJavaFile = new File("Main.class");
        byte[] bytecodeBinary = FileUtils.getFileBytes(compiledJavaFile);

        return new CompiledJavaCode(javaHumanByteCode, bytecodeBinary);
    }

}
