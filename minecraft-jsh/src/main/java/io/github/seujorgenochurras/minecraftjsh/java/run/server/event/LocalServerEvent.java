package io.github.seujorgenochurras.minecraftjsh.java.run.server.event;

import java.io.PrintWriter;

public record LocalServerEvent(String eventArg, PrintWriter eventOutput) {
}
