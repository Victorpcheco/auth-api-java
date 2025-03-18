package api.register.login.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Entity
@Table(name = "Tb_usuarios")
@ToString
public class UsuariosEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @NotBlank(message = "O campo nome não pode ser vazio!")
    @JsonProperty("nome") // <- Garante que o nome seja reconhecido no JSON
    private String nome;


    @Column(name = "email", unique = true, nullable = false)
    @NotBlank(message = "O campo email não pode ser vazio!")
    @Email(message = "O email precisa ser válido!")
    private String email;

    @Column(name = "senha", nullable = false)
    @NotBlank(message = "O campo senha não pode ser vazio!")
    @Size(min = 8, message = "A senha precisa ter no mínimo 8 caracteres.")
    @Pattern(
            regexp = "^(?=.*[!@#$%^&*(),.?\":{}|<>]).+$",
            message = "A senha deve conter pelo menos um caractere especial.")
    private String senha;


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


