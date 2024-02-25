package com.davidgeorgewilliams.workflow.commands;

import lombok.AccessLevel;
import lombok.Builder;
import picocli.CommandLine;

@Builder(access = AccessLevel.PRIVATE)
@CommandLine.Command(description = "Command line interface.",
        mixinStandardHelpOptions = true,
        name = "Workflow",
        subcommands = {
            DemoCommand.class
        },
        version = "1.0")
public class Command {
    public static Command of() {
        return Command.builder().build();
    }
}
