package com.wakeupinc.hpandroid;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.app.AlertDialog;
import android.text.InputType;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Scroller;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.android.gms.gcm.GoogleCloudMessaging;

import org.xmlpull.v1.XmlPullParserException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private int PICK_IMAGE_REQUEST = 1;
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private SearchView mSearchview;
    private ViewPager mViewPager;
    private TabLayout tablayout;
    private String correo;
    private String nombrefull;
    private String imgurl;
    private String contra;
    private TextView vistacorreo;
    private TextView vistanombre;
    String[][] publicaciones = new String[50][50];
    String[][] contactos = new String[50][50];
    String[][] alertas = new String[50][50];
    static LinearLayout[] myLinearLayout = null;
    public int currentPage = 0;
    boolean yacargocontactos = false;
    boolean yacargomedicamentos = false;
    boolean yacargoalertas =false;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            Bundle recibe = new Bundle();
            recibe = this.getIntent().getExtras();
            correo = recibe.getString("correo");
            nombrefull = recibe.getString("nombrefull");
            imgurl = recibe.getString("imgurl");
            contra = recibe.getString("contra");
        } else {
            correo = savedInstanceState.getString("correo");
            nombrefull = savedInstanceState.getString("nombrefull");
            imgurl = savedInstanceState.getString("imgurl");
            contra = savedInstanceState.getString("contra");
        }
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSearchview = (SearchView) findViewById(R.id.mySearchView);
        mSearchview.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                new bajapublicaciones("0", "0").execute("hola");
                return false;
            }
        });
        mSearchview.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                new bajapublicaciones("6", query).execute("hola");
                mViewPager.setCurrentItem(0);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
//              if (searchView.isExpanded() && TextUtils.isEmpty(newText)) {

//              }
                return true;
            }

        });
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.setOffscreenPageLimit(4);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                if (mViewPager.getCurrentItem() == 1 && !yacargocontactos) {
                    new bajacontactos().execute("hola");
                } else if (mViewPager.getCurrentItem() == 2 && !yacargomedicamentos) {
                    new bajamedicamentos().execute("hola");
                }
                else if (mViewPager.getCurrentItem()==3 && !yacargoalertas){
                    new traealertas().execute("hola");
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        tablayout = (TabLayout) findViewById(R.id.tabs);
        tablayout.setupWithViewPager(mViewPager);
        tablayout.getTabAt(0).setIcon(R.drawable.textwhite);

        tablayout.getTabAt(1).setIcon(R.drawable.userswhite);

        tablayout.getTabAt(2).setIcon(R.drawable.alarmwhite);

        tablayout.getTabAt(3).setIcon(R.drawable.ic_report_24dp);
        // ((EditText)((SearchView)findViewById(R.id.mySearchView)).findViewById(((SearchView)findViewById(R.id.mySearchView)).getContext().getResources().getIdentifier("android:id/search_src_text", null, null))).setHintTextColor(Color.WHITE);
        ((EditText) ((SearchView) findViewById(R.id.mySearchView)).findViewById(((SearchView) findViewById(R.id.mySearchView)).getContext().getResources().getIdentifier("android:id/search_src_text", null, null))).setTextColor(Color.WHITE);
        View header = navigationView.getHeaderView(0);
        vistacorreo = (TextView) header.findViewById(R.id.email123456);
        vistanombre = (TextView) header.findViewById(R.id.username);
        vistanombre.setText(nombrefull);
        vistacorreo.setText(correo);
        new DownloadImageTask((ImageView) header.findViewById(R.id.profile_image))
                .execute("http://"+Config.serverUrl+"/HPNet/UsrImagenes/" + imgurl);
        new bajapublicaciones("0", "0").execute("hola");
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }



    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.


        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        int id = item.getItemId();
        changePagerScroller();
        switch (id) {
            case R.id.contactos:
                mViewPager.setCurrentItem(1);
                drawer.closeDrawer(GravityCompat.START);
                break;
            case R.id.configuraciones:
                final Intent intent = new Intent(this, SettingsActivity.class);
                Bundle datos = new Bundle();
                datos.putString("correo", correo);
                datos.putString("nombrefull", nombrefull);
                datos.putString("imgurl", imgurl);
                datos.putString("contra", contra);
                intent.putExtras(datos);
                startActivity(intent);
                overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
                break;
            case R.id.blog:
                mViewPager.setCurrentItem(0);
                drawer.closeDrawer(GravityCompat.START);
                break;
            case R.id.medicamentos:
                mViewPager.setCurrentItem(2);
                drawer.closeDrawer(GravityCompat.START);
                break;
            case R.id.cerrarsesion:
                final Intent intent2 = new Intent(this, LoginActivity.class);
                intent2.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent2);
                finish();
                drawer.closeDrawer(GravityCompat.START);
                break;
            case R.id.notificaciones:
                mViewPager.setCurrentItem(3);
                drawer.closeDrawer(GravityCompat.START);
                //------------------------------------------------------------------------------------------------------------------------------
//                new DownloadImageTask((ImageView) findViewById(R.id.imageView123))
//                        .execute("http://192.168.0.4:8080/HPNet/UsrImagenes/"+imgurl);
//------------------------------------------------------------------------------------------------------------------------------
                break;
            case R.id.pulsera:
                final Intent intentP = new Intent(this, DeviceList.class);
                startActivity(intentP);
                overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
                break;
            case R.id.nav_share:
                Intent intent135 = new Intent();
// Show only images, no videos or anything else
                intent135.setType("image/*");
                intent135.setAction(Intent.ACTION_GET_CONTENT);
// Always show the chooser (if there are multiple options available)
                startActivityForResult(Intent.createChooser(intent135, "Select Picture"), PICK_IMAGE_REQUEST);
                break;

        }
        return true;
    }


    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentStatePagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            if (position == 0) {
                return PlaceholderFragment.newInstance();
            } else if (position == 1) {
                return PlaceholderFragment2.newInstance();
            } else if (position==2) {
                return PlaceholderFragment3.newInstance();
            }
            else {
                return PlaceholderFragment4.newInstance();
            }


        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 4;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "";
                case 1:
                    return "";
                case 2:
                    return "";
                case 3:
                    return "";
            }
            return null;
        }
    }

    public void actualizahome(View view) {
        new bajapublicaciones("0", "0").execute("hola");
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View header = navigationView.getHeaderView(0);
        new DownloadImageTask((ImageView) header.findViewById(R.id.profile_image))
                .execute("http://" + Config.serverUrl + "/HPNet/UsrImagenes/" + imgurl);
        new bajacontactos().execute("");
        new bajamedicamentos().execute("");
        new traealertas().execute("");

    }

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


        public static PlaceholderFragment newInstance() {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, 0);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            super.onCreateView(inflater, container, savedInstanceState);


            View rootView = inflater.inflate(R.layout.fragment_blog, container, false);
            return rootView;
        }
    }

    public static class PlaceholderFragment2 extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment2() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment2 newInstance() {
            PlaceholderFragment2 fragment = new PlaceholderFragment2();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, 1);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.contactosframe, container, false);
            return rootView;
        }
    }

    public static class PlaceholderFragment3 extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment3() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment3 newInstance() {
            PlaceholderFragment3 fragment = new PlaceholderFragment3();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, 2);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.notificaciones, container, false);

            return rootView;
        }
    }

    public static class PlaceholderFragment4 extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment4() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment4 newInstance() {
            PlaceholderFragment4 fragment = new PlaceholderFragment4();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, 3);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.alertas, container, false);
            return rootView;
        }
    }

    public class ViewPagerScroller extends Scroller {


        private int mScrollDuration = 300;

        public ViewPagerScroller(Context context) {
            super(context);
        }

        public ViewPagerScroller(Context context, Interpolator interpolator) {
            super(context, interpolator);
        }

        @Override
        public void startScroll(int startX, int startY, int dx, int dy, int duration) {
            super.startScroll(startX, startY, dx, dy, mScrollDuration);
        }

        @Override
        public void startScroll(int startX, int startY, int dx, int dy) {
            super.startScroll(startX, startY, dx, dy, mScrollDuration);
        }


    }

    private void changePagerScroller() {
        try {
            Field mScroller = null;
            mScroller = ViewPager.class.getDeclaredField("mScroller");
            mScroller.setAccessible(true);
            ViewPagerScroller scroller = new ViewPagerScroller(mViewPager.getContext());
            mScroller.set(mViewPager, scroller);
        } catch (Exception e) {
            Log.d("error ", "as");
        }
    }


    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            result.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byte[] byteArray = stream.toByteArray();
            Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
            bmImage.setImageBitmap(bitmap);


        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        // Save UI state changes to the savedInstanceState.
        // This bundle will be passed to onCreate if the process is
        // killed and restarted.
        savedInstanceState.putString("correo", correo);
        savedInstanceState.putString("nombrefull", nombrefull);
        savedInstanceState.putString("imgurl", imgurl);
        // etc.
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        correo = savedInstanceState.getString("correo");
        nombrefull = savedInstanceState.getString("nombrefull");
        imgurl = savedInstanceState.getString("imgurl");


    }

    private class bajapublicaciones extends AsyncTask<String, Void, String[][]> {
        public String opc = "";
        public String query = "";

        public bajapublicaciones(String opc, String query) {
            this.opc = opc;
            this.query = query;
        }

        protected String[][] doInBackground(String... urls) {
            try {
                publicaciones = WebService.publicaciones(opc, correo, contra, query, "Publicaciones");
            } catch (IOException e) {
                e.printStackTrace();
            } catch (XmlPullParserException e) {
                e.printStackTrace();
            }
            return publicaciones;
        }

        protected void onPostExecute(final String[][] result) {
            //bmtext.setText(result[0][0]+" "+result[1][0]+" "+result[2][0]+" "+result[3][0]+" "+result[4][0]);
            // bmtext.setText(""+result[0].length);
            final EditText postea = (EditText) findViewById(R.id.editText);
            final Button botonpostea = (Button) findViewById(R.id.botonpublica);
            postea.setImeActionLabel("Publica", KeyEvent.KEYCODE_ENTER);
            postea.setOnKeyListener(new View.OnKeyListener() {
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    // If the event is a key-down event on the "enter" button
                    if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                            (keyCode == KeyEvent.KEYCODE_ENTER)) {
                        // Perform action on key press
                        new Publica(postea.getText().toString()).execute("");
                        postea.setText("");
                        return true;
                    }
                    return false;
                }
            });

            botonpostea.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    new Publica(postea.getText().toString()).execute("");
                    postea.setText("");
                }
            });
            LinearLayout lv = (LinearLayout) findViewById(R.id.LinearLayout01);
            if (lv != null) {
                lv.removeAllViewsInLayout();
            }
            myLinearLayout = new LinearLayout[result[0].length];
            Resources r = getResources();
            int px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 40, r.getDisplayMetrics());
            //postea.setVisibility(View.GONE);
            lv.addView(postea);
            lv.addView(botonpostea);
            for (int k = 0; k < result[0].length; k++) {
                final String idmipersona=result[8][k];
                final String idpersona = result[0][k];
                final String nameamedias = result[2][k] + " " + result[3][k];
                final String nombrefull2 = result[2][k] + " " + result[3][k] + "\n" + result[4][k];
                final SpannableStringBuilder sb = new SpannableStringBuilder(nombrefull2);
                final StyleSpan bss = new StyleSpan(android.graphics.Typeface.BOLD); // Span to make text bold
                final RelativeSizeSpan iss = new RelativeSizeSpan(1.1f);
                final RelativeSizeSpan iss2 = new RelativeSizeSpan(0.9f);

                sb.setSpan(bss, 0, nameamedias.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE); // make first 4 characters Bold
                sb.setSpan(iss, 0, nameamedias.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE); // make first 4 bigger
                sb.setSpan(iss2, nameamedias.length(), nombrefull2.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);

                final String urlimgpost = result[5][k];
                final String idp = result[6][k];
///////Imagen
                final de.hdodenhof.circleimageview.CircleImageView circleImageView = new de.hdodenhof.circleimageview.CircleImageView(getApplicationContext());
                new DownloadImageTask(circleImageView)
                        .execute("http://"+Config.serverUrl+"/HPNet/UsrImagenes/" + result[5][k]);
                RelativeLayout.LayoutParams paramsimgview = new RelativeLayout.LayoutParams(px, px);
                //paramsimgview.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
                circleImageView.setLayoutParams(paramsimgview);

                ///Nombre
                final TextView nameTextView = new TextView(getApplicationContext());
                LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, px));
                nameTextView.setText(sb);
                nameTextView.setTextColor(Color.BLACK);
                nameTextView.setPadding(10, 0, 0, 0);
                nameTextView.setLayoutParams(params1);

//////////////////Descripcion
                final TextView rowTextView = new TextView(getApplicationContext());
                rowTextView.setText(result[1][k]);
                final String descripcion = result[1][k];
                rowTextView.setTextColor(Color.BLACK);
                rowTextView.setTextSize(2, 15);
                rowTextView.setBackgroundColor(Color.WHITE);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                params.setMargins(10, 0, 10, 0);
                rowTextView.setPadding(20, 0, 10, 10);
                rowTextView.setLayoutParams(params);
                rowTextView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent publicacionFull = new Intent(getApplicationContext(), PublicacionFull.class);
                        Bundle datospublicacion = new Bundle();
                        datospublicacion.putString("descrip", descripcion);
                        datospublicacion.putString("names", nombrefull2);
                        datospublicacion.putString("urlimg", urlimgpost);
                        datospublicacion.putString("imgusr", imgurl);
                        datospublicacion.putString("idp", idp);
                        datospublicacion.putString("mail", correo);
                        datospublicacion.putString("contra", contra);
                        datospublicacion.putInt("longitudname", nameamedias.length());
                        datospublicacion.putString("nombreusr", nombrefull);
                        datospublicacion.putString("idpersona", idpersona);
                        publicacionFull.putExtras(datospublicacion);
                        startActivity(publicacionFull);
                        overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
                    }
                });

                /////Comentarios
                final TextView comentariosview = new TextView(getApplicationContext());
                comentariosview.setText(result[7][k] + " Respuestas");
                comentariosview.setBackgroundColor(Color.WHITE);
                params.setMargins(10, 0, 10, 0);
                comentariosview.setLayoutParams(params);
                comentariosview.setPadding(20, 0, 10, 10);
                comentariosview.setTextColor(getResources().getColor(R.color.colorAccent));
                comentariosview.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent publicacionFull = new Intent(getApplicationContext(), PublicacionFull.class);
                        Bundle datospublicacion = new Bundle();
                        datospublicacion.putString("descrip", descripcion);
                        datospublicacion.putString("names", nombrefull2);
                        datospublicacion.putString("urlimg", urlimgpost);
                        datospublicacion.putString("imgusr", imgurl);
                        datospublicacion.putString("idp", idp);
                        datospublicacion.putString("mail", correo);
                        datospublicacion.putString("contra", contra);
                        datospublicacion.putInt("longitudname", nameamedias.length());
                        datospublicacion.putString("nombreusr", nombrefull);
                        datospublicacion.putString("idpersona", idpersona);
                        publicacionFull.putExtras(datospublicacion);
                        startActivity(publicacionFull);
                        overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
                    }
                });


//                final SpannableStringBuilder sb = new SpannableStringBuilder(nombrefull+"\n"+result[1][k]);
//
//                final StyleSpan bss = new StyleSpan(android.graphics.Typeface.BOLD); // Span to make text bold
//                final RelativeSizeSpan iss = new RelativeSizeSpan(1.1f); //Span to make text italic
//                sb.setSpan(bss, 0, nombrefull.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE); // make first 4 characters Bold
//                sb.setSpan(iss, 0, nombrefull.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE); // make first 4 bigger
//rowTextView.setText(sb);

                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                LinearLayout l = new LinearLayout(getApplicationContext());
                l.addView(circleImageView);
                l.addView(nameTextView);
                l.setLongClickable(true);
                if(idpersona.equals(idmipersona)) {

                    l.setOnLongClickListener(new View.OnLongClickListener() {
                        public boolean onLongClick(View arg0) {


                            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                            builder.setTitle("Seleccione una opción");
                            String[] options = {"Eliminar", "Responder"};
                            builder.setNegativeButton("Cancel", null);
                            builder.setItems(options, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    if (which == 0) {
                                        new eliminaPublicacion(idp).execute("");
                                    }
                                    else if (which==1){
                                        Intent publicacionFull = new Intent(getApplicationContext(), PublicacionFull.class);
                                        Bundle datospublicacion = new Bundle();
                                        datospublicacion.putString("descrip", descripcion);
                                        datospublicacion.putString("names", nombrefull2);
                                        datospublicacion.putString("urlimg", urlimgpost);
                                        datospublicacion.putString("imgusr", imgurl);
                                        datospublicacion.putString("idp", idp);
                                        datospublicacion.putString("mail", correo);
                                        datospublicacion.putString("contra", contra);
                                        datospublicacion.putInt("longitudname", nameamedias.length());
                                        datospublicacion.putString("nombreusr", nombrefull);
                                        datospublicacion.putString("idpersona", idpersona);
                                        publicacionFull.putExtras(datospublicacion);
                                        startActivity(publicacionFull);
                                        overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
                                    }
                                }
                            });
                            builder.show();

                            return true;    // <- set to true
                        }
                    });
                }
                else{
                    l.setOnLongClickListener(new View.OnLongClickListener() {
                        public boolean onLongClick(View arg0) {


                            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                            builder.setTitle("Seleccione una opción");
                            String[] options = {"Responder"};
                            builder.setNegativeButton("Cancel", null);
                            builder.setItems(options, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    if (which == 0) {
                                        Intent publicacionFull = new Intent(getApplicationContext(), PublicacionFull.class);
                                        Bundle datospublicacion = new Bundle();
                                        datospublicacion.putString("descrip", descripcion);
                                        datospublicacion.putString("names", nombrefull2);
                                        datospublicacion.putString("urlimg", urlimgpost);
                                        datospublicacion.putString("imgusr", imgurl);
                                        datospublicacion.putString("idp", idp);
                                        datospublicacion.putString("mail", correo);
                                        datospublicacion.putString("contra", contra);
                                        datospublicacion.putInt("longitudname", nameamedias.length());
                                        datospublicacion.putString("nombreusr", nombrefull);
                                        datospublicacion.putString("idpersona", idpersona);
                                        publicacionFull.putExtras(datospublicacion);
                                        startActivity(publicacionFull);
                                        overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
                                    }
                                }
                            });
                            builder.show();

                            return true;    // <- set to true
                        }
                    });
                }
//                l.addView(rowTextView);
                l.setBackgroundColor(Color.WHITE);
                layoutParams.setMargins(10, 10, 10, 0);
                l.setPadding(10, 10, 10, 10);
                l.setBackground(getResources().getDrawable(R.drawable.border_rad));
                l.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent publicacionFull = new Intent(getApplicationContext(), PublicacionFull.class);
                        Bundle datospublicacion = new Bundle();
                        datospublicacion.putString("descrip", descripcion);
                        datospublicacion.putString("names", nombrefull2);
                        datospublicacion.putString("urlimg", urlimgpost);
                        datospublicacion.putString("imgusr", imgurl);
                        datospublicacion.putString("idp", idp);
                        datospublicacion.putString("mail", correo);
                        datospublicacion.putString("contra", contra);
                        datospublicacion.putInt("longitudname", nameamedias.length());
                        datospublicacion.putString("nombreusr", nombrefull);
                        datospublicacion.putString("idpersona", idpersona);
                        publicacionFull.putExtras(datospublicacion);
                        startActivity(publicacionFull);
                        overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
                    }
                });
//                l.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                lv.addView(l, layoutParams);
                comentariosview.setBackground(getResources().getDrawable(R.drawable.border_rad_down));
                lv.addView(rowTextView);
                lv.addView(comentariosview);

                myLinearLayout[k] = lv;
            }


        }
    }

    private class Publica extends AsyncTask<String, Void, String> {
        String descrip = "";

        public Publica(String descripcion) {
            this.descrip = descripcion;
        }

        protected String doInBackground(String... urls) {
            try {
                WebService.hazpublicacion(correo, contra, descrip, "hazpublicacion");
            } catch (IOException e) {
                e.printStackTrace();
            } catch (XmlPullParserException e) {
                e.printStackTrace();
            }
            return "hola";
        }

        protected void onPostExecute(String result) {
            new bajapublicaciones("0", "0").execute("hola");
        }
    }


    private class bajacontactos extends AsyncTask<String, Void, String[][]> {

        public bajacontactos() {
        }

        protected String[][] doInBackground(String... urls) {
            try {
                contactos = WebService.contactos(correo, contra, "contactos");
            } catch (IOException e) {
                e.printStackTrace();
            } catch (XmlPullParserException e) {
                e.printStackTrace();
            }
            return contactos;
        }

        protected void onPostExecute(final String[][] result) {
            Resources r = getResources();
            int px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 40, r.getDisplayMetrics());
            LinearLayout lvd = (LinearLayout) findViewById(R.id.LinearLayoutcontacts);
            if (lvd != null) {
                lvd.removeAllViewsInLayout();
            }
            for (int k = 0; k < result[0].length; k++) {

                LinearLayout llnameTextView = new LinearLayout( getApplicationContext());
                LinearLayout.LayoutParams llp1 = new LinearLayout.LayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                llnameTextView.setGravity(Gravity.LEFT);
                LinearLayout lltb = new LinearLayout( getApplicationContext());
                LinearLayout.LayoutParams llp2 = new LinearLayout.LayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                lltb.setGravity(Gravity.RIGHT);

                //final String idpersona = result[0][k];
                final String nombrefull2 = result[2][k];
///////Imagen
                final de.hdodenhof.circleimageview.CircleImageView circleImageView = new de.hdodenhof.circleimageview.CircleImageView(getApplicationContext());
                new DownloadImageTask(circleImageView)
                        .execute("http://"+Config.serverUrl+"/HPNet/UsrImagenes/" + result[1][k]);
                RelativeLayout.LayoutParams paramsimgview = new RelativeLayout.LayoutParams(px, px);
                circleImageView.setLayoutParams(paramsimgview);

                ///Nombre
                final TextView nameTextView = new TextView(getApplicationContext());
                LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, px));
                nameTextView.setText(nombrefull2);
                nameTextView.setTypeface(Typeface.DEFAULT_BOLD);
                nameTextView.setTextColor(Color.BLACK);
                nameTextView.setPadding(10, 0, 0, 0);
                nameTextView.setLayoutParams(params1);

                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                LinearLayout l = new LinearLayout(getApplicationContext());
                llnameTextView.addView(circleImageView);
                llnameTextView.addView(nameTextView);
//                l.addView(rowTextView);

                //phone call icon


                final String telefonousr=result[3][k];
                final ImageView circleImageView2 = new ImageView(getApplicationContext());
                circleImageView2.setImageBitmap(drawableToBitmap(getResources().getDrawable(R.drawable.ic_call_24dp)));
                RelativeLayout.LayoutParams paramsimgview2 = new RelativeLayout.LayoutParams(px, px);
                circleImageView2.setLayoutParams(paramsimgview2);
                circleImageView2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + telefonousr));
                        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                            // TODO: Consider calling
                            return;
                        }
                        startActivity(intent);
                    }
                });
                lltb.addView(circleImageView2);

                l.setBackgroundColor(Color.WHITE);
                layoutParams.setMargins(10, 10, 10, 0);
                l.setPadding(10, 10, 10, 10);
                l.setBackground(getResources().getDrawable(R.drawable.border_rad_white));



                l.addView(llnameTextView, llp1);
                l.addView(lltb, llp2);
                lvd.addView(l, layoutParams);
        }
yacargocontactos=true;

        }
    }


    private class bajamedicamentos extends AsyncTask<String, Void, String[][]> {

        public bajamedicamentos() {
        }

        protected String[][] doInBackground(String... urls) {
            try {
                contactos=WebService.medicamentos(correo, contra, "traeMedicamentos");
            } catch (IOException e) {
                e.printStackTrace();
            } catch (XmlPullParserException e) {
                e.printStackTrace();
            }
            return contactos;
        }

        protected void onPostExecute(final String[][] result) {
            Resources r = getResources();
            int px = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 40, r.getDisplayMetrics());
            LinearLayout lvd = (LinearLayout) findViewById(R.id.LinearLayoutmedicamentos);
            if(lvd!=null) {
                lvd.removeAllViewsInLayout();
            }

            for (int k=0;k<result[0].length;k++){
                final String nombrefull2 = result[1][k];
                LinearLayout llnameTextView = new LinearLayout( getApplicationContext());
                LinearLayout.LayoutParams llp1 = new LinearLayout.LayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                llnameTextView.setGravity(Gravity.LEFT);
                LinearLayout lltb = new LinearLayout( getApplicationContext());
                LinearLayout.LayoutParams llp2 = new LinearLayout.LayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                lltb.setGravity(Gravity.RIGHT);
                ///Nombre
                final TextView nameTextView = new TextView(getApplicationContext());
                LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, px));

                nameTextView.setText(nombrefull2+"\nCada "+result[4][k]+" hrs");
                nameTextView.setTypeface(Typeface.DEFAULT_BOLD);
                nameTextView.setTextColor(Color.BLACK);
                nameTextView.setPadding(10, 0, 0, 0);
                nameTextView.setLayoutParams(params1);
                nameTextView.setGravity(Gravity.TOP);

                // add Toggle button
                LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, px));
                final ToggleButton tb = new ToggleButton(getApplicationContext());
                tb.setLayoutParams(params2);
                tb.setTextOn("Activar");
                tb.setTextOff("Desactivar");
                final boolean check=!result[5][k].equals("1");
                final String idrelusumedicamento=result[6][k];
                final String tipo=result[7][k];

                final String idmedicamento = result[0][k];
                tb.setChecked(check);
                tb.setGravity(Gravity.CENTER);
                tb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        new togglemedicamentos(idrelusumedicamento, tipo, idmedicamento).execute("");
                    }
                });

                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                LinearLayout l = new LinearLayout( getApplicationContext());
                l.setGravity(Gravity.LEFT);
                llnameTextView.addView(nameTextView);
                lltb.addView(tb);
                l.addView(llnameTextView, llp1);
                l.addView(lltb, llp2);
//                l.addView(rowTextView);
                l.setBackgroundColor(Color.WHITE);
                layoutParams.setMargins(10, 10, 10, 0);
                l.setPadding(10, 10, 10, 10);
                l.setBackground(getResources().getDrawable(R.drawable.border_rad_white));
                lvd.addView(l, layoutParams);
            }
            yacargomedicamentos=true;

        }
    }

    private class togglemedicamentos extends AsyncTask<String, Void, String> {
        String idrelusumedicamento="";
        String tipo="";
        String idmedicamento="";
        public togglemedicamentos(String idrelusumedicamento,String tipo,String idmedicamento) {

            this.idrelusumedicamento=idrelusumedicamento;
            this.idmedicamento=idmedicamento;
            this.tipo=tipo;
        }

        protected String doInBackground(String... urls) {
            try {
                WebService.togglemedicina(correo, contra,idrelusumedicamento,tipo,idmedicamento,"togglemedicamentos");
            } catch (IOException e) {
                e.printStackTrace();
            } catch (XmlPullParserException e) {
                e.printStackTrace();
            }
            return "hola";
        }

        protected void onPostExecute(String result) {

            Toast.makeText(getApplicationContext(),"Hecho",
                    Toast.LENGTH_SHORT).show();
        }
    }


    private class traealertas extends AsyncTask<String, Void, String[][]> {
        public traealertas() {
        }

        protected String[][] doInBackground(String... urls) {
            try {
                alertas=WebService.bajaalertas(correo, contra, "alertasu");
            } catch (IOException e) {
                e.printStackTrace();
            } catch (XmlPullParserException e) {
                e.printStackTrace();
            }
            return alertas;
        }

        protected void onPostExecute(final String[][] result) {
            Resources r = getResources();
            int px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 40, r.getDisplayMetrics());
            LinearLayout lvd = (LinearLayout) findViewById(R.id.LinearLayoutalertas);
            if (lvd != null) {
                lvd.removeAllViewsInLayout();
            }
            for (int k = 0; k < result[0].length; k++) {

                LinearLayout llnameTextView = new LinearLayout( getApplicationContext());
                LinearLayout.LayoutParams llp1 = new LinearLayout.LayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                llnameTextView.setGravity(Gravity.LEFT);
                LinearLayout lltb = new LinearLayout( getApplicationContext());
                LinearLayout.LayoutParams llp2 = new LinearLayout.LayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                lltb.setGravity(Gravity.RIGHT);
                final String nombrefull2 = "Su presión fue de "+result[2][k]+"ppm";
///////Imagen
                final de.hdodenhof.circleimageview.CircleImageView circleImageView = new de.hdodenhof.circleimageview.CircleImageView(getApplicationContext());
                new DownloadImageTask(circleImageView)
                        .execute("http://"+Config.serverUrl+"/HPNet/UsrImagenes/" + imgurl);
                RelativeLayout.LayoutParams paramsimgview = new RelativeLayout.LayoutParams(px, px);
                circleImageView.setLayoutParams(paramsimgview);

                ///Nombre
                final TextView nameTextView = new TextView(getApplicationContext());
                LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, px));
                nameTextView.setText(nombrefull2);
                nameTextView.setTypeface(Typeface.DEFAULT_BOLD);
                nameTextView.setTextColor(Color.BLACK);
                nameTextView.setPadding(10, 0, 0, 0);
                nameTextView.setLayoutParams(params1);

                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                LinearLayout l = new LinearLayout(getApplicationContext());
                llnameTextView.addView(circleImageView);
                llnameTextView.addView(nameTextView);
//                l.addView(rowTextView);

                l.setBackgroundColor(Color.WHITE);
                layoutParams.setMargins(10, 10, 10, 0);
                l.setPadding(10, 10, 10, 10);
                l.setBackground(getResources().getDrawable(R.drawable.border_rad_white));



                l.addView(llnameTextView, llp1);

                final String latitud=result[1][k];
                final String longitud=result[0][k];
                l.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(Intent.ACTION_VIEW,Uri.parse("geo:"+longitud+","+latitud+""));
                        i.setClassName("com.google.android.apps.maps",
                                "com.google.android.maps.MapsActivity");
                        startActivity(i);
                    }
                });
                lvd.addView(l, layoutParams);
            }
            yacargoalertas=true;

        }
    }



    public static Bitmap drawableToBitmap (Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable)drawable).getBitmap();
        }

        int width = drawable.getIntrinsicWidth();
        width = width > 0 ? width : 1;
        int height = drawable.getIntrinsicHeight();
        height = height > 0 ? height : 1;

        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);

        return bitmap;
    }

    public void addmedica (View view){
        Intent publicacionFull = new Intent(getApplicationContext(), AddMedica.class);
        Bundle datospublicacion = new Bundle();
        datospublicacion.putString("imgusr", imgurl);
        datospublicacion.putString("mail", correo);
        datospublicacion.putString("contra", contra);
        datospublicacion.putString("nombreusr", nombrefull);
        publicacionFull.putExtras(datospublicacion);
        startActivity(publicacionFull);
    }

    private class eliminaPublicacion extends AsyncTask<String, Void, String> {
        String idpublicacion;
        public eliminaPublicacion(String idpublicacion) {
            this.idpublicacion=idpublicacion;
        }

        protected String doInBackground(String... urls) {
            try {
                WebService.eliminapub(correo, contra, idpublicacion, "eliminapub");
            } catch (IOException e) {
                e.printStackTrace();
            } catch (XmlPullParserException e) {
                e.printStackTrace();
            }
            return "hola";
        }

        protected void onPostExecute(String result) {

            Toast.makeText(getApplicationContext(),"Hecho",
                    Toast.LENGTH_SHORT).show();
            new bajapublicaciones("0","0").execute("hola");
        }
    }
}

