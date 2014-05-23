package edu.upc.eetac.dsa.nmendo.books.android.api;

import java.util.HashMap;
import java.util.Map;

public class BookRootAPI {

	private Map<String, Link> links;

	public BookRootAPI() {
		links = new HashMap<>();
	}

	public Map<String, Link> getLinks() {
		return links;
	}

}
