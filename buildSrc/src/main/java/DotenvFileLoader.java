import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

final class DotenvFileLoader {

    private DotenvFileLoader() { /* no-op */ }

    static Properties readDotenvFileAsProperties(
        final String location,
        final String filename,
        final boolean allowMissingFile
    ) throws IOException {
        try (final var dotenvFileInputStream = getDotenvFileInputStream(location, filename, allowMissingFile)) {
            return readDotenvFileAsProperties(dotenvFileInputStream);
        }
    }

    static Properties readDotenvFileAsProperties(final InputStream dotenvFileInputStream) throws IOException {
        final var properties = new Properties();
        properties.load(dotenvFileInputStream);
        return properties;
    }

    private static InputStream getDotenvFileInputStream(
        final String location,
        final String filename,
        final boolean allowMissingFile
    ) throws IOException {

        final var fileLocation = getDotenvFileLocation(location, filename);
        final var path = getDotenvFilePath(fileLocation);

        if (Files.exists(path)) {
            return Files.newInputStream(path);
        } else {
            return getDotenvFileInputStreamFromClasspath(fileLocation, allowMissingFile);
        }
    }

    private static InputStream getDotenvFileInputStreamFromClasspath(
        final String fileLocation,
        final boolean allowMissingFile
    ) {
        final var currentClass = DotenvFileLoader.class;

        var inputStream = currentClass.getResourceAsStream(fileLocation);
        if (inputStream == null) {
            inputStream = currentClass.getClassLoader().getResourceAsStream(fileLocation);
        }
        if (inputStream == null) {
            inputStream = ClassLoader.getSystemResourceAsStream(fileLocation);
        }
        if (inputStream == null) {
            if (allowMissingFile) {
                inputStream = ByteArrayInputStream.nullInputStream();
            } else {
                throw new RuntimeException("Could not find " + fileLocation + " on the classpath");
            }
        }
        return inputStream;
    }

    private static String getDotenvFileLocation(final String location, final String filename) {
        final var dir = location
            .replaceAll("\\\\", "/")
            .replaceFirst("\\.env$", "")
            .replaceFirst("/$", "");

        return dir + File.separator + filename;
    }

    private static Path getDotenvFilePath(final String fileLocation) {
        return shouldUseURI(fileLocation.toLowerCase())
            ? Paths.get(URI.create(fileLocation))
            : Paths.get(fileLocation);
    }

    private static boolean shouldUseURI(final String fileLocation) {
        return fileLocation.startsWith("file:");
    }
}
