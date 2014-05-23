package edu.upc.eetac.dsa.nmendo.books.android.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
import java.util.Properties;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;
import edu.upc.eetac.dsa.nmendo.books.android.MediaType;
import edu.upc.eetac.dsa.nmendo.books.android.api.BooksAPI;
import edu.upc.eetac.dsa.nmendo.books.android.api.BookAndroidException;
import edu.upc.eetac.dsa.nmendo.books.android.api.BookRootAPI;
import edu.upc.eetac.dsa.nmendo.books.android.api.Link;
import edu.upc.eetac.dsa.nmendo.books.android.api.SimpleLinkHeaderParser;
import edu.upc.eetac.dsa.nmendo.books.android.api.Book;
import edu.upc.eetac.dsa.nmendo.books.android.api.BooksCollection;

public class BooksAPI {
	private final static String TAG = BooksAPI.class.getName();
	private static BooksAPI instance = null;
	private URL url;

	private BookRootAPI rootAPI = null;

	private BooksAPI(Context context) throws IOException, BookAndroidException {
		super();

		AssetManager assetManager = context.getAssets();
		Properties config = new Properties();
		config.load(assetManager.open("config.properties"));
		String serverAddress = config.getProperty("server.address");
		String serverPort = config.getProperty("server.port");
		url = new URL("http://" + serverAddress + ":" + serverPort
				+ "/books-api");

		Log.d("LINKS", url.toString());
		getRootAPI();
	}

	public final static BooksAPI getInstance(Context context)
			throws BookAndroidException {
		if (instance == null)
			try {
				instance = new BooksAPI(context);
			} catch (IOException e) {
				throw new BookAndroidException("Can't load configuration file");
			}
		return instance;
	}

	private void getRootAPI() throws BookAndroidException {
		Log.d(TAG, "getRootAPI()");
		rootAPI = new BookRootAPI();
		HttpURLConnection urlConnection = null;
		try {
			urlConnection = (HttpURLConnection) url.openConnection();
			urlConnection.setRequestMethod("GET");
			urlConnection.setDoInput(true);
			urlConnection.connect();
		} catch (IOException e) {
			throw new BookAndroidException(
					"Can't connect to Beeter API Web Service");
		}

		// la url devuelve un JSON y lo que hace a partir de esta línea es leer
		// el JSON recibido.
		BufferedReader reader;
		try {
			reader = new BufferedReader(new InputStreamReader(
					urlConnection.getInputStream()));
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line);
			}

			JSONObject jsonObject = new JSONObject(sb.toString());
			JSONArray jsonLinks = jsonObject.getJSONArray("links");
			parseLinks(jsonLinks, rootAPI.getLinks());
		} catch (IOException e) {
			throw new BookAndroidException(
					"Can't get response from Beeter API Web Service");
		} catch (JSONException e) {
			throw new BookAndroidException("Error parsing Books Root API");
		}

	}

	private void parseLinks(JSONArray jsonLinks, Map<String, Link> map)
			throws BookAndroidException, JSONException {
		for (int i = 0; i < jsonLinks.length(); i++) {
			Link link = SimpleLinkHeaderParser
					.parseLink(jsonLinks.getString(i));
			String rel = link.getParameters().get("rel");
			String rels[] = rel.split("\\s");
			for (String s : rels)
				map.put(s, link);
		}

	}

	public BooksCollection getBooks(URL params) throws BookAndroidException {
		Log.d(TAG, "getBooks()");
		BooksCollection books = new BooksCollection();

		HttpURLConnection urlConnection = null;
		try {
			urlConnection = (HttpURLConnection) new URL(rootAPI.getLinks()
					.get("books").getTarget()).openConnection();
			urlConnection.setRequestMethod("GET");
			urlConnection.setDoInput(true);
			urlConnection.connect();
		} catch (IOException e) {
			throw new BookAndroidException(
					"Can't connect to Books API Web Service");
		}

		BufferedReader reader;
		try {
			reader = new BufferedReader(new InputStreamReader(
					urlConnection.getInputStream()));
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line);
			}

			JSONObject jsonObject = new JSONObject(sb.toString());
			JSONArray jsonLinks = jsonObject.getJSONArray("links");
			parseLinks(jsonLinks, books.getLinks());

			JSONArray jsonStings = jsonObject.getJSONArray("Books");

			
			for (int i = 0; i < jsonStings.length(); i++) {
				Book book = new Book();
				JSONObject jsonSting = jsonStings.getJSONObject(i);
				book.setTitulo(jsonSting.getString("titulo"));
				book.setAutor(jsonSting.getString("autor"));
				book.setBookid(jsonSting.getString("bookid"));
				book.setEdicion(jsonSting.getString("edicion"));
				book.setEditorial(jsonSting.getString("editorial"));
				jsonLinks = jsonSting.getJSONArray("links");
				parseLinks(jsonLinks, book.getLinks());
				books.getBooks().add(book);
			}
		} catch (IOException e) {
			throw new BookAndroidException(
					"Can't get response from Books API Web Service");
		} catch (JSONException e) {
			throw new BookAndroidException("Error parsing Books Root API");
		}

		return books;
	}

	public Book getBook(URL urlBook) throws BookAndroidException {
		Book book = new Book();

		HttpURLConnection urlConnection = null;
		try {
			URL url =urlBook;
			urlConnection = (HttpURLConnection) url.openConnection();
			urlConnection.setRequestMethod("GET");
			urlConnection.setDoInput(true);
			urlConnection.connect();
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					urlConnection.getInputStream()));
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line);
			}

			JSONObject jsonSting = new JSONObject(sb.toString());
			book.setAutor(jsonSting.getString("autor"));
			book.setTitulo(jsonSting.getString("title"));
			book.setBookid(jsonSting.getString("bookid"));
			book.setEdicion(jsonSting.getString("edicion"));
			book.setEditorial(jsonSting.getString("editorial"));
			JSONArray jsonLinks = jsonSting.getJSONArray("links");
			parseLinks(jsonLinks, book.getLinks());
		} catch (MalformedURLException e) {
			Log.e(TAG, e.getMessage(), e);
			throw new BookAndroidException("Bad sting url");
		} catch (IOException e) {
			Log.e(TAG, e.getMessage(), e);
			throw new BookAndroidException("Exception when getting the sting");
		} catch (JSONException e) {
			Log.e(TAG, e.getMessage(), e);
			throw new BookAndroidException("Exception parsing response");
		}

		return book;
	}

}
