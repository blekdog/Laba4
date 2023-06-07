package com.example.laba22jakarta;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mysql.cj.jdbc.Driver; // Класс для установления подключения к БД

@WebServlet("/read")
public class ReadServlet extends HttpServlet {
    private Connection connection;
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletContext sc = getServletContext();
        sc.getRequestDispatcher("/jsp/read.jsp").forward(req, resp);
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Driver driver = new com.mysql.cj.jdbc.Driver(); // Драйвер для подключения к MySQL
            DriverManager.registerDriver(driver); // Указание диспетчеру драйверов какой драйвер загружается
            connection = DriverManager.getConnection("jdbc:mysql://localhost:8080/food_database", "root", "");
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        response.setContentType("application/json");
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String query = String.format("select * from Food");
        ArrayList<Food> food = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query); // Выполнение SQL инструкции
            while (resultSet.next()) {
                Food tempFood = new Food (resultSet.getString("firstMeal"),
                        resultSet.getString("secondMeal "),
                        resultSet.getString("thirdMeal"),
                        resultSet.getString("fourthMeal"),
                        resultSet.getInt("weight"),
                        resultSet.getInt("food_index"));
                food.add(tempFood);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        PrintWriter out = response.getWriter();
        String jsonArrayString = "";
        if (food.size() != 0) {
            jsonArrayString = gson.toJson(food);
        }
        out.print(jsonArrayString);
        out.close();
    }
}
