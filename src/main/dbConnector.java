/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author axcee
 */
public class dbConnector {
        private Connection connect;
      // constructor to connect to our database
        public dbConnector(){
            try{
                connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/prospectus", "root", "");
            }catch(SQLException ex){
                    System.out.println("Can't connect to database: "+ex.getMessage());
            }
        }
             
        //Function to save data
        public boolean insertData(String query, Object... values) {
            try (PreparedStatement pstmt = connect.prepareStatement(query)) {
                for (int i = 0; i < values.length; i++) {
                    pstmt.setObject(i + 1, values[i]); // Set values dynamically
                }
                int rowsAffected = pstmt.executeUpdate();
                return rowsAffected > 0; // Returns true if the insertion was successful
            } catch (SQLException e) {
                System.out.println("Insert failed: " + e.getMessage());
                e.printStackTrace();
                return false;
            }
        }
        
        //Function to retrieve data
        public ResultSet getData(String sql) throws SQLException{
            Statement stmt = connect.createStatement();
            ResultSet rst = stmt.executeQuery(sql);
            return rst;
        }
            // Method to get the connection
        public  Connection getConnection() {
            return connect;
        }
        public boolean updateData(String query, Object... params) {
        try (PreparedStatement pstmt = connect.prepareStatement(query)) {
            for (int i = 0; i < params.length; i++) {
                pstmt.setObject(i + 1, params[i]);
            }
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

//        public void displayData(){
//           try{
//               dbConnector dbc = new dbConnector();
//               ResultSet rs = dbc.getData("SELECT * FROM tbl_student");
//               student_table.setModel(DbUtils.resultSetToTableModel(rs));
//                rs.close();
//           }catch(SQLException ex){
//               System.out.println("Errors: "+ex.getMessage());
//
//           }
//
//       }

    }
        

