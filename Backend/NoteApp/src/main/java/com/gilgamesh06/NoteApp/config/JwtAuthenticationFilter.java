package com.gilgamesh06.NoteApp.config;

import com.gilgamesh06.NoteApp.auth.service.CustomUserDetailsService;
import com.gilgamesh06.NoteApp.auth.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

/**
 * Clase que extiende de la clase OncePerRequestFilter
 * @see OncePerRequestFilter clase que evita que se llame mas de una vez
 */
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    private final CustomUserDetailsService userDetailsService;

    /**
     * Constructor que inicializa las dependencias necesarias
     * @param jwtService servicio que maneja los metodos para {crear,validar,extraer} el token jwt
     * @param userDetailsService metodo que contiene el metodo que retorna un objeto de tipo UserDetails
     */
    public JwtAuthenticationFilter(JwtService jwtService, CustomUserDetailsService userDetailsService){
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    /**
     * Metodo que recibe la peticion http y por medio del header determina si es una peticion
     * a un endpoint publico o que requiere autenticacion si es publico retorna para seguir la cadena de
     * filtros definidos en la clase SecurityConfig  si no verifica que el nickname del usuario no se nulo
     * ni que no este autenticado si es asi valida el token si el token es valido guarda al usaurio en el contexto de
     * autenticacion y continua la cadena de filtros
     * @param request
     * @param response
     * @param filterChain
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected  void doFilterInternal(HttpServletRequest request,
                                     HttpServletResponse response,
                                     FilterChain filterChain) throws ServletException, IOException{
        // se obtiene el encabezado de la peticion http que contiene Autorization
        final String authHeader = request.getHeader("Authorization");
        // se crean las variables que van almacenar el token y el nickname del usuario
        final String jwt;
        final String nickname;

        // si la cadena es nula o el header no comienza con Bearer deja pasar la peticion
        // esto es util para los endpoints publicos
        if(authHeader == null || !authHeader.startsWith("Bearer ")){
            filterChain.doFilter(request,response);
            return;
        }

        // Quita al String authHeader los primeros 7 caracteres "Bearer " dejando solo el token y almacenandolo en la variable jwt
        jwt = authHeader.substring(7);
        // Llama al metodo extracUsername que obtiene el cuerpo del token y con este obtiene el nickname
        nickname = jwtService.extractUsername(jwt);

        // Si el nombre no es nulo y el usuario no esta autenticado es decir es nulo
        if (nickname != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            // Obtiene el objeto UserDetails
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(nickname);

            // valida el token pasa los parametros {jwt el token, userDetails objeto que contiene el nickname y password del usaurio}
            if (jwtService.isTokenValid(jwt, userDetails)) {
                // Crea un objeto de tipo UsernamePassworAuthenticationToken que representa la session autenticada
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // Guarda elen el SecurityContextHolder el objeto UsernamePassworAutenticationToken
                SecurityContextHolder.getContext().setAuthentication(authToken);
                // Ahora spring sabe que usuario esta autenticado y con que roles/permisos.
            }
        }
        // Pasa el control al siguiente filtro de la cadena osea {en SecurityConfig}
        filterChain.doFilter(request,response);
    }
}
