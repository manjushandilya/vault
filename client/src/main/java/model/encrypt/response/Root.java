package model.encrypt.response;

public record Root(String request_id, String lease_id, boolean renewable, int lease_duration, Data data,
                   Object wrap_info, Object warnings, Object auth) {

}
