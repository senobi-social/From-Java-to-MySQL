package temperature01;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Sample01 {


	private static String COMMA = ","  ; //コンマ

	public static void main(String[] args) {

		/*
		 *  ① データベースへの接続準備
		 */

		// JDBCドライバの相対パスを指定
		String driverName = "com.mysql.cj.jdbc.Driver";


		// 接続先のデータベースを指定
		String jdbcUrl = "jdbc:mysql://localhost:3306/temperature?characterEncoding=UTF-8&serverTimezone=JST&useSSL=false";


		// 接続するユーザー名を指定
		String userId = "test_user" ;


		// 接続するユーザーのパスワードを指定
		String userPass = "test_pass" ;


		/*
		 *  ② JDBCドライバのロード
		 */

		try {


			Class.forName(driverName);


		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}


		// JDBCに接続するためのオブジェクトを生成
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;



		try {

			// ① 接続の確立
			con = DriverManager.getConnection(jdbcUrl , userId , userPass);

			// ② SQL文の送信 & 抽出結果の処理
			StringBuffer buf = new StringBuffer();

			buf.append("SELECT") ;
			buf.append(" id ,") ;
			buf.append(" name ,") ;
			buf.append(" gender ") ;
			buf.append(" FROM") ;
			buf.append(" temperature_members") ;
			buf.append(" ORDER BY") ;
			buf.append(" id ") ;


			// psオブジェクトを生成&発行するSQLをセット
			ps = con.prepareStatement(buf.toString());

			// sql文の送信&実行結果を取得
			rs = ps.executeQuery();


		while (rs.next()) {

			StringBuffer rsbuf = new StringBuffer();

			rsbuf.append(rs.getString("id"));
			rsbuf.append(COMMA);
			rsbuf.append(rs.getString("name"));
			rsbuf.append(COMMA);
			rsbuf.append(rs.getString("gender"));


			// 加工したレコード分のデータを表示
			System.out.println(rsbuf.toString());
		}


		} catch(SQLException e) {
			e.printStackTrace();

		} finally {

			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			};


			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			};

			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			};




		};
	}

}
