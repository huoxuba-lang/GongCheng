package MySQL;

import java.sql.*;

public class BaseDao {
    public void queryBook(){
        ResultSet rs = null;
        Statement st = null;
        Connection conn = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/school","root","root");
            String sql = "select * from book a";
            st = conn.createStatement();
            rs = st.executeQuery(sql);

            while (rs.next()){
                String bld = rs.getString("bld");
                String bName = rs.getString("bName");
                System.out.println("图书编号:" +  bld +" 图书名称：" + bName);
            }
            // 修改一条图书数据
            String sqlUpdate = "update book set pubComp = '清华出版社' where bld = 'B001'";
            st.execute(sqlUpdate);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            // 资源要释放
            try {
                rs.close();
                st.close();
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }


    }




}
