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

	public boolean changePassword(String userName, String oldPwd,String newPwd) throws SQLException{

		String pwd = "SELECT password FROM user where username = ?";

		try(PreparedStatement ps = con.prepareStatement(pwd)){
			ps.setString(1, userName);
				
			try(ResultSet rs = ps.executeQuery()){
				if(rs.next()){
					if(oldPwd.equals(rs.getString(1))){
					String updatePwd = "UPDATE user SET password = ? WHERE username = ?";
	
						try(PreparedStatement ps1 = con.prepareStatement(updatePwd)){
							ps1.setString(1,newPwd);
							ps1.setString(2,userName);

							int updateCount = ps1.executeUpdate();
							if(updateCount == 1){
								con.commit();
								return true;
							}
						}
					}else{
						System.out.println("Invalid Old Password !!!");
					}
				}
			}
		}
		return false;
	}

	public boolean addCustomer(Customer newCust) throws SQLException{

		String user = "INSERT INTO user VALUES (DEFAULT,1,?,?,?,?)";
		String cust = "INSERT INTO customer VALUES (?,0,0)";
		
		try(PreparedStatement ps = con.prepareStatement(user)){
			ps.setString(1, newCust.getUserName());
			ps.setString(2, newCust.getPassword());
			ps.setString(3, newCust.getFullName());
			ps.setString(4, newCust.getContact());

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

	public boolean addMember(String userName,Member newMemb) throws SQLException{

		int user_id ; 
		try(ResultSet rs = st.executeQuery("SELECT user_id FROM user where username='" +userName +"'")){
			if(rs.next()){
				user_id = rs.getInt(1);
			}else{
				return false;
			}
		}
		String query = "INSERT INTO member VALUES (DEFAULT,?,?,?,?,?,?)";
		try(PreparedStatement ps = con.prepareStatement(query)){
			ps.setInt(1,user_id);
			ps.setString(2,newMemb.getName());
			ps.setString(3,newMemb.getAadhar());
			ps.setString(4,newMemb.getDob());
			ps.setInt(5,newMemb.getAge());
			ps.setString(6,newMemb.getGender());

			int updateCount = ps.executeUpdate();
			if(updateCount == 1){
				con.commit();
				return true;
			}
		}
		return false;
	}

	public boolean addHospital(Hospital newHosp) throws SQLException{

		String hosp = "INSERT INTO hospital VALUES (DEFAULT,?,?,?,?)";
		try(PreparedStatement ps = con.prepareStatement(hosp)){
			ps.setString(1, newHosp.getName());
			ps.setString(2, newHosp.getPincode());
			ps.setString(3, newHosp.getLocation());
			ps.setString(4, newHosp.getContact());

			int updateCount = ps.executeUpdate();
			if(updateCount == 1){
				try(ResultSet rs = st.executeQuery("SELECT LAST_INSERT_ID()")){
					if(rs.next()){
						int hospID = rs.getInt(1);
						newHosp.setHospId(hospID);
						con.commit();
						return true;
					}
				}
			}
		}
		con.rollback();
		return false;
	}

	public boolean addHospitalAdmin(HospitalAdmin hospAdmin) throws SQLException{

		String user = "INSERT INTO user VALUES (DEFAULT,3,?,?,?,?)";
		String hosp_admin = "INSERT INTO hosp_admin VALUES (?,?)";
		
		try(PreparedStatement ps = con.prepareStatement(user)){
			ps.setString(1, hospAdmin.getUserName());
			ps.setString(2, hospAdmin.getPassword());
			ps.setString(3, hospAdmin.getFullName());
			ps.setString(4, hospAdmin.getContact());

			int updateCount = ps.executeUpdate();
			if(updateCount == 1){
				try(ResultSet rs = st.executeQuery("SELECT LAST_INSERT_ID()")){
					if(rs.next()){
						int userID = rs.getInt(1);
						try(PreparedStatement ps1 = con.prepareStatement(hosp_admin)){
							ps1.setInt(1,userID);
							ps1.setInt(2,hospAdmin.getHospital().getHospitalId());

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