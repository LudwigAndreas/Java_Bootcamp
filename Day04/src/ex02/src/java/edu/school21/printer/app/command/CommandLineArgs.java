package edu.school21.printer.app.command;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

@Parameters(separators = "=")
public class CommandLineArgs {

    @Parameter(names = {"--white", "-w"}, description = "Color of white pixel on ascii image")
    public String white;

    @Parameter(names = {"--black", "-b"}, description = "Color of black pixel on ascii image")
    public String black;

    @Parameter(names = {"--inputImage", "-f"}, description = "Input file path")
    public String inputFilePath;

    @Parameter(names = {"--version", "-v"}, description = "Shows application version", help = true)
    public boolean version;

    @Parameter(names = {"--help", "-h"}, description = "Shows help message", help = true)
    public boolean help;
}
