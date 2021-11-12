package com.example.producelist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    TextView idView;
    EditText productBox;
    EditText priceBox;
    Button viewProductsBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        idView = (TextView) findViewById(R.id.productID);
        productBox = (EditText) findViewById(R.id.productName);
        priceBox = (EditText) findViewById(R.id.productPrice);
        viewProductsBtn = (Button) findViewById(R.id.viewAll);

    }
    public void newProduct (View view) {
        MyDBHandler dbHandler = new MyDBHandler(this);

        double price = Double.parseDouble(priceBox.getText().toString());

        Product product = new Product(productBox.getText().toString(), price);

        dbHandler.addProduct(product);
        idView.setText("Record Added");



    }

    public void lookupProduct (View view) {
        MyDBHandler dbHandler = new MyDBHandler(this);

        Product product = dbHandler.findProduct(productBox.getText().toString());

        if (product != null) {
            idView.setText(String.valueOf(product.getID()));
            priceBox.setText(String.valueOf(product.getPrice()));
        } else {
            idView.setText("no Match Found");
        }
    }

    public void removeProduct (View view) {
        MyDBHandler dbHandler = new MyDBHandler(this);

        boolean result = dbHandler.deleteProduct(productBox.getText().toString());

        if (result) {
            idView.setText("Record Deleted");
            productBox.setText("");
            priceBox.setText("");
        } else {
            idView.setText("No Match Found");
        }
    }

    public void viewProducts(View view) {
        setContentView(R.layout.activity_display_product);

        ArrayList<Product> productArrayList = new ArrayList<>();
        MyDBHandler dbHandler = new MyDBHandler(this);

        productArrayList = dbHandler.readProducts();

        ProductAdapter productAdapter = new ProductAdapter(productArrayList, this);

        RecyclerView productsRV = findViewById(R.id.idProductDisplay);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        productsRV.setLayoutManager(linearLayoutManager);

        productsRV.setAdapter(productAdapter);
    }
}