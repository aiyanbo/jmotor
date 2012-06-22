package jmotor.util.type;

/**
 * Component:
 * Description:
 * Date: 11-8-23
 *
 * @author Andy.Ai
 */
public enum ResourceType {
    MAIN_CONTEXT("mainContext"),
    CLASS_PATH("classPath"),
    FILE_SYSTEM("fileSystem"),
    URL("url");
    private String expression;

    private ResourceType(String expression) {
        this.expression = expression;
    }

    @Override
    public String toString() {
        return expression;
    }
}
