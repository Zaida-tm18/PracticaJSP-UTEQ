package ec.edu.uteq.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.mindrot.jbcrypt.BCrypt;
import java.io.IOException;

@WebServlet(urlPatterns = {"/registro"})
public class RegistroServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/registro.jsp")
                .forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");

        String nombre = req.getParameter("nombre");
        String email = req.getParameter("email");
        String clave = req.getParameter("clave");
        String clave2 = req.getParameter("clave2");
        String edadStr = req.getParameter("edad");

        StringBuilder errores = new StringBuilder();

        if (nombre == null || nombre.trim().isEmpty()) {
            errores.append("El nombre es obligatorio. ");
        }
        if (email == null || !email.matches("^[A-Za-z0-9+_.\\-]+@[A-Za-z0-9.\\-]+$")) {
            errores.append("El email no es valido. ");
        }
        if (clave == null || clave.length() < 8) {
            errores.append("La clave debe tener min. 8 caracteres. ");
        }
        if (clave != null && !clave.equals(clave2)) {
            errores.append("Las claves no coinciden. ");
        }

        int edad = 0;
        try {
            edad = Integer.parseInt(edadStr);
            if (edad < 1 || edad > 120) {
                errores.append("La edad debe estar entre 1 y 120. ");
            }
        } catch (NumberFormatException e) {
            errores.append("La edad debe ser un numero entero. ");
        }

        if (errores.length() > 0) {
            req.setAttribute("errores", errores.toString());
            req.setAttribute("nombre", nombre);
            req.setAttribute("email", email);
            req.setAttribute("edad", edadStr);
            req.getRequestDispatcher("/WEB-INF/views/registro.jsp")
                    .forward(req, resp);
            return;
        }

        String hashClave = BCrypt.hashpw(clave, BCrypt.gensalt(12));

        resp.sendRedirect(req.getContextPath() + "/login?registrado=true");
    }
}