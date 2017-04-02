package com.wakeupinc.hpandroid;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;
import org.xmlpull.v1.XmlPullParserException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class PublicacionFull extends AppCompatActivity {
public LinearLayout lv;
    public String imgurl;
    public String correo;
    public String contra;
    public EditText comenta;
    public String idp;
    public int cuantoscomment=0;
    public String nombre;
    public String nombreusr;
    public int longitudname=0;
    public String imgusr;
    public String idusuario;
    public LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupActionBar();
        Bundle recibe = new Bundle();
        recibe = this.getIntent().getExtras();
        idp=recibe.getString("idp");
        String descrip = recibe.getString("descrip");
        nombre=recibe.getString("names");
        correo=recibe.getString("mail");
        contra=recibe.getString("contra");
        imgurl=recibe.getString("urlimg");
        imgusr=recibe.getString("imgusr");
        nombreusr=recibe.getString("nombreusr");
        longitudname=recibe.getInt("longitudname");
        setContentView(R.layout.activity_publicacion_full);
        lv=(LinearLayout)findViewById(R.id.publicacionlv);
        comenta=(EditText)findViewById(R.id.editTextcomenta);
        comenta.setImeActionLabel("Responde", KeyEvent.KEYCODE_ENTER);
        comenta.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    // Perform action on key press
                    new Comentar(comenta.getText().toString()).execute("");
                    comenta.setText("");
                    return true;
                }
                return false;
            }
        });
        if(lv!=null) {
            lv.removeAllViewsInLayout();
        }
        final String nombrefull2=nombre;
        final SpannableStringBuilder sb = new SpannableStringBuilder(nombrefull2);
        final StyleSpan bss = new StyleSpan(android.graphics.Typeface.BOLD); // Span to make text bold
        final RelativeSizeSpan iss = new RelativeSizeSpan(1.1f);
        final RelativeSizeSpan iss2 = new RelativeSizeSpan(0.9f);

        sb.setSpan(bss, 0, longitudname, Spannable.SPAN_INCLUSIVE_INCLUSIVE); // make n characters bold
        sb.setSpan(iss, 0, longitudname, Spannable.SPAN_INCLUSIVE_INCLUSIVE); // make n characters bigger
        sb.setSpan(iss2, longitudname, nombrefull2.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        Resources r = getResources();
        int px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 40, r.getDisplayMetrics());
///////Imagen
        final de.hdodenhof.circleimageview.CircleImageView circleImageView=new de.hdodenhof.circleimageview.CircleImageView(getApplicationContext());
        new DownloadImageTask(circleImageView)
                .execute("http://"+Config.serverUrl+"/HPNet/UsrImagenes/" + imgurl);
        RelativeLayout.LayoutParams paramsimgview = new RelativeLayout.LayoutParams(px,px);
        //paramsimgview.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        circleImageView.setLayoutParams(paramsimgview);

        ///Nombre
        TextView nameTextView = new TextView(getApplicationContext());
        LinearLayout.LayoutParams params1 =new  LinearLayout.LayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, px));
        nameTextView.setText(sb);
        nameTextView.setTextColor(Color.BLACK);
        nameTextView.setPadding(10, 0, 0, 0);
        nameTextView.setLayoutParams(params1);

//////////////////Descripcion
        TextView rowTextView = new TextView(getApplicationContext());
        rowTextView.setText(descrip);
        rowTextView.setTextColor(Color.BLACK);
        rowTextView.setTextSize(2, 15);
        rowTextView.setBackgroundColor(Color.WHITE);

        params.setMargins(10, 0, 10, 0);
        rowTextView.setPadding(20, 0, 10, 10);
        rowTextView.setLayoutParams(params);





//                final SpannableStringBuilder sb = new SpannableStringBuilder(nombrefull+"\n"+result[1][k]);
//
//                final StyleSpan bss = new StyleSpan(android.graphics.Typeface.BOLD); // Span to make text bold
//                final RelativeSizeSpan iss = new RelativeSizeSpan(1.1f); //Span to make text italic
//                sb.setSpan(bss, 0, nombrefull.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE); // make first 4 characters Bold
//                sb.setSpan(iss, 0, nombrefull.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE); // make first 4 bigger
//rowTextView.setText(sb);

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        LinearLayout l = new LinearLayout( getApplicationContext());
        l.addView(circleImageView);
        l.addView(nameTextView);
//                l.addView(rowTextView);
        l.setBackgroundColor(Color.WHITE);
        layoutParams.setMargins(10, 10, 10, 0);
        l.setPadding(10, 10, 10, 10);
        l.setBackground(getResources().getDrawable(R.drawable.border_rad));
//                l.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
        lv.addView(l, layoutParams);
        lv.addView(rowTextView);

        new bajacomentarios(idp).execute(idp);


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
            Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray , 0, byteArray.length);
            bmImage.setImageBitmap(bitmap);


        }
    }

    private class bajacomentarios extends AsyncTask<String, Void, String[][]> {
        String[][] comentarios=null;
        String idp=null;

        public bajacomentarios(String idp) {
            this.idp=idp;
        }

        protected String[][] doInBackground(String... urls) {
            try {
                comentarios=WebService.comentarios(idp, correo, contra, "0", "comentarios");
            } catch (IOException e) {
                e.printStackTrace();
            } catch (XmlPullParserException e) {
                e.printStackTrace();
            }
            return comentarios;
        }

        protected void onPostExecute(final String[][] result) {

if(result!=null) {
for (int i = 0; i < result[0].length; i++) {
        final LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        layoutParams2.setMargins(10, 0, 10, 0);
        final LinearLayout l = new LinearLayout(getApplicationContext());

        /////// Imagen Comentarista
        final de.hdodenhof.circleimageview.CircleImageView circleImageView2 = new de.hdodenhof.circleimageview.CircleImageView(getApplicationContext());
        new DownloadImageTask(circleImageView2)
                .execute("http://"+Config.serverUrl+"/HPNet/UsrImagenes/" + result[2][i]);
        RelativeLayout.LayoutParams paramsimgview = new RelativeLayout.LayoutParams(35, 35);
        //paramsimgview.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        circleImageView2.setLayoutParams(paramsimgview);
        //////Name comentarista
        final TextView namecomentarista = new TextView(getApplicationContext());
        namecomentarista.setTypeface(null, Typeface.BOLD);
        namecomentarista.setText(result[1][i]);
        namecomentarista.setBackgroundColor(Color.WHITE);
        params.setMargins(2, 0, 10, 0);
        namecomentarista.setLayoutParams(params);
        namecomentarista.setTextColor(getResources().getColor(R.color.colorAccent));
        /////Comentarios
        final TextView comentariosview = new TextView(getApplicationContext());
        comentariosview.setText(result[0][i]);
        comentariosview.setBackgroundColor(Color.WHITE);
        params.setMargins(10, 0, 10, 0);
        comentariosview.setLayoutParams(params);
        comentariosview.setPadding(20, 0, 10, 10);
        comentariosview.setTextColor(Color.BLACK);
        comentariosview.setBackground(getResources().getDrawable(R.drawable.bordesdown));
        l.addView(circleImageView2);
        l.addView(namecomentarista);
        l.setBackground(getResources().getDrawable(R.drawable.bordesup));
        l.setPadding(10, 10, 10, 10);
        lv.addView(l, layoutParams2);
        lv.addView(comentariosview);
    cuantoscomment++;
    }
    cuantoscomment=(cuantoscomment * 2)+2;
}
lv.addView(comenta);
        }
    }

    private class Comentar extends AsyncTask<String, Void, String> {
        String descrip="";
        public Comentar(String descripcion) {
            this.descrip=descripcion;
        }

        protected String doInBackground(String... urls) {
            try {
                WebService.hazcomentario(correo, contra, descrip, idp,"hazcomentario");
            } catch (IOException e) {
                e.printStackTrace();
            } catch (XmlPullParserException e) {
                e.printStackTrace();
            }
            return "hola";
        }

        protected void onPostExecute(String result) {
            final LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            layoutParams2.setMargins(10, 0, 10, 0);
            final LinearLayout l = new LinearLayout(getApplicationContext());

            /////// Imagen Comentarista
            final de.hdodenhof.circleimageview.CircleImageView circleImageView2 = new de.hdodenhof.circleimageview.CircleImageView(getApplicationContext());
            new DownloadImageTask(circleImageView2)
                    .execute("http://"+Config.serverUrl+"/HPNet/UsrImagenes/" + imgusr);
            RelativeLayout.LayoutParams paramsimgview = new RelativeLayout.LayoutParams(35, 35);
            //paramsimgview.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
            circleImageView2.setLayoutParams(paramsimgview);
            //////Name comentarista
            final TextView namecomentarista = new TextView(getApplicationContext());
            namecomentarista.setTypeface(null, Typeface.BOLD);
            namecomentarista.setText(nombreusr);
            namecomentarista.setBackgroundColor(Color.WHITE);
            params.setMargins(2, 0, 10, 0);
            namecomentarista.setLayoutParams(params);
            namecomentarista.setTextColor(getResources().getColor(R.color.colorAccent));
            /////Comentarios
            final TextView comentariosview = new TextView(getApplicationContext());
            comentariosview.setText(descrip);
            comentariosview.setBackgroundColor(Color.WHITE);
            params.setMargins(10, 0, 10, 0);
            comentariosview.setLayoutParams(params);
            comentariosview.setPadding(20, 0, 10, 10);
            comentariosview.setTextColor(Color.BLACK);
            comentariosview.setBackground(getResources().getDrawable(R.drawable.bordesdown));
            l.addView(circleImageView2);
            l.addView(namecomentarista);
            l.setBackground(getResources().getDrawable(R.drawable.bordesup));
            l.setPadding(10, 10, 10, 10);
            lv.addView(l,cuantoscomment, layoutParams2);
            lv.addView(comentariosview,cuantoscomment+1);
        }
    }
    private void setupActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            // Show the Up button in the action bar.
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }
    @Override
    public void onBackPressed() {

        super.onBackPressed();
        overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            super.onBackPressed();
            overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
