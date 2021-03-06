package br.com.futfatec.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;

import br.com.futfatec.R;
import br.com.futfatec.content.AppPreferencesData;
import br.com.futfatec.fragment.ArtilhariaFragment;
import br.com.futfatec.fragment.ClassificacaoFragment;
import br.com.futfatec.fragment.RodadaFragment;

public class MenuActivity extends AppCompatActivity {
    private static final int ITEM_CLASSIFICACAO_INDEX = 0;
    private static final int ITEM_ARTILHARIA_INDEX = 1;
    private static final int ITEM_RODADA_INDEX = 2;
    BottomNavigationBar bottomNavigationBar;
    private BottomNavigationBar.OnTabSelectedListener futFatecMenuListener;
    private FragmentTransaction fragmentTransaction;
    private FragmentManager fragmentManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        bottomNavigationBar = (BottomNavigationBar) findViewById(R.id.bottom_navigation_bar);
        fragmentManager = getSupportFragmentManager();
        this.futFatecMenuListener = new FutFatecMenuSelectedListener();
        initMenu();
        switchFragment(ClassificacaoFragment.newInstance());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.app_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.sair:
                new AppPreferencesData(this).invalidateSession();
                startActivity(new Intent(this, AuthActivity.class));
                finish();
                break;
        }

        return true;
    }

    private void initMenu() {
        BottomNavigationItem tabelaItem = new BottomNavigationItem(R.drawable.ic_menu_item_tabela, getResources().getString(R.string.menu_item_tabela));
        BottomNavigationItem artilhariaItem = new BottomNavigationItem(R.drawable.ic_menu_item_chuteira, getResources().getString(R.string.menu_item_artilharia));
        BottomNavigationItem rodadaItem = new BottomNavigationItem(R.drawable.ic_menu_item_bola, getResources().getString(R.string.menu_item_rodada));
        tabelaItem.setActiveColorResource(R.color.colorClassificacao);
        artilhariaItem.setActiveColorResource(R.color.colorArtilharia);
        rodadaItem.setActiveColorResource(R.color.colorRodada);
        bottomNavigationBar
                .addItem(tabelaItem)
                .addItem(artilhariaItem)
                .addItem(rodadaItem)
                .initialise();

        bottomNavigationBar.setFirstSelectedPosition(ITEM_CLASSIFICACAO_INDEX);

        bottomNavigationBar.setMode(BottomNavigationBar.MODE_SHIFTING);

        bottomNavigationBar.setTabSelectedListener(futFatecMenuListener);
        bottomNavigationBar.setBarBackgroundColor(R.color.colorBackgroundMenu);
    }

    private void switchFragment(Fragment fragment){
        fragmentManager.popBackStackImmediate();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainer, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public void onBackPressed() {}

    private class FutFatecMenuSelectedListener implements BottomNavigationBar.OnTabSelectedListener {

        @Override
        public void onTabSelected(int position) {
            Fragment fragment = null;
            switch (position) {
                case ITEM_CLASSIFICACAO_INDEX:
                    fragment = ClassificacaoFragment.newInstance();
                    break;
                case ITEM_ARTILHARIA_INDEX:
                    fragment = ArtilhariaFragment.newInstance();
                    break;

                case ITEM_RODADA_INDEX:
                    fragment = RodadaFragment.newInstance();
                    break;
            }

            switchFragment(fragment);
        }
        @Override
        public void onTabUnselected(int position) {

        }

        @Override
        public void onTabReselected(int position) {

        }
    }

}
