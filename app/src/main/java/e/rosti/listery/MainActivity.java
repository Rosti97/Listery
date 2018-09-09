package e.rosti.listery;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUI();
        displaySelectedScreen(R.id.einkaufsliste);
    }

    private void initUI() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
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
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.einkaufsliste_info:
                showListInfoDialog();
                return true;
            case R.id.bilanz_info:
                showBilanzInfoDialog();
                return true;
            case R.id.wg_info:
                showWgInfoDialog();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Hier wird der InfoDialog im WG-Übersichts-Fragment
     **/
    private void showWgInfoDialog() {
        final AlertDialog.Builder adBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();

        adBuilder.setTitle("Info");
        adBuilder.setIcon(R.drawable.ic_info_outline_black_24dp);
        adBuilder.setView(inflater.inflate(R.layout.layout_infodialog_wg, null));
        adBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {

            }
        });
        AlertDialog iDialog = adBuilder.create();
        iDialog.show();
    }

    /**
     * Hier wird der InfoDialog im Bilanz-Fragment angezeigt
     **/
    private void showBilanzInfoDialog() {
        final AlertDialog.Builder adBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();

        adBuilder.setTitle("Info");
        adBuilder.setIcon(R.drawable.ic_info_outline_black_24dp);
        adBuilder.setView(inflater.inflate(R.layout.layout_infodialog_bilanz, null));
        adBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {

            }
        });
        AlertDialog iDialog = adBuilder.create();
        iDialog.show();
    }

    /**
     * Hier wird der InfoDialog im Einkaufsliste-Fragment angezeigt
     **/
    private void showListInfoDialog() {
        final AlertDialog.Builder adBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();

        adBuilder.setTitle(R.string.info);
        adBuilder.setIcon(R.drawable.ic_info_outline_black_24dp);
        adBuilder.setView(inflater.inflate(R.layout.layout_infodialog_einkaufsliste, null));
        adBuilder.setPositiveButton(R.string.ok_button, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {

            }
        });
        AlertDialog iDialog = adBuilder.create();
        iDialog.show();
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        displaySelectedScreen(item.getItemId());
        return true;
    }

    /**
     * Welches Fragment wird angezeigt
     **/
    private void displaySelectedScreen(int itemId) {

        //creating fragment object
        Fragment fragment = null;

        //initializing the fragment object which is selected
        switch (itemId) {
            case R.id.einkaufsliste:
                fragment = new EinkaufslisteFragment();
                break;
            case R.id.bilanz:
                fragment = new BilanzFragment();
                break;
            case R.id.wguebersicht:
                fragment = new WGUebersichtFragment();
                break;
        }

        //replacing the fragment
        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, fragment);
            ft.commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }
}




