package com.example.laba22jakarta;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.mysql.cj.jdbc.Driver; // Класс для установления подключения к БД

@WebServlet("/delete")
public class Delete extends HttpServlet {
    private Connection connection;
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletContext sc = getServletContext();
        sc.getRequestDispatcher("/jsp/read.jsp").forward(req, resp);
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        try {
            Driver driver = new com.mysql.cj.jdbc.Driver(); // Драйвер для подключения к MySQL
            DriverManager.registerDriver(driver); // Указание диспетчеру драйверов какой драйвер загружается
            connection = DriverManager.getConnection("jdbc:mysql://localhost:8080/food_database", "root", ""); // Установление соединения с БД
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String query = String.format("delete from food where food_index='" + request.getReader().readLine() + "'");
        try {
            Statement statement = connection.createStatement();
            statement.execute(query); // Выполнение SQL инструкции
        } catch (SQLException e) {
            e.printStackTrace();
        }
        response.sendRedirect("jsp/write.jsp");
    }
}