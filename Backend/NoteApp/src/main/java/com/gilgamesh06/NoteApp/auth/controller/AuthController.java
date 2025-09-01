package com.gilgamesh06.NoteApp.controller;

import com.gilgamesh06.NoteApp.auth.service.AuthService;
import com.gilgamesh06.NoteApp.model.dto.login.RegisterDTO;
import com.gilgamesh06.NoteApp.model.dto.login.InfoUserDTO;
import com.gilgamesh06.NoteApp.model.dto.login.LoginDTO;
import com.gilgamesh06.NoteApp.model.entity.Usuario;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {


    /**
     * Atributa para crear una instancias de la clase PersonaService
     * @param personaService
     */
    private final AuthService authService;

    /**
     * Constructor que asigna la instancia al atibuto PersonaService
     * @param authService
     */
    public AuthController(AuthService authService){
        this.authService = authService;
    }

    /**
     * Enpoint url {/"create-user"} que permite guardar un persona y su respectivo dato de usuario
     * @param register
     * @return 201 y infoUserDTO o retorna un 404
     */
    @PostMapping("/register")
    public ResponseEntity<InfoUserDTO> createUser(@Valid RegisterDTO register){
        InfoUserDTO user = authService.registerUser(register);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(LoginDTO login){
        String token = authService.loginUser(login);
        return new ResponseEntity<>(token,HttpStatus.OK);
    }

}
