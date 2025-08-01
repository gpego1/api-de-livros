package io.github.cursospringboot.libraryapi.security;
import io.github.cursospringboot.libraryapi.model.User;
import io.github.cursospringboot.libraryapi.service.UsuarioService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class LoginSocialSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

   private static final String SENHA_PADRAO = "dudagabi";
    private final UsuarioService userService;

   @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication) throws ServletException, IOException {
       OAuth2AuthenticationToken auth2AuthenticationToken = (OAuth2AuthenticationToken) authentication;
       OAuth2User principal = auth2AuthenticationToken.getPrincipal();

       String email = principal.getAttribute("email");
       User usuario = userService.obterPorEmail(email);

       if(usuario == null){
          cadastrarUsuarioNaBase(email);
       }

       authentication = new CustomAuthentication(usuario);
       SecurityContextHolder.getContext().setAuthentication(authentication);

       super.onAuthenticationSuccess(request, response, authentication);
   }

   private User cadastrarUsuarioNaBase(String email){
         User usuario;
         usuario = new User();
         usuario.setEmail(email);
         usuario.setLogin(obterLoginPorEmail(email));
         usuario.setSenha(SENHA_PADRAO);
         usuario.setRoles(List.of("OPERADOR"));
         userService.salvar(usuario);
         return usuario;
   }

   private String obterLoginPorEmail(String email) {
      return email.substring(0, email.indexOf("@"));
   }
}
