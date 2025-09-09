package br.com.giovanni.todolist.filter;

import java.io.IOException;
import java.util.Base64;

import at.favre.lib.crypto.bcrypt.BCrypt;
import br.com.giovanni.todolist.user.IUserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class FilterTaskAuth extends OncePerRequestFilter {

    @Autowired

    private IUserRepository userRepository;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        var serveltPath= request.getServletPath();

        if (serveltPath.startsWith("/tasks/")){// Pega o header de autorização
            var authorization = request.getHeader("Authorization");
            var authEncoder=authorization.substring("Basic".length()).trim();
            byte[] authDecode= Base64.getDecoder().decode(authEncoder);

            var authString= new String(authDecode);




            //["Giovanni","123456"]
            String[] credentials= authString.split(":");
            String username=credentials[0];
            String password=credentials[1];



            // validar usuario
            var user=   this.userRepository.findByUsername(username);
            if (user==null){
                response.sendError(401);
            } else {

                //Validar senha
                var passwordVerify=   BCrypt.verifyer().verify(password.toCharArray(), user.getPassword());
                if (passwordVerify.verified){
                    request.setAttribute("idUser",user.getId());
                    filterChain.doFilter(request, response);
                } else {
                    response.sendError(401);
                }
                // Continua o fluxo normalmente

            }

        }else {
            filterChain.doFilter(request, response);
        }



    }
}
