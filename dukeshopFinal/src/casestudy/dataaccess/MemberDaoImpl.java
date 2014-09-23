/**
 * 파일명 : MemberDaoImpl.java
 * 작성일 : 2014. 2. 12.
 * 파일설명 : 
 */
package casestudy.dataaccess;

import java.sql.*;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import casestudy.business.domain.Member;
import casestudy.business.service.MemberDao;

/**
 * 회원 관련 데이터 액세스 처리를 JDBC API를 활용하여 구현한 클래스로 
 * 데이터베이스 접속과 Member 테이블을 사용하는 SQL 문의 수행을 통해 관련 처리를 수행한다.
 * 
 * @author 고범석(kidmania@hotmail.com)
 *
 */
public class MemberDaoImpl implements MemberDao {
	private DataSource dataSource;
	
    /*
     * 1. JNDI API를 이용하여 네이밍 서비스에 바인딩(jdbc/dukeshopDB)된 DataSource를 검색한다.
     */
    public MemberDaoImpl() {
        try {
            Context context = new InitialContext();
            dataSource = (DataSource) context.lookup("java:comp/env/jdbc/dukeshopDB");
        } catch(NamingException ne) {
            System.err.println("A JNDI error occured.");
            ne.printStackTrace(System.err);
            throw new RuntimeException("A JNDI error occurred. " + ne.getMessage());            
        }    	
    }
    
    /*
     * 2. DataSource의 getConnection() 메소드를 통해 Connection을 만든다.
     */
    private Connection obtainConnection() throws SQLException {
    	return dataSource.getConnection();
    }
    
	/* (non-Javadoc)
	 * @see casestudy.business.service.MemberDao#insertMember(casestudy.business.domain.Member)
	 * 
	 * 인자로 받은 Member 객체 정보를 통해 Member 테이블에 새로운 레코드를 추가(insert) 한다.
     * 3. 기존 Statement 객체 대신 PreparedStatement 객체를 활용한다.
	 */
	@Override
	public void insertMember(Member member) {
        String query = "INSERT INTO Member (MemberID, Password, Name, Email, Tel, Zipcode, Address, Point, MemberDate) " 
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        System.out.println("MemberDAOImpl insertMember() query: " + query);
        
        Connection connection = null;
        PreparedStatement stmt = null;
        
        try {
            connection = obtainConnection();
            stmt = connection.prepareStatement(query);
            stmt.setString(1, member.getMemberID());
            stmt.setString(2, member.getPassword());
            stmt.setString(3, member.getName());
            stmt.setString(4, member.getEmail());
            stmt.setString(5, member.getTel());
            stmt.setString(6, member.getZipcode());
            stmt.setString(7, member.getAddress());
            stmt.setInt(8, member.getPoint());
            stmt.setDate(9, new Date(System.currentTimeMillis()));            
            stmt.executeUpdate();

        } catch (SQLException se) {
            System.err.println("MemberDAOImpl insertMember() Error :" + se.getMessage());
            se.printStackTrace(System.err);
            throw new RuntimeException("A database error occurred. " + se.getMessage());
            
        } finally {
            try { 
                if (stmt != null) stmt.close(); 
            } catch (SQLException se) { se.printStackTrace(System.err); }
            try { 
                if (connection != null) connection.close(); 
            } catch (SQLException se) { se.printStackTrace(System.err); }
        }
	}

	/* (non-Javadoc)
	 * @see casestudy.business.service.MemberDao#selectMember(java.lang.String)
	 * 
	 * 인자로 받은 memberID로 레코드를 찾아(select) 해당 정보를 가진 Member 객체를 리턴한다.
     * 4. 기존 Statement 객체 대신 PreparedStatement 객체를 활용한다.
	 */
	@Override
	public Member selectMember(String memberID) {
        Member member = null;
        
        String query = "SELECT MemberID, Password, Name, Email, Tel, Zipcode, Address, Point, MemberDate"
        		+" FROM Member WHERE MemberID = ?";                
        System.out.println("MemberDAOImpl selectMember() query: " + query);
        
        Connection connection = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            connection = obtainConnection();
            stmt = connection.prepareStatement(query);
            stmt.setString(1, memberID);
            rs = stmt.executeQuery();
            
            if (rs.next()) {
                member = new Member(rs.getString("MemberID"), 
                					rs.getString("Password"),
                					rs.getString("Name"),
                					rs.getString("Email"),
                					rs.getString("Tel"),
                					rs.getString("Zipcode"),
                					rs.getString("Address"),
                					rs.getInt("Point"),
                					rs.getDate("MemberDate"));
            }
            
        } catch(SQLException se) {
            System.err.println("MemberDAOImpl selectMember() Error :" + se.getMessage());
            se.printStackTrace(System.err);        	
            throw new RuntimeException("A database error occurred. " + se.getMessage());
            
        } finally {
            try {
                if (rs != null) rs.close();
            } catch (SQLException se) { se.printStackTrace(System.err); }
            try { 
                if (stmt != null) stmt.close(); 
            } catch (SQLException se) { se.printStackTrace(System.err); }
            try {
                if (connection != null) connection.close(); 
            } catch (SQLException se) { se.printStackTrace(System.err); }
        }
        
        return member;
	}

	/* (non-Javadoc)
	 * @see casestudy.business.service.MemberDao#updateMember(casestudy.business.domain.Member)
	 * 
     * 인자로 받은 Member 객체의 정보로 Member 테이블의 레코드를 갱신(update) 한다.
     * 5. 기존 Statement 객체 대신 PreparedStatement 객체를 활용한다.
	 */
	@Override
	public void updateMember(Member member) {
        String query = "UPDATE Member SET Password=?, Name=?, Email=?, Tel=?, Zipcode=?, Address=?" 
                + " WHERE MemberID=?";
        System.out.println("MemberDAOImpl updateMember() query: " + query);
        
        Connection connection = null;
        PreparedStatement stmt = null;
        
        try {            
            connection = obtainConnection();
            stmt = connection.prepareStatement(query);
            stmt.setString(1, member.getPassword());
            stmt.setString(2, member.getName());
            stmt.setString(3, member.getEmail());
            stmt.setString(4, member.getTel());
            stmt.setString(5, member.getZipcode());
            stmt.setString(6, member.getAddress());
            stmt.setString(7, member.getMemberID());
            stmt.executeUpdate();
            
        } catch(SQLException se) {
            System.err.println("MemberDAOImpl updateMember() Error :" + se.getMessage());
            se.printStackTrace(System.err);    
            throw new RuntimeException("A database error occurred. " + se.getMessage());
            
        } finally {
            try { 
                if (stmt != null) stmt.close(); 
            } catch (SQLException se) { se.printStackTrace(System.err); }
            try { 
                if (connection != null) connection.close(); 
            } catch (SQLException se) { se.printStackTrace(System.err); }
        }
	}

	/* (non-Javadoc)
	 * @see casestudy.business.service.MemberDao#deleteMember(casestudy.business.domain.Member)
	 * 
     * 인자로 받은 Member 객체의 정보를 통해 Member 테이블의 레코드를 삭제(delete) 한다.
     * 6. 기존 Statement 객체 대신 PreparedStatement 객체를 활용한다.
     */
	@Override
	public void deleteMember(Member member) {
        String query = "DELETE FROM Member WHERE MemberID=?";
        System.out.println("MemberDAOImpl deleteMember() query: " + query);
        
        Connection connection = null;
        PreparedStatement stmt = null;
        
        try {            
            connection = obtainConnection();
            stmt = connection.prepareStatement(query);
            stmt.setString(1, member.getMemberID());
            stmt.executeUpdate();
            
        } catch(SQLException se) {
            System.err.println("MemberDAOImpl deleteMember() Error :" + se.getMessage());
            se.printStackTrace(System.err);   
            throw new RuntimeException("A database error occurred. " + se.getMessage());
            
        } finally {
            try { 
                if (stmt != null) stmt.close(); 
            } catch (SQLException se) { se.printStackTrace(System.err); }
            try { 
                if (connection != null) connection.close(); 
            } catch (SQLException se) { se.printStackTrace(System.err); }
        }
	}

	/* (non-Javadoc)
	 * @see casestudy.business.service.MemberDao#checkMember(java.lang.String, java.lang.String)
	 * 
	 * 인자로 받은 아이디와 패스워드 정보로 로그인 가능 여부(id, password 확인)를 확인하고 
	 * 해당 정보를 담은 Member 객체를 리턴한다.
     *  (1) 아이디가 존재하지 않을 경우 Member의 check의 값을 Member.INVALID_ID 로,
     *  (2) 패스워드가 맞지 않을 경우 Member의 check 값을 Member.INVALID_PASSWORD 로,
     *  (3) 아이디와 패스워드가 모두 일치할 경우 Member의 check의 값을 Member.VALID_MEMBER 로 세팅하여
     *  Member 객체를 리턴한다.
     *  
     * 7. 기존 Statement 객체 대신 PreparedStatement 객체를 활용한다.
	 */
	@Override
	public Member checkMember(String memberID, String password) {
		Member member = new Member(memberID, password);
		
        String query = "SELECT Password, Name, Email, Tel, Zipcode, Address, Point, MemberDate FROM Member WHERE MemberID=?";
        System.out.println("MemberDAOImpl checkMember() query: " + query);
        
        Connection connection = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            connection = obtainConnection();
            stmt = connection.prepareStatement(query);
            stmt.setString(1, memberID);
            rs = stmt.executeQuery();
            
            if (rs.next()) {
                String pw = rs.getString("Password");
                if (pw.equals(password)) {
                    member.setName(rs.getString("Name"));
                    member.setEmail(rs.getString("Email"));
                    member.setTel(rs.getString("Tel"));
                    member.setZipcode(rs.getString("Zipcode"));
                    member.setAddress(rs.getString("Address"));
                    member.setPoint(rs.getInt("Point"));
                    member.setMemberDate(rs.getDate("MemberDate"));
                    member.setCheck(Member.VALID_MEMBER);
                } else {
                    member.setCheck(Member.INVALID_PASSWORD);
                }
            } else {
                member.setCheck(Member.INVALID_ID);
            }
            
        } catch(SQLException se) {
            System.err.println("MemberDAOImpl checkMember() Error :" + se.getMessage());
            se.printStackTrace(System.err);
            throw new RuntimeException("A database error occurred. " + se.getMessage());

        } finally {
            try { if (rs != null) rs.close(); } catch (SQLException se) { se.printStackTrace(System.err); }
            try { if (stmt != null) stmt.close(); } catch (SQLException se) { se.printStackTrace(System.err); }
            try { if (connection != null) connection.close(); } catch (SQLException se) { se.printStackTrace(System.err); }
        }

        return member;
	}

	/* (non-Javadoc)
	 * @see casestudy.business.service.MemberDao#selectAllMembers()
	 * 
     * Member 테이블에서 모든 회원 정보를 검색해 배열에 담아 리턴한다.
     * 8. 기존 Statement 객체 대신 PreparedStatement 객체를 활용한다.
	 */
	@Override
	public Member[] selectAllMembers() {
        String query = "SELECT MemberID, Password, Name, Email, Tel, Zipcode, Address, Point, MemberDate FROM Member";                
        System.out.println("MemberDAOImpl selectAllMembers() query: " + query);
        
        Connection connection = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        ArrayList<Member> temp = new ArrayList<Member>();
        Member member = null;
        
        try {
            connection = obtainConnection();
            stmt = connection.prepareStatement(query);
            rs = stmt.executeQuery();
            
            while (rs.next()) {
                member = new Member(rs.getString("MemberID"), 
    								rs.getString("Password"),
    								rs.getString("Name"),
    								rs.getString("Email"),
    								rs.getString("Tel"),
    								rs.getString("Zipcode"),
    								rs.getString("Address"),
    								rs.getInt("Point"),
    								rs.getDate("MemberDate"));
                temp.add(member);
            }
            
        } catch(SQLException se) {
            System.err.println("MemberDAOImpl selectAllMembers() Error :" + se.getMessage());
            se.printStackTrace(System.err);  
            throw new RuntimeException("A database error occurred. " + se.getMessage());

        } finally {
            try { if (rs != null) rs.close(); } catch(SQLException ex) { ex.printStackTrace(System.err); }
            try { if (stmt != null) stmt.close(); } catch(SQLException ex) { ex.printStackTrace(System.err); }
            try { if (connection != null) connection.close(); } catch(SQLException ex){ ex.printStackTrace(System.err); }
        }

        return temp.toArray(new Member[0]);
	}

	/* (non-Javadoc)
	 * @see casestudy.business.service.MemberDao#memberIDExists(java.lang.String)
	 * 
     * 인자로 받은 memberID에 해당하는 기존 레코드가 Member 테이블에 존재하는지 여부를 확인한다.
     * 9. 기존 Statement 객체 대신 PreparedStatement 객체를 활용한다.
	 */
	@Override
	public boolean memberIDExists(String memberID) {
		boolean result = false;
		
        String query = "SELECT MemberID FROM Member WHERE MemberID=?";
        System.out.println("MemberDAOImpl memberIDExists() query: " + query);
        
        Connection connection = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            connection = obtainConnection();
            stmt = connection.prepareStatement(query);
            stmt.setString(1, memberID);
            rs = stmt.executeQuery();
            result = rs.next();

        } catch(SQLException se) {
            System.err.println("MemberDAOImpl memberIDExists() Error :" + se.getMessage());
            se.printStackTrace(System.err);  
            throw new RuntimeException("A database error occurred. " + se.getMessage());

        } finally {
            try { if (rs != null) rs.close(); } catch(SQLException ex) { ex.printStackTrace(System.err); }
            try { if (stmt != null) stmt.close(); } catch(SQLException ex) { ex.printStackTrace(System.err); }
            try { if (connection != null) connection.close(); } catch(SQLException ex){ ex.printStackTrace(System.err); }
        }
        
        return result;
	}
}
