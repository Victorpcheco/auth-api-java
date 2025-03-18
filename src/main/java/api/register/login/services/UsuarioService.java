package api.register.login.services;


import api.register.login.entities.UsuariosEntity;
import api.register.login.repository.UsuariosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuariosRepository repository;
    @Autowired
    private PasswordEncoder passwordEncoder;


    public UsuariosEntity registerUser(UsuariosEntity usuarioEntity) {

        usuarioEntity.setSenha(passwordEncoder.encode(usuarioEntity.getSenha()));
        return repository.save(usuarioEntity);
    }

    public Optional<UsuariosEntity> findUserByEmail(String email){
        return repository.findByEmail(email);
    }


    // metodo para autenticar um usuario com base no email e senha
    public boolean autenticateUser(String email, String senha){
        Optional<UsuariosEntity> usuarioModelOptional = repository.findByEmail(email);

        if (usuarioModelOptional.isPresent()){
            UsuariosEntity usuario = usuarioModelOptional.get(); // se existir, verifica senha
            //System.out.println("Usuario encontrado" + usuario.getEmail()); // log de teste
            boolean senhaCorreta = passwordEncoder.matches(senha, usuario.getSenha());
            //System.out.println("Senha correta?" + senhaCorreta); // log de teste senha
            return senhaCorreta;
        } else {
            return false;
        }
    }

}
