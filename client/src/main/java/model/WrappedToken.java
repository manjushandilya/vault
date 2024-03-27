package model;

import java.util.Date;

public record WrappedToken(String token, String accessor, int ttl, Date creation_time, String creation_path,
                           String wrapped_accessor) {
}
