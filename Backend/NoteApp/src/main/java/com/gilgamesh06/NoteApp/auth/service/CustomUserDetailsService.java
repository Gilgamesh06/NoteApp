package com.gilgamesh06.NoteApp.auth.service;

import com.gilgamesh06.NoteApp.model.entity.Usuario;
import com.gilgamesh06.NoteApp.repository.UsuarioRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Clase que implementa la interfaz UserDetailsService (que posee el metodo loadUserByUsername)
 * @see UserDetailsService mirar la interfaz
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    /**
     * Constructor que instacia la clase UsuarioRepository
     * @param usuarioRepository
     */
    public CustomUserDetailsService(UsuarioRepository usuarioRepository){
        this.usuarioRepository = usuarioRepository;
    }

    /**
     * Metodo sobrescrito de la interfaz UserDetailsService para generar un objeto de la clase UserDetails
     * @see UserDetails mirar la clase userDetails
     * @param nickname nombre del usuario que queremos obtener us contraseña para crear un UserDetails
     * @return retorna un objeto de tipo UserDetails con el nickname y la password del Usuario
     * @throws UsernameNotFoundException retorna el mensaje: {Usuario no encontrado + nickname}
     */
    @Override
    public UserDetails loadUserByUsername(String nickname) throws UsernameNotFoundException{
        // Obtiene el objeto usuario a partir del nombre del usuario.
        Usuario usuario = usuarioRepository.findByNickname(nickname)
                .orElseThrow(()-> new UsernameNotFoundException("Usuario no encontrado: "+ nickname));

        return org.springframework.security.core.userdetails.User.builder()
                .username(usuario.getNickname())
                .password(usuario.getPassword())
                .roles("USER") // muy importante, añade ROLE_USER
                .build();

    }

}
