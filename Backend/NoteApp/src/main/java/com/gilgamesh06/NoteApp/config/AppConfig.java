package com.gilgamesh06.NoteApp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Clase que genera el metodo PasswordEncoder el cual me permite encriptar y desencritar la contraseña
 * @see PasswordEncoder
 */
@Configuration
public class AppConfig {

    /**
     * Metodo: @Bean (se carga en el contexto de Spring se puede instancias como atributo) para encriptar y desencritar
     * @see BCryptPasswordEncoder metodo utilizando de encritacion
     * @return retorna un objeto de la clase PasswordEncoder
     */
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
