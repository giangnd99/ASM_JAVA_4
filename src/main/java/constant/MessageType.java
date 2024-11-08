package constant;

public enum MessageType {
    SUCCESS("successMessage"),
    ERROR("errorMessage"),
    WARNING("warningMessage"),
    INFO("infoMessage");

    private final String value;

    MessageType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
