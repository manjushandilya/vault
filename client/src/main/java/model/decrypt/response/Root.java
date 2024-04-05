package model.decrypt.response;

public record Root(String request_id, String lease_id, boolean renewable, int lease_duration, Data data,
                   String wrap_info, String warnings, Object auth) {
}
