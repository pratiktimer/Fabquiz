package google.com.fabquiz.recyclerviewsearch;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import google.com.fabquiz.Internet.TestApplication;
import google.com.fabquiz.Login.Login;
import google.com.fabquiz.Login.User;
import google.com.fabquiz.R;
import online.Category.GetCategory;

public class MainActivity extends AppCompatActivity implements ContactsAdapter.ContactsAdapterListener {
    private static final String TAG = MainActivity.class.getSimpleName();
    HashSet<String> h = new HashSet<String>();
    private RecyclerView recyclerView;
    private List<User> contactList;
    private List<User> contactList2;
    private ContactsAdapter mAdapter;
    private SearchView searchView;
    DatabaseReference postRef;
    ArrayList<String> alContacts;
    ArrayList<String> alContacts2;
    public static User contact2;
    FirebaseFirestore mFirestore;

    // url to fetch contacts json
    private static final String URL = "https://onlinequiz-7035a.firebaseio.com/Users.json";
    Cursor cursor ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (android.os.Build.VERSION.SDK_INT <=23) {
            setTheme(R.style.Theme_AppCompat_NoActionBar);

        }
        else{
            setTheme(R.style.AppTheme);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // toolbar fancy stuff
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.toolbar_title);
        if (android.os.Build.VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.bgrules));
        }
      //  toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.back);
        alContacts = new ArrayList<String>();
        alContacts2 = new ArrayList<String>();
        recyclerView = findViewById(R.id.recycler_view);
        contactList = new ArrayList<>();
        contactList2 = new ArrayList<>();
        mAdapter = new ContactsAdapter(this, contactList, this);

        // white background notification bar
       // whiteNotificationBar(recyclerView);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new MyDividerItemDecoration(this, DividerItemDecoration.VERTICAL, 36));
        recyclerView.setAdapter(mAdapter);
        GetContactsIntoArrayList();
        fetchContacts();
    }
    public void updateStatusBarColor(String color) {
        if (android.os.Build.VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(Color.parseColor(color));
        }
    }
    /**
     * fetches json by making http calls
     */
    private void fetchContacts() {
        JsonArrayRequest request = new JsonArrayRequest(URL,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        if (response == null) {
                            Toast.makeText(getApplicationContext(), "Couldn't fetch the contacts! Pleas try again.", Toast.LENGTH_LONG).show();
                            return;
                        }

                        List<User> row = new Gson().fromJson(response.toString(), new TypeToken<List<User>>() {
                        }.getType());
                        contactList.clear();
                        List<User> filteredList = new ArrayList<>();
                        int p = alContacts.size()-1;
                        while (p>0) {
                            for (User row2 : row) {

                                if (row2.getPhonenumber().equals(alContacts.get(p))) {
                                    filteredList.add(row2);
                                }
                            }
                            p--;
                        }
//                        user2id();
                        contactList.addAll(filteredList);
                        mAdapter.notifyDataSetChanged();

//                        int p = alContacts.size()-1;
//                        Iterator<String> i = h.iterator();
//                        while (i.hasNext()){
//                            if (row.get(17).getPhone().equals(i.next())) {
//
//                                contactList.add(row.get(17));
//
//                            }
//                        }


//        int q=row.size()-1;
//              // Toast.makeText(MainActivity.this,""+alContacts.get(p),Toast.LENGTH_SHORT).show();


                            // name match condition. this might differ depending on your requirement
                            // here we are looking for name or phone number match



                        // adding contacts to contacts list
                      //  contactList.clear();
                      //  contactList.addAll(contactList);

                        // refreshing recycler view
                      //  mAdapter.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // error in getting json
                Log.e(TAG, "Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(), "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        TestApplication.getInstance().addToRequestQueue(request);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        // Associate searchable configuration with the SearchView
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.action_search)
                .getActionView();
        searchView.setSearchableInfo(searchManager
                .getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);

        // listening to search query text change
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // filter recycler view when query submitted
                mAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                // filter recycler view when text is changed
                mAdapter.getFilter().filter(query);
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {
            return true;
        }
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        // close search view on back button pressed
        if (!searchView.isIconified()) {
            searchView.setIconified(true);
            return;
        }
        super.onBackPressed();
    }

//    private void whiteNotificationBar(View view) {
////        if (android.os.Build.VERSION.SDK_INT >= 21) {
////            Window window = getActivity().getWindow();
////            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
////            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
////            window.setStatusBarColor(this.getResources().getColor(R.color.bgrules));
////        }
//    }

    @Override
    public void onContactSelected(User contact) {

  //      Toast.makeText(MainActivity.this,""+ContactsAdapter.user2id,Toast.LENGTH_SHORT).show();
//        Toast.makeText(getApplicationContext(), "Selected: " + contact.getFullname()+ ", " + contact.getPhonenumber(), Toast.LENGTH_LONG).show();

      contact2=contact;
        FirebaseAuth mAuth= FirebaseAuth.getInstance();
        if(mAuth.getCurrentUser()!=null) {
           // user2id();
            Intent conceptIntent = new Intent(MainActivity.this, GetCategory.class);
            conceptIntent.putExtra("position", 2);
            conceptIntent.putExtra("multiplayers", 0);
            startActivity(conceptIntent);
            finish();
        }
        else{
            startActivity(new Intent(MainActivity.this, Login.class));
        }
    }

//    private void user2id() {
//        mFirestore= FirebaseFirestore.getInstance();
//        mFirestore.collection("Users").addSnapshotListener(MainActivity.this, new EventListener<QuerySnapshot>() {
//            @Override
//            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
//                for(DocumentChange doc:documentSnapshots.getDocumentChanges()){
//                    if(doc.getType()==DocumentChange.Type.ADDED){
//                      String  user2id=doc.getDocument().getId();
//                      //Toast.makeText(MainActivity.this,""+user2id,Toast.LENGTH_SHORT).show();
//                      User users=doc.getDocument().toObject(User.class).withId(user2id);
//                      contactList.add(users);
//
//                    }
//
//                }
//                mAdapter.notifyDataSetChanged();
//            }
//        });
//    }

    public void GetContactsIntoArrayList(){
        alContacts.clear();
        contactList2.clear();
        cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,null, null, null);
        while (cursor.moveToNext()) {

            String contactnumber = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();
            try {
                // phone must begin with '+'
                Phonenumber.PhoneNumber numberProto = phoneUtil.parse(contactnumber, "IN");
                String usE164 = phoneUtil.format(numberProto,PhoneNumberUtil.PhoneNumberFormat.E164);           //+12025550100
                String countryCode = String.valueOf(numberProto.getCountryCode());
                usE164.concat(countryCode);

                if(!alContacts.contains(usE164)) {
                    alContacts.add(usE164);
                }
                if(!h.contains(usE164)) {
                    h.add(usE164);
                }
            } catch (NumberParseException e) {
                System.err.println("NumberParseException was thrown: " + e.toString());
            }




        }

        cursor.close();

//        int p = alContacts.size()-1;
//        while (p >= 0)
//
//        {
//            //Toast.makeText(MainActivity.this, " " + p, Toast.LENGTH_LONG).show();
//            smfuc(alContacts.get(p));
//            p--;
//        }

    }


}
