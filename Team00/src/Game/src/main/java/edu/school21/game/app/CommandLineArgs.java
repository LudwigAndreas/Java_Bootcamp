package edu.school21.game.app;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

@Parameters(separators = "=")
public class CommandLineArgs {
    @Parameter(names = {"--help", "-h"}, description = "Shows help message")
    public boolean help;

    @Parameter(names = {"--version", "-v"}, description = "Shows program version", help = true)
    public boolean version;

    @Parameter(names = {"--enemiesCount", "-e"}, description = "Sets enemies count")
    public int enemiesCount = 2;

    @Parameter(names = {"--wallsCount", "-w"}, description = "Sets walls count")
    public int wallsCount = 11;

    @Parameter(names = {"--size", "-s"}, description = "Sets map size")
    public int size = 8;

    @Parameter(names = {"--profile", "-p"}, description = "Sets application profile")
    public String profile = "production";
}
