import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;

final class DotenvImpl implements Dotenv {

    private static final String REFERENCE_START = "${";
    private static final String REFERENCE_END = "}";

    private final boolean strictMode;
    private final boolean includeSystemVariables;
    private final boolean allowOverrideSystemVariables;

    private final Map<String, String> systemEnvironment;
    private final Properties fileContent;

    private final Map<String, String> resolvedRefs = new HashMap<>();
    private final List<String> currentSearchHistory = new ArrayList<>();

    DotenvImpl(
        final DotenvConfigurer config,
        final Map<String, String> systemEnvironment,
        final Properties fileContent
    ) {
        Objects.requireNonNull(config, "config must not be null");
        Objects.requireNonNull(systemEnvironment, "systemEnvironment must not be null");
        Objects.requireNonNull(fileContent, "fileContent must not be null");

        this.strictMode = config.isStrictMode();
        this.includeSystemVariables = config.isIncludeSystemVariables();
        this.allowOverrideSystemVariables = config.isAllowOverrideSystemVariables();
        this.systemEnvironment = systemEnvironment;
        this.fileContent = fileContent;
    }

    @Override
    public String get(final String name) {
        return getPropertyThenClearSearchHistory(name);
    }

    @Override
    public String get(final String name, final String defaultValue) {
        final var property = getPropertyThenClearSearchHistory(name);
        return (property != null)
            ? property
            : defaultValue;
    }

    @Override
    public String getRequired(final String name) throws MissingPropertyException {
        final var property = getPropertyThenClearSearchHistory(name);
        if (property != null) {
            return property;
        }
        throw new MissingPropertyException("Cannot find property `" + name + "`");
    }

    private String getPropertyThenClearSearchHistory(final String name) {
        try {
            return getProperty(name);
        } finally {
            currentSearchHistory.clear();
        }
    }

    private String getProperty(final String name) {
        final var property = findProperty(name);
        return (property != null)
            ? resolveReferences(property)
            : null;
    }

    private String findProperty(final String name) {
        final var property = includeSystemVariables
            ? systemEnvironment.get(name)
            : null;
        return ((property == null) || allowOverrideSystemVariables)
            ? fileContent.getProperty(name)
            : property;
    }

    private String resolveReferences(final String input) {
        return resolveReferences(input, 0);
    }

    private String resolveReferences(final String input, final int startIndex) {
        final var refStartIndex = input.indexOf(REFERENCE_START, startIndex);

        if (refStartIndex == -1) {
            return input;
        }

        final var refEndIndex = input.indexOf(REFERENCE_END, refStartIndex);

        if (refEndIndex == -1) {
            return input;
        }

        final var refName = input.substring(refStartIndex + REFERENCE_START.length(), refEndIndex);
        final var refValue = findRefValue(refName);

        final var prefix = (refStartIndex > 0) ? input.substring(0, refStartIndex) : "";
        final var suffix = input.substring(refEndIndex + 1);

        return resolveReferences(prefix + refValue + suffix, prefix.length() + refValue.length());
    }

    private String findRefValue(final String refName) {
        if (currentSearchHistory.contains(refName)) {
            currentSearchHistory.add(refName);
            throw new CyclicReferenceException("Cyclic references found, path: " + currentSearchPath());
        } else {
            currentSearchHistory.add(refName);
        }

        var result = resolvedRefs.get(refName);

        if (result == null) {
            result = getProperty(refName);
            if (result == null) {
                if (strictMode) {
                    throw new UnresolvedReferenceException(
                        "Cannot resolve reference with name '" + refName + "', path: " + currentSearchPath()
                    );
                }
                result = REFERENCE_START + refName + REFERENCE_END;
            }
            resolvedRefs.put(refName, result);
        }
        return result;
    }

    private String currentSearchPath() {
        return String.join(" -> ", currentSearchHistory);
    }
}
