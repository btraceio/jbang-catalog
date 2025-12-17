//JAVA 21+
//DEPS io.jafar:jfr-shell:0.3.7
//DEPS org.slf4j:slf4j-simple:2.0.5
//REPOS mavenCentral
//REPOS https://jitpack.io

//DESCRIPTION Interactive CLI for exploring and analyzing JFR files
//MAIN io.jafar.shell.Main

/*
 * JFR Shell - JBang Wrapper Script
 *
 * This script downloads and runs jfr-shell via JBang.
 *
 * Usage:
 *   jbang jfr-shell.java [options] [file.jfr]
 *   jbang jfr-shell.java show file.jfr "events/jdk.ExecutionSample | count()"
 *   jbang jfr-shell.java -f file.jfr    # Interactive mode
 *
 * For more information: https://github.com/btraceio/jafar
 */

import io.jafar.shell.Main;

public class jfr_shell {
    public static void main(String... args) throws Exception {
        // Delegate to actual Main class from dependency
        System.exit(Main.main(args));
    }
}
