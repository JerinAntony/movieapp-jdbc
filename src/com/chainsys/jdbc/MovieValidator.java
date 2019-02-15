package com.chainsys.jdbc;

public class MovieValidator {

	public void validateAdd(Movies movies) throws Exception {
		if (movies.name == null || movies.name.equalsIgnoreCase("null")
				|| movies.name.isEmpty() || movies.name.length() == 0) {
			throw new Exception("Invalid Name");
		}
		if (movies.price <= 0) {
			throw new Exception("Invalid Price!Price cannot be less than 0");
		}
	}

	public void validateUpdate(Movies movies) throws Exception {
		if (movies.id <= 0) {
			throw new Exception("Invalid Id");
		}
		if (movies.name == null || movies.name.equalsIgnoreCase("null")
				|| movies.name.isEmpty() || movies.name.length() == 0) {
			throw new Exception("Invalid Name");
		}

	}

	public void validateFind(Movies movies) throws Exception {
		if (movies.id <= 0) {
			throw new Exception("Invalid Id");
		}

	}

	public void validateDelete(Movies movies) throws Exception {
		if (movies.id <= 0) {
			throw new Exception("Invalid Id");
		}

	}

}
