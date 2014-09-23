/**
 * 파일명 : ProductServiceImpl.java
 * 작성일 : 2014. 2. 13.
 * 파일설명 : 
 */
package casestudy.business.service;

import casestudy.business.domain.Product;

/**
 * 상품 관련 비즈니스 로직을 구현할 서비스 클래스로 
 * 데이터 액세스 처리는 ProductDao 객체에게 위임하여 수행한다.
 * 
 * @author 고범석(kidmania@hotmail.com)
 *
 */
public class ProductServiceImpl implements ProductService {
	
    private ProductDao productDataAccess;

    /* 
     * ProductDaoImpl 객체를 생성하여 productDataAccess 인스턴스 변수 초기화  
     */
    public ProductServiceImpl() {
    	productDataAccess = new casestudy.dataaccess.ProductDaoImpl();
    }    
    
	/* (non-Javadoc)
	 * @see casestudy.business.service.ProductService#findProduct(java.lang.String)
	 * 
	 * ProductDao 객체를 사용해 인자로 받은 productID에 해당하는 상품 정보를 찾아서 리턴한다.
     *  1. productID에 해당하는 상품이 존재하지 않을 경우 DataNotFoundException을 발생시킨다.
	 */
	@Override
	public Product findProduct(String productID) throws DataNotFoundException {
		if (!productDataAccess.productIDExists(productID)) {
			throw new DataNotFoundException("존재하지 않는 상품입니다.(" + productID + ")");
		}
		
        return productDataAccess.selectProduct(productID);
	}

	/* (non-Javadoc)
	 * @see casestudy.business.service.ProductService#getAllProducts()
	 * 
	 * ProductDao 객체를 사용해 모든 상품 정보를 구해서 리턴한다.
	 */
	@Override
	public Product[] getAllProducts() {
        return productDataAccess.selectAllProducts();
	}

}
