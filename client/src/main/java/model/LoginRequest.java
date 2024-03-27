package model;

public record LoginRequest(String role_id, String secret_id) {
    public LoginRequest {
        if (role_id == null || role_id.isBlank()) {
            throw new IllegalArgumentException("role_id cannot be null or blank");
        }
        if (secret_id == null || secret_id.isBlank()) {
            throw new IllegalArgumentException("secret_id cannot be null or blank");
        }
    }

}
