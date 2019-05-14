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