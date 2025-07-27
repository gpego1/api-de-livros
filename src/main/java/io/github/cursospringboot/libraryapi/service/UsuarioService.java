package io.github.cursospringboot.libraryapi.service;
import io.github.cursospringboot.libraryapi.model.User;
import io.github.cursospringboot.libraryapi.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository repository;
    private final PasswordEncoder encoder;

    public void salvar(User usuario){
        String senha = usuario.getSenha();
        usuario.setSenha(encoder.encode(senha));
        repository.save(usuario);
    }

    public User obterPorLogin(String login){
        return repository.findByLogin(login);
    }
    public User obterPorEmail(String email){
        return repository.findByEmail(email);
    }
}
