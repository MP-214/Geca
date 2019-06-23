package com.example.lenovo.geca;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,View.OnClickListener {
    private ProgressDialog progressDialog;
    TextView tv;
    ImageView iv;
    String img,phone;
    Firebase ref,ref1;
    Query q;
    //String name1=TEST.name;
    Integer REQUEST_CAMERA=2,SELECT_FILE=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        NavigationView navigationView1 = (NavigationView) findViewById(R.id.nav_view);
        navigationView1.setNavigationItemSelectedListener(this);
        View headerView = navigationView1.getHeaderView(0);
        iv=(ImageView)headerView.findViewById(R.id.imageView);
        iv.setOnClickListener(this);
        TextView navUsername = (TextView) headerView.findViewById(R.id.textView);
        TextView navemail = (TextView) headerView.findViewById(R.id.textView1);
        //Toast.makeText(this, "name="+ TEST.name, Toast.LENGTH_SHORT).show();
        //Toast.makeText(this, "name="+TEST.email, Toast.LENGTH_SHORT).show();
        navemail.setText(TEST.email);
        navUsername.setText(TEST.name);
        Firebase.setAndroidContext(this);
        ref1 = new Firebase("https://gecaproject.firebaseio.com/");
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        select();

//        selectImage();


//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        FragmentManager fm = getSupportFragmentManager();


            int commit = fm.beginTransaction().replace(R.id.content_frame, new Layer1()).commit();


    }
    public void select(){
        phone=TEST.phone;
        ref = new Firebase("https://gecaproject.firebaseio.com/Image/");
        q = ref.orderByKey();
        q.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot sn:dataSnapshot.getChildren()){
                    try {
                        details p1 = sn.getValue(details.class);
                        Log.d("Error..",""+p1.getPhone());
                        Log.d("Error1..",""+phone);
                        Toast.makeText(MainActivity.this, phone+"="+p1.getPhone(), Toast.LENGTH_SHORT).show();
                        if(p1.getPhone().trim().equals(phone.trim())){
                            TEST.cimg=p1.getImg();
                            Log.d("Error3..",""+TEST.cimg);
                            iv.setImageBitmap(decode(TEST.cimg));
                            Toast.makeText(MainActivity.this, phone+"="+p1.getPhone(), Toast.LENGTH_SHORT).show();
                            break;
                        }


                    }catch (Exception e){
                        Log.d("Error4.",""+e);
                        Toast.makeText(MainActivity.this, ""+e, Toast.LENGTH_SHORT).show();

                    }
                }

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });


    }
    public Bitmap decode(String imageString) {

        try {

            //decode base64 string to image
            byte[] imageBytes = Base64.decode(imageString, Base64.DEFAULT);

            Bitmap decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);

            // iv.setImageBitmap(decodedImage);
            return decodedImage;
        } catch (Exception e) {
            //  Toast.makeText(this, "" + e, Toast.LENGTH_SHORT).show();}
        }
        return null;
    }
private void selectImage(){
        final CharSequence[] item={"CAMERA","GALLERY","CANCEL"};
    AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
    builder.setTitle("ADD IMAGE");
    builder.setItems(item, new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            if(item[i].equals("CAMERA")){
//                Intent it =new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                startActivityForResult(it,REQUEST_CAMERA);
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                File f = new File(android.os.Environment.getExternalStorageDirectory(), "temp.jpg");
                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
                startActivityForResult(intent, REQUEST_CAMERA);
            }
            else if(item[i].equals("GALLERY")){
                Intent it=new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                //it.setType("image/*");
                startActivityForResult(it, 1);
                // startActivityForResult(it.createChooser(it,"Select File"),SELECT_FILE);
            }
            else if(item[i].equals("CANCEL")){
                dialogInterface.dismiss();;
            }
        }

    });
    builder.show();

    }
    String img1="";
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //if (resultCode == Activity.RESULT_OK) {
            try {
            if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CAMERA) {
//                Bundle bundle =data.getExtras();
//                final Bitmap bmp=(Bitmap)bundle.get("data");
//                iv.setImageBitmap(bmp);
                File f = new File(Environment.getExternalStorageDirectory().toString());
                for (File temp : f.listFiles()) {
                    if (temp.getName().equals("temp.jpg")) {
                        f = temp;
                        break;
                    }
                }
                try {
                    Bitmap bitmap;
                    BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
                    bitmap = BitmapFactory.decodeFile(f.getAbsolutePath(),
                            bitmapOptions);
                    iv.setImageBitmap(bitmap);
                    img1=BitMapToString(bitmap);
                    add();
                    String path = android.os.Environment.getExternalStorageDirectory() + File.separator + "Phoenix" + File.separator + "default";
                    f.delete();
//                    OutputStream outFile = null;
//                    File file = new File(path, String.valueOf(System.currentTimeMillis()) + ".jpg");
//                    try {
//                        outFile = new FileOutputStream(file);
//                        bitmap.compress(Bitmap.CompressFormat.JPEG, 85, outFile);
//                        outFile.flush();
//                        outFile.close();
//                    } catch (FileNotFoundException e) {
//                        e.printStackTrace();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                //write code for firebase
                Toast.makeText(this, "image inserted", Toast.LENGTH_SHORT).show();

            }//else if(resultCode==SELECT_FILE) {
//                Uri uri=data.getData();
//                iv.setImageURI(uri);


                    // When an Image is picked
                    else if (requestCode == 1 && resultCode ==Activity.RESULT_OK
                            && null != data) {
                        //Toast.makeText(this, "in 2nd block", Toast.LENGTH_SHORT).show();
                        // Get the Image from data
                        Uri selectedImage = data.getData();
                        //iv.setImageURI(Uri.parse(img));
                        String[] filePathColumn = {MediaStore.Images.Media.DATA};
                        // Get the cursor
                        Cursor cursor = this.getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                        // Move to first row
                        cursor.moveToFirst();
                        Toast.makeText( this, "in gllery", Toast.LENGTH_SHORT).show();
                        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                        String imgDecodableString = cursor.getString(columnIndex);
                        cursor.close();
                        //iv = (ImageView)findViewById(R.id.imageView);
                        iv.setDrawingCacheEnabled(true);
                        // Set the Image in ImageView after decoding the String
                        iv.setImageBitmap(BitmapFactory.decodeFile(imgDecodableString));
                        Bitmap bmp = BitmapFactory.decodeFile(imgDecodableString);
                        img1 = BitMapToString(bmp);
                        add();
                        //write code for firebase
                        Toast.makeText(this, "image inserted", Toast.LENGTH_SHORT).show();





                    } else {
                        Toast.makeText( this, "You haven't picked Image",Toast.LENGTH_LONG).show();
                    }
                   // }
                } catch (Exception e) {
                    Toast.makeText( this, ""+e, Toast.LENGTH_LONG)
                            .show();
                }
            //}
        }
    //}
    public String BitMapToString(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        String temp = Base64.encodeToString(b, Base64.DEFAULT);
        return temp;
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
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        progressDialog = new ProgressDialog(this);

        // Handle navigation view item clicks here.
        int id = item.getItemId();
        FragmentManager fm=getSupportFragmentManager();
        if (id == R.id.nav_home) {
            int commit=fm.beginTransaction().replace(R.id.content_frame,new Home()).addToBackStack("Layer1").commit();
        } else if (id == R.id.nav_camera) {
            int commit=fm.beginTransaction().replace(R.id.content_frame,new News()).addToBackStack("Layer1").commit();
        }else if (id == R.id.nav_gallery) {
            progressDialog.setMessage("Please Wait...");
            progressDialog.show();
            Intent it= new Intent(Intent.ACTION_VIEW,Uri.parse("https://www.collegesearch.in/engineering/colleges-maharashtra"));
            progressDialog.dismiss();
            startActivity(it);



        } else if (id == R.id.nav_slideshow) {
            int commit=fm.beginTransaction().replace(R.id.content_frame,new Branch()).addToBackStack("Layer1").commit();

        } else if (id == R.id.nav_manage) {
            int commit=fm.beginTransaction().replace(R.id.content_frame,new feedback()).addToBackStack("Layer1").commit();


        } else if (id == R.id.nav_share) {
            try {
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, "My application name");
                String shareMessage= "\nLet me recommend you this application\n\n";
                shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID +"\n\n";
                shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                startActivity(Intent.createChooser(shareIntent, "choose one"));
            } catch(Exception e) {
                //e.toString();
            }

        } else if (id == R.id.nav_contactus) {
            int commit=fm.beginTransaction().replace(R.id.content_frame,new Contactus()).addToBackStack("Layer1").commit();



        }else if (id == R.id.nav_logout) {
            Intent intent=new Intent(getApplicationContext(),LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);


        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    void add(){
        details ob =new details();
        ob.setImg(img1);
        ob.setPhone(TEST.phone);
        ref1.child("Image").child(TEST.phone).setValue(ob);
    }

    @Override
    public void onClick(View view) {

        selectImage();


        //Toast.makeText(this, "Image added in fb", Toast.LENGTH_SHORT).show();
       //iv.setImageResource(R.);

    }
}
