package com.snapUrl.User.Service.services;
import com.snapUrl.User.Service.dtos.AuthResponse;
import com.snapUrl.User.Service.dtos.LoginReq;
import com.snapUrl.User.Service.dtos.SignUpReq;
import com.snapUrl.User.Service.entities.RefreshToken;
import com.snapUrl.User.Service.entities.UserEntity;
import com.snapUrl.User.Service.enums.Role;
import com.snapUrl.User.Service.repositories.UserRepo;
import com.snapUrl.User.Service.utils.JwtUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final RefreshTokenService refreshTokenService;
    private final UserDetailsService userDetailsService;

    //signup
    public void signup(SignUpReq req) {

        if(req.getEmail() == null || req.getUsername() == null || req.getPassword() ==null )
        {
            throw new IllegalArgumentException("All fields are required ");
        }
        if (userRepo.existsByEmail(req.getEmail())) {
            throw new IllegalArgumentException("Email already exists");
        }
        if (userRepo.existsByUsername(req.getUsername())) {
            throw new IllegalArgumentException("Username already exists");
        }
        UserEntity user = new UserEntity();
        user.setUsername(req.getUsername());
        user.setEmail(req.getEmail());
        user.setPassword(passwordEncoder.encode(req.getPassword()));
        user.setRole(Role.USER);
        userRepo.save(user);
    }

    //login
    public AuthResponse login(LoginReq req) {

        if (req.getUsername() == null || req.getPassword() == null) {
            throw new IllegalArgumentException("All fields are required");
        }

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            req.getUsername(),
                            req.getPassword()
                    )
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);

            UserEntity userDetails = (UserEntity) authentication.getPrincipal();

            String accessToken = jwtUtils.generateAccessToken(userDetails);
            String refreshToken = jwtUtils.generateRefreshToken(userDetails);

            UserEntity user = userRepo.findByUsername(userDetails.getUsername())
                    .orElseThrow(() -> new IllegalArgumentException("User not found"));

            //save refresh token in db
            refreshTokenService.save(user, refreshToken);
            AuthResponse res = new AuthResponse();
            res.setEmail(user.getEmail());
            res.setUsername(user.getUsername());
            res.setRole(String.valueOf(user.getRole()));
            res.setAccessToken(accessToken);
            res.setRefreshToken(refreshToken);
            return res;

        } catch (BadCredentialsException ex) {
            throw new IllegalArgumentException("Invalid credentials");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    //refresh Access Token
    public AuthResponse refreshAccessToken(String refreshToken) throws NoSuchAlgorithmException {

        // SHA-256 + Base64 encode
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(refreshToken.getBytes(StandardCharsets.UTF_8));
        String tokenHash = Base64.getEncoder().encodeToString(hash);

        // Find the refresh token in DB
        RefreshToken token = (RefreshToken) refreshTokenService.findValidToken(tokenHash)
                .orElseThrow(() -> new IllegalArgumentException("Invalid or expired refresh token"));

        // N+1 query
        UserEntity user = token.getUser();
        com.snapUrl.User.Service.entities.UserEntity userDetails = (UserEntity) userDetailsService.loadUserByUsername(user.getUsername());

        // Generate new access token
        String newAccessToken = jwtUtils.generateAccessToken(userDetails);

        //we can also issue a new refresh token if we want rotating refresh token
        AuthResponse authRes = new AuthResponse();
        authRes.setUsername(user.getUsername());
        authRes.setEmail(user.getEmail());
        authRes.setRole(String.valueOf(user.getRole()));
        authRes.setAccessToken(newAccessToken);
        return authRes;

    }

    //logout
    @Transactional
    public void logout(String refreshToken) {
        try {
            // SHA-256 hash
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(refreshToken.getBytes(StandardCharsets.UTF_8));
            String tokenHash = Base64.getEncoder().encodeToString(hash);
            //System.out.println(tokenHash);
            refreshTokenService.revokeByTokenHash(tokenHash);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 not available");
        }
    }

    //verify user
    public AuthResponse verify()
    {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserEntity user = (UserEntity) auth.getPrincipal();
        AuthResponse res = new AuthResponse();
        res.setUsername(user.getUsername());
        res.setEmail(user.getEmail());
        return res;
    }

}
