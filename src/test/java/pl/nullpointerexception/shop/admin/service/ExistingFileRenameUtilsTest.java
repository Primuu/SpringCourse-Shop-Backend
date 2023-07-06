package pl.nullpointerexception.shop.admin.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ExistingFileRenameUtilsTest {

    @Test
    void shouldNotRenameFile(@TempDir Path tempDir) throws IOException {
        //when
        String newName = ExistingFileRenameUtils.renameIfExists(tempDir, "test.png");
        //then
        assertEquals("test.png", newName);
    }

    @Test
    void shouldRenameExistingFile(@TempDir Path tempDir) throws IOException {
        //given
        Files.createFile(tempDir.resolve("test.png"));
        //when
        String newName = ExistingFileRenameUtils.renameIfExists(tempDir, "test.png");
        //then
        assertEquals("test-1.png", newName);
    }

    @Test
    void shouldRenameManyExistingFiles(@TempDir Path tempDir) throws IOException {
        //given
        Files.createFile(tempDir.resolve("test.png"));
        Files.createFile(tempDir.resolve("test-1.png"));
        Files.createFile(tempDir.resolve("test-2.png"));
        Files.createFile(tempDir.resolve("test-3.png"));
        //when
        String newName = ExistingFileRenameUtils.renameIfExists(tempDir, "test.png");
        //then
        assertEquals("test-4.png", newName);
    }

}