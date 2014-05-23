package edu.upc.eetac.dsa.nmendo.books.android.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BooksCollection {
	private List<Book> books;

	public void BookCollection() {
		books = new ArrayList<Book>();
	}

	public List<Book> getBooks() {
		return books;
	}

	public void setStings(List<Book> books) {
		this.books = books;
	}

	public void addBook(Book book) {
		books.add(book);
	}
	private Map<String, Link> links = new HashMap<>();

	public Map<String, Link> getLinks() {
		return links;
	}

	public void setLinks(Map<String, Link> links) {
		this.links = links;
	}
}
