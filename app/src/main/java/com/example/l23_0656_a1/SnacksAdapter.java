package com.example.l23_0656_a1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;

public class SnacksAdapter extends ArrayAdapter<Snacks> {

    private Context context;
    private ArrayList<Snacks> snackList;

    public SnacksAdapter(Context context, ArrayList<Snacks> snackList) {
        super(context, 0, snackList);
        this.context = context;
        this.snackList = snackList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_snack, parent, false);
        }

        Snacks snack = snackList.get(position);

        ImageView imgSnack = convertView.findViewById(R.id.imgSnack);
        TextView txtName = convertView.findViewById(R.id.txtSnackName);
        TextView txtPrice = convertView.findViewById(R.id.txtSnackPrice);
        TextView txtQty = convertView.findViewById(R.id.txtSnackQty);
        Button btnPlus = convertView.findViewById(R.id.btnSnackPlus);
        Button btnMinus = convertView.findViewById(R.id.btnSnackMinus);

        imgSnack.setImageResource(snack.getImageResId());
        txtName.setText(snack.getName());
        txtPrice.setText(String.format("$%.2f", snack.getPrice()));
        txtQty.setText(String.valueOf(snack.getQuantity()));

        btnPlus.setOnClickListener(v -> {
            snack.setQuantity(snack.getQuantity() + 1);
            txtQty.setText(String.valueOf(snack.getQuantity()));
        });

        btnMinus.setOnClickListener(v -> {
            if (snack.getQuantity() > 0) {
                snack.setQuantity(snack.getQuantity() - 1);
                txtQty.setText(String.valueOf(snack.getQuantity()));
            }
        });

        return convertView;
    }
}