package Controllers;

import java.io.IOException;
import java.util.Map;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import dao.Dao;
import dao.IDao;
import com.google.gson.Gson;

@WebServlet("/chartData")
public class chartController extends HttpServlet{
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        IDao dao = new Dao(); 
        Map<String, Long> chartData = dao.inscriptionsOfCurrentYearByMonth();
        Gson gson = new Gson();
        String jsonData = gson.toJson(chartData);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(jsonData);
    }

}
