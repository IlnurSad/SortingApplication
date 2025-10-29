package ru.aston.finalproject.appender;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public class FileAppenderTest {
    private static final String TEST_FILE_PATH = "test_output.txt";
    private FileAppender<Object> fileAppender;

    @BeforeEach
    public void setUp() {
        File testFile = new File(TEST_FILE_PATH);
        if (testFile.exists()) {
            testFile.delete();
        }

        fileAppender = new FileAppender<>(TEST_FILE_PATH);
    }

    @AfterEach
    public void tearDown() {
        File testFile = new File(TEST_FILE_PATH);
        if (testFile.exists()) {
            testFile.delete();
        }
    }

    @Test
    public void testAppendData_WritesLinesToFile() {
        List<String> data = Arrays.asList("Line1", "Line2", "Line3");

        fileAppender.appendData(Collections.singletonList(data), item -> item.toString());

        File file = new File(TEST_FILE_PATH);
        assertTrue(file.exists());
        assertTrue(file.length() > 0);

        String content;
        try {
            content = new String(Files.readAllBytes(file.toPath()));
        } catch (IOException e) {
            fail("Не удалось прочитать содержимое файла: " + e.getMessage());
            return;
        }

        assertTrue(content.contains("Line1"));
        assertTrue(content.contains("Line2"));
        assertTrue(content.contains("Line3"));
    }

    @Test
    public void testAppendValue_WritesSingleLineToFile() {
        String value = "TestValue";

        fileAppender.appendValue(value, item -> item.toString());

        File file = new File(TEST_FILE_PATH);
        assertTrue(file.exists());
        assertTrue(file.length() > 0);

        String content;
        try {
            content = new String(Files.readAllBytes(file.toPath()));
        } catch (IOException e) {
            fail("Не удалось прочитать содержимое файла: " + e.getMessage());
            return;
        }

        assertEquals(value + "\n", content);
    }

    @Test
    public void testAppendWithCustomConverter() {
        List<Integer> numbers = Arrays.asList(1, 2, 3);
        fileAppender.appendData((List<Object>) (List<?>) numbers, n -> "Number: " + n);

        File file = new File(TEST_FILE_PATH);
        String content;
        try {
            content = new String(Files.readAllBytes(file.toPath()));
        } catch (IOException e) {
            fail("Не удалось прочитать содержимое файла: " + e.getMessage());
            return;
        }

        assertTrue(content.contains("Number: 1"));
        assertTrue(content.contains("Number: 2"));
        assertTrue(content.contains("Number: 3"));
    }

    @Test
    public void testAppendWithIOException_HandlesErrorGracefully() {
        File testFile = new File(TEST_FILE_PATH);
        try {
            testFile.createNewFile();
            testFile.setWritable(false);

            fileAppender.appendData(Arrays.asList("Should fail"), item -> item.toString());
        } catch (Exception e) {
        } finally {
            testFile.setWritable(true);
            testFile.delete();
        }
    }
}
