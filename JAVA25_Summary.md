Comprehensive Summary of Java Updates (JDK 21-25)
This document consolidates all features and changes presented across the provided materials, covering Java versions 21 through 25.

1. Performance & Startup
Virtual Threads
Concept: Introduced in JDK 21, virtual threads are lightweight threads designed to dramatically reduce the effort of writing and maintaining high-throughput concurrent applications.

Benefits:

Efficient: Scales to thousands or millions of threads. The intended model is one virtual thread per task/request.

Easy to use: Allows for writing straightforward, synchronous, blocking code while maintaining high concurrency. It uses the same Java observability and debugging capabilities.

Ahead-of-Time (AOT) Compilation
Concept: A feature of JDK 25 that improves application startup time by making classes instantly available when the JVM starts.

Mechanism: It works by monitoring an application's execution and caching the JVM state for subsequent runs.

Performance: A sample Helidon application demonstrated a startup time that was 3x faster with AOT.

Relevant JEPs (JDK 25):

JEP 483: Ahead-of-Time Class Loading & Linking

JEP 514: Ahead-of-Time Command-Line Ergonomics

JEP 515: Ahead-of-Time Method Profiling

JEP 519: Compact Object Headers
Change: Reduces the size of object headers from 64 bits to a more compact format, combining the Mark and Class words into a single 8-byte header.

Impact: Significantly reduces heap space usage, as most Java objects are small and header overhead is a major contributor to memory consumption.

Benefits (from SPECjbb2015 benchmark):

22% less heap space

8% less CPU time

15% reduction in the number of garbage collections

How to Enable: This is an opt-in feature activated with the JVM flag: -XX:+UseCompactObjectHeaders.

2. Language & API Features
Scoped Values (JEP 506)
Concept: Finalized in Java 25, Scoped Values provide dynamic scoping, allowing a caller to pass implicit, immutable parameters to indirect callees without modifying method signatures. They are a modern replacement for many uses of ThreadLocal.

Mechanism: The value of a scoped variable is its most recent binding on the call stack. It's available only within the lexical scope of a run() method and is automatically cleared upon exit.

Example Code:

Java

// ScopedValue is created in an unbound state
ScopedValue<String> SV = ScopedValue.newInstance();

// Bind the value to "Hello!" for the duration of the lambda
ScopedValue.where(SV, "Hello!").run(() -> {
    // Any method called from here can access SV.get()
    System.out.println(SV.get()); // Prints "Hello!"
});

// SV is unbound again outside the 'where' block
Stream Gatherers
Concept: A new intermediate operation for the Stream API that allows for the creation of complex, stateful transformations that were previously difficult or impossible.

Usage: The .gather() method takes a Gatherer implementation. Several built-in gatherers are provided, and custom ones can be created.

Example Code:

Java

import java.util.stream.Gatherer;

void main() {
    var letters = List.of("A", "B", "C", "D", "E", "F");

    // Example using a built-in gatherer for a sliding window
    var result = letters.stream()
                        .gather(Gatherers.windowSliding(3))
                        .toList();
    // result: [[A, B, C], [B, C, D], [C, D, E], [D, E, F]]

    // Example of a custom gatherer
    Gatherer<String, ?, String> myCustomGatherer = ... ;
    var customResult = letters.stream()
                              .gather(myCustomGatherer)
                              .toList();
}
3. Security Enhancements
Quantum-Resistant Cryptography (QRC)
JDK 21: Set the foundation for security with an emphasis on secure defaults and initial Post-Quantum Cryptography (PQC) enhancements like HSS/LMS verification.

JDK 22: Updated keytool and jarsigner to support the HSS/LMS (Hierarchical Signature System/Leighton-Micali Signature) quantum-resistant signature algorithm.

JDK 24 (JEP 496): Implemented ML-KEM (Module-Lattice-Based Key-Encapsulation Mechanism), a NIST-standardized algorithm for securing symmetric keys against quantum attacks.

JDK 24 (JEP 497): Implemented ML-DSA (Module-Lattice-Based Digital Signature Algorithm), a NIST-standardized algorithm for creating quantum-resistant digital signatures.

JDK 25 (JEP 470): Introduced a preview API for PEM Encodings of Cryptographic Objects, enhancing interoperability by allowing easy encoding/decoding of keys and certificates to the common PEM format.

Other Security Changes
Enhanced Security Configuration Visibility (JDK 22): Added a new security category to the -XshowSettings launcher option, allowing developers to easily inspect security properties, providers, and TLS settings.

Disabled the Security Manager (JDK 24): The legacy Security Manager has been disabled, as it was rarely used, expensive to maintain, and impeded other security enhancements.

Security Property File Inclusion (JDK 25): The main security properties file now supports an include directive to load other configuration files (e.g., include /path/to/tls-config.security).

4. Unsafe API & Restricted Operations
Phasing Out Unsafe Memory Access
Reason: The legacy methods in sun.misc.Unsafe have been superseded by modern, safer alternatives like VarHandles (JEP 193) and the Foreign Function & Memory API (JEP 454).

Phase-out Plan:

JDK 23: Deprecation and compile-time warnings.

JDK 24: Run-time warnings.

JDK 26+: Will throw exceptions and eventually be removed.

Finding Usage: Use the --sun-misc-unsafe-memory-access flag with values warn, debug, or deny to identify dependencies still using the unsafe API.

Restricted Methods and Operations
Concept: Access to potentially dangerous operations like native code linkage is now restricted.

Enabling Access: Control is managed via command-line flags:

--enable-native-access=$value: For selective access, where $value is a module name or ALL-UNNAMED.

--illegal-native-access=$value: A short-term fix where $value can be allow, warn, or deny.

Restricted Methods: Several methods related to the Foreign Linker API, native library loading (System.load, Runtime.load), and memory access are now restricted.

5. Tooling, Compiler, and Documentation
Javadoc Supports Markdown
Problem: Writing complex Javadoc using HTML is cumbersome, with confusing paragraph tags (<p>), verbose lists, and difficult tables.

Solution: Javadoc now supports Markdown for writing documentation comments. This is a more pleasant and widely known syntax that simplifies writing lists, code snippets, and tables.

Default Annotation Processing
Change: Annotation processors on the class path are no longer picked up silently.

Requirement: You must now explicitly enable them via flags like --processor-path or --processor-module-path.

Null-Checks In Inner Class Constructors
Change: The javac compiler now automatically injects null-checks in the constructors of non-static inner classes.

Reason: This prevents future NullPointerExceptions by ensuring the implicit outer class reference is not null.

Opt-out: The check can be disabled with -XDenyNullCheckOuterThis=false.

6. Core Library Updates
File-System Operations (on Windows)
File::delete no longer deletes read-only files.

File no longer accepts paths that have trailing spaces.

Paths using the \\?\ style are now processed correctly.

CLDR Update
JDK 20/22: The Common Locale Data Repository (CLDR) was updated, bringing changes to date, time, and unit formatting/parsing.

JDK 23: The COMPAT locale provider was removed.

Tools

Gemini can make mistakes, so double-check it   HERE