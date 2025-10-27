package br.com.codemain.nutrixpertai.service;

import br.com.codemain.nutrixpertai.dto.Auth.LoginResponseDTO;
import br.com.codemain.nutrixpertai.dto.Auth.RegisterDTO;

public interface IAuthenticationService {
    
    public LoginResponseDTO login(String email, String password);

    public void register(RegisterDTO body);
}
