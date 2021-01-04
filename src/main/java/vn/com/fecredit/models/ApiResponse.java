package vn.com.fecredit.models;

public class ApiResponse {

	private int status;
	private String message;
	private Object data;

	public ApiResponse() {
		super();
	}

	public ApiResponse(int status, String message, Object data) {
		super();
		this.status = status;
		this.message = message;
		this.data = data;
	}

	public ApiResponse(int status, String message) {
		super();
		this.status = status;
		this.message = message;

	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method
		return "ApiRepsonse{" + "status='" + status + '\'' + ", message='" + message + "\'}'";
	}
}