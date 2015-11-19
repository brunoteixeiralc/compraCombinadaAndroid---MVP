package br.com.compracombinada.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v4.widget.DrawerLayout;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import br.com.compracombinada.fragment.Amizades;
import br.com.compracombinada.fragment.Cotacoes;
import br.com.compracombinada.fragment.Eventos;
import br.com.compracombinada.fragment.EventosConvidados;
import br.com.compracombinada.fragment.FinalizarEvento;
import br.com.compracombinada.fragment.Listas;
import br.com.compracombinada.navigationdrawer.NavigationDrawerFragment;
import br.com.compracombinada.fragment.ProdutosNaoContem;
import br.com.compracombinada.R;
import br.com.compracombinada.fragment.Sobre;
import br.com.compracombinada.fragment.SobreMim;
import br.com.compracombinada.fragment.Solicitacoes;
import br.com.compracombinada.model.Usuario;
import br.com.compracombinada.model.UsuarioSingleton;
import br.com.compracombinada.utils.Utils;


public class MainActivity extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    private Fragment fragment;
    private NavigationDrawerFragment mNavigationDrawerFragment;
    private CharSequence mTitle;
    private SharedPreferences prefs;
    private Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
    }

    public void convertJsonStringToArray(String jsonString) {

        Gson gson = new Gson();
        usuario = new Usuario();
        JsonParser parser = new JsonParser();
        JsonObject usuarioJSON = (JsonObject) parser.parse(jsonString);
        usuario = gson.fromJson(usuarioJSON, Usuario.class);

        UsuarioSingleton.getInstance().setUsuario(usuario);

    }


    @Override
    public void onNavigationDrawerItemSelected(int position) {

        fragment = null;
        Usuario usuario1 = new Usuario();

        if (usuario == null) {
            prefs = MainActivity.this.getSharedPreferences("settings", Context.MODE_PRIVATE);
            usuario1 = (Usuario) Utils.convertJsonStringToObject(prefs.getString("jsonString", null));
            UsuarioSingleton.getInstance().setUsuario(usuario1);
        }

        Bundle bundle = new Bundle();
        bundle.putSerializable("usuario", usuario1);

        switch (position) {

            case 0:
                fragment = new SobreMim();
                break;
            case 1:
                fragment = new Eventos();
                break;
            case 2:
                fragment = new EventosConvidados();
                break;
            case 3:
                fragment = new FinalizarEvento();
                break;
            case 4:
                fragment = new ProdutosNaoContem();
                break;
            case 5:
                fragment = new Cotacoes();
                break;
            case 6:
                fragment = new Listas();
                break;
//            case 7:
//                fragment = new Configuracao();
//                break;
            case 7:
                fragment = new Amizades();
                break;
            case 8:
                fragment = new Solicitacoes();
                break;
            case 9:
                fragment = new Sobre();
                break;
            case 10:
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                break;
        }

        if (fragment != null) {
            fragment.setArguments(bundle);

            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.container, fragment).addToBackStack(null)
                    .commit();
        }


    }

    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                mTitle = getString(R.string.sobre_mim);
                break;
            case 2:
                mTitle = getString(R.string.eventos);
                break;
            case 3:
                mTitle = getString(R.string.evento_convidado);
                break;
            case 4:
                mTitle = getString(R.string.finalizar);
                break;
            case 5:
                mTitle = getString(R.string.nao_contem);
                break;
            case 6:
                mTitle = getString(R.string.cotacoes);
                break;
            case 7:
                mTitle = getString(R.string.listas);
                break;
//            case 8:
//                mTitle = getString(R.string.configuracao);
//                break;
            case 8:
                mTitle = getString(R.string.amizades);
                break;
            case 9:
                mTitle = getString(R.string.melhorias);
                break;
            case 10:
                mTitle = getString(R.string.sobre);
                break;
            case 11:
                mTitle = getString(R.string.sair);
                break;

        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {

            getMenuInflater().inflate(R.menu.main, menu);

            MenuItem item = menu.findItem(R.id.merge);
            item.setVisible(false);

            MenuItem item2 = menu.findItem(R.id.add);
            item2.setVisible(false);

            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

}
