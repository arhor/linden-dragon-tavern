/**
 * Interface representing loaded .env file.
 */
public interface Dotenv {

    /**
     * Factory method returning {@link DotenvConfigurer} instance.
     *
     * @return Dotenv configurer
     */
    static DotenvConfigurer configure() {
        return DotenvConfigurer.getInstance();
    }

    String get(String name);

    String get(String name, String defaultValue);

    String getRequired(String name) throws MissingPropertyException;
}
