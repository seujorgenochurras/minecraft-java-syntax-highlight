package io.github.seujorgenochurras.minecraftjsh.compile.shell;

import io.github.seujorgenochurras.minecraftjsh.compile.shell.exception.CommandExecutionException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Shell {
    private Shell() {
    }

    public static String execute(String command) {

        StringBuilder output = new StringBuilder();

        try {
            command = "\"\"" + command + "\"\"";
            ProcessBuilder processBuilder = new ProcessBuilder("sh", "-c", command);
            Process process = processBuilder.start();

            BufferedReader processBuffReader = new BufferedReader(new InputStreamReader(process.getInputStream()));

            String line;
            while ((line = processBuffReader.readLine()) != null) {
                output.append(line).append("\n");
            }
            if (process.waitFor() != 0 && process.waitFor() != 2) {
                throw new CommandExecutionException("Something went wrong while trying to execute command " + command + " code:" + process.waitFor());
            }

        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        return output.toString();
    }

}
