package ec.edu.uteq.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.mindrot.jbcrypt.BCrypt;
import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String csrfToken = java.util.UUID.randomUUID().toString();
        req.getSession().setAttribute("csrfToken", csrfToken);
        req.getRequestDispatcher("/WEB-INF/views/login.jsp")
                .forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        String tokenFormulario = req.getParameter("csrfToken");
        String tokenSesion = (String) req.getSession().getAttribute("csrfToken");
        if (tokenSesion == null || !tokenSesion.equals(tokenFormulario)) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN, "Token CSRF invalido");
            return;
        }

        String email = req.getParameter("email");
        String clave = req.getParameter("clave");

        // Simulacion: en produccion se busca en la BD
        String hashGuardado = BCrypt.hashpw("password123", BCrypt.gensalt(12));

        if (BCrypt.checkpw(clave, hashGuardado)) {
            HttpSession sesion = req.getSession(true);
            sesion.setAttribute("usuarioEmail", email);
            sesion.setMaxInactiveInterval(30 * 60);
            req.changeSessionId();
            resp.sendRedirect(req.getContextPath() + "/app/dashboard");
        } else {
            req.setAttribute("errorLogin", "Email o contrasena incorrectos.");
            req.getRequestDispatcher("/WEB-INF/views/login.jsp")
                    .forward(req, resp);
        }
    }
}