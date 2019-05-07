package challenge;

import compiler.CompilerAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Path;

class Exercise3Tests {

    private CompilerAssertions testCompiler;

    @TempDir
    Path outputDirectory;

    @BeforeEach
    void setUp() {
        testCompiler = CompilerAssertions.withOptions(
                "-Xlint:unchecked",
                "-Xlint:rawtypes",
                "-d", outputDirectory.toString())
                .withAutomaticImport("domain.*")
                .withAutomaticImport("java.util.*")
                .withAutomaticImport("static challenge.TestData.*")
                .generateCodeInPackage("challenge");
    }

    @Test
    void class_compiles_without_warnings() {
        testCompiler.assertThatClassCompilesCleanly(Exercise3.class);
    }

    @Test
    void test1() {
        testCompiler.assertThatCodeCompilesCleanly(
                "List<Animal> list = Exercise3.sort(CAT_SET, COMPARE_LIVING_BEINGS);"
        );
    }

    @Test
    void test2() {
        testCompiler.assertThatCodeCompilesCleanly(
                "List<Animal> list = Exercise3.sort(CAT_SET, COMPARE_ANIMALS);"
        );
    }

    @Test
    void test3() {
        testCompiler.assertThatCodeCompilesCleanly(
                "List<Animal> list = Exercise3.sort(CAT_SET, COMPARE_CATS);"
        );
    }

    @Test
    void test4() {
        testCompiler.assertThatCodeDoesNotCompile(
                "Exercise3.sort(CAT_SET, COMPARE_DOGS);"
        );
    }

    @Test
    void test5() {
        testCompiler.assertThatCodeCompilesCleanly(
                "List<Animal> list = Exercise3.sort(ANIMAL_SET, COMPARE_ANIMALS);"
        );
    }

    @Test
    void test6() {
        testCompiler.assertThatCodeDoesNotCompile(
                "Exercise3.sort(ANIMAL_SET, COMPARE_DOGS);"
        );
    }

    @Test
    void test7() {
        testCompiler.assertThatCodeCompilesCleanly(
                "List<Cat> list = Exercise3.sort(CAT_SET, COMPARE_LIVING_BEINGS);"
        );
    }

    @Test
    void test8() {
        testCompiler.assertThatCodeCompilesCleanly(
                "List<Dog> list = Exercise3.sort(DOG_LIST, COMPARE_LIVING_BEINGS);"
        );
    }

    @Test
    void test9() {
        testCompiler.assertThatCodeCompilesCleanly(
                "List<LivingBeing> list = Exercise3.sort(DOG_LIST, COMPARE_LIVING_BEINGS);"
        );
    }

    @Test
    void test10() {
        testCompiler.assertThatCodeCompilesCleanly(
                "List<Animal> list = Exercise3.sort(DOG_LIST, COMPARE_LIVING_BEINGS);"
        );
    }
}