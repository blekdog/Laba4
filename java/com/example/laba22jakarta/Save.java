package com.example.laba22jakarta;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.ServletContext;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mysql.cj.jdbc.Driver; // Класс для установления подключения к БД

@WebServlet("/save")
public class Save extends HttpServlet {
    Connection connection;
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String data = request.getReader().readLine();
        Food food = gson.fromJson(data, Food.class);
        String query = String.format("update food set firstMeal='%s', secondMeal='%s', thirdMeal='%s', fourthMeal='%s', weight='%d' where food_index='%d'",
                food.getFirstMeal(),
                food.getSecondMeal(),
                food.getThirdMeal(),
                food.getFourthMeal(),
                food.getWeight(),
                food.getIdInDatabase());
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(query); // Выполнение SQL инструкции
        } catch (SQLException e) {
            e.printStackTrace();
        }
        sc.getRequestDispatcher("/jsp/read.jsp").forward(request, response);
    }