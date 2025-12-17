///usr/bin/env jbang "$0" "$@" ; exit $?
//JAVA 25+
//DEPS io.btrace:jafar-shell:0.3.11
//DEPS org.slf4j:slf4j-simple:2.0.5
//REPOS mavenCentral

//DESCRIPTION Interactive CLI for exploring and analyzing JFR files
//MAIN io.jafar.shell.Main

/*
 * JFR Shell - JBang Launcher Script
 *
 * This script launches jfr-shell via JBang, delegating directly to io.jafar.shell.Main.
 *
 * Usage:
 *   jbang jfr-shell.java [options] [file.jfr]
 *   jbang jfr-shell@btraceio [options] [file.jfr]
 *
 * For more information: https://github.com/btraceio/jafar
 */
