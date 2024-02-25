package com.davidgeorgewilliams.workflows;

import com.davidgeorgewilliams.workflows.commands.Command;
import picocli.CommandLine;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@UtilityClass
public class Main {
    public static void main(@NonNull final String... args) {
        new CommandLine(Command.of()).execute(args);
    }
}