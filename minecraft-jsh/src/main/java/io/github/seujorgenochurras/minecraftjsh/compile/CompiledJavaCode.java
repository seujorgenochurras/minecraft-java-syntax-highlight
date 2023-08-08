package io.github.seujorgenochurras.minecraftjsh.compile;

public class CompiledJavaCode {

    private final String humanByteCode;
    private final byte[] binaryByteCode;

    public CompiledJavaCode(String humanByteCode, byte[] binaryByteCode) {
        this.humanByteCode = humanByteCode;
        this.binaryByteCode = binaryByteCode;
    }

    public String getHumanByteCode() {
        return humanByteCode;
    }

    public byte[] getBinaryByteCode() {
        return binaryByteCode;
    }
}
