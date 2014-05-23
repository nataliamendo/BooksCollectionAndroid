package edu.upc.eetac.dsa.nmendo.books.android.api;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import edu.upc.eetac.dsa.nmendo.books.android.R;
import edu.upc.eetac.dsa.nmendo.books.android.api.Book;


public class BookAdapter extends BaseAdapter{
	ArrayList<Book> data;
	LayoutInflater inflater;

	private static class ViewHolder {
		TextView tvLibro;
		TextView tvAutor;
		TextView tvEditorial;
	}

	public BookAdapter(Context context, ArrayList<Book> data) {
		super();
		inflater = LayoutInflater.from(context);
		this.data = data;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return data.size();
	}

	@Override
	public Object getItem(int posicion) {
		// TODO Auto-generated method stub
		return data.get(posicion);
	}


	@Override
	public long getItemId(int position) {
		return Long.parseLong(String.valueOf(((Book)getItem(position)).getBookid()));
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		ViewHolder viewHolder = null;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.initial, null);

			viewHolder = new ViewHolder();
		
			viewHolder.tvLibro = (TextView) convertView
					.findViewById(R.id.tvLibro);
			viewHolder.tvAutor= (TextView) convertView
					.findViewById(R.id.tvAutor);
			viewHolder.tvEditorial = (TextView) convertView
					.findViewById(R.id.tvEditorial);

			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		String Libro = data.get(position).getTitulo();
		String Autor = data.get(position).getAutor();
		String Editorial = data.get(position).getEditorial();
		viewHolder.tvLibro.setText(Libro);
		viewHolder.tvAutor.setText(Autor);
		viewHolder.tvEditorial.setText(Editorial);
		return convertView;
	}


}

	