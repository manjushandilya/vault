package model;

import java.util.List;

public record Auth(String client_token, String accessor, List<String> policies, List<String> token_policies, Metadata metadata,
            int lease_duration, boolean renewable, String entity_id, String token_type, boolean orphan,
            Object mfa_requirement, int num_uses) {
}
