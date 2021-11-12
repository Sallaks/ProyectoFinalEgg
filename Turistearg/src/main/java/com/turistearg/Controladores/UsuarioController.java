
package com.turistearg.Controladores;


import com.turistearg.Entidades.Usuario;
import com.turistearg.Excepciones.ErrorServicio;
import com.turistearg.Servicios.UsuarioServicio;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {
    
    @Autowired
    UsuarioServicio usuarioServicio;
    
     @GetMapping("/usuario")
        public String usuario(){
        return "usuario";
    }
        @GetMapping("/perfil")
        public String perfil(){
        return "perfil";
    }
        @GetMapping("/editar-perfil")
        public String editarPerfil(){
        return "editarPerfil";    
        }

    
        @GetMapping("/cambiarfoto")
        public String cambiarfoto(){
        return "cambiarfoto";
        
        }
        
        @PreAuthorize(("hasAnyRole('ROLE_USUARIO_REGISTRADO')"))
        @PostMapping("/actualizar-perfil")
        public String actualizarperfil(ModelMap model,HttpSession session,@RequestParam String id, @RequestParam String nombreDeUsuario,
            @RequestParam String apellido,
            @RequestParam MultipartFile foto,
            @RequestParam String clave1,
            @RequestParam String clave2,
            @RequestParam String mail)
            {
            
            Usuario usuario = null;
             try {
            Usuario login = (Usuario) session.getAttribute("usuariosession");
            if (login == null || !login.getId().equals(id)) {
                return "redirect:/inicio";
            }
            
            usuario = usuarioServicio.buscarPorId(id);
            usuarioServicio.modificar(foto, id, nombreDeUsuario, mail, clave1, clave2);
            session.setAttribute("usuariosession", usuario);
            return "redirect:/inicio";
        } catch (ErrorServicio ex) {
            
            
            model.put("error", ex.getMessage());
            model.put("usuario", usuario);
        }
        return "perfil";

        } 
       }
      
