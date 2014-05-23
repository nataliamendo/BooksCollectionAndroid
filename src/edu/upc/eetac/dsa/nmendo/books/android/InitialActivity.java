package edu.upc.eetac.dsa.nmendo.books.android;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import edu.upc.eetac.dsa.nmendo.books.android.BooksMainActivity;
import edu.upc.eetac.dsa.nmendo.books.android.R;

public class InitialActivity  extends Activity {
	private final static String TAG = InitialActivity.class.getName();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d(TAG, "onCreate()");
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		SharedPreferences prefs = getSharedPreferences("books-profile",
				Context.MODE_PRIVATE);
		String username = prefs.getString("username", null);
		String password = prefs.getString("password", null);
		// Uncomment the next two lines to test the application without login
		// each time
		// username = "alicia";
		// password = "alicia";
		if ((username != null) && (password != null)) {
			Intent intent = new Intent(this, BooksMainActivity.class);
			startActivity(intent);
			finish();
		}
		setContentView(R.layout.initial);
	}

	public void searchIt(View v) {
		EditText etTitulo = (EditText) findViewById(R.id.tvLibro);
		EditText etAutor = (EditText) findViewById(R.id.tvAutor);
		//EditText etPassword = (EditText) findViewById(R.id.etPassword);

		String contentToSearch = etTitulo.getText().toString();
		
		//String password = etPassword.getText().toString();

		// Launch a background task to check if credentials are correct
		// If correct, store username and password and start Beeter activity
		// else, handle error

		String username = "alicia";
		String password = "alicia";
		// I'll suppose that u/p are correct:
		SharedPreferences prefs = getSharedPreferences("books-profile",
				Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = prefs.edit();
		editor.clear();
		editor.putString("username", username);
		editor.putString("password", password);
		boolean done = editor.commit();
		if (done)
			Log.d(TAG, "preferences set");
		else
			Log.d(TAG, "preferences not set. THIS A SEVERE PROBLEM");

		startBookActivity();
	}

	private void startBookActivity() {
		Intent intent = new Intent(this, BooksMainActivity.class);
		startActivity(intent);
		finish();
	}

}
