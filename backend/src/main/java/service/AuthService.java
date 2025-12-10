package service;

import exception.ResourceNotFoundException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import model.User;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import web.dto.auth.JwtRequest;
import web.dto.auth.JwtResponse;
import web.security.JwtTokenProvider;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class AuthService {

    AuthenticationManager authenticationManager;
    UserService userService;
    JwtTokenProvider jwtTokenProvider;

    public JwtResponse login(JwtRequest loginRequest) {
        var jwtResponse = new JwtResponse();
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        User user = userService.getByEmail(loginRequest.getUsername());
        if (!user.isEnabled()) {
            throw new ResourceNotFoundException("User not click on email link for end register");
        }
        jwtResponse.setUsername(user.getEmail());
        jwtResponse.setId(user.getId());
        jwtResponse.setAccessToken(jwtTokenProvider.createAccessToken(user.getId(), user.getEmail(), user.getRoles()));
        jwtResponse.setRefreshToken(jwtTokenProvider.createRefreshToken(user.getId(), user.getEmail()));
        return jwtResponse;
    }

    public JwtResponse refresh(String refreshToken) {
        return jwtTokenProvider.refreshUserTokens(refreshToken);
    }

}
