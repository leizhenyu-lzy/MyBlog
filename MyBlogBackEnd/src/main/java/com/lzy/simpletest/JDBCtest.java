package com.lzy.simpletest;

import java.sql.*;

public class JDBCtest
{
    public static void main(String[] args)
    {
        Connection connection = null;
        Statement statement = null;
        try
        {
            java.sql.Driver driver = new org.postgresql.Driver();
            DriverManager.registerDriver(driver);

            String dburl = "jdbc:postgresql://127.0.0.1:5432/blogdatabase";
            String dbuser = "postgres";
            String dbpassword = "Lzy010409";

            connection = DriverManager.getConnection(dburl, dbuser, dbpassword);
            System.out.println("数据库连接对象 = " + connection);

            statement = connection.createStatement();

            String sql = new String("select * from blogschema.users;");
            // String sql1 = "select * from blogschema.users;";
            //得到输出研制成功，与数据库连接成功

            ResultSet resultset = statement.executeQuery(sql);
            // ResultSet resultset1 = statement.executeQuery(sql1);

            while(resultset.next())
            {
                String user_id = resultset.getString("user_id");
                String user_name = resultset.getString("username");
                String user_password = resultset.getString("user_password");
                System.out.println("user_id:"+user_id+" - "+"user_name:"+user_name+" - "+"user_password:"+user_password);
            }

        }
        catch(SQLException throwables)
        {
            throwables.printStackTrace();
        }
        finally
        {
            try
            {
                if(statement!=null)
                    statement.close();
            }
            catch (SQLException throwables)
            {
                throwables.printStackTrace();
            }

            try
            {
                if(connection!=null)
                    connection.close();
            }
            catch (SQLException throwables)
            {
                throwables.printStackTrace();
            }
        }
    }
}
