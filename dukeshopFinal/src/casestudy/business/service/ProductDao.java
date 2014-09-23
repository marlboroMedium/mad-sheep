/**
 * 파일명 : ProductDao.java
 * 작성일 : 2014. 2. 12.
 * 파일설명 : 
 */
package casestudy.business.service;

import casestudy.business.domain.Product;

/**
 * 상품 관련 데이터 액세스 처리를 담당할 객체의 규격을 정의한 인터페이스.<br/> 
 * 데이터 액세스 층을 분리함으로써 데이터 액세스 층 이용 기술이나 구현이 변경되어도 
 * 비즈니스 로직 층에 영향을 주지 않는다.
 * 
 * @author 고범석(kidmania@hotmail.com)
 *
 */
public interface ProductDao {
	
    /**
     * 데이터 저장소에서 인수로 주어진 productID에 해당하는 상품정보를 검색한다.
     *
     * @param productID 검색하고자 하는 상품의 productID
     * @return 검색된 상품정보를 담고 있는 Product 객체
     */
	public Product selectProduct(String productID);
	
    /**
     * 데이터 저장소에서 모든 상품정보를 검색한다.
     * 
     * @return 검색된 모든 상품정보를 담고 있는 Product 배열
     */
	public Product[] selectAllProducts();
	
    /**
     * 데이터 저장소에 인수로 주어진 productID에 해당하는 기존 상품정보가 있는지 확인한다.
     * 
     * @return 해당하는 상품정보가 존재하면 true, 존재하지 않으면 false
     */
	public boolean productIDExists(String productID);
	
}
