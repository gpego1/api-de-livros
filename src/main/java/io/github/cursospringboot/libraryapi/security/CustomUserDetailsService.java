package io.github.cursospringboot.libraryapi.security;
import io.github.cursospringboot.libraryapi.model.User;
import io.github.cursospringboot.libraryapi.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UsuarioService service;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException{
        User usuario = service.obterPorLogin(login);

        if(usuario == null){
             throw new UsernameNotFoundException("Usuario nao encontrado");
        }

        return org.springframework.security.core.userdetails.User.builder()
                .username(usuario.getLogin())
                .password(usuario.getSenha())
                .roles(String.valueOf(usuario.getRoles()))
                .build();
    }
}
