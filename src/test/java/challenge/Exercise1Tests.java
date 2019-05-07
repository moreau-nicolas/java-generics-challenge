package challenge;

import compiler.CompilerAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Path;

class Exercise1Tests {

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
        testCompiler.assertThatClassCompilesCleanly(Exercise1.class);
    }

    @Test
    void should_accept_animals() {
        testCompiler.assertThatCodeCompilesCleanly(
                "boolean vaccinated = Exercise1.isVaccinated(ANIMAL_LIST);"
        );
    }

    @Test
    void should_accept_cats() {
        testCompiler.assertThatCodeCompilesCleanly(
                "boolean vaccinated = Exercise1.isVaccinated(CAT_LIST);"
        );
    }

    @Test
    void should_accept_dogs() {
        testCompiler.assertThatCodeCompilesCleanly(
                "boolean vaccinated = Exercise1.isVaccinated(DOG_LIST);"
        );
    }

    @Test
    void should_reject_living_beings() {
        testCompiler.assertThatCodeDoesNotCompile(
                "boolean vaccinated = Exercise1.isVaccinated(LIVING_BEING_LIST);"
        );
    }

    @Test
    void should_reject_plants() {
        testCompiler.assertThatCodeDoesNotCompile(
                "boolean vaccinated = Exercise1.isVaccinated(PLANT_LIST);"
        );
    }
}