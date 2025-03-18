package api.register.login.dto;

public class LoginRequest {
    // Campos da classe
    private String email; // Armazena o email do usuário
    private String senha; // Armazena a senha do usuário


    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getSenha() {
        return senha;
    }
    public void setSenha(String senha) {
        this.senha = senha;
    }
}