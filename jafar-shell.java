///usr/bin/env jbang "$0" "$@" ; exit $?
//JAVA 26+
//DEPS io.btrace:jafar-shell:0.29.0
//REPOS mavenCentral
//JAVA_OPTIONS --enable-native-access=ALL-UNNAMED

//DESCRIPTION Interactive CLI for JFR recordings, heap dumps, pprof profiles and OTLP profiles
//MAIN io.jafar.shell.unified.Main

/*
 * Jafar Shell - JBang Launcher Script
 *
 * This script launches jafar-shell via JBang, delegating directly to io.jafar.shell.unified.Main.
 *
 * The artifact is a self-contained fat jar with its own SLF4J provider (logback), so no logging
 * dependency is added here: a second provider makes SLF4J warn on every run.
 *
 * JBang runs the jar from a classpath, not with java -jar, so an Enable-Native-Access manifest
 * attribute does not apply; --enable-native-access keeps JNA (bundled for JLine) from printing
 * the JEP 472 restricted-method warnings on JDK 24+.
 *
 * Usage:
 *   jbang jafar-shell.java [options] [file]
 *   jbang jafar-shell@btraceio [options] [file]
 *
 * For more information: https://github.com/btraceio/jafar
 */
