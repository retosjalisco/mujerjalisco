package com.dotstudio.mujerjalisco;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    FragmentManager fragmentManager;
    private CharSequence mTitle;
    public static String filename = "datos";
    SharedPreferences data;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        mTitle = getTitle();

        Fragment objFragment=  new ComunidadFragment();
                mTitle = getString(R.string.comunidad);

                fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.container, objFragment)
                        .commit();
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
      //  getMenuInflater().inflate(R.menu.main, menu);

        return true;
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
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
        Fragment objFragment = null;

        int id = item.getItemId();

        if (id == R.id.comunidad) {
            objFragment = new ComunidadFragment();
            mTitle = getString(R.string.comunidad);
        } else if (id == R.id.programas) {
            objFragment = new ProgramasFragment();
            mTitle = "Programas";

        } else if (id == R.id.buzon) {
            objFragment = new BuzonFragment();
            mTitle = "Buzón";

        } else if (id == R.id.ventas) {
            objFragment = new VentasFragment();
            mTitle = "Ventas";

        } else if (id == R.id.perfil) {
            objFragment = new PerfilFragment();
            mTitle = "Perfil";

        } else if (id == R.id.cerrarsesion) {
            AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
            builder1.setMessage("¿Desea cerrar sesión?");
            builder1.setCancelable(true);

            builder1.setPositiveButton(
                    "Aceptar",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                           logout();
                            dialog.cancel();
                        }
                    });

            builder1.setNegativeButton(
                    "Cancelar",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });

            AlertDialog alert11 = builder1.create();
            alert11.show();
        }

        restoreActionBar();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        try{
            fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.container, objFragment)
                    .commit();
        }
        catch (Exception e){

        }

        return true;
    }

    public void logout(){
        Intent mainIntent = new Intent().setClass(this, CuentaActivity.class);
        startActivity(mainIntent);
        data = getSharedPreferences(filename, 0);
        SharedPreferences.Editor editor = data.edit();
        editor.putInt("login", 0);
        editor.commit();
    }

}
