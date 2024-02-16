package spring.oauth.jwt.domain.auth.presentation.dto;

public interface OAuth2Response {
  String getProvider();
  String getProviderId();
  String getEmail();
  String getName();
}
