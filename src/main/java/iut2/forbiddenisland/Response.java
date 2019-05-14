public class Response {

	Request request;
	private T data;
	private boolean success;

	public T getData() {
		return this.data;
	}

	/**
	 * 
	 * @param d
	 */
	public void setData(T d) {
		this.data = d;
	}

	public boolean isOk() {
		// TODO - implement Response.isOk
		throw new UnsupportedOperationException();
	}

}