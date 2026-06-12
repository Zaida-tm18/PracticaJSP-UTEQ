package ec.edu.uteq.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/app/dashboard")
public class DashboardServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        HttpSession sesion = req.getSession(false);
        String email = (String) sesion.getAttribute("usuarioEmail");
        req.setAttribute("email", email);
        req.getRequestDispatcher("/WEB-INF/views/dashboard.jsp")
                .forward(req, resp);
    }
}