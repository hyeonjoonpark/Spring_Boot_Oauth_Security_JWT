package spring.oauth.jwt.domain.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import spring.oauth.jwt.domain.auth.presentation.dto.CustomOAuth2User;
import spring.oauth.jwt.domain.auth.presentation.dto.OAuth2Response;
import spring.oauth.jwt.domain.auth.presentation.dto.UserDto;
import spring.oauth.jwt.domain.auth.presentation.dto.google.GoogleResponse;
import spring.oauth.jwt.domain.user.User;
import spring.oauth.jwt.domain.user.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
  private final UserRepository userRepository;
  @Override
  public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
    OAuth2User oAuth2User = super.loadUser(userRequest);
    System.out.println("oAuth2User = " + oAuth2User);

    String registrationId = userRequest.getClientRegistration().getRegistrationId();

    OAuth2Response oAuth2Response = null;
    if (registrationId.equals("google")) {
      oAuth2Response = new GoogleResponse(oAuth2User.getAttributes());
    }

    String userName =
      oAuth2Response.getProvider() + " " + oAuth2Response.getProviderId();

    User existData = userRepository.findByUserName(userName);

    // 한번도 로그인 하지 않아서 데이터가 없음
    if(existData == null) {
      User user = User.builder()
        .userName(userName)
        .name(oAuth2Response.getName())
        .email(oAuth2Response.getEmail())
        .role("ROLE_USER")
        .build();

      userRepository.save(user);

      UserDto userDto = new UserDto();
      userDto.setUserName(userName);
      userDto.setName(oAuth2Response.getName());
      userDto.setRole("ROLE_USER");

      return new CustomOAuth2User(userDto);
    } else {

      existData.setEmail(oAuth2Response.getEmail());
      existData.setName(oAuth2Response.getName());

      userRepository.save(existData);

      UserDto userDTO = new UserDto();
      userDTO.setUserName(existData.getUserName());
      userDTO.setName(oAuth2Response.getName());
      userDTO.setRole(existData.getRole());

      return new CustomOAuth2User(userDTO);
    }
  }
}
