package casestudy.dataaccess;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import casestudy.business.domain.Board;
import casestudy.business.service.BoardDao;

public class BoardDaoImpl implements BoardDao {

	private DataSource dataSource;

	public BoardDaoImpl() {
		try {
			Context context = new InitialContext();
			dataSource = (DataSource) context
					.lookup("java:comp/env/jdbc/dukeshopDB");
		} catch (NamingException ne) {
			System.out.println("JNDI error occured.");
			ne.printStackTrace(System.err);
			throw new RuntimeException("JNDI error occured." + ne.getMessage());
		}
	}

	private Connection obtainConnection() throws SQLException {
		return dataSource.getConnection();
	}

	@Override
	public List<Board> selectBoardList(Map<String, Object> searchInfo) {
		System.out.println(searchInfo);
		// searchInfo Map으로부터 검색 조건을 구한다.
		String searchType = (String) searchInfo.get("searchType");
		String searchText = (String) searchInfo.get("searchText");

		// searchInfo Map으로부터 현재 페이지에 보여질 게시글의 행 번호(startRow, endRow) 값을 구한다.
		int startRow = (Integer) searchInfo.get("startRow");
		int endRow = (Integer) searchInfo.get("endRow");

		// searchType 값에 따라 사용될 조건절(WHERE)을 생성한다.
		String whereSQL = "";

		if ((searchType == null) || (searchType.length() == 0)) {
			whereSQL = "";
		} else if (searchType.equals("all")) {
			whereSQL = "WHERE title LIKE ? OR writer LIKE ? OR contents LIKE ?";
		} else if (searchType.equals("title") || searchType.equals("writer")
				|| searchType.equals("contents")) {
			whereSQL = "WHERE " + searchType + " LIKE ?";
		}

		// LIKE 절에 포함될 수 있도록 searchText 값 앞뒤에 % 기호를 붙인다.
		if (searchText != null) {
			searchText = "%" + searchText.trim() + "%";
		}

		String query = "select * from (select rownum r, num, writer, title, read_count, reg_date from (select num, writer, title, read_count, reg_date from board "+ whereSQL + " order by num DESC))where r between ? and ?";
		/*
		 * String query =
		 * "SELECT num, writer, title, read_count, reg_date FROM board " +
		 * whereSQL + " ORDER BY num DESC";
		 */

		Connection connection = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		List<Board> boardList = new ArrayList<Board>();
		Board board = null;

		try {
			connection = obtainConnection();
			stmt = connection.prepareStatement(query);

			// searchType 값에 따라 PreparedStatement의 파라미터(startRow, endRow 값 포함) 값을 설정한다. 
			if ((searchType == null) || (searchType.length() == 0)) {
				stmt.setInt(1, startRow);
				stmt.setInt(2, endRow);
			} else if (searchType.equals("all")) {
				stmt.setString(1, searchText);
				stmt.setString(2, searchText);
				stmt.setString(3, searchText);
				stmt.setInt(4, startRow);
				stmt.setInt(5, endRow);
			} else if (searchType.equals("title")|| searchType.equals("writer")|| searchType.equals("contents")) {
				stmt.setString(1, searchText);
				stmt.setInt(2, startRow);
				stmt.setInt(3, endRow);
			}

			rs = stmt.executeQuery();

			while (rs.next()) {
				String title = rs.getString("title");
				if(title.length()>28){
//					title = title.substring(0, 28) + "...";
					title = new StringBuilder(title.substring(0, 28)).append("...").toString();
				}
				board = new Board(rs.getInt("num"), 
						rs.getString("writer"),
						title, 
						rs.getInt("read_count"),
						rs.getString("reg_date"));
				boardList.add(board);
			}
		} catch (SQLException se) {
			System.err.println("BoardDAOImpl selectBoardList() Error :"
					+ se.getMessage());
			se.printStackTrace(System.err);
		} finally {
			try {
				if (rs != null)
					rs.close();
			} catch (SQLException ex) {
				ex.printStackTrace(System.err);
			}
			try {
				if (stmt != null)
					stmt.close();
			} catch (SQLException ex) {
				ex.printStackTrace(System.err);
			}
			try {
				if (connection != null)
					connection.close();
			} catch (SQLException ex) {
				ex.printStackTrace(System.err);
			}
		}
		return boardList;

	}

	@Override
	public int selectBoardCount(Map<String, Object> searchInfo) {
		// searchInfo으로부터 검색 조건을 구한다.
		String searchType = (String) searchInfo.get("searchType");
		String searchText = (String) searchInfo.get("searchText");

		// searchType 값에 따라 사용될 조건절(WHERE)을 생성한다.
		String whereSQL = "";
		if ((searchType == null) || (searchType.length() == 0)) {
			whereSQL = "";
		} else if (searchType.equals("all")) {
			whereSQL = "WHERE title LIKE ? OR writer LIKE ? OR contents LIKE ?";
		} else if (searchType.equals("title") || searchType.equals("writer")
				|| searchType.equals("contents")) {
			whereSQL = "WHERE " + searchType + " LIKE ?";
		}

		// LIKE 절에 포함될 수 있도록 searchText 값 앞뒤에 % 기호를 붙인다.
		if (searchText != null) {
			searchText = "%" + searchText.trim() + "%";
		}

		// SELECT 문에 생성된 WHERE 절을 붙인다.
		String query = "SELECT count(num) FROM board " + whereSQL;

		Connection connection = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		int count = 0;

		try {
			connection = obtainConnection();
			stmt = connection.prepareStatement(query);

			// searchType 값에 따라 PreparedStatement의 파라미터 값을 설정한다.
			if ((searchType == null) || (searchType.length() == 0)) {

			} else if (searchType.equals("all")) {
				stmt.setString(1, searchText);
				stmt.setString(2, searchText);
				stmt.setString(3, searchText);
			} else if (searchType.equals("title")
					|| searchType.equals("writer")
					|| searchType.equals("contents")) {
				stmt.setString(1, searchText);
			}

			rs = stmt.executeQuery();

			if (rs.next()) {
				count = rs.getInt(1);
			}

		} catch (SQLException se) {
			System.err.println("BoardDAOImpl selectBoardCount() Error :"
					+ se.getMessage());
			se.printStackTrace(System.err);
			// throw new RuntimeException("A database error occured. " +
			// se.getMessage());

		} finally {
			try {
				if (rs != null)
					rs.close();
			} catch (SQLException ex) {
				ex.printStackTrace(System.err);
			}
			try {
				if (stmt != null)
					stmt.close();
			} catch (SQLException ex) {
				ex.printStackTrace(System.err);
			}
			try {
				if (connection != null)
					connection.close();
			} catch (SQLException e) {
				e.printStackTrace(System.err);
			}
		}

		return count;
	}

	@Override
	public Board selectBoard(int num) {
		Board board = null;

		String query = "SELECT num,writer,title,contents,ip,read_count,reg_date,mod_date FROM board WHERE num=?";

		Connection connection = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			connection = obtainConnection();
			stmt = connection.prepareStatement(query);
			stmt.setInt(1, num);
			rs = stmt.executeQuery();

			if (rs.next()) {
				board = new Board(rs.getInt("num"), rs.getString("writer"),
						rs.getString("title"), rs.getString("contents"),
						rs.getString("ip"), rs.getInt("read_count"),
						rs.getString("reg_date"), rs.getString("mod_date"));
			}
		} catch (SQLException se) {
			System.err.println("BoardDAOImpl selectBoard() Error :"
					+ se.getMessage());
			se.printStackTrace(System.err);
			// throw new RuntimeException("A database error occured. " +
			// se.getMessage());

		} finally {
			try {
				if (rs != null)
					rs.close();
			} catch (SQLException ex) {
				ex.printStackTrace(System.err);
			}
			try {
				if (stmt != null)
					stmt.close();
			} catch (SQLException ex) {
				ex.printStackTrace(System.err);
			}
			try {
				if (connection != null)
					connection.close();
			} catch (SQLException e) {
				e.printStackTrace(System.err);
			}
		}

		return board;
	}

	@Override
	public void addReadCount(int num) {

		String query = "UPDATE board SET read_count=read_count+1 WHERE num=?";

		System.out.println("BoardDAOImpl addReadCount() query: " + query);

		Connection connection = null;
		PreparedStatement stmt = null;

		try {
			connection = obtainConnection();
			stmt = connection.prepareStatement(query);

			stmt.setInt(1, num);

			stmt.executeUpdate();

		} catch (SQLException se) {
			System.err.println("BoardDAOImpl addReadCount() Error :"
					+ se.getMessage());
			se.printStackTrace(System.err);
			throw new RuntimeException("A database error occurred. "
					+ se.getMessage());

		} finally {
			try {
				if (stmt != null)
					stmt.close();
			} catch (SQLException se) {
				se.printStackTrace(System.err);
			}
			try {
				if (connection != null)
					connection.close();
			} catch (SQLException se) {
				se.printStackTrace(System.err);
			}
		}
	}

	@Override
	public boolean boardNumExists(int num) {
		boolean result = false;

		String query = "SELECT num FROM board WHERE num=?";
		System.out.println("BoardDAOImpl boardNumExists() query: " + query);

		Connection connection = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			connection = obtainConnection();
			stmt = connection.prepareStatement(query);
			stmt.setInt(1, num);
			rs = stmt.executeQuery();
			result = rs.next();

		} catch (SQLException se) {
			System.err.println("BoardDAOImpl boardNumExists() Error :"
					+ se.getMessage());
			se.printStackTrace(System.err);
			throw new RuntimeException("A database error occurred. "
					+ se.getMessage());

		} finally {
			try {
				if (rs != null)
					rs.close();
			} catch (SQLException ex) {
				ex.printStackTrace(System.err);
			}
			try {
				if (stmt != null)
					stmt.close();
			} catch (SQLException ex) {
				ex.printStackTrace(System.err);
			}
			try {
				if (connection != null)
					connection.close();
			} catch (SQLException ex) {
				ex.printStackTrace(System.err);
			}
		}

		return result;
	}

	@Override
	public void insertBoard(Board board) {
		String query = "INSERT INTO board(num, writer, title, contents, ip, read_count, reg_date, mod_date) VALUES (board_num_seq.NEXTVAL, ?, ?, ?, ?, 0, SYSDATE, SYSDATE)";

		System.out.println("DAOImpl insertBoard() query: " + query);

		Connection connection = null;
		PreparedStatement stmt = null;

		try {
			connection = obtainConnection();
			stmt = connection.prepareStatement(query);
			stmt.setString(1, board.getWriter());
			stmt.setString(2, board.getTitle());
			stmt.setString(3, board.getContents());
			stmt.setString(4, board.getIp());

			stmt.executeUpdate();

		} catch (SQLException se) {
			System.err.println("BoardDAOImpl insertBoard() Error :"
					+ se.getMessage());
			se.printStackTrace(System.err);
			throw new RuntimeException("A database error occurred. "
					+ se.getMessage());

		} finally {
			try {
				if (stmt != null)
					stmt.close();
			} catch (SQLException se) {
				se.printStackTrace(System.err);
			}
			try {
				if (connection != null)
					connection.close();
			} catch (SQLException se) {
				se.printStackTrace(System.err);
			}
		}
	}

	@Override
	public void updateBoard(Board board) {
		String query = "UPDATE board SET writer=?, title=?, contents=?, ip=?, mod_date=SYSDATE WHERE num=?";

		System.out.println("BoardDAOImpl updateBoard() query: " + query);

		Connection connection = null;
		PreparedStatement stmt = null;

		try {
			connection = obtainConnection();
			stmt = connection.prepareStatement(query);
			stmt.setString(1, board.getWriter());
			stmt.setString(2, board.getTitle());
			stmt.setString(3, board.getContents());
			stmt.setString(4, board.getIp());
			stmt.setInt(5, board.getNum());

			stmt.executeUpdate();

		} catch (SQLException se) {
			System.err.println("BoardDAOImpl updateBoard() Error :"
					+ se.getMessage());
			se.printStackTrace(System.err);
			throw new RuntimeException("A database error occurred. "
					+ se.getMessage());

		} finally {
			try {
				if (stmt != null)
					stmt.close();
			} catch (SQLException se) {
				se.printStackTrace(System.err);
			}
			try {
				if (connection != null)
					connection.close();
			} catch (SQLException se) {
				se.printStackTrace(System.err);
			}
		}
	}

	@Override
	public void deleteBoard(int num) {
		String query = "DELETE FROM board WHERE num = ?";
		System.out.println("BoardDAOImpl deleteBoard() query: " + query);

		Connection connection = null;
		PreparedStatement stmt = null;

		try {
			connection = obtainConnection();
			stmt = connection.prepareStatement(query);
			stmt.setInt(1, num);

			stmt.executeUpdate();

		} catch (SQLException se) {
			System.err.println("MemberDAOImpl deleteMember() Error :"
					+ se.getMessage());
			se.printStackTrace(System.err);
			throw new RuntimeException("A database error occurred. "
					+ se.getMessage());

		} finally {
			try {
				if (stmt != null)
					stmt.close();
			} catch (SQLException se) {
				se.printStackTrace(System.err);
			}
			try {
				if (connection != null)
					connection.close();
			} catch (SQLException se) {
				se.printStackTrace(System.err);
			}
		}
	}

}
