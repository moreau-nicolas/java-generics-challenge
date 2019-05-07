package challenge;

import compiler.CompilerAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Path;

class Exercise2Tests {

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
        testCompiler.assertThatClassCompilesCleanly(Exercise2.class);
    }

    @Test
    void should_accept_living_beings() {
        testCompiler.assertThatCodeCompilesCleanly(
                "Exercise2.addAnimalOfTheMonth(LIVING_BEING_LIST);"
        );
    }

    @Test
    void should_accept_animals() {
        testCompiler.assertThatCodeCompilesCleanly(
                "Exercise2.addAnimalOfTheMonth(ANIMAL_LIST);"
        );
    }

    @Test
    void should_reject_zebras() {
        testCompiler.assertThatCodeDoesNotCompile(
                "Exercise2.addAnimalOfTheMonth(ZEBRA_LIST);"
        );
    }

    @Test
    void should_reject_dogs() {
        testCompiler.assertThatCodeDoesNotCompile(
                "Exercise2.addAnimalOfTheMonth(DOG_LIST);"
        );
    }

    @Test
    void should_reject_cats() {
        testCompiler.assertThatCodeDoesNotCompile(
                "Exercise2.addAnimalOfTheMonth(CAT_LIST);"
        );
    }

    @Test
    void should_reject_plants() {
        testCompiler.assertThatCodeDoesNotCompile(
                "Exercise2.addAnimalOfTheMonth(PLANT_LIST);"
        );
    }
}