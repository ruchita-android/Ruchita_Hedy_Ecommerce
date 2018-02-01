package ecommerce.assesment.com.ruchita.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.List;

import ecommerce.assesment.com.ruchita.BaseActivity;
import ecommerce.assesment.com.ruchita.R;
import ecommerce.assesment.com.ruchita.adapter.VariantsAdapter;
import ecommerce.assesment.com.ruchita.models.Product;
import ecommerce.assesment.com.ruchita.models.Variant;


/**
 * Created by root on 31/1/18.
 */

public class ProductDescription extends BaseActivity {

    private Product product;
    private TextView tvProdName;
    private RecyclerView variantList;
    private TextView tvprice;
    private TextView tvTax;
    private TextView tvSize;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bun= getIntent().getExtras();
        if(bun!=null)
            product = (Product)bun.getSerializable("product");


        tvProdName = (TextView)findViewById(R.id.tvProdName);
        tvprice = (TextView)findViewById(R.id.tvprice);
        tvTax = (TextView)findViewById(R.id.tvTax);
        tvSize = (TextView)findViewById(R.id.tvSize);

        variantList = (RecyclerView) findViewById(R.id.variantList);
        LinearLayoutManager mLayoutManager1 = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        variantList.setLayoutManager(mLayoutManager1);
        variantList.setItemAnimator(new DefaultItemAnimator());

        updateUIwithData();

    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_product;
    }

    @Override
    protected String getToolbarTitle() {
        return getString(R.string.product_screen);
    }

    @Override
    protected void doActionOnHomeButton() {
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        menu.findItem(R.id.filter).setVisible(false);
    return true;
    }


    private void updateUIwithData(){
        tvProdName.setText(product.getName());
        tvTax.setText(product.getTax().getName() +" : Rs. " +product.getTax().getValue());

        final List<Variant> variants = product.getVariants();

        if(variants.get(0).getSize()!=null) {
            tvSize.setVisibility(View.VISIBLE);
            tvSize.setText("Size : "+variants.get(0).getSize());
        }else
            tvSize.setVisibility(View.GONE);

        tvprice.setText("Price : Rs. "+ variants.get(0).getPrice());



        VariantsAdapter adapter= new VariantsAdapter(variants, new VariantsAdapter.ItemSelected() {
            @Override
            public void onItemClick(Variant variant) {

                tvprice.setText("Price : Rs. "+ variant.getPrice());

                if(variant.getSize()!=null) {
                    tvSize.setVisibility(View.VISIBLE);
                    tvSize.setText("Size : "+variant.getSize());
                }else
                    tvSize.setVisibility(View.GONE);

            }
        });

        variantList.setAdapter(adapter);

    }

}
