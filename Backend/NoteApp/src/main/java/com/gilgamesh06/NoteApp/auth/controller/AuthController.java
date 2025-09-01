package com.gilgamesh06.NoteApp.auth.controller;

import com.gilgamesh06.NoteApp.auth.service.AuthService;
import com.gilgamesh06.NoteApp.auth.dto.login.RegisterDTO;
import com.gilgamesh06.NoteApp.auth.dto.login.InfoUserDTO;
import com.gilgamesh06.NoteApp.auth.dto.login.LoginDTO;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Clase Controller que contiene los endpoints para {register, login}
 */
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    /**
     * Constructor que instancia la dependencia necesaria
     * @param authService servicio que contiene los metodos:
     *                    1- para guardar una Persona y su Usuario
     *                    2- Para logearse por medio de nickname y password
     */
    public AuthController(AuthService authService){
        this.authService = authService;
    }

    /**
     * Enpoint: {/"create-user"} que permite guardar un objeto persona y su respectivo objeto usuario
     * @param register DTO: contiene los atributos necesarios para crear un objeto de tipo Persona y Usuario
     * @return retorna un StatusCode: 201 CREATED y un DTO: infoUserDTO
     */
    @PostMapping("/register")
    public ResponseEntity<InfoUserDTO> createUser(@Valid @RequestBody RegisterDTO register){
        InfoUserDTO user = authService.registerUser(register);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    /**
     * Enpoint: {"/login"} que permite logearse genera el token jwt de acceso a los endpoints privados
     * @param login DTO: que contiene los atributos nickname y password
     * @return retorna un StatusCode: 200 OK y el String que contiene el token jwt
     */
    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@Valid @RequestBody  LoginDTO login){
        String token = authService.loginUser(login);
        return new ResponseEntity<>(token,HttpStatus.OK);
    }

}
