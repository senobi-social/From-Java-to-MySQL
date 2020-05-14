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

			// Class.forNameはバイトコードのJDBCドライバをロードするメソッド
			// メモリ上にClassオブジェクトとしてJDBCドライバを生成する
			
			Class.forName(driverName);


		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}


		/*
		 *  ③ JDBCに接続するためのオブジェクトを準備
		 */
		
		// ConnectionオブジェクトはDB接続に関する情報を持つ
		Connection con = null;
		
		// PreparedStatementオブジェクトはSQL文をセットするもの
		PreparedStatement ps = null;
		
		// ResultSetオブジェクトはセットされたSQL文を送信&取得するもの
		ResultSet rs = null;



		try {
			
			/*
			 *  ④ データベースへの接続の確立
			 */

			// JDBCドライバマネージャからMySQLに接続
			// 引数は(接続先DB , ユーザー名 , パスワード)
			// DB接続が完了したら、準備済みのConnectionオブジェクトに情報を渡す
			
			con = DriverManager.getConnection(jdbcUrl , userId , userPass);
			
			
			
			/*
			 *  ⑤ SQL文の送信
			 */
			
			// 発行したいSQL文を用意しておく
			StringBuffer buf = new StringBuffer();

			buf.append("SELECT") ;
			buf.append(" id ,") ;
			buf.append(" name ,") ;
			buf.append(" gender ") ;
			buf.append(" FROM") ;
			buf.append(" temperature_members") ;
			buf.append(" ORDER BY") ;
			buf.append(" id ") ;


			// 発行するSQL文をセット(PreparedStatementオブジェクト）
			ps = con.prepareStatement(buf.toString());

			// SQL文の送信&実行結果を取得（ResultSetオブジェクト）
			rs = ps.executeQuery();

		/*
		 *  ⑥ ResultSetオブジェクトから１レコードずつ情報を取得&表示
		 */
			
		while (rs.next()) {

			StringBuffer rsbuf = new StringBuffer();

			rsbuf.append(rs.getString("id"));
			rsbuf.append(COMMA);
			rsbuf.append(rs.getString("name"));
			rsbuf.append(COMMA);
			rsbuf.append(rs.getString("gender"));


			// 加工した１レコード分のデータを表示
			System.out.println(rsbuf.toString());
		}


		} catch(SQLException e) {
			
			e.printStackTrace();
					
		} finally {
			
			/*
			 *  ⑦ 接続の解除
			 */
			
			
			// ResultSetオブジェクトの解除
			
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			};
			
			// PreparedStatementオブジェクトの解除
			
			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			};
			
			// Connectionオブジェクトの解除
			
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
