package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class RecentViewDAO {
	Connection conn;
	PreparedStatement pstmt;


	final String sql_Insert = "INSERT INTO RECENTVIEW(PPK, WPK) VALUES(?,?)";
	final String sql_Join = "SELECT WPK,TITLE FROM RECENTVIEW, WEBTOON WHERE RECENTVIEW.PPK = ? AND RECENTVIEW.WPK = WEBTOON.WID ORDER BY RECENTVIEW.RPK DESC";
	final String sql_Cnt = "SELECT COUNT(*) AS CNT FROM RECENTVIEW WHERE PPK = ?";
	final String sql_Delete = "DELETE FROM RECENTVIEW WHERE RPK =(SELECT A.B FROM(SELECT MIN(RPK) AS B FROM RECENTVIEW  WHERE PPK = ?) A) ";
	final String sql_Search = "SELECT * FROM RECENTVIEW WHERE PPK=? AND WPK=?";
	final String sql_SearchDelete = "DELETE FROM RECENTVIEW WHERE PPK=? AND WPK=? ";
	final String sql_WebDelete = "DELETE FROM RECENTVIEW WHERE WPK = ?";
	public boolean searchDelete(RecentViewVO vo) {// 최근본웹툰 테이블에서 vo.getId 와 PID를 비교해
		// 동일 할 경우 가장 마지막에 본 웹툰행을 삭제하는 메소드
		conn=JDBCUtil.connect();
		try {
			pstmt = conn.prepareStatement(sql_SearchDelete); // SQL문 DELETE 사용
			pstmt.setString(1, vo.getPpk()); // 인풋값으로 받은 이름값을 SQL문에 넣음
			pstmt.setInt(2, vo.getWpk()); // 인풋값으로 받은 이름값을 SQL문에 넣음
			int a = pstmt.executeUpdate(); // SQL문 실행

			if(a != 0) { // executeUpdate() 결과값이 0 이라면 값이 없는 것
//				System.out.println("로그 : 중복된 웹툰 삭제완료");
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
//			System.out.println("로그 : 에러발생 (recentViewDelete 오류");
			return false;
		}finally {
			JDBCUtil.disconnect(pstmt, conn);
		}
//		System.out.println("로그 : 중복된 웹툰 삭제실패");
		return false;

	}
	public boolean recentWebDelete(RecentViewVO vo) {// 최근본웹툰 테이블에서 vo.getId 와 PID를 비교해
		// 동일 할 경우 가장 마지막에 본 웹툰행을 삭제하는 메소드
		conn=JDBCUtil.connect();
		try {
			pstmt = conn.prepareStatement(sql_WebDelete); // SQL문 DELETE 사용
			pstmt.setInt(1, vo.getWpk()); // 인풋값으로 받은 이름값을 SQL문에 넣음
			int a = pstmt.executeUpdate(); // SQL문 실행

			if(a != 0) { // executeUpdate() 결과값이 0 이라면 값이 없는 것
//				System.out.println("로그 : 최근본 웹툰에 삭제된 웹툰 삭제완료");
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
//			System.out.println("로그 : 에러발생 (recentViewDelete 오류");
			return false;
		}finally {
			JDBCUtil.disconnect(pstmt, conn);
		}
//		System.out.println("로그 : 최근본 웹툰에 삭제된 웹툰 삭제실패");
		return false;

	}
	
	

	public boolean search(RecentViewVO vo) {
		conn=JDBCUtil.connect();
		try {
			pstmt = conn.prepareStatement(sql_Search); // ppk와 wpk가 같은 데이터가 있는지?
			pstmt.setString(1, vo.getPpk()); // 인풋값으로 받은 ppk값과 wpk값을 SQL문에 넣음
			pstmt.setInt(2, vo.getWpk()); // 인풋값으로 받은 ppk값과 wpk값을 SQL문에 넣음
			
			ResultSet rs= pstmt.executeQuery();

			if(rs.next()) { 
//				System.out.println("로그 : 중복된 웹툰이 있음");
				return true;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
//			System.out.println("로그 : 에러발생 (recentViewDelete 오류");
			return false;
		}finally {
			JDBCUtil.disconnect(pstmt, conn);
		}
//		System.out.println("로그 : 중복된 웹툰이 없음");
		return false;
	}

	public boolean recentViewDelete(RecentViewVO vo) {// 최근본웹툰 테이블에서 vo.getId 와 PID를 비교해
		// 동일 할 경우 가장 마지막에 본 웹툰행을 삭제하는 메소드
		conn=JDBCUtil.connect();
		try {
			pstmt = conn.prepareStatement(sql_Delete); // SQL문 DELETE 사용
			pstmt.setString(1, vo.getPpk()); // 인풋값으로 받은 이름값을 SQL문에 넣음
			int a = pstmt.executeUpdate(); // SQL문 실행

			if(a != 0) { // executeUpdate() 결과값이 0 이라면 값이 없는 것
//				System.out.println("로그 : 마지막으로 본 웹툰 삭제성공");
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
//			System.out.println("로그 : 에러발생 (recentViewDelete 오류");
			return false;
		}finally {
			JDBCUtil.disconnect(pstmt, conn);
		}
//		System.out.println("로그 : 마지막으로 본 웹툰 삭제실패");
		return false;

	}

	
	
	public boolean recentViewCnt(RecentViewVO vo) {// 해당 사용자의 최근본웹툰의 갯수를 Count(*)하여
		// 10개라면 true를 출력해주는 메소드
		conn=JDBCUtil.connect();
		try {
			pstmt = conn.prepareStatement(sql_Cnt);
			pstmt.setString(1, vo.getPpk()); 
			ResultSet rs = pstmt.executeQuery();
			rs.next();					//getint를 사용하기위해 사용
			if(rs.getInt("CNT") == 10) {// 10개부터 true 리턴을 하여 Delete 사용할 예정
				// 최근본 웹툰을 최대 10개까지 유지 하기 위해서!
//				System.out.println("로그 : 저장된 최근본웹툰이 10개임!");
				return true;
			}
//			System.out.println("로그 : 저장된 최근본웹툰이 10개가 아님!!");
			return false;


		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
//			System.out.println("로그 : 에러발생 (recentViewCnt 오류)");
			return false;
		}finally {
			JDBCUtil.disconnect(pstmt, conn);
		}
		//final String sql_Delete = "DELETE FROM RECENTVIEW WHERE PPK = ? GROUP BY ? HAVING MIN(RPK)";
	}

	
	
	public ArrayList<RecentViewVO> joinTitle(RecentViewVO vo) { // 사용자의 최근본웹툰을 이퀄조인으로 확인하여 Title를 출력하고
		// 자바의 RecentViewVO의 Title에 저장하여
		// ArrayList에 담아 아웃풋으로 보내주는 메소드
		conn=JDBCUtil.connect();
		ArrayList<RecentViewVO> datas = new ArrayList<RecentViewVO>(); // ArrayList 객체화
		
		try {
			pstmt = conn.prepareStatement(sql_Join);
			pstmt.setString(1, vo.getPpk());
			ResultSet rs = pstmt.executeQuery(); // 이퀄조인을 사용하여, 출력된 Title들을 저장한 Set배열 rs
			while(rs.next()) {
				RecentViewVO rvo=new RecentViewVO();
				rvo.setWpk(rs.getInt("WPK")); // 웹툰 pk도 추가
				rvo.setTitle(rs.getString("TITLE")); // 자바에만 존재하는 Title 멤버변수에 저장함
				datas.add(rvo);					// Title을 저장한 vo를 ArrayList에 담는 것
			}
			if(datas.size() != 0) { // ArrayList datas에 저장된게 있다면! ( 0개가 아니라면 )
//				System.out.println("로그 : 이퀄조인 타이틀저장 성공");
				return datas;
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
//			System.out.println("로그 : 에러발생 (joinTitle 오류)");
			return null;
		}finally {
			JDBCUtil.disconnect(pstmt, conn);
		}
//		System.out.println("로그 : 이퀄조인 타이틀저장된게 없음");
		return null;

	}

	public boolean recentViewInsert(RecentViewVO vo) { // 사용자의 ID (PK)와 웹툰의 PK를 받아서
		// RecentView 테이블에 저장하는 메소드

		conn=JDBCUtil.connect();

		try {
			pstmt = conn.prepareStatement(sql_Insert);
			pstmt.setString(1, vo.getPpk());
			pstmt.setInt(2, vo.getWpk());
			int a = pstmt.executeUpdate();
			if(a == 0) {
				return false;
			}
			return true;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}finally {
			JDBCUtil.disconnect(pstmt, conn);
		}

	}


}
