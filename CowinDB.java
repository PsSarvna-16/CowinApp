package com.sarvna;

import java.sql.*;

class CowinDB{

	private String url = null;
	private String username = null;
	private String password = null;
	private String database = null;
	private Connection con = null; 
	private Statement st = null;

	public CowinDB(String username, String password, String database){

		this.username =  username;
		this.password = password;
		this.database = database;
		this.url = "jdbc:mysql://localhost:3306/" + this.database;
	}

	public boolean makeConnection() throws SQLException{

		con=DriverManager.getConnection(url, username, password);
		con.setAutoCommit(false);
		st = con.createStatement();
		if(con != null){
			return true;
		}else{
			return false;
		}
	}

	public boolean validatePassword(String type, String userName, String password) throws SQLException{

		String query = "SELECT u.username,u.password FROM user u INNER JOIN user_type ut USING (user_type_id) WHERE ut.name = ?";

		try(PreparedStatement ps = con.prepareStatement(query)){

			ps.setString(1, type);
	
			try(ResultSet rs = ps.executeQuery()){

				while(rs.next()){

					if(userName.equals(rs.getString(1))){
						if(password.equals(rs.getString(2))){
							System.out.println("Welcome " + userName);
							return true;
						}else{
							System.out.println("Invalid password !!!");
							return false;
						}
					}
				}
			}
		}

		System.out.println("Invalid Username !!!");
		return false;
	}

	public boolean addCustomer(String userName, String password, String name, String contact) throws SQLException{

		String user = "INSERT INTO user VALUES (DEFAULT,1,?,?,?,?)";
		String cust = "INSERT INTO customer VALUES (?,0,0)";
		con.setAutoCommit(false);
		try(PreparedStatement ps = con.prepareStatement(user)){
			ps.setString(1, userName);
			ps.setString(2, password);
			ps.setString(3, name);
			ps.setString(4, contact);

			int updateCount = ps.executeUpdate();
			if(updateCount == 1){
				try(ResultSet rs = st.executeQuery("SELECT LAST_INSERT_ID()")){
					if(rs.next()){
						int insertID = rs.getInt(1);
						try(PreparedStatement ps1 = con.prepareStatement(cust)){
							ps1.setInt(1,insertID);

							updateCount = ps1.executeUpdate();
							if(updateCount == 1){
								con.commit();
								return true;
							}
						}
					}
				}
			}
		}
		con.rollback();
		return false;
	}

	public void closeConnection() throws SQLException{

		con.close();
	}

}