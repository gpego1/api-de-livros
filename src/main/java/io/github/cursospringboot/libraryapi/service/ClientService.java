package io.github.cursospringboot.libraryapi.service;
import io.github.cursospringboot.libraryapi.model.Client;
import io.github.cursospringboot.libraryapi.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository repository;
    private final PasswordEncoder encoder;

    public Client salvar(Client client){
        String secret = encoder.encode(client.getClientSecret());
        client.setClientSecret(secret);
        return repository.save(client);
    }

    public Client obterPorClientId(String clientId){
        return repository.findByClientId(clientId);
    }
}
