///usr/bin/env jbang "$0" "$@" ; exit $?
//DEPS io.btrace:btrace-client:2.2.6
//JAVA_OPTIONS -XX:+IgnoreUnrecognizedVMOptions

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class btrace {
    /**
     * BTrace client launcher for jbang.
     *
     * This script replicates the functionality of the btrace shell script:
     * - Sets up proper VM arguments for all Java versions
     * - Enables module exports for Java 9+
     * - Handles tools.jar for Java 8 and earlier
     *
     * Usage:
     *   jbang btrace <pid> <script.java>
     *   jbang btrace -lp <pid>
     *   jbang btrace -le <pid>
     */
    public static void main(String[] args) throws Exception {
        String javaHome = System.getProperty("java.home");
        if (javaHome == null || javaHome.isEmpty()) {
            System.err.println("ERROR: JAVA_HOME not set");
            System.exit(1);
        }

        // Build VM arguments
        List<String> vmArgs = new ArrayList<>();
        vmArgs.add("-XX:+IgnoreUnrecognizedVMOptions");

        // Add module exports for Java 9+ (needed for VirtualMachine attach)
        if (isJava9OrLater()) {
            vmArgs.add("-XX:+AllowRedefinitionToAddDeleteMethods");
            vmArgs.add("--add-exports");
            vmArgs.add("jdk.internal.jvmstat/sun.jvmstat.monitor=ALL-UNNAMED");
            vmArgs.add("--add-exports");
            vmArgs.add("java.base/sun.reflect=ALL-UNNAMED");
        }

        // Execute with proper classpath
        executeWithClasspath(vmArgs, args, javaHome);
    }

    private static void executeWithClasspath(List<String> vmArgs, String[] args, String javaHome)
            throws Exception {
        // Get tools.jar path
        String toolsJar = findToolsJar(javaHome);

        // Build command
        List<String> cmd = new ArrayList<>();
        cmd.add(new File(javaHome, "bin/java").getAbsolutePath());

        // Add VM arguments
        cmd.addAll(vmArgs);

        // Add classpath (-cp is added by jbang runtime, but we need tools.jar)
        if (toolsJar != null) {
            cmd.add("-cp");
            // Format: ${APP_CP}:${TOOLS_JAR}
            // jbang sets APP_CP via environment
            String appCp = System.getProperty("jbang.app.cp");
            if (appCp != null) {
                cmd.add(appCp + ":" + toolsJar);
            } else {
                // Fallback if APP_CP not available
                cmd.add(toolsJar);
            }
        }

        // Add main class
        cmd.add("org.openjdk.btrace.client.Main");

        // Add user arguments
        for (String arg : args) {
            cmd.add(arg);
        }

        // Execute BTrace client
        ProcessBuilder pb = new ProcessBuilder(cmd);
        pb.inheritIO();
        int exitCode = pb.start().waitFor();
        System.exit(exitCode);
    }

    private static String findToolsJar(String javaHome) {
        // Try standard location: JAVA_HOME/lib/tools.jar
        String toolsJar = new File(javaHome, "lib/tools.jar").getAbsolutePath();
        if (new File(toolsJar).exists()) {
            return toolsJar;
        }

        // Try JRE location: JAVA_HOME/../lib/tools.jar
        toolsJar = new File(javaHome, "../lib/tools.jar").getAbsolutePath();
        if (new File(toolsJar).exists()) {
            return toolsJar;
        }

        // macOS: try classes.jar in framework
        if (isMacOS()) {
            String version = System.getProperty("java.version");
            toolsJar = "/System/Library/Frameworks/JavaVM.framework/Versions/"
                    + version + "/Classes/classes.jar";
            if (new File(toolsJar).exists()) {
                return toolsJar;
            }

            toolsJar = javaHome + "/../Classes/classes.jar";
            if (new File(toolsJar).exists()) {
                return toolsJar;
            }
        }

        // Java 9+: modules are built in, no need for tools.jar
        if (new File(javaHome, "jmods").exists()) {
            return null;
        }

        // Not found, but don't fail for Java 9+
        if (isJava9OrLater()) {
            return null;
        }

        // For Java 8 and earlier, we need tools.jar
        System.err.println("WARNING: Unable to locate tools.jar");
        return null;
    }

    private static boolean isJava9OrLater() {
        try {
            String version = System.getProperty("java.version");
            int major = Integer.parseInt(version.split("\\.")[0]);
            return major >= 9;
        } catch (Exception e) {
            return false;
        }
    }

    private static boolean isMacOS() {
        return System.getProperty("os.name", "").toLowerCase().contains("mac");
    }
}
