///usr/bin/env jbang "$0" "$@" ; exit $?
//DEPS io.btrace:btrace:3.0.0-SNAPSHOT
//JAVA_OPTIONS -XX:+IgnoreUnrecognizedVMOptions
//JAVA_OPTIONS --add-modules=jdk.attach
//JAVA_OPTIONS --add-exports=jdk.internal.jvmstat/sun.jvmstat.monitor=ALL-UNNAMED

import io.btrace.boot.Loader;

/** Launches the BTrace client from the single masked distribution JAR. */
public class btrace {
  public static void main(String[] args) {
    Loader.main(args);
  }
}
