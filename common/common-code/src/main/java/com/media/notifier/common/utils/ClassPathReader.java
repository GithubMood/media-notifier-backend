package com.media.notifier.common.utils;

import lombok.experimental.UtilityClass;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

@UtilityClass
public class ClassPathReader {
    public String readFile(String file) {
        try {
            InputStream resourceAsStream = ClassPathReader.class.getClassLoader().getResourceAsStream(file);
            return IOUtils.toString(resourceAsStream, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new IllegalArgumentException("Unable to read file: " + file, e);
        }
    }
}
