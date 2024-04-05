package model.login.response;

public record Root(String request_id, String lease_id, boolean renewable, int lease_duration, Object data,
                   Object wrap_info,
                   Object warnings, Auth auth) {
}

