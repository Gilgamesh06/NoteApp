package com.gilgamesh06.NoteApp.auth.service;

import com.gilgamesh06.NoteApp.auth.dto.login.LoginDTO;
import com.gilgamesh06.NoteApp.auth.dto.login.RegisterDTO;
import com.gilgamesh06.NoteApp.auth.dto.login.InfoUserDTO;
import com.gilgamesh06.NoteApp.model.entity.Persona;
import com.gilgamesh06.NoteApp.model.entity.Usuario;
import com.gilgamesh06.NoteApp.service.impl.PersonaService;
import com.gilgamesh06.NoteApp.service.impl.UsuarioService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

/**
 * Clase de servicio para {registrar, y logearse} : auth
 */
@Service
public class AuthService {

    private final PersonaService personaService;

    private final UsuarioService usuarioService;

    private final AuthenticationManager authenticationManager;

    private final JwtService jwtService;

    /**
     * Constructor que instancia las dependencias necesarias
     * @param personaService contiene los metodos para {crear,eliminar,actualizar,obtener} persona
     * @param usuarioService contiene los metodos para {crear,eliminar,actualizar,obtener} usuario
     * @param authenticationManager metodo que maneja la autenticacion del usario {SecurityContextHolder}
     * @param jwtService clase que contiene los metodos {crear,validar,obtenerCuerpo} de token jwt
     * @see org.springframework.security.core.context.SecurityContextHolder mirar manejo del contexto de seguridad
     */
    public AuthService(PersonaService personaService, UsuarioService usuarioService,
                       AuthenticationManager authenticationManager,
                       JwtService jwtService){
        this.personaService = personaService;
        this.usuarioService = usuarioService;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }


    /**
     * Metodo para registrar un usuario por medio del DTO: RegisterDTO
     * @param register  DTO: que contiene la informacion para crear los objetos de tipo Persona y Usuario
     * @return retorna un DTO InfoUserDTO
     */
    public InfoUserDTO registerUser(RegisterDTO register){
        Persona persona = personaService.save(register);
        Usuario usuario = usuarioService.save(register, persona);
        return  usuarioService.createObjectInfoUserDTO(usuario);
    }

    /**
     * Metodo para logearse por medio de un DTO_ LoginDTO
     * @param login DTO: que contiene los atributos nickname y password
     * @return retrona un string que es el jwt token compactado
     */
    public String loginUser(LoginDTO login) {
        // Aquí entra el AuthenticationProvider configurado
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        login.getNickname(),
                        login.getPassword()
                )
        );
        // Si llega aquí, significa que fue autenticado con éxito
        UserDetails userDetails = (UserDetails) auth.getPrincipal();

        // Generar el token con el usuario autenticado
        return jwtService.generateToken(userDetails);
    }
}
