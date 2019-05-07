package compiler;

import javax.tools.DiagnosticCollector;
import javax.tools.JavaCompiler;
import javax.tools.JavaCompiler.CompilationTask;
import javax.tools.JavaFileObject;
import javax.tools.ToolProvider;
import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;

public class CompilerAssertions {

    public static CompilerAssertions withOptions(String... options) {
        return new CompilerAssertions(Arrays.asList(options));
    }

    private final List<String> options;
    private final List<String> automaticImports;
    private String packageName;

    private CompilerAssertions(List<String> options) {
        this.options = requireNonNull(options);
        this.automaticImports = new ArrayList<>();
    }

    public CompilerAssertions withAutomaticImport(String automaticImport) {
        requireNonNull(automaticImport);
        automaticImports.add(automaticImport);
        return this;
    }

    public CompilerAssertions generateCodeInPackage(String packageName) {
        this.packageName = requireNonNull(packageName);
        return this;
    }

    private List<?> gatherDiagnostics(JavaFileObject source) {
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<>();
        Iterable<JavaFileObject> sources = Collections.singletonList(source);
        CompilationTask task = compiler.getTask(null, null, diagnostics,
                options, null, sources);
        task.call();
        return diagnostics.getDiagnostics();
    }

    private List<String> gatherDiagnosticMessages(JavaFileObject source) {
        return gatherDiagnostics(source).stream()
                .map(Object::toString)
                .collect(toList());
    }

    public void assertThatCodeDoesNotCompile(String code) {
        JavaFileObject source = createJavaFileObject(code);
        List<?> diagnostics = gatherDiagnostics(source);
        assertThat(diagnostics)
                .withFailMessage("This code should not compile but does: %s", code)
                .isNotEmpty();
    }

    public void assertThatCodeCompilesCleanly(String code) {
        JavaFileObject source = createJavaFileObject(code);
        List<String> diagnosticMessages = gatherDiagnosticMessages(source);
        assertThat(diagnosticMessages)
                .withFailMessage("This code should compile cleanly but does not: %s\n\n%s",
                        code, String.join("\n\n", diagnosticMessages))
                .isEmpty();
    }

    public void assertThatClassCompilesCleanly(Class<?> clazz) {
        JavaFileObject source = createJavaFileObject(clazz);
        List<String> diagnosticMessages = gatherDiagnosticMessages(source);
        assertThat(diagnosticMessages)
                .withFailMessage("This class should compile cleanly but does not: %s\n\n%s",
                        clazz, String.join("\n\n", diagnosticMessages))
                .isEmpty();
    }

    private JavaFileObject createJavaFileObject(String code) {
        StringBuilderJavaSource source = new StringBuilderJavaSource("Main");
        source.append(String.format("package %s;", packageName));
        for (String automaticImport : automaticImports) {
            source.append(String.format("import %s;", automaticImport));
        }
        source.append("public class Main {");
        source.append("  public static void main(String... args){");
        source.append("    " + code);
        source.append("  }");
        source.append("}");
        return source;
    }

    private JavaFileObject createJavaFileObject(Class<?> clazz) {
        String className = clazz.getCanonicalName();
        Path rootPath = Paths.get(".").toAbsolutePath().normalize();
        Path path = Paths.get("src", "main", "java")
                .resolve(className.replace('.', File.separatorChar) + ".java");
        StringBuilderJavaSource source = new StringBuilderJavaSource(className);
        try (Stream<String> lines = Files.lines(rootPath.resolve(path))) {
            lines.forEachOrdered(source::append);
            return source;
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
