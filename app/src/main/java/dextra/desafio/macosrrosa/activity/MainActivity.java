package dextra.desafio.macosrrosa.activity;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;
import dextra.desafio.macosrrosa.R;
import dextra.desafio.macosrrosa.adapter.RepositoriesAdapter;
import dextra.desafio.macosrrosa.adapter.UsersAdapter;
import dextra.desafio.macosrrosa.bean.Repository;
import dextra.desafio.macosrrosa.bean.User;
import dextra.desafio.macosrrosa.bean.WsResponse;
import dextra.desafio.macosrrosa.util.Webservice;

/**
 * Main Activity
 * @author Marcos Rafael Rosa (marcos.fael@gmail.com)
 */

public class MainActivity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private static SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private static ViewPager mViewPager;

    private static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        handleIntent(getIntent());
        context = this;

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

    }

    @Override
    protected void onNewIntent(Intent intent) {
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            if (mViewPager.getCurrentItem() == 0) {
                getUsers(query);
                mViewPager.setCurrentItem(0);
            } else if (mViewPager.getCurrentItem() == 1) {
                getRepos(query);
                mViewPager.setCurrentItem(1);
            }
        }
    }

    private void getUsers(final String query) {
        PlaceholderFragment f = (PlaceholderFragment) mSectionsPagerAdapter.getItem(0);
        f.setVisibleProgressBar(0, View.VISIBLE);

        AsyncTask<Void, Void, Void> mWSTask = new AsyncTask<Void, Void, Void>() {
            private WsResponse wsResponse = null;
            @Override
            protected Void doInBackground(Void... params) {
                wsResponse = Webservice.get(context.getString(R.string.url_ws_user) + query);
                return null;
            }
            @Override
            protected void onPostExecute(Void result) {
                callBackGetUsers(wsResponse);
            }
        };
        mWSTask.execute(null, null, null);// execute AsyncTask
    }
    private void callBackGetUsers(WsResponse wsResponse) {
        PlaceholderFragment f = (PlaceholderFragment) mSectionsPagerAdapter.getItem(0);

        if ( wsResponse.getJsonObject() != null &&  wsResponse.getJsonObject().has("items")) {
            List<User> listUsers = new ArrayList<User>();
            try {
                JSONArray jArrayUsers =  wsResponse.getJsonObject().getJSONArray("items");
                for ( int i=0; i < jArrayUsers.length(); i++ ) {
                    JSONObject jUser = jArrayUsers.getJSONObject(i);
                    listUsers.add(new User(jUser.getString("login"),
                                           jUser.getString("avatar_url"),
                                           jUser.getString("html_url")));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            f.updateUserList(listUsers);
            mSectionsPagerAdapter.notifyDataSetChanged();

        } else {
            Toast.makeText(this, getString(R.string.search_no_data), Toast.LENGTH_SHORT).show();
        }
        f.setVisibleProgressBar(0, View.GONE);
        mViewPager.setCurrentItem(0);
    }

    private void getRepos(final String query) {
        PlaceholderFragment f = (PlaceholderFragment) mSectionsPagerAdapter.getItem(1);
        f.setVisibleProgressBar(1, View.VISIBLE);

        AsyncTask<Void, Void, Void> mWSTask = new AsyncTask<Void, Void, Void>() {
            private WsResponse wsResponse = null;
            @Override
            protected Void doInBackground(Void... params) {
                wsResponse = Webservice.get(context.getString(R.string.url_ws_repo) + query);
                return null;
            }
            @Override
            protected void onPostExecute(Void result) {
                callBackGetRepos(wsResponse);
            }
        };
        mWSTask.execute(null, null, null);// execute AsyncTask
    }
    private void callBackGetRepos(WsResponse wsResponse) {
        PlaceholderFragment f = (PlaceholderFragment) mSectionsPagerAdapter.getItem(1);

        if ( wsResponse.getJsonObject() != null &&  wsResponse.getJsonObject().has("items")) {
            List<Repository> listRepos = new ArrayList<Repository>();
            try {
                JSONArray jArrayRepos =  wsResponse.getJsonObject().getJSONArray("items");
                for ( int i=0; i < jArrayRepos.length(); i++ ) {
                    JSONObject jRepo = jArrayRepos.getJSONObject(i);
                    listRepos.add(new Repository(jRepo.getString("name"),
                                                jRepo.getString("full_name"),
                                                jRepo.getString("html_url")));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            f.updateRepoList(listRepos);
            mSectionsPagerAdapter.notifyDataSetChanged();

        } else {
            Toast.makeText(this, getString(R.string.search_no_data), Toast.LENGTH_SHORT).show();
        }
        f.setVisibleProgressBar(1, View.GONE);
        mViewPager.setCurrentItem(1);
    }

    public static void gotoUrl(String url) {
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        context.startActivity(i);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        // Associate searchable configuration with the SearchView
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        MenuItem searchMenuItem = menu.findItem( R.id.search );
        searchMenuItem.expandActionView(); // Expand the search menu item in order to show by default the query

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
            Snackbar.make(mViewPager, getString(R.string.about_app), Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            this.finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        //The fragment argument representing the section number for this fragment.
        private static final String ARG_SECTION_NUMBER = "section_number";
        //RecyclerView, to use Cards on Listview

        //private static List<RecyclerView.Adapter> adapters = new ArrayList<RecyclerView.Adapter>();
        private static RecyclerView recyclerViewUser;
        private static RecyclerView recyclerViewRepo;
        private static View rootViewUser = null;
        private static View rootViewRepo = null;

        private static List<Repository> repos;
        private static List<User> users;

        public PlaceholderFragment() {}

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber-1);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            //To Get Id Tab => getArguments().getInt(ARG_SECTION_NUMBER

            //View rootView = inflater.inflate(R.layout.fragment_main, container, false);

            if (getArguments().getInt(ARG_SECTION_NUMBER) == 0) {
                if (rootViewUser == null) {
                    rootViewUser = inflater.inflate(R.layout.fragment_main, container, false);;
                    recyclerViewUser = (RecyclerView) rootViewUser.findViewById(R.id.recycler_view);
                    recyclerViewUser.setLayoutManager(new LinearLayoutManager(getActivity()));
                } else {
                    return rootViewUser;
                }
            } else if (getArguments().getInt(ARG_SECTION_NUMBER) == 1) {
                if (rootViewRepo == null) {
                    rootViewRepo = inflater.inflate(R.layout.fragment_main, container, false);
                    recyclerViewRepo = (RecyclerView) rootViewRepo.findViewById(R.id.recycler_view);
                    recyclerViewRepo.setLayoutManager(new LinearLayoutManager(getActivity()));
                } else {
                    return rootViewRepo;
                }
            }
            return null;
        }

        public void setVisibleProgressBar(int tab, int v) {
            if (tab==0 && rootViewUser != null) {
                ProgressBar loading = (ProgressBar) rootViewUser.findViewById(R.id.loading);
                loading.setVisibility(v);
            } else if (tab==1 && rootViewRepo != null) {
                ProgressBar loading = (ProgressBar) rootViewRepo.findViewById(R.id.loading);
                loading.setVisibility(v);
            }
        }

        public void updateUserList(List<User> users) {
            this.users = users;
            RecyclerView.Adapter userAdapter = new UsersAdapter(rootViewUser.getContext(), users, new UsersAdapter.UserOnClickListener() {
                @Override
                public void onClick(View view, int idx) {gotoUrlUser(idx);}
                @Override
                public void onLongClick(View itemView, int position) {}
            });
            recyclerViewUser.setAdapter(userAdapter);
            recyclerViewUser.setItemAnimator(new DefaultItemAnimator());
            userAdapter.notifyDataSetChanged();
        }
        private void gotoUrlUser(int pos) {
            MainActivity.gotoUrl(users.get(pos).getUrlPage());
        }

        public void updateRepoList(List<Repository> repos) {
            this.repos = repos;

            RecyclerView.Adapter repoAdapter = new RepositoriesAdapter(rootViewRepo.getContext(), repos, new RepositoriesAdapter.RepoOnClickListener() {
                @Override
                public void onClick(View view, int idx) {gotoUrlRepo(idx);}
                @Override
                public void onLongClick(View itemView, int position) {}
            });
            recyclerViewRepo.setAdapter(repoAdapter);
            recyclerViewRepo.setItemAnimator(new DefaultItemAnimator());
            repoAdapter.notifyDataSetChanged();
        }
        private void gotoUrlRepo(int pos) {
            MainActivity.gotoUrl(repos.get(pos).getUrlPage());
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
            // Show 2 total pages.
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return getString(R.string.title_tab_users);
                case 1:
                    return getString(R.string.title_tab_repos);
            }
            return null;
        }
    }
}
