package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class WebtoonDAO {
	Connection conn;
	PreparedStatement pstmt;

	JDBCUtil jdbc = new JDBCUtil();

	final String sql_insert = "INSERT INTO WEBTOON(TITLE,IMG,AUTHOR) VALUES(?,?,?)";
	final String sql_delete = "DELETE FROM WEBTOON WHERE WID = ? ";
	final String sql_update = "UPDATE WEBTOON SET CNT = CNT+1 WHERE WID = ?";
	final String sql_selectOne = "SELECT * FROM WEBTOON WHERE WID = ?";
	final String sql_selectLike = "SELECT * FROM WEBTOON WHERE TITLE LIKE CONCAT('%',?,'%') ";
	final String sql_selectAll = "SELECT * FROM WEBTOON ";
	

	public boolean insert(WebtoonVO vo) {	//웹툰 추가
		conn = jdbc.connect();
		try {
			pstmt = conn.prepareStatement(sql_insert);
			pstmt.setString(1, vo.getTitle());
			pstmt.setString(2, vo.getImg());
			pstmt.setString(3, vo.getAuthor());
			int a = pstmt.executeUpdate(); // c u d는 update() 그리고 select가 query()
			if (a <= 0) {
//				System.out.println("로그 : 실패");
				return false;
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCUtil.disconnect(pstmt, conn);
		}
		return true;
	}

	public boolean delete(WebtoonVO vo) {	//웹툰 삭제
		conn = JDBCUtil.connect();
		try {
			pstmt = conn.prepareStatement(sql_delete);  //웹툰의 pk를 통해서 그 pk를 가지 웹툰을 delete
			pstmt.setInt(1, vo.getWid());
			int b = pstmt.executeUpdate();
			if (b <= 0) {
//				System.out.println("로그 : 삭제실패");
				return false;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCUtil.disconnect(pstmt, conn);
		}
		return true; // 성공
	}

	public boolean update(WebtoonVO vo) {	//조회수 +1
		conn = JDBCUtil.connect();
		try {
			pstmt = conn.prepareStatement(sql_update); //조회한 웹툰의 Wid를 통해 그 웹툰의 CNT(조회수)를 +1
			pstmt.setInt(1, vo.getWid());
			
			int c = pstmt.executeUpdate(); 
			if (c <= 0) {
//				System.out.println("로그 : 조회 수행 실패");
				return false;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCUtil.disconnect(pstmt, conn);
		}

		return true;

	}

	public WebtoonVO selectOne(WebtoonVO vo) {	//컨트롤에서 장바구니에 담겨진 웹툰들 중 하나를 고를 때 쓰는 메서드
		conn = JDBCUtil.connect();
		try {
			pstmt = conn.prepareStatement(sql_selectOne);// 웹툰 pk를 통해, 그 pk를 가진 웹툰의 모든 정보를 반환
			pstmt.setInt(1, vo.getWid());
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				WebtoonVO vo1 = new WebtoonVO();
				vo1.setAuthor(rs.getString("AUTHOR"));
				vo1.setCnt(rs.getInt("CNT"));
				vo1.setImg(rs.getString("IMG"));
				vo1.setTitle(rs.getString("TITLE"));
				vo1.setWid(rs.getInt("Wid"));
				return vo1;
			}
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public ArrayList<WebtoonVO> selectLike(WebtoonVO vo) {	//검색한 글자가 들어간 웹툰들을 모두 출력하기 위한 메서드
		conn = JDBCUtil.connect();
		ArrayList<WebtoonVO> datas = new ArrayList<WebtoonVO>();
		try {
			pstmt = conn.prepareStatement(sql_selectLike);	//'?'글자가 들어간 웹툰들을 배열로 반환
			pstmt.setString(1, vo.getTitle());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				WebtoonVO wvo = new WebtoonVO();
				wvo.setTitle(rs.getString("TITLE"));
				wvo.setAuthor(rs.getString("AUTHOR"));
				wvo.setCnt(rs.getInt("CNT"));
				wvo.setImg(rs.getString("IMG"));
				wvo.setWid(rs.getInt("Wid"));
				datas.add(wvo);
			}
			rs.close();
			return datas;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCUtil.disconnect(pstmt, conn);
		}
		return null;
	}
	
	public ArrayList<WebtoonVO> selectAll(WebtoonVO vo) {	//모든 웹툰의 정보를 배열로 반환
		conn = JDBCUtil.connect();
		ArrayList<WebtoonVO> datas = new ArrayList<WebtoonVO>();
		try {
			pstmt = conn.prepareStatement(sql_selectAll);
			
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				WebtoonVO wvo = new WebtoonVO();
				wvo.setTitle(rs.getString("TITLE"));
				wvo.setAuthor(rs.getString("AUTHOR"));
				wvo.setCnt(rs.getInt("CNT"));
				wvo.setImg(rs.getString("IMG"));
				wvo.setWid(rs.getInt("Wid"));
				datas.add(wvo);
			}
			rs.close();
			return datas;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCUtil.disconnect(pstmt, conn);
		}
		return null;
	}
	
	

}
