/*
 * MIT License
 *
 * Copyright (c) 2018 Thisseas Xanthopoulos
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.thisseasx.class_generator;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

@SuppressWarnings({"ConstantConditions", "ResultOfMethodCallIgnored"})
class ClassGenerator {

    private enum Type {
        CLASS("Class", "%s"),
        MAP("Map", "%sMap"),
        INTERFACE("Interface", "I%sRepository"),
        IMPL("Impl", "%sRepositoryNHibernate"),
        TEST("Test", "%sRepositoryTest");

        private String type;
        private String fileName;

        Type(String type, String fileName) {
            this.type = type;
            this.fileName = fileName;
        }
    }

    private final String extension;
    private final List<String> classes = new ArrayList<>();
    private final long timestamp = System.currentTimeMillis();

    ClassGenerator(String... args) throws Exception {
        validateArgs(args);

        this.extension = args[0];

        String[] classArray = Arrays.copyOfRange(args, 1, args.length);
        List<String> classList = Arrays.asList(classArray);

        this.classes.addAll(classList);
    }

    private void validateArgs(String[] args) throws Exception {
        if (args.length < 1) {
            throw new Exception("Please specify file extension.");
        }

        if (args.length >= 1 && (!args[0].startsWith(".") || args[0].length() < 2)) {
            throw new Exception("The first argument must be the file extension, starting with a period '.' e.g. .java.");
        }

        if (args.length < 2) {
            throw new Exception("Please specify class names to be generated.");
        }
    }

    void make() {
        // Currently not needed
        // generateFilesForType(Type.CLASS);
        // generateFilesForType(Type.MAP);
        generateFilesForType(Type.INTERFACE);
        generateFilesForType(Type.IMPL);
        generateFilesForType(Type.TEST);
    }

    private void generateFilesForType(Type type) {
        for (String className : classes) {
            String pascalCaseName = className.substring(0, 1).toUpperCase()
                    + className.substring(1);
            generateFile(type, pascalCaseName);
        }
    }

    private void generateFile(Type type, String className) {
        File dir = new File(System.getProperty("user.home") + "/Desktop/ClassGeneratorFiles/" + timestamp, type.type);
        dir.mkdirs();

        File classFile = new File(dir, getProperFileName(type, className));

        try (FileWriter writer = new FileWriter(classFile)) {
            writer.write(String.format(getTemplate(type), className));

        } catch (IOException | NullPointerException e) {
            e.printStackTrace();
        }
    }

    private String getProperFileName(Type type, String className) {
        return String.format(type.fileName, className) + extension;
    }

    private static String getTemplate(Type type) {
        try (InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("template.properties")) {
            Properties props = new Properties();
            props.load(is);
            return props.getProperty("template." + type.toString().toLowerCase());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
