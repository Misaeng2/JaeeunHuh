package gooroomeeboards;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class BbsDAO {

	private Connection conn;
	private ResultSet rs;

	// �⺻ ������
	public BbsDAO() {
		try {
			String dbURL = "jdbc:oracle:thin:@localhost:1521:xe";
			String dbID = "C##dbexam";
			String dbPassword = "m1234";
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection(dbURL, dbID, dbPassword);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// �ۼ����� �޼ҵ�
	public String getDate() {
		String sql = "insert sysdate";
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				return rs.getString(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ""; // �����ͺ��̽� ����
	}

	// �Խñ� ��ȣ �ο� �޼ҵ�
	public int getNext() {
		// ���� �Խñ��� ������������ ��ȸ�Ͽ� ���� ������ ���� ��ȣ�� ���Ѵ�
		String sql = "select bbsID from gooroomeeboards order by bbsID desc";
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				return rs.getInt(1) + 1;
			}
			return 1; // ù ��° �Խù��� ���
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1; // �����ͺ��̽� ����
	}

	// �۾��� �޼ҵ�
	public int write(String bbsTitle, String userID, String bbsContent) {
		String sql = "insert into gooroomeeboards values(?, ?, ?, sysdate, ?, ?)";
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, getNext());
			pstmt.setString(2, bbsTitle);
			pstmt.setString(3, userID);
//			pstmt.setString(4, "sysdate");
			pstmt.setString(4, bbsContent);
			pstmt.setInt(5, 1); // ���� ��ȿ��ȣ
			return pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1; // �����ͺ��̽� ����
	}

	//�Խñ� ����Ʈ �޼ҵ�
		public ArrayList<Gooroomeeboards> getList(int pageNumber){
			String sql = "select * from gooroomeeboards where bbsAvailable = 1 order by bbsID desc;";
			ArrayList<Gooroomeeboards> list = new ArrayList<Gooroomeeboards>();
			try {
				PreparedStatement pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, getNext() - (pageNumber - 1) * 10);
				rs = pstmt.executeQuery();
				while(rs.next()) {
					Gooroomeeboards bbs = new Gooroomeeboards();
					bbs.setBbsID(rs.getInt(1));
					bbs.setBbsTitle(rs.getString(2));
					bbs.setUserID(rs.getString("userID"));
					bbs.setBbsDate(rs.getString("bbsDate"));
					bbs.setBbsContent(rs.getString("bbsContent"));
					bbs.setBbsAvailable(rs.getInt("bbsAvailable"));
					list.add(bbs);
					
				}
			}catch (Exception e) {
				e.printStackTrace();
			}
			return list;
		}

	// ����¡ ó�� �޼ҵ�
	public boolean nextPage(int pageNumber) {
		String sql = "select * from gooroomeeboards where bbsAvailable = 1";
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, getNext() - (pageNumber - 1) * 10);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	//�ϳ��� �Խñ��� ���� �޼ҵ�
		public Gooroomeeboards getBbs(int bbsID) {
			String sql = "select * from bbs where bbsID = ?";
			try {
				PreparedStatement pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, bbsID);
				rs = pstmt.executeQuery();
				if(rs.next()) {
					Gooroomeeboards bbs = new Gooroomeeboards();
					bbs.setBbsID(rs.getInt(1));
					bbs.setBbsTitle(rs.getString(2));
					bbs.setUserID(rs.getString(3));
					bbs.setBbsDate(rs.getString(4));
					bbs.setBbsContent(rs.getString(5));
					bbs.setBbsAvailable(rs.getInt(6));
					return bbs;
				}
			}catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}
		
		public int update(int bbsID, String bbsTitle, String bbsContent) {
			String SQL = "UPDATE gooroomeeboards SET bbsTitle = ?, bbsContent = ? WHERE bbsID =?";
			try {
				PreparedStatement pstmt = conn.prepareStatement(SQL);
				pstmt.setString(1, bbsTitle);
				pstmt.setString(2, bbsContent);
				pstmt.setInt(3, bbsID);
				
				return pstmt.executeUpdate(); 
			} catch(Exception e) {
				e.printStackTrace();
			}
			return -1; // �����ͺ��̽� ����
		}
		
		public int delete(int bbsID) {
			String SQL = "UPDATE gooroomeeboards"
					+ " SET bbsAvailable = 0 WHERE bbsID = ?";
			try {
				PreparedStatement pstmt = conn.prepareStatement(SQL);
				pstmt.setInt(1, bbsID);
				
				return pstmt.executeUpdate(); 
			} catch(Exception e) {
				e.printStackTrace();
			}
			return -1; // �����ͺ��̽� ����
		}
	

}