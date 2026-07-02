///usr/bin/env jbang "$0" "$@" ; exit $?
//JAVA 21+
//DEPS io.btrace:jafar-tools:0.27.0-SNAPSHOT
//REPOS mavenCentral,https://central.sonatype.com/repository/maven-snapshots/

//DESCRIPTION JFR -> pprof converter, latest development version
//MAIN io.jafar.jfr2pprof.Main

/*
 * jfr2pprof (latest/snapshot) - JBang Launcher Script
 *
 * Converts JFR recordings to pprof profiles using a YAML mapping config.
 *
 * Usage:
 *   jbang jfr2pprof-latest@btraceio --config mapping.yaml --output out.pprof recording.jfr
 *
 * For more information: https://github.com/btraceio/jafar
 */
