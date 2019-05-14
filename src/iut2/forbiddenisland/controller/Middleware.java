package iut2.forbiddenisland.controller;

public abstract class Middleware {

	/**
	 * 
	 * @param request
	 */
	public abstract Request handleRequest(Request request);

	/**
	 * 
	 * @param response
	 */
	public abstract Response handleResponse(Response response);

}
