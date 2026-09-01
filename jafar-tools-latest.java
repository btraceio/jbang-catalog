///usr/bin/env jbang "$0" "$@" ; exit $?
//JAVA 26+
//DEPS io.btrace:jafar-tools:0.27.0-SNAPSHOT
//REPOS mavenCentral,https://central.sonatype.com/repository/maven-snapshots/

//DESCRIPTION jafar-tools launcher (jfr2pprof, scrub, ...), latest development version
//MAIN io.jafar.tools.Main

/*
 * jafar-tools (latest/snapshot) - JBang Launcher Script
 *
 * Single launcher dispatching to the CLI tools bundled in jafar-tools.
 *
 * Usage:
 *   jbang jafar-tools-latest@btraceio jfr2pprof --config mapping.yaml --output out.pprof recording.jfr
 *   jbang jafar-tools-latest@btraceio scrub --input recording.jfr --output scrubbed.jfr --scrub-field jdk.InitialSystemProperty.value
 *
 * For more information: https://github.com/btraceio/jafar
 */
