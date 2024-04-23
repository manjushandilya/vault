package model.login.response;

public record Metadata(String created_time, Object custom_metadata, String deletion_time, boolean destroyed, int version) {
}
