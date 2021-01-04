package vn.com.fecredit.utils;

public class Constants {
	public static final int SUCCESS_CODE = 200;
	public static final int FAILURE_CODE = 400;
	public static final int FORBIDDEN_CODE = 403;
	public static final int AUTHENTICATION_FAILURE_CODE = 401;
	public static final int NOT_FOUND = 404;
	public static final int INTERNAL_SERVER_ERROR_CODE = 500;
	public static final int EXPECTATION_FAILED_ERROR_CODE = 417;
	public static final int INVALID_POST_REQUEST_DATA_ERROR_CODE = 422;
	public static final int INVALID_INITIATE_REQUEST = 405;
	public static final int NID_NOT_MATCHED = 102;
	
	public static String UNSUCCESS_MESSAGE = "UnSuccessful";
	public static String EXCEPTION_MESSAGE = "Exception occured";
	
	public static enum ErrorCode{
		SUCCESS_CODE(200),
	    PAGE_NOT_FOUND(404),
	    INTERNAL_SERVER_ERROR(500),
		SOME_ERROR(400);
		private final int value;

		// NOTE: Enum constructor must have private or package scope. You can not use
		// the public access
		// modifier.
		private ErrorCode(int value) {
			this.value = value;
		}

		public int getValue() {
			return value;
		}
	};
}
