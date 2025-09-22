package br.com.codemain.nutrixpertai.service.impl;

import br.com.codemain.nutrixpertai.dto.Auth.LoginResponseDTO;
import br.com.codemain.nutrixpertai.dto.Auth.RegisterDTO;
import br.com.codemain.nutrixpertai.entity.User;
import br.com.codemain.nutrixpertai.infra.security.TokenService;
import br.com.codemain.nutrixpertai.repository.UserRepository;
import br.com.codemain.nutrixpertai.service.IAuthenticationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements IAuthenticationService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    public LoginResponseDTO login(String email, String password) {
        var authToken = new UsernamePasswordAuthenticationToken(email, password);
        var auth = authenticationManager.authenticate(authToken);
        var user = (User) auth.getPrincipal();
        var token = tokenService.generateToken(user);
        return new LoginResponseDTO(user.getId().toString(), token);
    }

    public void register(RegisterDTO body) {
        if (userRepository.findByEmail(body.email()) != null) {
            throw new IllegalArgumentException("Usuário já existe com o e-mail informado");
        }

        String hashedPassword = new BCryptPasswordEncoder().encode(body.password());
        User newUser = new User(
                body.name(),
                body.email(),
                hashedPassword,
                body.role()
        );

        userRepository.save(newUser);
    }
}
