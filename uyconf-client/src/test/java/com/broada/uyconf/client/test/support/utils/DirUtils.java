package com.broada.uyconf.client.test.support.utils;

import java.io.File;
import java.io.IOException;

/**
 * @author wnb
 * @author 2019-12-23
 */
public class DirUtils {

    public static File createTempDirectory() throws IOException {

        final File temp;

        temp = File.createTempFile("temp", Long.toString(System.nanoTime()));

        if (!(temp.delete())) {
            throw new IOException("Could not delete temp file: " + temp.getAbsolutePath());
        }

        if (!(temp.mkdir())) {
            throw new IOException("Could not create temp directory: " + temp.getAbsolutePath());
        }

        return (temp);
    }
}
