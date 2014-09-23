/**
 * 파일명 : DataDuplicatedException.java
 * 작성일 : 2014. 2. 14.
 * 파일설명 : 
 */
package casestudy.business.service;

/**
 * 중복된 정보가 존재할 경우 발생하는 예외
 * 
 * @author 고범석(kidmania@hotmail.com)
 *
 */
public class DataDuplicatedException extends Exception {
	private static final long serialVersionUID = 1L;

	public DataDuplicatedException() {
		super();
	}

	public DataDuplicatedException(String message, Throwable cause) {
		super(message, cause);
	}

	public DataDuplicatedException(String message) {
		super(message);
	}

	public DataDuplicatedException(Throwable cause) {
		super(cause);
	}	
}
