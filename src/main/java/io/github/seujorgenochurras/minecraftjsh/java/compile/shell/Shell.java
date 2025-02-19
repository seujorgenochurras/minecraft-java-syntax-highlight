package io.github.seujorgenochurras.minecraftjsh.java.compile.shell;

import io.github.seujorgenochurras.minecraftjsh.java.compile.shell.exception.CommandExecutionException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Shell {
    private Shell() {
    }

    public static String execute(String command) {
        return execute("sh", "-c", command);
    }

    public static String execute(String... args) {
        StringBuilder output = new StringBuilder();

        try {
            ProcessBuilder processBuilder = new ProcessBuilder(args);
            Process process = processBuilder.start();

            BufferedReader processBuffReader = new BufferedReader(new InputStreamReader(process.getInputStream()));

            String line;
            while ((line = processBuffReader.readLine()) != null) {
                output.append(line).append("\n");
            }
            int returnCode = process.waitFor();
            if (returnCode != 0 && returnCode != 2) {
                throw new CommandExecutionException("Something went wrong while trying to execute command " + Arrays.toString(args) + " code:" + returnCode);
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return output.toString();
    }
}
