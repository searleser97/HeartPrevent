package com.wakeupinc.hpandroid;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class Img_Perfil extends AppCompatActivity {
    private int PICK_IMAGE_REQUEST = 1;
    private static final int CAMERA_REQUEST = 1888;
    private ImageView imageView;
    private View checkmark;
    private Bitmap bitmap;
    private Button aceptar;
    private View mProgressView;
    protected UserLoginTask mAuthTask=null;
    private String correo;
    private String nombrefull;
    private String imgurl;
    private String contra;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_img__perfil);
        imageView = (ImageView) findViewById(R.id.img_act_imgperfil);
        aceptar = (Button) findViewById(R.id.button2);

        if(savedInstanceState != null) {
            Bitmap bitmap1 = savedInstanceState.getParcelable("image");
            imageView.setImageBitmap(bitmap1);
            aceptar.setVisibility(View.VISIBLE);
        }
        else{
            aceptar.setVisibility(View.INVISIBLE);
        }
        mProgressView = findViewById(R.id.login_progress2);
        checkmark = findViewById(R.id.gcheckmark);
        Bundle recibe = new Bundle();
        recibe = this.getIntent().getExtras();
        correo = recibe.getString("correo");
        nombrefull = recibe.getString("nombrefull");
        imgurl = recibe.getString("imgurl");
        contra=recibe.getString("contra");
    }



    public void seleccionaImg(View view){
        Intent intent135 = new Intent();
// Show only images, no videos or anything else
        intent135.setType("image/*");
        intent135.setAction(Intent.ACTION_GET_CONTENT);
// Always show the chooser (if there are multiple options available)
        startActivityForResult(Intent.createChooser(intent135, "Select Picture"), PICK_IMAGE_REQUEST);
    }



    public void lanzacamara(View view){
        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, CAMERA_REQUEST);
    }



    public void MandaIMG(View view){
        showProgress(true);
        mAuthTask= new UserLoginTask(bitmap);
        mAuthTask.execute((Void) null);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

            Uri uri = data.getData();

            try {
                 bitmap = Bitmap.createScaledBitmap(MediaStore.Images.Media.getBitmap(getContentResolver(), uri),1024,1024,true);
                imageView = (ImageView) findViewById(R.id.img_act_imgperfil);
                imageView.setImageBitmap(bitmap);
                aceptar.setVisibility(View.VISIBLE);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if(requestCode == CAMERA_REQUEST && resultCode == RESULT_OK ){
            Bundle extras = data.getExtras();
                bitmap = (Bitmap) extras.get("data");
                imageView = (ImageView) findViewById(R.id.img_act_imgperfil);
                imageView.setImageBitmap(bitmap);
            aceptar.setVisibility(View.VISIBLE);

        }
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if(imageView.getDrawable()!=null)
        {
            BitmapDrawable drawable = (BitmapDrawable) imageView.getDrawable();
            Bitmap bitmap = drawable.getBitmap();
            outState.putParcelable("image", bitmap);
            super.onSaveInstanceState(outState);
        }

    }
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
        }
    }
    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        private Bitmap bitas;

        UserLoginTask(Bitmap bit) {
            bitas=bit;
        }
        boolean status=true;
        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byte[] byteArray = stream.toByteArray();
            WebService.subeImg(byteArray, "uploadImg2",correo,contra);
            return status;



        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
            showProgress(false);
            checkmark.setVisibility(View.VISIBLE);
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            Bundle datos = new Bundle();
            datos.putString("correo", correo);
            datos.putString("nombrefull", nombrefull);
            datos.putString("imgurl", imgurl);
            datos.putString("contra",contra);
            intent.putExtras(datos);
            startActivity(intent);
            finish();

        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }
    }

}
