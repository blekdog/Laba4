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

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mysql.cj.jdbc.Driver; // Класс для установления подключения к БД

@WebServlet("/write")
public class WriteServlet extends HttpServlet {
    private Connection connection;
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletContext sc = getServletContext();
        sc.getRequestDispatcher("/jsp/write.jsp").forward(req, resp);
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Driver driver = new com.mysql.cj.jdbc.Driver(); // Драйвер для подключения к MySQL
            DriverManager.registerDriver(driver); // Указание диспетчеру драйверов какой драйвер загружается
            connection = DriverManager.getConnection("jdbc:mysql://localhost:8080/food_database","root",""); // Установление соединения с БД
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String data = request.getReader().readLine();
        Food food = gson.fromJson(data, Food.class);
        String query = String.format("insert into food(firstMeal, secondMeal, thirdMeal, fourthMeal, weight) values ('%s','%s','%s','%s','%d')",
                food.getFirstMeal(),
                food.getSecondMeal(),
                food.getThirdMeal(),
                food.getFourthMeal(),
                food.getWeight());
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(query); // Выполнение SQL инструкции
        } catch (SQLException e) {
            e.printStackTrace();
        }