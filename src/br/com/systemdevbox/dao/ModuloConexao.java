/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.systemdevbox.dao;

import java.sql.*;

/**
 *
 * @author Felipe Chagas
 */
public class ModuloConexao {

    //Responsável por estabelecer conexão
    public static Connection conector() {
        Connection conexao = null;
    //chama o driver    
        String driver = "com.mysql.cj.jdbc.Driver"; 
    //Informações referente ao db    
        String url = "jdbc:mysql://localhost:3306/devboxdb";
        String user = "root";
        String password = "1234";
    //Estabelecendo conexão    
        try {
            Class.forName(driver);
            conexao = DriverManager.getConnection(url,user,password);
            return conexao;
        } catch (Exception e) {
        //Serve para esclarecer o erro apresentado    
            return null;
        }
    }

}
