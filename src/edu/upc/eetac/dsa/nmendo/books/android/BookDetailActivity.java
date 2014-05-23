package edu.upc.eetac.dsa.nmendo.books.android;

import java.net.MalformedURLException;
import java.net.URL;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;
import edu.upc.eetac.dsa.nmendo.books.android.api.Book;
import edu.upc.eetac.dsa.nmendo.books.android.api.BookAndroidException;
import edu.upc.eetac.dsa.nmendo.books.android.api.BooksAPI;

public class BookDetailActivity extends Activity {
	public final static String Tag = BookDetailActivity.class.toString();
	public BooksAPI api;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.book_detail_layout);
		URL urlBook = null;
		try {
			urlBook = new URL((String) getIntent().getExtras().get("url"));
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		(new FetchBookTask()).execute(urlBook);
	}

	private void loadBook(Book libro) {
		TextView tvLibro = (TextView) findViewById(R.id.tvLibro);
		TextView tvAutor = (TextView) findViewById(R.id.tvAutor);

		tvLibro.setText(libro.getTitulo());
		tvAutor.setText(libro.getAutor());

	}

	private class FetchBookTask extends AsyncTask<URL, Void, Book> {
		private ProgressDialog pd;

		@Override
		protected Book doInBackground(URL... params) {
			Book book = null;
			URL url = params[0];
			try {

				book = BooksAPI.getInstance(BookDetailActivity.this).getBook(url);
			} catch (BookAndroidException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return book;
		}

		@Override
		protected void onPostExecute(Book result) {
			loadBook(result);
			if (pd != null) {
				pd.dismiss();
			}
		}

		@Override
		protected void onPreExecute() {
			pd = new ProgressDialog(BookDetailActivity.this);
			pd.setTitle("Loading...");
			pd.setCancelable(false);
			pd.setIndeterminate(true);
			pd.show();
		}

	}

}