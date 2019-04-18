package com.destinyapp.aplikasisdn07.Admin;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.destinyapp.aplikasisdn07.API.ApiRequest;
import com.destinyapp.aplikasisdn07.API.RetroServer;
import com.destinyapp.aplikasisdn07.Admin.Fragment.DataJadwal;
import com.destinyapp.aplikasisdn07.Admin.Fragment.MainJadwalAdmin;
import com.destinyapp.aplikasisdn07.Admin.Fragment.MainKelasAdmin;
import com.destinyapp.aplikasisdn07.Admin.Fragment.MainMataPelajaranAdmin;
import com.destinyapp.aplikasisdn07.Admin.Fragment.MainNilaiAdmin;
import com.destinyapp.aplikasisdn07.Admin.Fragment.MainSiswaAdmin;
import com.destinyapp.aplikasisdn07.Admin.Fragment.UpdateAbsenAdmin;
import com.destinyapp.aplikasisdn07.Admin.Fragment.UpdateNilaiAdmin;
import com.destinyapp.aplikasisdn07.Admin.Fragment.VerifAbsenAdmin;
import com.destinyapp.aplikasisdn07.Admin.Fragment.DataGuruAdmin;
import com.destinyapp.aplikasisdn07.Admin.Fragment.DataKelasAdmin;
import com.destinyapp.aplikasisdn07.Admin.Fragment.DataMataPelajaran;
import com.destinyapp.aplikasisdn07.Admin.Fragment.DataSiswaAdmin;
import com.destinyapp.aplikasisdn07.Admin.Fragment.InputDataGuruAdmin;
import com.destinyapp.aplikasisdn07.Admin.Fragment.InputDataJadwalAdmin;
import com.destinyapp.aplikasisdn07.Admin.Fragment.InputDataKelasAdmin;
import com.destinyapp.aplikasisdn07.Admin.Fragment.InputDataMataPelajaranAdmin;
import com.destinyapp.aplikasisdn07.Admin.Fragment.InputDataSiswaAdmin;
import com.destinyapp.aplikasisdn07.Admin.Fragment.MainGuruAdmin;
import com.destinyapp.aplikasisdn07.Admin.Fragment.VerifNilaiAdmin;
import com.destinyapp.aplikasisdn07.Fragment.AboutFragment;
import com.destinyapp.aplikasisdn07.Guru.Fragment.AbsensiSiswaGuru;
import com.destinyapp.aplikasisdn07.MainActivity;
import com.destinyapp.aplikasisdn07.Models.ResponseModel;
import com.destinyapp.aplikasisdn07.R;
import com.destinyapp.aplikasisdn07.Session.DB_Helper;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainAdminActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    DB_Helper dbHelper;
    String User="";
    TextView navUsername,navName;
    CircleImageView navHeaderPP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_admin);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View headerView = navigationView.getHeaderView(0);

        navUsername = (TextView) headerView.findViewById(R.id.tvUsernameAdminHeader);
        navName = (TextView)headerView.findViewById(R.id.tvNamaAdminHeader);
        navHeaderPP = (CircleImageView) headerView.findViewById(R.id.navHeaderPPAdmin);


        dbHelper = new DB_Helper(this);
        Cursor cursor = dbHelper.checkSession();

        while (cursor.moveToNext()){
            User=cursor.getString(0);
        }

        getDataAdmin();
        navUsername.setText("Username : "+User);

        Fragment fragment = null;
        fragment = new MainGuruAdmin();


        //IF ELSE
        Intent data = getIntent();
        String inputGuru = data.getStringExtra("INPUT_GURU");
        String outputGuru = data.getStringExtra("OUTPUT_GURU");
        String inputSiswa = data.getStringExtra("INPUT_SISWA");
        String outputSiswa = data.getStringExtra("OUTPUT_SISWA");
        String inputJadwal = data.getStringExtra("INPUT_JADWAL");
        String outputJadwal = data.getStringExtra("OUTPUT_JADWAL");
        String inputKelas = data.getStringExtra("INPUT_KELAS");
        String outputKelas = data.getStringExtra("OUTPUT_KELAS");
        String inputMapel = data.getStringExtra("INPUT_MATA_PELAJARAN");
        String outputMapel = data.getStringExtra("OUTPUT_MATA_PELAJARAN");
        final String UI = data.getStringExtra("KEY_UI");
        final String NIP = data.getStringExtra("KEY_NIP");
        final String NIS = data.getStringExtra("KEY_NIS");
        final String AbsenVerif = data.getStringExtra("AbsenVerif");
        final String UpdateAbsen = data.getStringExtra("UPDATE_ABSEN");
        final String NilaiVerif = data.getStringExtra("NilaiVerif");
        final String UpdateNilai = data.getStringExtra("UPDATE_NILAI");
        final String UpdateIDMapel = data.getStringExtra("UPDATE_IDMAPEL");
        if (inputGuru != null){
            Bundle bundle = new Bundle();
            bundle.putString("KEY_UI",UI);
            bundle.putString("KEY_NIP",NIP);
            fragment = new InputDataGuruAdmin();
            fragment.setArguments(bundle);
        }else if(outputGuru != null){
            fragment=new DataGuruAdmin();
        }else if(inputSiswa != null){
            Bundle bundle = new Bundle();
            bundle.putString("KEY_UI",UI);
            bundle.putString("KEY_NIS",NIS);
            fragment = new InputDataSiswaAdmin();
            fragment.setArguments(bundle);
        }else if(outputSiswa != null){
            fragment=new DataSiswaAdmin();
        }else if(inputJadwal !=null){
            fragment=new InputDataJadwalAdmin();
        }else if(outputJadwal !=null){
            fragment=new DataJadwal();
        }else if(inputKelas !=null){
            fragment=new InputDataKelasAdmin();
        }else if(outputKelas !=null){
            fragment=new DataKelasAdmin();
        }else if(inputMapel !=null){
            fragment=new InputDataMataPelajaranAdmin();
        }else if(outputMapel !=null){
            fragment=new DataMataPelajaran();
        }else if(AbsenVerif !=null){
            Bundle bundle = new Bundle();
            bundle.putString("KEY_VERIF_ABSEN",AbsenVerif);
            fragment=new VerifAbsenAdmin();
            fragment.setArguments(bundle);
        }else if(UpdateAbsen !=null){
            Bundle bundle = new Bundle();
            bundle.putString("KEY_NIS",UpdateAbsen);
            fragment=new UpdateAbsenAdmin();
            fragment.setArguments(bundle);
        }else if(NilaiVerif !=null){
            Bundle bundle = new Bundle();
            bundle.putString("KEY_VERIF_NILAI",NilaiVerif);
            fragment=new VerifNilaiAdmin();
            fragment.setArguments(bundle);
        }else if(UpdateNilai !=null){
            Bundle bundle = new Bundle();
            bundle.putString("KEY_NIS",UpdateNilai);
            bundle.putString("KEY_MAPEL",UpdateIDMapel);
            fragment=new UpdateNilaiAdmin();
            fragment.setArguments(bundle);
        }

        //DONE
        ChangeFragment(fragment);


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
        getMenuInflater().inflate(R.menu.main_admin, menu);
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
        // Handle navigation view item clicks here.
        Fragment fragment = null;
        int id = item.getItemId();

        if (id == R.id.nav_guru_admin) {
            fragment=new MainGuruAdmin();
        } else if (id == R.id.nav_siswa_admin) {
            fragment=new MainSiswaAdmin();
        } else if (id == R.id.nav_mataPelajaran_admin) {
            fragment=new MainMataPelajaranAdmin();
        } else if (id == R.id.nav_kelas_admin) {
            fragment=new MainKelasAdmin();
        } else if (id == R.id.nav_jadwal_admin) {
            fragment=new MainJadwalAdmin();
        } else if (id == R.id.nav_penilaianAdmin) {
            fragment=new MainNilaiAdmin();
        } else if (id == R.id.nav_data_diri_admin) {

        }else if (id == R.id.nav_logout_admin) {
            logout();
        }else if (id == R.id.nav_about) {
            fragment=new AboutFragment();
        }

        ChangeFragment(fragment);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    private void getDataAdmin(){
        String Username=User;
        ApiRequest api = RetroServer.getClient().create(ApiRequest.class);

        final Call<ResponseModel> getAdmin = api.getDataAdmin(Username);
        getAdmin.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                String nama = response.body().getNama_admin();
                String profile = response.body().getProfile_admin();
                getImageFromURL(profile);
                navName.setText("Nama :"+nama);
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Toast.makeText(MainAdminActivity.this,"Data Error",Toast.LENGTH_LONG).show();
            }
        });
    }
    private void ChangeFragment(Fragment fragment){
        if(fragment != null){
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction ft = fragmentManager.beginTransaction();
            ft.replace(R.id.screen_areaMainAdmin,fragment);
            ft.commit();
        }
    }
    private void logout(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Anda Yakin ingin Logout ?")
                .setCancelable(false)
                .setPositiveButton("Iya", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = new Intent(MainAdminActivity.this, MainActivity.class);
                        dbHelper = new DB_Helper(MainAdminActivity.this);
                        dbHelper.userLogout(User,MainAdminActivity.this);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                })
                //Set your icon here
                .setTitle("Perhatian !!!")
                .setIcon(R.drawable.ic_close_black_24dp);
        AlertDialog alert = builder.create();
        alert.show();
    }
    private void getImageFromURL(String url){
        String BASE_URL = getString(R.string.base_url);
        String URL = BASE_URL+"ProfilePicture/Guru/"+url;
        Glide.with(MainAdminActivity.this)
                .load(URL)
                .into(navHeaderPP);
    }
}
