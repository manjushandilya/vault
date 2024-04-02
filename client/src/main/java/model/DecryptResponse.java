package model;

public record DecryptResponse(String request_id, String lease_id, boolean renewable, int lease_duration, Data data,
                              String wrap_info, String warnings, Auth auth) {
}
