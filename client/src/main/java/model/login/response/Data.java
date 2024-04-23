package model.login.response;

import java.util.Map;

public record Data(Map<String, String> data, Metadata metadata) {
}
