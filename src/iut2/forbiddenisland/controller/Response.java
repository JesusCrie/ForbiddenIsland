package iut2.forbiddenisland.controller;

public class Response<T> {

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
		// TODO - implement iut2.forbiddenisland.controller.Response.isOk
		throw new UnsupportedOperationException();
	}

}
