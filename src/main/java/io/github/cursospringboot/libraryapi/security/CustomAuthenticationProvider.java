package io.github.cursospringboot.libraryapi.security;
import io.github.cursospringboot.libraryapi.model.User;
import io.github.cursospringboot.libraryapi.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final UsuarioService userService;
    private final PasswordEncoder encoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String login = authentication.getName();
        String senhaDigitada = authentication.getCredentials().toString();

        User foundUser = userService.obterPorLogin(login);
        if(foundUser == null){
            throw new UsernameNotFoundException("Usuario e/ou senhas incorretos");
        }
        String senhaCriptografada = foundUser.getSenha();
        boolean senhasBatem = encoder.matches(senhaDigitada, senhaCriptografada);
        if(senhasBatem){
            return new CustomAuthentication(foundUser);
        }
        throw new UsernameNotFoundException("Usuario e/ou senhas incorretos");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.isAssignableFrom(UsernamePasswordAuthenticationToken.class);
    }
}
