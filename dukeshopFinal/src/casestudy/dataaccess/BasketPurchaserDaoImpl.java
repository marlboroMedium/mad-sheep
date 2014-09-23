package casestudy.dataaccess;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import casestudy.business.domain.Basket;
import casestudy.business.domain.Purchaser;
import casestudy.business.domain.TheOrder;
import casestudy.business.service.BasketPurchaserDao;

/**
 * Basket(쇼핑카트)과 Purchaser(구매) 관련 데이터 액세스 처리를 구현한 클래스 
 */
public class BasketPurchaserDaoImpl implements BasketPurchaserDao {
    private DataSource dataSource;
    
    public BasketPurchaserDaoImpl() {
        try {
            // Retrieve the DataSource from JNDI
            Context context = new InitialContext();
            dataSource = (DataSource) context.lookup("java:comp/env/jdbc/dukeshopDB");
        } catch (NamingException ne) {
            System.err.println("A JNDI error occured.");
            ne.printStackTrace(System.err);        	
            throw new RuntimeException("A JNDI error occurred. " + ne.getMessage());
        }
    }
    
    private Connection obtainConnection() throws SQLException {
    	return dataSource.getConnection();
    }    
    
    /**
     * Basket, Purchaser 객체와 MemberID를 argument로 받아 Basket 테이블에 정보를 등록시킨다.
     * @param Basket basket,Purchaser purchaser,String memberID
     */
    @Override
	public void insertBasketPurchaser(Basket basket, Purchaser purchaser, String memberID) {
        String insertBasket_query = "INSERT INTO Basket (OrderNum, ProductID, MemberID, Quantity, Price) VALUES (?, ?, ?, ?, ?)";
        System.out.println("BasketPurchaserDaoImpl insertBasket query: " + insertBasket_query);

        String insertPurchaser_query = "INSERT INTO Purchaser "
        	+ "(OrderNum, Place, MemberID, Name, Address, Tel, Email, PayType, Amount, PayStatus, PurchaseDate, CardType, CardNumber) "
        	+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        System.out.println("BasketPurchaserDaoImpl insertPurchaser query: " + insertPurchaser_query);
        
        Connection connection = null;
        PreparedStatement stmtBasket = null;
        PreparedStatement stmtPurchaser = null;
        
        try {            
            // Get a database connection
            connection = obtainConnection();
            connection.setAutoCommit(false);
            
            stmtBasket = connection.prepareStatement(insertBasket_query);
            stmtBasket.setInt(1, basket.getOrderNum());
            stmtBasket.setString(2, basket.getProductID());
            stmtBasket.setString(3, memberID);
            stmtBasket.setInt(4, basket.getQuantity());
            stmtBasket.setInt(5, basket.getPrice());
            stmtBasket.executeUpdate();
            
            stmtPurchaser = connection.prepareStatement(insertPurchaser_query);
            stmtPurchaser.setInt(1, purchaser.getOrderNum());
            stmtPurchaser.setString(2, purchaser.getPlace());
            stmtPurchaser.setString(3, purchaser.getMemberID());
            stmtPurchaser.setString(4, purchaser.getName());
            stmtPurchaser.setString(5, purchaser.getAddress());
            stmtPurchaser.setString(6, purchaser.getTel());
            stmtPurchaser.setString(7, purchaser.getEmail());
            stmtPurchaser.setString(8, purchaser.getPayType());
            stmtPurchaser.setInt(9, purchaser.getAmount());
            stmtPurchaser.setString(10, purchaser.getPayStatus());
            stmtPurchaser.setDate(11, new Date(System.currentTimeMillis()));
            stmtPurchaser.setString(12, purchaser.getCardType());
            stmtPurchaser.setString(13, purchaser.getCardNumber());
            stmtPurchaser.executeUpdate();

            connection.commit();            
            
        } catch(SQLException se) {
            try {
                connection.rollback();                
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            System.err.println("BasketPurchaserDaoImpl insertBasketPurchaser() Error :" + se.getMessage());
            se.printStackTrace(System.err);
            throw new RuntimeException("A database error occurred. " + se.getMessage());
        } finally {
            try { if (stmtBasket != null) stmtBasket.close(); } catch(SQLException ex) { ex.printStackTrace(System.err); }
            try { if (stmtPurchaser != null) stmtPurchaser.close(); } catch(SQLException ex) { ex.printStackTrace(System.err); }
            try { if (connection != null) connection.setAutoCommit(true); connection.close(); } catch(SQLException ex){ ex.printStackTrace(System.err); }
        }
    }
    
	/**
	 * 주문번호를 새로 추가하기 위해 주문번호 중 가장 큰 값을 조회하여 1 증가시킨 값을 반환한다.
	 * @param
	 * @return int
	 */
    @Override
	public int orderMaxNo() {
        String query = "SELECT Max(OrderNum) FROM Purchaser";
        System.out.println("BasketPurchaserDaoImpl orderMaxNo() query: " + query);
        
        int maxNo = 0;
        Connection connection = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            connection = obtainConnection();            
            stmt = connection.prepareStatement(query);
            rs = stmt.executeQuery();
            
            if (rs.next()) {
                maxNo = rs.getInt(1) + 1;
            }

        } catch(SQLException se) {
            System.err.println("BasketPurchaserDaoImpl orderMaxNo() Error :" + se.getMessage());
            se.printStackTrace(System.err);
            throw new RuntimeException("A database error occurred. " + se.getMessage());
        } finally {
            try { if ( rs != null ) rs.close(); } catch(SQLException ex) { ex.printStackTrace(System.err); }
            try { if (stmt != null) stmt.close(); } catch(SQLException ex) { ex.printStackTrace(System.err); }
            try { if (connection != null) connection.close(); } catch(SQLException ex){ ex.printStackTrace(System.err); }
        }
        
        return maxNo;
    }
    
	/**
	 * 구매 완료 후에 memberID를 argument로 받아 Basket 테이블에서 정보를 삭제한다.
	 * @param String memberID
	 */
    @Override
	public void deleteBasket(String memberID) {
        String query = "DELETE FROM Basket WHERE MemberID=?";
        System.out.println("BasketPurchaserDaoImpl deleteBasket() query: " + query + " " + memberID);
        
        Connection connection = null;
        PreparedStatement stmt = null;
        
        try {            
            // Get a database connection
            connection = obtainConnection();
            stmt = connection.prepareStatement(query);
            stmt.setString(1, memberID);            
            int delCount = stmt.executeUpdate();
            System.out.println("BasketPurchaserDaoImpl deleteBasket() delCount:" + delCount);

        } catch(SQLException se) {
            System.err.println("BasketPurchaserDaoImpl deleteBasket() Error :" + se.getMessage());
            se.printStackTrace(System.err);
            throw new RuntimeException("A database error occurred. " + se.getMessage());
        } finally {
            try { if (stmt != null) stmt.close(); } catch(SQLException ex) { ex.printStackTrace(System.err); }
            try { if (connection != null) connection.close(); } catch(SQLException ex) { ex.printStackTrace(System.err); }
        }
    }
    
    /**
     * memberID를 argument로 받아 하나의 주문정보를 나타내는 TheOrder 객체를 생성하고
     * 완성된 TheOrder 객체(Purchaser, Basket, Product 테이블에서 데이터 검색)들을 Collection에 담아 리턴한다.
     * @param String memID
     * @return Collection
     */
    @Override
	public Collection<TheOrder> listBasketPurchaser(String memberID) {
        String query =
	            "Select a.OrderNum, c.ProductName, b.Price, b.Quantity, a.Name, a.Address, a.PayType, a.Email, a.Tel "
	            + "FROM Purchaser a, Basket b, Product c "
	            + "WHERE a.memberID= ? AND a.PayStatus='N' "
	            + "AND a.OrderNum=b.OrderNum AND b.ProductID=c.ProductID ORDER BY OrderNum";
        System.out.println("BasketPurchaserDaoImpl listBasketPurchaser() query: " + query);
        
        Connection connection = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Collection<TheOrder> arrayList = null;
        TheOrder theOrder = null;
        
        try {
            connection = obtainConnection();
            stmt = connection.prepareStatement(query);
            stmt.setString(1, memberID);
            rs = stmt.executeQuery();
            
            arrayList = new ArrayList<TheOrder>();
            while (rs.next()) {
                theOrder = new TheOrder();
                
                theOrder.setOrderNum(rs.getInt(1));
                theOrder.setProductName(rs.getString(2));
                theOrder.setPrice(rs.getInt(3));
                theOrder.setQuantity(rs.getInt(4));
                theOrder.setName(rs.getString(5));
                theOrder.setAddress(rs.getString(6));
                theOrder.setPaytype(rs.getString(7));
                theOrder.setEmail(rs.getString(8));
                theOrder.setTel(rs.getString(9));
                arrayList.add(theOrder);
            }
            
        } catch(SQLException se) {
            System.err.println("BasketPurchaserDaoImpl listBasketPurchaser() Error :" + se.getMessage());
            se.printStackTrace(System.err);
            throw new RuntimeException("A database error occurred. " + se.getMessage());
        } finally {
            try { if (rs != null) rs.close(); } catch(SQLException ex) { ex.printStackTrace(System.err); }
            try { if (stmt != null) stmt.close(); } catch(SQLException ex) { ex.printStackTrace(System.err); }
            try { if (connection != null) connection.close(); } catch(SQLException ex){ ex.printStackTrace(System.err); }
        }
        
        return arrayList;
    }    
}