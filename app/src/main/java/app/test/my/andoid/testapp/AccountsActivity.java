package app.test.my.andoid.testapp;

import android.animation.ObjectAnimator;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class AccountsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_accounts);

        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        recyclerView.setAdapter(new RecyclerAdapter());
    }

    class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> {

        private final Handler handler;
        List<AccountModel> list = new ArrayList<>();

        RecyclerAdapter () {
            list.add(new AccountModel("Saving Wallet", 49.95, 36.0, false));
            list.add(new AccountModel("Main Wallet", 2049.95, 32.0, true));
            list.add(new AccountModel("Rainy Wallet", 249.95, 45.0, false));
            list.add(new AccountModel("Summer Wallet", 29.95, 89.0, true));
            list.add(new AccountModel("Winter Wallet", 209.95, 32.0, true));

            handler = new Handler(Looper.getMainLooper()) {
                @Override
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);
                }
            };

        }


        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
            View view = LayoutInflater.from(AccountsActivity.this)
                    .inflate(R.layout.row_account_list, parent, false);
            return  new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull final MyViewHolder holder, int i) {

            final AccountModel model = list.get(i);

            holder.progressBar.setVisibility(View.GONE);
            holder.circularProgress.setVisibility(View.VISIBLE);
            holder.tvTitle.setText(model.getName());
            String amount = String.format("%.2f", model.getAmount());
            holder.tvAmount.setText("$"+amount);

            String percent = String.format("%.0f", model.getPercentage());
            if (model.isPositive()) {
                holder.tvPercent.setText("+\n"+ percent + " %");
                int greenColor = ContextCompat.getColor(AccountsActivity.this, R.color.green_color);
                holder.tvPercent.setTextColor(greenColor);
                holder.progressBar.getProgressDrawable().setColorFilter(greenColor, PorterDuff.Mode.SRC_IN);
                holder.circularProgress.getIndeterminateDrawable().setColorFilter(greenColor, PorterDuff.Mode.SRC_IN);
            } else {
                int redColor = ContextCompat.getColor(AccountsActivity.this, R.color.red_color);
                holder.tvPercent.setText("-\n"+ percent + " %");
                holder.tvPercent.setTextColor(redColor);
                holder.progressBar.getProgressDrawable().setColorFilter(redColor, PorterDuff.Mode.SRC_IN);
                holder.circularProgress.getIndeterminateDrawable().setColorFilter(redColor, PorterDuff.Mode.SRC_IN);
            }

            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    holder.progressBar.setVisibility(View.VISIBLE);
                    holder.circularProgress.setVisibility(View.GONE);
                    setProgressMax(holder.progressBar, 100);
                    setProgressAnimate(holder.progressBar, (int) model.getPercentage());
                }
            }, 2000);
        }


        private void setProgressMax(ProgressBar pb, int max) {
            pb.setMax(max * 100);
        }

        private void setProgressAnimate(ProgressBar pb, int progressTo) {
            ObjectAnimator animation = ObjectAnimator.ofInt(pb, "progress", pb.getProgress(), progressTo * 100);
            animation.setDuration(2000);
            animation.setInterpolator(new DecelerateInterpolator());
            animation.start();
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder {

            TextView tvTitle;
            TextView tvAmount;
            TextView tvPercent;
            ProgressBar circularProgress;
            ProgressBar progressBar;

            public MyViewHolder(View view) {
                super(view);

                tvTitle = view.findViewById(R.id.tvTitle);
                tvAmount = view.findViewById(R.id.tvAmount);
                tvPercent = view.findViewById(R.id.tvPercent);
                progressBar = view.findViewById(R.id.progressBar);
                circularProgress = view.findViewById(R.id.circularProgress);
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
