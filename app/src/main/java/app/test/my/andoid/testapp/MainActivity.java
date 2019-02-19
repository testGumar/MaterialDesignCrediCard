package app.test.my.andoid.testapp;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(mViewPager, true);

        mViewPager.setPageTransformer(false, new PageTransformer());

        ImageView imgProfile = findViewById(R.id.imgProfile);

        imgProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, AccountsActivity.class));
            }
        });

        setupRecyclerView();
    }

    private void setupRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        recyclerView.setAdapter(new RecyclerAdapter());
    }


    public static class PageTransformer implements ViewPager.PageTransformer {
        @Override
        public void transformPage(View page, float position) {

            // this part changes the scale, which is the zoom part
            page.setScaleX(1.0F - .15F * Math.abs(position));
            page.setScaleY(1.0F - .15F * Math.abs(position));

            // this part sets up so the page zooms from the center
            page.setPivotX(page.getWidth() / 2.0F);
            page.setPivotY(page.getHeight() / 2.0F);
        }
    }


   /* @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_card, container, false);
//            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
//            textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
            return rootView;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }
    }

    class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> {

        private final Handler handler;
        List<TransactionModel> list = new ArrayList<>();

        RecyclerAdapter () {

            list.add(new TransactionModel("Sony Playstation", "Fifa 2022 Game", "-$53.95", "March 14, 2021", false));
            list.add(new TransactionModel("Bank Transfer", "Salary For March", "$2053.45", "April 14, 2021", true));
            list.add(new TransactionModel("Coffee Shop", "Fifa 2022 Game", "-$53.95", "March 14, 2021", false));
            list.add(new TransactionModel("Sony Playstation", "Fifa 2022 Game", "-$53.95", "March 14, 2021", false));
            list.add(new TransactionModel("Flat Rental", "Fifa 2022 Game", "-$53.95", "March 14, 2021", false));

            handler = new Handler(Looper.getMainLooper()) {
                @Override
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);
                }
            };

        }


        @NonNull
        @Override
        public RecyclerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
            View view = LayoutInflater.from(MainActivity.this)
                    .inflate(R.layout.row_transaction_list, parent, false);
            return  new RecyclerAdapter.MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull final RecyclerAdapter.MyViewHolder holder, int i) {

            final TransactionModel model = list.get(i);

            holder.tvTitle.setText(model.getName());
            holder.tvDescription.setText(model.getDescription());
            holder.tvDate.setText(model.getDate());
            holder.tvAmount.setText(model.getAmount());

            if (model.isPositive) {
                holder.tvAmount.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.red_color));
            } else {
                holder.tvAmount.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.green_color));
            }
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
            TextView tvDescription;
            TextView tvAmount;
            TextView tvDate;

            MyViewHolder(View view) {
                super(view);

                tvTitle = view.findViewById(R.id.tvTitle);
                tvDescription = view.findViewById(R.id.tvDescription);
                tvAmount = view.findViewById(R.id.tvAmount);
                tvDate = view.findViewById(R.id.tvDate);
            }
        }
    }

}
