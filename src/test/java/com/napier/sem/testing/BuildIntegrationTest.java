package com.napier.sem.testing;

import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Integration test to check that Maven successfully built the JAR.
 */
public class BuildIntegrationTest {

    @Test
    void testJarExists() {
        File jar = new File("target/App.jar");

       // If jar exists it's a pass, else it fails.
        assertTrue(jar.exists(), "Build should produce the JAR file in target/ directory");
    }
}
