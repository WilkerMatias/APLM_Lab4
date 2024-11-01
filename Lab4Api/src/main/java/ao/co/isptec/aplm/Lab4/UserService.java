package ao.co.isptec.aplm.Lab4;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public String register(User user) {
        if (userRepository.findByUsername(user.getName()) != null) {
            return "Usuário já existe!";
        }
        userRepository.save(user);
        return "Usuário registrado com sucesso!";
    }

    public boolean login(User user) {
        User existingUser = userRepository.findByUsername(user.getName());
        return existingUser != null && (user.getPassword().equals(existingUser.getPassword()));
    }
}
