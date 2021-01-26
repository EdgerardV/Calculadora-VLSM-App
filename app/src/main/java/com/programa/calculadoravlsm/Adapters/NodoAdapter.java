package com.programa.calculadoravlsm.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.programa.calculadoravlsm.Database.NodoDatabase.NodoDataHolder;
import com.programa.calculadoravlsm.Database.NodoDatabase.NodosDatabaseHelper;
import com.programa.calculadoravlsm.EditNodoActivity;
import com.programa.calculadoravlsm.EditProyectActivity;
import com.programa.calculadoravlsm.R;

import java.util.List;

public class NodoAdapter extends BaseAdapter{
	private Context context;
	private int list_item_layout;
	private List<NodoDataHolder> Nodos;
	private LayoutInflater inflater;
	private EditNodoActivity activity;

	public NodoAdapter(Context _context, int _list_item_layout, List<NodoDataHolder> _Nodos){
		this.context = _context;
		this.Nodos = _Nodos;
		this.list_item_layout = _list_item_layout;
		inflater = (LayoutInflater)((Activity) context).getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		return Nodos.size();
	}

	@Override
	public Object getItem(int posicion) {
		return Nodos.get(posicion);
	}

	@Override
	public long getItemId(int posicion) {
		//return Integer.parseInt(Nodos.get(posicion).getId());
		return Nodos.indexOf(Nodos.get(posicion));
	}

	@Override
	public View getView(final int posicion, View contentView, final ViewGroup parent) {
		final NodoAdapterHolder nodoAdapterHolder;
		final NodoDataHolder nodoDataHolder = this.Nodos.get(posicion);

		if(contentView == null){
			contentView = inflater.inflate(list_item_layout,null);
			nodoAdapterHolder = new NodoAdapterHolder();
		}else{
			nodoAdapterHolder = (NodoAdapterHolder)contentView.getTag();
		}

		nodoAdapterHolder.nombreNodo_TxtView = contentView.findViewById(R.id.textView_NombreNodo_ListItemNodo);
		nodoAdapterHolder.cantidadNodos_TxtView = contentView.findViewById(R.id.textView_CantidadNodos_ListItemNodo);
		nodoAdapterHolder.ipInicial_TxtView = contentView.findViewById(R.id.textView_IpInicial_ListItemNodo);
		nodoAdapterHolder.ipFinal_TxtView = contentView.findViewById(R.id.textView_IpFinal_ListItemNodo);
		nodoAdapterHolder.mask_TxtView = contentView.findViewById(R.id.textView_Mascara_ListItemNodo);
		nodoAdapterHolder.edit_ImageButton = contentView.findViewById(R.id.imageButton_editNodo);

		contentView.setTag(nodoAdapterHolder);

		nodoAdapterHolder.nombreNodo_TxtView.setSelected(true);
		nodoAdapterHolder.nombreNodo_TxtView.setText(nodoDataHolder.getDescripcion());
		nodoAdapterHolder.cantidadNodos_TxtView.setText(Integer.toString(nodoDataHolder.getCantidadNodos()));
		nodoAdapterHolder.ipInicial_TxtView.setText(nodoDataHolder.getIpInicialString());
		nodoAdapterHolder.ipFinal_TxtView.setText(nodoDataHolder.getIpFinalString());
		nodoAdapterHolder.mask_TxtView.setText(Integer.toString(nodoDataHolder.getMascara()));

		nodoAdapterHolder.edit_ImageButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(NodoAdapter.this.context, EditNodoActivity.class);
				intent.putExtra(context.getString(R.string.iddelNodoAEditar),nodoDataHolder.getId());
				context.startActivity(intent);
			}
		});

		return contentView;
	}

	static class NodoAdapterHolder{
		public TextView nombreNodo_TxtView, cantidadNodos_TxtView, ipInicial_TxtView, ipFinal_TxtView, mask_TxtView;
		public ImageButton edit_ImageButton;
/*
		public NodoAdapterHolder(TextView nombreNodo,TextView cantidadNodos, TextView ipInicial,TextView ipFinal, TextView mask, ImageButton edit,ImageButton delete){
			this.nombreNodo_TxtView = nombreNodo;
			this.cantidadNodos_TxtView = cantidadNodos;
			this.ipInicial_TxtView = ipInicial;
			this.ipFinal_TxtView = ipFinal;
			this.mask_TxtView = mask;
			this.edit_ImageButton = edit;
			this.deleteImageButton = delete;
		}*/
	}
}
