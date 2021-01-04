package vn.com.fecredit.models;

public class HTTPResponse {

	private int statusCode;
	private String response;
	private String contentType;

	public HTTPResponse() {
		super();
	}

	public HTTPResponse(int statusCode, String response) {
		this.setStatusCode(statusCode);
		this.setResponse(response);
	}

	public HTTPResponse(int statusCode, String response, String contentType) {
		this.setStatusCode(statusCode);
		this.setResponse(response);
		this.setContentType(contentType);
	}
	
	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	@Override
	public String toString() {
		return "HTTPResponse [statusCode=" + statusCode + ", response=" + response + ", contentType=" + contentType
				+ "]";
	}
	
}