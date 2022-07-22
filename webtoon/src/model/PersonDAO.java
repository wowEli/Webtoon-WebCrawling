package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PersonDAO {
	Connection conn;
	PreparedStatement pstmt;

	final String sql_login ="SELECT * FROM PERSON WHERE ID = ? AND PASSWORD = ?";
	final String sql_signup ="INSERT INTO PERSON(ID, PASSWORD, NAME) VALUES(?,?,?)";
	final String sql_delete = "DELETE FROM PERSON WHERE ID=? AND PASSWORD=?";
	final String sql_signupCheck ="SELECT * FROM PERSON WHERE ID = ?";


	public PersonVO login(PersonVO vo) {// 로그인
		conn = JDBCUtil.connect();

		try {
			pstmt = conn.prepareStatement(sql_login);
			pstmt.setString(1, vo.getId());
			pstmt.setString(2, vo.getPassword());
			ResultSet rs = pstmt.executeQuery();
			PersonVO vo1 = new PersonVO();
			if(rs.next()) {
				vo1.setId(rs.getString("ID"));
				vo1.setPassword(rs.getString("PASSWORD"));
				vo1.setName(rs.getString("Name"));
				return vo1;
			}			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			JDBCUtil.disconnect(pstmt, conn);
		}
		return null;

	}

	public boolean signUp(PersonVO vo) { // 회원가입
		conn = JDBCUtil.connect();
		try {
			pstmt=conn.prepareStatement(sql_signup);
			pstmt.setString(1, vo.getId());
			pstmt.setString(2, vo.getPassword());
			pstmt.setString(3, vo.getName());
			int a = pstmt.executeUpdate();
			if(a<=0) {
//				System.out.println("회원가입 실패");
				return false;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			JDBCUtil.disconnect(pstmt, conn);
		}
		return true;

	}

	public boolean delete(PersonVO vo) { // 회원 탈퇴
		conn=JDBCUtil.connect();
		try {
			pstmt=conn.prepareStatement(sql_delete);
			pstmt.setString(1, vo.getId());
			pstmt.setString(2, vo.getPassword());
			int a = pstmt.executeUpdate();
			if(a<=0) {
//				System.out.println("로그 :회원탈퇴 실패");
				return false;
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			JDBCUtil.disconnect(pstmt, conn);
		}
//		System.out.println("로그 : 회원가입 성공");
		return true;

	}
	public boolean signUpCheck(PersonVO vo) {
		conn = JDBCUtil.connect();
		try {
			pstmt= conn.prepareStatement(sql_signupCheck);
			pstmt.setString(1, vo.getId());
			ResultSet rs =pstmt.executeQuery();
			if(!(rs.next())) {
//				System.out.println("로그 : 중복된 아이디 없음");
				return true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCUtil.disconnect(pstmt, conn);
		}
//		System.out.println("로그 : 중복된 아이디 입니다");
		return false;
	}
}