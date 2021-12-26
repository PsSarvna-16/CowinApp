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

	public int[] validatePassword(String userName, String password) throws SQLException{

		String query = "SELECT u.username,u.password,u.user_id,u.user_type_id  FROM user u INNER JOIN user_type ut USING (user_type_id)";

		try(PreparedStatement ps = con.prepareStatement(query)){
	
			try(ResultSet rs = ps.executeQuery()){

				while(rs.next()){

					if(userName.equals(rs.getString(1))){
						if(password.equals(rs.getString(2))){
							System.out.println("Welcome " + userName);
							return new int[] {rs.getInt(3),rs.getInt(4)};
						}else{
							System.out.println("Invalid password !!!");
							return null;
						}
					}
				}
			}
		}

		System.out.println("Invalid Username !!!");
		return null;
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
						newCust.setUserId(insertID);
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
							ps1.setInt(2,hospAdmin.getHospitalId());

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

	public boolean addVaccineSlot(VaccineSlot vaccSlot) throws SQLException{


		String vacc = "INSERT INTO vacc_slot VALUES (DEFAULT,?,?,?,?,?)";
		
		try(PreparedStatement ps = con.prepareStatement(vacc)){
			ps.setInt(1, vaccSlot.getHospitalId());
			ps.setString(2, vaccSlot.getDateOfDose());
			ps.setString(3, vaccSlot.getSlot());
			ps.setInt(4, vaccSlot.getVaccId());
			ps.setInt(5, vaccSlot.getAvailableDose());

			int updateCount = ps.executeUpdate();
			if(updateCount == 1){
				try(ResultSet rs = st.executeQuery("SELECT LAST_INSERT_ID()")){
					if(rs.next()){
						int vaccSlotId = rs.getInt(1);
						vaccSlot.setVaccSlotId(vaccSlotId);
						con.commit();
						return true;
					}
				}
			}
		}
		con.rollback();
		return false;
	}

	public boolean addAppointment(Appointment app) throws SQLException{

		String appoint = "INSERT INTO appointment VALUES (DEFAULT,?,?,?,?,?)";
		try(ResultSet rs1 = st.executeQuery("SELECT available FROM vacc_slot WHERE vacc_slot_id = " +app.getVaccSlotId() )){
			if(rs1.next()){
				int available = rs1.getInt(1);
				if(available < 1){
					con.rollback();
					return false;
				}
			}

			try(PreparedStatement ps = con.prepareStatement(appoint)){
				ps.setInt(1, app.getVaccSlotId());
				ps.setInt(2, app.getMemberId());
				ps.setInt(3, app.getHospitalId());
				ps.setString(4, app.getStatus());
				
				if(app.getVaccinatedBy() == 0){
					ps.setNull(5, Types.INTEGER);	
				}else{
					ps.setInt(5, app.getVaccinatedBy());
				}

				int updateCount = ps.executeUpdate();
				if(updateCount == 1){
					try(ResultSet rs2 = st.executeQuery("SELECT LAST_INSERT_ID()")){
						if(rs2.next()){
							int appID = rs2.getInt(1);
							app.setAppointmentId(appID);
							updateCount = st.executeUpdate("UPDATE vacc_slot SET available = available-1 WHERE vacc_slot_id="+app.getVaccSlotId());
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

	public Customer getCustomer(int userId) throws SQLException{

		Customer newCust = null;
		String cust = "SELECT username, password, name, contact, doses_taken, members_count FROM user INNER JOIN customer USING (user_id) WHERE user_id=?;";
		try(PreparedStatement ps = con.prepareStatement(cust)){
			ps.setInt(1, userId);

			try(ResultSet rs = ps.executeQuery()){
				if(rs.next()){

					String userName = rs.getString(1);
					String password = rs.getString(2);
					String fullName = rs.getString(3);
					String contact = rs.getString(4);
					int doses_taken = rs.getInt(5);
					int membersCount = rs.getInt(6);

					newCust = new Customer(userName,fullName,password,contact);
					newCust.setUserId(userId);
					newCust.setDosesTaken(doses_taken);
					newCust.setMembersCount(membersCount);
				}
			}
		}
		return newCust;
	}

	public Admin getCowinAdmin(int userId) throws SQLException{

		Admin newAdmin = null;
		String cust = "SELECT username, password, name, contact FROM user INNER JOIN cowin_admin USING (user_id) WHERE user_id=?";
		try(PreparedStatement ps = con.prepareStatement(cust)){
			ps.setInt(1, userId);

			try(ResultSet rs = ps.executeQuery()){
				if(rs.next()){

					String userName = rs.getString(1);
					String password = rs.getString(2);
					String fullName = rs.getString(3);
					String contact = rs.getString(4);

					newAdmin = new Admin(userName,fullName,password,contact);
					newAdmin.setUserId(userId);
				}
			}
		}
		return newAdmin;
	}

	public HospitalAdmin getHospitalAdmin(int userId) throws SQLException{

		HospitalAdmin newAdmin = null;
		String cust = "SELECT username, password, name, contact,hosp_id FROM user INNER JOIN hosp_admin USING (user_id) WHERE user_id=?";
		try(PreparedStatement ps = con.prepareStatement(cust)){
			ps.setInt(1, userId);

			try(ResultSet rs = ps.executeQuery()){
				if(rs.next()){

					String userName = rs.getString(1);
					String password = rs.getString(2);
					String fullName = rs.getString(3);
					String contact = rs.getString(4);
					int hospId = rs.getInt(5);

					newAdmin = new HospitalAdmin(userName,fullName,password,contact,hospId);
					newAdmin.setUserId(userId);
				}
			}
		}
		return newAdmin;
	}

	public void printMembers(int user_id) throws SQLException{

		String memb = "SELECT member_id,name,aadhar,dob,age,gender FROM member WHERE user_id = ?";
		try(PreparedStatement ps = con.prepareStatement(memb)){
			ps.setInt(1, user_id);
			
			try(ResultSet rs =  ps.executeQuery()){
				printResultSet(rs);
			}
		}
	}

	public void printCustAppointments(int user_id) throws SQLException{

		String appoint = "SELECT m.name,v.date,v.slot,h.name AS 'hosp_name',a.status FROM appointment a INNER JOIN member m USING (member_id) INNER JOIN user u USING (user_id) INNER JOIN hospital h USING (hosp_id) INNER JOIN vacc_slot v USING (vacc_slot_id) WHERE u.user_id = ?";
		try(PreparedStatement ps = con.prepareStatement(appoint)){
			ps.setInt(1, user_id);
			
			try(ResultSet rs =  ps.executeQuery()){
				printResultSet(rs);
			}
		}
	}

	public void printVaccAppointments(int vaccineSlotId) throws SQLException{

		String appoint = "SELECT m.name,v.date,v.slot,h.name AS 'hosp_name',a.status FROM appointment a INNER JOIN member m USING (member_id) INNER JOIN user u USING (user_id) INNER JOIN hospital h USING (hosp_id) INNER JOIN vacc_slot v USING (vacc_slot_id) WHERE v.vacc_slot_id = ?";
		try(PreparedStatement ps = con.prepareStatement(appoint)){
			ps.setInt(1, vaccineSlotId);
			
			try(ResultSet rs =  ps.executeQuery()){
				printResultSet(rs);
			}
		}
	}

	public void printHospitals(String pincode) throws SQLException{

		String hosp = "SELECT * FROM hospital WHERE pincode = ?";
		try(PreparedStatement ps = con.prepareStatement(hosp)){
			ps.setString(1, pincode);
			
			try(ResultSet rs =  ps.executeQuery()){
				printResultSet(rs);
			}
		}
	}

	public void printHospitals() throws SQLException{

		String hosp = "SELECT * FROM hospital";
		try(PreparedStatement ps = con.prepareStatement(hosp)){
			try(ResultSet rs =  ps.executeQuery()){
				printResultSet(rs);
			}
		}
	}

	public void printVaccines() throws SQLException{

		String vacc_slot = "SELECT vacc_id,name FROM vaccine";

		try(PreparedStatement ps = con.prepareStatement(vacc_slot)){
			try(ResultSet rs =  ps.executeQuery()){
				printResultSet(rs);
			}
		}
	}

	public void printVaccineSlots(int hospId) throws SQLException{

		String vacc_slot = "SELECT vs.vacc_slot_id,date,vs.slot,v.name,vs.available FROM vacc_slot vs INNER JOIN vaccine v USING (vacc_id) WHERE hosp_id = ?";

		try(PreparedStatement ps = con.prepareStatement(vacc_slot)){
			ps.setInt(1, hospId);
			
			try(ResultSet rs =  ps.executeQuery()){
				printResultSet(rs);
			}
		}
	}

	public void printResultSet(ResultSet rs) throws SQLException{

			// Get ResultSetMetaData
			ResultSetMetaData rsmd = rs.getMetaData();
			int columnCount = rsmd.getColumnCount();

			for(int i= 1 ; i <= columnCount; i++){
				System.out.print( leftJustify(rsmd.getColumnName(i), rsmd.getColumnDisplaySize(i)) );
			}
			System.out.println();

			while(rs.next()){

				for(int i= 1 ; i <= columnCount; i++){
					System.out.print( leftJustify(rs.getObject(i).toString(), rsmd.getColumnDisplaySize(i)) );
				}
				System.out.println();
			}
	}

	public String leftJustify(String s, int n){
		if(s.length() <= n) n++;

		return String.format("%1$-" + n + "s", s);
	}	

	public void closeConnection() throws SQLException{

		con.close();
	}

}