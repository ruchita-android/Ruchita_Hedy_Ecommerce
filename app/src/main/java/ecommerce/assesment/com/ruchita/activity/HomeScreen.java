package ecommerce.assesment.com.ruchita.activity;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import ecommerce.assesment.com.ruchita.AppController;
import ecommerce.assesment.com.ruchita.BaseActivity;
import ecommerce.assesment.com.ruchita.R;
import ecommerce.assesment.com.ruchita.adapter.CategoryAdapter;
import ecommerce.assesment.com.ruchita.adapter.ProductAdapter;
import ecommerce.assesment.com.ruchita.adapter.RankingAdapter;
import ecommerce.assesment.com.ruchita.models.Category;
import ecommerce.assesment.com.ruchita.models.Product;
import ecommerce.assesment.com.ruchita.models.Product_;
import ecommerce.assesment.com.ruchita.models.Ranking;
import ecommerce.assesment.com.ruchita.models.ResultBean;
import ecommerce.assesment.com.ruchita.serverutil.ApiClient;
import ecommerce.assesment.com.ruchita.serverutil.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by root on 30/1/18.
 */

public class HomeScreen extends BaseActivity {

    private RecyclerView recyclerView;
    private RecyclerView catTypeList;
    private CategoryAdapter categoryAdapter;
    private ProductAdapter productAdapter;
    private List<Product> products;
    private TextView tvNoProd;
    private ProgressBar progressBar;
    private LinearLayout linearLayout;
    private List<Category> catList;
    private List<Ranking> rankList;
    private HashMap<Long, Product> productIdHashMap;
    private TextView tvFilter;
    private LinearLayout llFilters;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        GridLayoutManager mLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        catTypeList = (RecyclerView) findViewById(R.id.catTypeList);
        LinearLayoutManager mLayoutManager1 = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        catTypeList.setLayoutManager(mLayoutManager1);
        catTypeList.setItemAnimator(new DefaultItemAnimator());

        linearLayout = (LinearLayout) findViewById(R.id.linearLayout);
        llFilters = (LinearLayout) findViewById(R.id.llFilters);
        tvNoProd = (TextView) findViewById(R.id.tvNoProd);
        tvFilter = (TextView) findViewById(R.id.tvFilter);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        getDataFromServer();

    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_home;
    }

    @Override
    protected String getToolbarTitle() {
        return getString(R.string.home_screen);
    }

    @Override
    protected void doActionOnHomeButton() {
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        switch (item.getItemId()) {
            case R.id.filter: {
                if(rankList!=null) {
                    PopupWindow popup = showRankingList();
                    popup.showAsDropDown(findViewById(item.getItemId()), 0, 0);
                    return true;
                }
            }
        }
        return super.onOptionsItemSelected(item);
    }


    private void getDataFromServer() {
        if (AppController.getInstance().isConnected(this)) {
            progressBar.setVisibility(View.VISIBLE);
            getProductDataFromUrl();
        } else {
            progressBar.setVisibility(View.GONE);
            Snackbar.make(linearLayout, R.string.check_net, Snackbar.LENGTH_INDEFINITE).setAction(R.string.retry, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getDataFromServer();
                }
            }).show();
        }
    }

    private void getProductDataFromUrl() {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        Call<ResultBean> call = apiService.getAllData();

        call.enqueue(new Callback<ResultBean>() {
            @Override
            public void onResponse(Call<ResultBean> call, Response<ResultBean> response) {

                Log.d("json response >>>", response.toString());

                progressBar.setVisibility(View.GONE);

                if (response != null) {
                    ResultBean resultBean = response.body();
                    if (resultBean != null) {
                        catList = resultBean.getCategories();
                        rankList = resultBean.getRankings();

                        categoryAdapter = new CategoryAdapter(HomeScreen.this, catList, new CategoryAdapter.CategorySelect() {
                            @Override
                            public void onItemClick(int selectedPosition) {

                                List<Product> products = catList.get(selectedPosition).getProducts();

                                if (productAdapter != null && products.size() > 0) {
                                    productAdapter = new ProductAdapter(HomeScreen.this, products, false);
                                    recyclerView.setAdapter(productAdapter);
                                    recyclerView.setVisibility(View.VISIBLE);
                                    tvNoProd.setVisibility(View.GONE);

                                } else {
                                    recyclerView.setVisibility(View.GONE);
                                    tvNoProd.setVisibility(View.VISIBLE);
                                }
                            }
                        });

                        catTypeList.setAdapter(categoryAdapter);

                        if (catList != null && catList.size() > 0) {
                            products = catList.get(0).getProducts();
                            productAdapter = new ProductAdapter(HomeScreen.this, products, false);
                            recyclerView.setAdapter(productAdapter);
                        }
                        llFilters.setVisibility(View.GONE);
                        catTypeList.setVisibility(View.VISIBLE);

                        productIdHashMap = new HashMap<>();

//                        Getting all products for ranking.
                        for (Category c : catList) {
                            for (Product p : c.getProducts()) {
                                productIdHashMap.put(p.getId(), p);
                            }
                        }

                    }
                }
            }

            @Override
            public void onFailure(Call<ResultBean> call, Throwable t) {
                Log.d("json response >>>", "fail");
                progressBar.setVisibility(View.GONE);
            }
        });

    }

    private PopupWindow showRankingList() {
        final PopupWindow popupWindow = new PopupWindow(this);

        RecyclerView rankfilterview = new RecyclerView(this);
        LinearLayoutManager mLayoutManager1 = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        rankfilterview.setLayoutManager(mLayoutManager1);
        rankfilterview.setItemAnimator(new DefaultItemAnimator());


        RankingAdapter rankingAdapter = new RankingAdapter(this, rankList, new RankingAdapter.RankingSelect() {
            @Override
            public void onItemClick(int selectedPosition) {
                getProductsByRank(selectedPosition);
                popupWindow.dismiss();
            }
        });

        rankfilterview.setAdapter(rankingAdapter);

        popupWindow.setFocusable(true);
        popupWindow.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        popupWindow.setContentView(rankfilterview);
        return popupWindow;
    }


    private void getProductsByRank(int position) {
        List<Product_> products = rankList.get(position).getProducts();

        if (rankList.get(position).getRanking().equalsIgnoreCase("Most Viewed Products"))
            Collections.sort(products, new ViewCountSort());
        else if (rankList.get(position).getRanking().equalsIgnoreCase("Most OrdeRed Products"))
            Collections.sort(products, new OrderCountSort());
        else if (rankList.get(position).getRanking().equalsIgnoreCase("Most Viewed Products"))
            Collections.sort(products, new SharesSort());

        List<Product> rankedProducts = new ArrayList<Product>();

        for (Product_ rankProd : products) {
            Product product = productIdHashMap.get(rankProd.getId());
            product.setRankProduct(rankProd);
            rankedProducts.add(product);
        }

        if (productAdapter != null && rankedProducts.size() > 0) {
            productAdapter = new ProductAdapter(HomeScreen.this, rankedProducts, true);
            recyclerView.setAdapter(productAdapter);
            recyclerView.setVisibility(View.VISIBLE);
            tvNoProd.setVisibility(View.GONE);
            catTypeList.setVisibility(View.GONE);
            llFilters.setVisibility(View.VISIBLE);
            tvFilter.setText(rankList.get(position).getRanking());
        } else {
            recyclerView.setVisibility(View.GONE);
            tvNoProd.setVisibility(View.VISIBLE);
            llFilters.setVisibility(View.GONE);
        }
    }


    public class ViewCountSort implements Comparator<Product_> {
        @Override
        public int compare(Product_ p1, Product_ p2) {
            return p1.getViewCount().compareTo(p2.getViewCount());
        }
    }

    public class OrderCountSort implements Comparator<Product_> {
        @Override
        public int compare(Product_ p1, Product_ p2) {
            return p1.getOrderCount().compareTo(p2.getOrderCount());
        }
    }

    public class SharesSort implements Comparator<Product_> {
        @Override
        public int compare(Product_ p1, Product_ p2) {
            return p1.getShares().compareTo(p2.getShares());
        }
    }

    public void clearFilter(View v) {
        llFilters.setVisibility(View.GONE);
        catTypeList.setVisibility(View.VISIBLE);
        if (catList != null && catList.size() > 0) {
            categoryAdapter.selectedPosition = 0;
            products = catList.get(0).getProducts();
            productAdapter = new ProductAdapter(HomeScreen.this, products, false);
            recyclerView.setAdapter(productAdapter);
            categoryAdapter.notifyDataSetChanged();

        }


    }
}


