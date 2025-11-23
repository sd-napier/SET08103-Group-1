
package com.napier.sem.testing;

import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertTrue;

// This file runs inside github actions.
public class BuildIntegrationTest {

    /**
     * Integration test to check that Maven successfully built the JAR.
     */
    @Test
    void testJarFileExists() {
        // Make an object that represents the path.
        // It doesn't create the jar, it is used to look inside the folder where jar should belong.
        // 100% read only.
        File jarFile = new File("./target/App.jar");

        assertTrue(jarFile.exists(),
                "Integration test failed: JAR file should exist after build.");
        assertTrue(jarFile.isFile(),
                "Integration test failed: Path exists but is not a file.");
        assertTrue(jarFile.length() > 0,
                "Integration test failed: JAR file is empty or corrupted.");
    }
}

