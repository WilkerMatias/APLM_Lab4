package ao.co.isptec.aplm.Lab4;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService authService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody User user) {
        String response = authService.register(user);
        return response.equals("Usuário registrado com sucesso!")
                ? ResponseEntity.ok(response)
                : ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody User user) {
        boolean success = authService.login(user);
        return success
                ? ResponseEntity.ok("Login bem-sucedido")
                : ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciais inválidas");
    }
}
