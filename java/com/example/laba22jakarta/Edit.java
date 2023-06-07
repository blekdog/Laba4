package com.example.laba22jakarta;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.ServletContext;

import com.mysql.cj.jdbc.Driver; // Класс для установления подключения к БД

@WebServlet("/edit")
public class Edit extends HttpServlet {
    Connection connection;
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServletContext sc = getServletContext();
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
        String query = String.format("select * from series where food_index=" + request.getParameter("id"));
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query); // Выполнение SQL инструкции
            if (resultSet.next()) {
                Food tempfood = new Food(resultSet.getString("firstMeal"),
                        resultSet.getString("secondMeal"),
                        resultSet.getString("thirdMeal"),
                        resultSet.getString("fourthMeal"),
                        resultSet.getInt("weight"),
                        resultSet.getInt("food_index"));
                request.setAttribute("series", tempfood);
            }
            sc.getRequestDispatcher("/jsp/edit.jsp").forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}