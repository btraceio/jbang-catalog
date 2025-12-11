# Jafar JBang Catalog

JBang distribution for [Jafar](https://github.com/btraceio/jafar) - Fast JFR analysis tools with interactive shell and powerful query language.

## Quick Start

### Option 1: Install from Catalog (Recommended)

```bash
# Add the catalog (one-time setup)
jbang catalog add btraceio https://github.com/btraceio/jbang-catalog/blob/main/jbang-catalog.json

# Or use implicit resolution (no setup needed)
jbang jfr-shell@btraceio recording.jfr
```

### Option 2: Direct Script Execution

```bash
# No setup required - just run the script URL
jbang https://github.com/btraceio/jbang-catalog/blob/main/jfr-shell.java recording.jfr
```

### Option 3: JitPack Coordinates

```bash
# Pin to specific version
jbang io.github.btraceio:jfr-shell:0.1.0 recording.jfr

# Latest development snapshot
jbang io.github.btraceio:jfr-shell:main-SNAPSHOT recording.jfr
```

## Usage Examples

### Interactive Mode

```bash
# Open JFR file in interactive shell
jbang jfr-shell@btraceio -f recording.jfr

# The shell provides tab completion and a REPL for JfrPath queries
jfr> show events/jdk.ExecutionSample | count()
jfr> show events/jdk.FileRead[bytes>1048576] | top(10, by=bytes)
jfr> help
```

### Non-Interactive Mode

```bash
# Execute a single query
jbang jfr-shell@btraceio show recording.jfr "events/jdk.ExecutionSample | count()"

# Get JSON output for scripting
jbang jfr-shell@btraceio show recording.jfr \
  "events/jdk.FileRead | top(10, by=bytes)" \
  --format json

# Analyze thread activity
jbang jfr-shell@btraceio show recording.jfr \
  "events/jdk.ExecutionSample | groupBy(thread/name)"

# GC analysis
jbang jfr-shell@btraceio show recording.jfr \
  "events/jdk.GCHeapSummary[when/when='After GC']/heapSpace"

# List metadata
jbang jfr-shell@btraceio metadata recording.jfr --events-only

# Show chunks
jbang jfr-shell@btraceio chunks recording.jfr --summary
```

### Install as Command

For repeated use, install jfr-shell as a permanent command:

```bash
# Install
jbang app install jfr-shell@btraceio

# Now use it like a native command
jfr-shell recording.jfr
jfr-shell show recording.jfr "events/jdk.ExecutionSample | count()"
jfr-shell --help
```

## Available Aliases

- **`jfr-shell`** - Default, uses wrapper script (recommended)
- **`jfr-shell-jitpack`** - Version-pinned via JitPack (0.1.0)
- **`jfr-shell-latest`** - Latest main branch snapshot

## JfrPath Query Language

JfrPath provides a concise, powerful way to query JFR data:

```bash
# Basic projection
events/jdk.FileRead/path

# Filtering
events/jdk.FileRead[bytes>1048576]

# Aggregations
events/jdk.ExecutionSample | count()
events/jdk.FileRead/bytes | sum()
events/jdk.FileRead/bytes | stats()

# Grouping
events/jdk.ExecutionSample | groupBy(thread/name)

# Top N
events/jdk.FileRead | top(10, by=bytes)

# Complex filters with functions
events/jdk.FileRead[contains(path, "/tmp/")]
events/jdk.ExecutionSample[len(stackTrace/frames)>20]

# Metadata exploration
metadata/jdk.types.StackTrace --tree
cp/jdk.types.Symbol
chunks --summary
```

See the [JfrPath Reference](https://github.com/btraceio/jafar/blob/main/doc/jfrpath.md) for complete syntax.

## Installation

### JBang

If you don't have JBang installed:

```bash
# macOS/Linux
curl -Ls https://sh.jbang.dev | bash -s - app setup

# Windows (PowerShell)
iex "& { $(iwr https://ps.jbang.dev) } app setup"

# Or via package managers
brew install jbangdev/tap/jbang          # macOS (Homebrew)
sdk install jbang                        # SDKMAN
scoop install jbang                      # Windows (Scoop)
choco install jbang                      # Windows (Chocolatey)
```

JBang automatically downloads Java if needed, so you don't need a pre-installed JDK.

## Documentation

- **[Main Repository](https://github.com/btraceio/jafar)** - Full source code and build instructions
- **[JFR Shell Usage Guide](https://github.com/btraceio/jafar/blob/main/JFR-SHELL-USAGE.md)** - Complete feature overview
- **[JfrPath Reference](https://github.com/btraceio/jafar/blob/main/doc/jfrpath.md)** - Query language grammar and operators
- **[JBang Documentation](https://www.jbang.dev/)** - JBang official docs

## Requirements

- Java 21+ (JBang will download automatically if needed)
- JBang 0.100.0+

## Features

- **Interactive REPL** with intelligent tab completion
- **JfrPath query language** for filtering, projection, and aggregation
- **Multiple output formats**: table (default) and JSON
- **Multi-session support**: work with multiple recordings simultaneously
- **Non-interactive mode**: execute queries from command line for scripting/CI
- **Fast streaming parser**: efficient memory usage, handles large recordings

## Examples

### Count Events by Type

```bash
jbang jfr-shell@btraceio show recording.jfr "events/jdk.ExecutionSample | count()"
```

### Find Hot Methods

```bash
jbang jfr-shell@btraceio show recording.jfr \
  "events/jdk.ExecutionSample | groupBy(stackTrace/frames[0]/method/name/string) | top(10, by=count)"
```

### Analyze File I/O

```bash
jbang jfr-shell@btraceio show recording.jfr \
  "events/jdk.FileRead | groupBy(path, agg=sum, value=bytes) | top(10, by=sum)"
```

### Memory Statistics

```bash
jbang jfr-shell@btraceio show recording.jfr \
  "events/jdk.GCHeapSummary[when/when='After GC']/heapUsed | stats()"
```

## Updating

```bash
# Update to latest version
jbang app install --force jfr-shell@btraceio

# Clear cache and reinstall
jbang cache clear
jbang app install jfr-shell@btraceio
```

## Troubleshooting

### JBang Not Found

Install JBang first (see Installation section above).

### Java Version Mismatch

JBang automatically downloads Java 21+ if needed. Force a fresh install:

```bash
jbang --fresh jfr-shell@btraceio recording.jfr
```

### Catalog Not Found

Add the catalog explicitly:

```bash
jbang catalog add btraceio https://github.com/btraceio/jbang-catalog/blob/main/jbang-catalog.json
jbang catalog list  # Verify it's added
```

### JitPack Build Issues

If using JitPack coordinates fails, check the build status at:
https://jitpack.io/com/github/btraceio/jfr-shell/

You can trigger a fresh build by visiting that URL.

## License

Apache License 2.0 - See the [main repository](https://github.com/btraceio/jafar) for details.

## Contributing

Issues and pull requests should be submitted to the [main Jafar repository](https://github.com/btraceio/jafar).
