package edu.upc.eetac.dsa.nmendo.books.android.api;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.upc.eetac.dsa.nmendo.books.android.api.Link;



public class Book {
	private String bookid;
	private String titulo;
	private String autor;
	private String lengua;
	private String edicion;
	private String editorial;
	// DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	private String fechae;// fecha de edición
	private String fechai;// fecha de impresión
	private List<Review> reviews = new ArrayList<Review>();
	private Map<String, Link> links = new HashMap<String, Link>();
	public Map<String, Link> getLinks() {
		return links;
	}
	public void setLinks(Map<String, Link> links) {
		this.links = links;
	}

	public String getBookid() {
		return bookid;
	}

	public void setBookid(String bookid) {
		this.bookid = bookid;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getAutor() {
		return autor;
	}

	public void setAutor(String autor) {
		this.autor = autor;
	}

	public String getLengua() {
		return lengua;
	}

	public void setLengua(String lengua) {
		this.lengua = lengua;
	}

	public String getEdicion() {
		return edicion;
	}

	public void setEdicion(String edicion) {
		this.edicion = edicion;
	}

	public String getEditorial() {
		return editorial;
	}

	public void setEditorial(String editorial) {
		this.editorial = editorial;
	}

	public String getFechae() {
		return fechae;
	}

	public void setFechae(String fechae) {
		this.fechae = fechae;
	}

	public String getFechai() {
		return fechai;
	}

	public void setFechai(String fechai) {
		this.fechai = fechai;
	}

	public List<Review> getReviews() {
		return reviews;
	}

	public void setReviews(List<Review> reviews) {
		this.reviews = reviews;
	}

	public void addReview(Review review) {
		reviews.add(review);
	}

}



