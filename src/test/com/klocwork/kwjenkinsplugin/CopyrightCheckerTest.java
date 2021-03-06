/*
 * *****************************************************************************
 * Copyright (c) 2020 Rogue Wave Software, Inc., a Perforce company
 * Author : Klocwork
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 * *****************************************************************************
 */
package com.klocwork.kwjenkinsplugin;

import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class CopyrightCheckerTest {
    @Test
    public void testCopyrightInEveryJavaFile() {
        try {
            assertTrue(getFilePaths().parallelStream().allMatch(this::perforceCopyrightInFile));
        } catch (IOException e) {
            fail();
        }
    }

    private List<Path> getFilePaths() throws IOException {
        return Files.walk(Paths.get(".\\src"))
                    .filter(path -> path.toString().endsWith(".java"))
                    .collect(Collectors.toList());
    }

    final String COPYRIGHT_PERFORCE = ".*\\*.*Copyright.*Perforce.*";
    private boolean perforceCopyrightInFile(Path path) {
        try {
            return Files.lines(path).anyMatch(line -> line.matches(COPYRIGHT_PERFORCE));
        } catch (IOException e) {
            return false;
        }
    }
}