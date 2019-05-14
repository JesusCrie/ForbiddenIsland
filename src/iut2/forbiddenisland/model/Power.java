package iut2.forbiddenisland.model;

import iut2.forbiddenisland.controller.Request;
import iut2.forbiddenisland.controller.Response;

public abstract interface Power {

	/**
	 * 
	 * @param req
	 */
	abstract void alterRequest(Request req);

	/**
	 * 
	 * @param res
	 */
	abstract void alterResponse(Response res);

}
