package api.register.login.controllers;


import api.register.login.dto.LoginRequest;
import api.register.login.dto.LoginResponse;
import api.register.login.entities.UsuariosEntity;
import api.register.login.jwt.JwtUtil;
import api.register.login.services.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {


    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UsuarioService service;

    @PostMapping("/register")
    public ResponseEntity<UsuariosEntity> registerNewUser(@Valid @RequestBody UsuariosEntity usuariosEntity){
        //System.out.println("Recebida requisição para registrar usuário: " + usuarioModel.getEmail());
        UsuariosEntity usuarioRegistrado = service.registerUser(usuariosEntity);
        return ResponseEntity.ok(usuarioRegistrado); // retorno 200 (ok) usuario registrado.
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request){
        boolean autenticado = service.autenticateUser(request.getEmail(), request.getSenha());

        if(autenticado) { // se autenticado >>
            String token = jwtUtil.generateToken(request.getEmail());
            return ResponseEntity.ok(new LoginResponse(token)); // retorna o token com stts 200
        }
        else {
            return ResponseEntity.status(401).build();
        }
    }

}
