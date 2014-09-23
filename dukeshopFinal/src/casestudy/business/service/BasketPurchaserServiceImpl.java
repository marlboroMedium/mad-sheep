package casestudy.business.service;

import java.util.Collection;

import casestudy.business.domain.Basket;
import casestudy.business.domain.Purchaser;
import casestudy.business.domain.TheOrder;
import casestudy.dataaccess.BasketPurchaserDaoImpl;

/**
 * 장바구니와 구매 처리 관련 비즈니스 로직을 구현할 서비스 클래스로 
 * 데이터 액세스 처리는 BasketPurchaserDao 객체에게 위임하여 수행한다.
 */
public class BasketPurchaserServiceImpl implements BasketPurchaserService {
    private BasketPurchaserDao basketPurchaserDao;

    public BasketPurchaserServiceImpl() {
        basketPurchaserDao = new BasketPurchaserDaoImpl();
    }
        
    /* (non-Javadoc)
	 * @see casestudy.business.service.BasketPurchaserService#insertBasketPurchaser(casestudy.business.domain.Basket, casestudy.business.domain.Purchaser, java.lang.String)
	 */
    @Override
	public void insertBasketPurchaser(Basket basket, Purchaser purchaser, String memberID) {
        basketPurchaserDao.insertBasketPurchaser(basket, purchaser, memberID);
    }
    
    /* (non-Javadoc)
	 * @see casestudy.business.service.BasketPurchaserService#deleteBasket(java.lang.String)
	 */
    @Override
	public void deleteBasket(String memberID) {
        basketPurchaserDao.deleteBasket(memberID);
    }

    /* (non-Javadoc)
	 * @see casestudy.business.service.BasketPurchaserService#listBasketPurchaser(java.lang.String)
	 */
    @Override
	public Collection<TheOrder> listBasketPurchaser(String memberID) {
        Collection<TheOrder> coll = basketPurchaserDao.listBasketPurchaser(memberID);
        return coll;
    }
      
    /* (non-Javadoc)
	 * @see casestudy.business.service.BasketPurchaserService#orderMaxNo()
	 */
    @Override
	public int orderMaxNo() {
        int maxNo = basketPurchaserDao.orderMaxNo();
        return maxNo;
    }    
}
