package edu.upc.eetac.dsa.nmendo.books.android;

import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.util.ArrayList;

import edu.upc.eetac.dsa.nmendo.books.android.BooksMainActivity;
import edu.upc.eetac.dsa.nmendo.books.android.R;
import edu.upc.eetac.dsa.nmendo.books.android.api.BooksAPI;
import edu.upc.eetac.dsa.nmendo.books.android.api.BookAndroidException;
import edu.upc.eetac.dsa.nmendo.books.android.api.Book;
import edu.upc.eetac.dsa.nmendo.books.android.api.BookAdapter;
import edu.upc.eetac.dsa.nmendo.books.android.api.BooksCollection;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;

@TargetApi(Build.VERSION_CODES.CUPCAKE)
public class BooksMainActivity extends ListActivity {

	private class FetchStingsTask extends
			AsyncTask<Void, Void, BooksCollection> {
		private ProgressDialog pd;

		@Override
		protected BooksCollection doInBackground(Void... params) {
			BooksCollection books = null;
			try {
				books = BooksAPI.getInstance(BooksMainActivity.this).getBooks(
						null);
			} catch (BookAndroidException e) {
				e.printStackTrace();
			}
			return books;
		}

		@Override
		protected void onPostExecute(BooksCollection result) {
			addBooks(result);
			if (pd != null) {
				pd.dismiss();
			}
		}

		@Override
		protected void onPreExecute() {
			pd = new ProgressDialog(BooksMainActivity.this);
			pd.setTitle("Searching...");
			pd.setCancelable(false);
			pd.setIndeterminate(true);
			pd.show();
		}

	}

	private final static String TAG = BooksMainActivity.class.toString();
	// private static final String[] items = { "lorem", "ipsum", "dolor", "sit",
	// "amet", "consectetuer", "adipiscing", "elit", "morbi", "vel",
	// "ligula", "vitae", "arcu", "aliquet", "mollis", "etiam", "vel",
	// "erat", "placerat", "ante", "porttitor", "sodales", "pellentesque",
	// "augue", "purus" };
	// private ArrayAdapter<String> adapter;
	private ArrayList<Book> bookList;
	private BookAdapter adapter;

	/*
	 * /** Called when the activity is first created.
	 * 
	 * @Override public void onCreate(Bundle savedInstanceState) {
	 * super.onCreate(savedInstanceState);
	 * setContentView(R.layout.beeter_layout); adapter = new
	 * ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
	 * setListAdapter(adapter); }
	 */

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.initial);
		bookList = new ArrayList<>();
		adapter = new BookAdapter(this, bookList);
		setListAdapter((ListAdapter) adapter);
		(new FetchStingsTask()).execute();
	}

	private void addBooks(BooksCollection books) {
		bookList.addAll(books.getBooks());
		adapter.notifyDataSetChanged();
	}

	/*
	 * @Override protected void onListItemClick(ListView l, View v, int
	 * position, long id) { Sting sting = stingList.get(position); Log.d(TAG,
	 * sting.getLinks().get("self").getTarget()); }
	 */

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		Book book = bookList.get(position);
		Log.d(TAG, book.getLinks().get("self").getTarget());
		Intent intent = new Intent(this, BookDetailActivity.class);
		intent.putExtra("url", book.getLinks().get("self").getTarget());
		startActivity(intent);
	}

	// AÑADIR MENÚ punto 3
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.books_action, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.tvLibro:
			Intent intent = new Intent(this, BookDetailActivity.class);
			startActivity(intent);
			return true;

		default:
			return super.onOptionsItemSelected(item);
		}

	}

}

/*
 * /** Called when the activity is first created.
 * 
 * @Override public void onCreate(Bundle savedInstanceState) {
 * super.onCreate(savedInstanceState); setContentView(R.layout.initial); }
 */

