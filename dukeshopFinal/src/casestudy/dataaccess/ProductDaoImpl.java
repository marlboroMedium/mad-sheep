/**
 * 파일명 : ProductDaoImpl.java
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

import casestudy.business.domain.Product;
import casestudy.business.service.ProductDao;

/**
 * 상품 관련 데이터 액세스 처리를 JDBC API를 활용하여 구현한 클래스로 
 * 데이터베이스 접속과 Product 테이블을 사용하는 SQL 문의 수행을 통해 관련 처리를 수행한다.
 * 
 * @author 고범석(kidmania@hotmail.com)
 *
 */
public class ProductDaoImpl implements ProductDao {
	private DataSource dataSource;
	
    /*
     * 1. JNDI API를 이용하여 네이밍 서비스에 바인딩(jdbc/dukeshopDB)된 DataSource를 검색한다.
     */
    public ProductDaoImpl() {
        try {
            Context context = new InitialContext();
            context = (Context) context.lookup("java:comp/env");
            dataSource = (DataSource) context.lookup("jdbc/dukeshopDB");
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
	 * @see casestudy.business.service.ProductDao#selectProduct(java.lang.String)
	 * 
	 * 인자로 받은 productID로 레코드를 찾아(select) 해당 정보를 가진 Product 객체를 리턴한다.
     * 3. 기존 Statement 객체 대신 PreparedStatement 객체를 활용한다.
	 */
	@Override
	public Product selectProduct(String productID) {
        Product product = null;

        String query = "SELECT ProductID, MallID, ProductName, Company, Price1, Price2, Installment, Keyword, Detail, ProductDate, PhotoDir " +
                "FROM Product WHERE ProductID=?";
        System.out.println("ProductDAOImpl selectProduct() query: " + query);
        
        Connection connection = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            connection = obtainConnection();
            stmt = connection.prepareStatement(query);
            stmt.setString(1, productID);
            rs = stmt.executeQuery();
            
            if (rs.next()) {
                product = new Product(rs.getString("ProductID"),
  					  				  rs.getString("MallID"),
  					  				  rs.getString("ProductName"),
  					  				  rs.getString("Company"),
  					  				  rs.getInt("Price1"),
  					  				  rs.getInt("Price2"),
  					  				  rs.getString("Installment"),
  					  				  rs.getString("Keyword"),
  					  				  rs.getString("Detail"),
  					  				  rs.getDate("ProductDate"),
  					  				  rs.getString("PhotoDir"));
            }
            
        } catch(SQLException se) {
            System.err.println("ProductDAOImpl selectProduct() Error :" + se.getMessage());
            se.printStackTrace(System.err);  
            throw new RuntimeException("A database error occured. " + se.getMessage());

        } finally {
            try { if (rs != null) rs.close(); } catch(SQLException ex) { ex.printStackTrace(System.err); }
            try { if (stmt != null) stmt.close(); } catch(SQLException ex) { ex.printStackTrace(System.err); }
            try { if (connection != null) connection.close(); } catch(SQLException e) { e.printStackTrace(System.err); }
        }

        return product;
	}

	/* (non-Javadoc)
	 * @see casestudy.business.service.ProductDao#selectAllProducts()
	 * 
     * Product 테이블에서 모든 상품 정보를 검색해 배열에 담아 리턴한다.
     * 4. 기존 Statement 객체 대신 PreparedStatement 객체를 활용한다.
	 */
	@Override
	public Product[] selectAllProducts() {
        String query = "SELECT ProductID, MallID, ProductName, Company, Price1, Price2, Installment, Keyword, Detail, ProductDate, PhotoDir FROM Product";
        System.out.println("ProductDAOImpl selectAllProducts() query: " + query);
        
        Connection connection = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        ArrayList<Product> temp = new ArrayList<Product>();
        Product product = null;
        
        try {
            connection = obtainConnection();
            stmt = connection.prepareStatement(query);
            rs = stmt.executeQuery();
            
            while (rs.next()) {
                product = new Product(rs.getString("ProductID"),
                					  rs.getString("MallID"),
                					  rs.getString("ProductName"),
                					  rs.getString("Company"),
                					  rs.getInt("Price1"),
                					  rs.getInt("Price2"),
                					  rs.getString("Installment"),
                					  rs.getString("Keyword"),
                					  rs.getString("Detail"),
                					  rs.getDate("ProductDate"),
                					  rs.getString("PhotoDir"));
                temp.add(product);
            }
            
        } catch(SQLException se) {
            System.err.println("ProductDAOImpl selectAllProducts() Error :" + se.getMessage());
            se.printStackTrace(System.err);  
            throw new RuntimeException("A database error occurred. " + se.getMessage());

        } finally {
            try { if (rs != null) rs.close(); } catch(SQLException ex) { ex.printStackTrace(System.err); }
            try { if (stmt != null) stmt.close(); } catch(SQLException ex) { ex.printStackTrace(System.err); }
            try { if (connection != null) connection.close(); } catch(SQLException ex){ ex.printStackTrace(System.err); }
        }

        return temp.toArray(new Product[0]);
	}

	/* (non-Javadoc)
	 * @see casestudy.business.service.ProductDao#productIDExists(java.lang.String)
	 * 
     * 인자로 받은 productID에 해당하는 기존 레코드가 Product 테이블에 존재하는지 여부를 확인한다.
     * 5. 기존 Statement 객체 대신 PreparedStatement 객체를 활용한다.
	 */
	@Override
	public boolean productIDExists(String productID) {
		boolean result = false;
		
        String query = "SELECT ProductID FROM Product WHERE ProductID=?";                
        System.out.println("ProductDAOImpl productIDExists() query: " + query);
        
        Connection connection = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            connection = obtainConnection();
            stmt = connection.prepareStatement(query);
            stmt.setString(1, productID);
            rs = stmt.executeQuery();
            result = rs.next();

        } catch(SQLException se) {
            System.err.println("ProductDAOImpl productIDExists() Error :" + se.getMessage());
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
