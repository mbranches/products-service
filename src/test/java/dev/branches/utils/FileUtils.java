package dev.branches.utils;

import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@Component
public class FileUtils {
    private final ResourceLoader resourceLoader;

    public FileUtils(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    public String readResourceFile(String fileName) throws IOException {
        File file = resourceLoader.getResource("classpath:%s".formatted(fileName)).getFile();

        return new String(Files.readAllBytes(file.toPath()));
    }
}
