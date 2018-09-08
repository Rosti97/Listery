package e.rosti.listery;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class PreiseingabeActivity extends AppCompatActivity {

    private PreiseingabeAdapter adapter;
    private ListView listView;
    private Button button;
    private List<Item> gekaufteItems;
    private List<Item> itemsWithPrice;
    private static final String KEY = "Gekauft";
    private List<Mate> changedMates;

    private MateViewModel mMateViewModel;
    private ItemViewModel mItemViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preiseingabe);

        mMateViewModel = ViewModelProviders.of(this).get(MateViewModel.class);
        mItemViewModel = ViewModelProviders.of(this).get(ItemViewModel.class);

        mMateViewModel.getmCurrentMate().observe(this, new Observer<List<Mate>>() {
            @Override
            public void onChanged(@Nullable List<Mate> mates) {
                changedMates = mates;
            }
        });

        setupView();
        setupButton();
    }


    private void setupView() {
        listView = findViewById(R.id.listview_preiseingabe);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();

        if (extras != null) {
            gekaufteItems = extras.getParcelableArrayList(KEY);
        }

        adapter = new PreiseingabeAdapter(gekaufteItems, this);

        listView.setAdapter(adapter);
    }

    private void setupButton() {
        button = findViewById(R.id.button_preiseingabe);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**TODO Datenbank einbindung
                 *
                 */

                /** TEST für Preisanzeige :D **/
                for (Item item: gekaufteItems) {
                    if (item.getPrice() == 0) {
                    Log.i("TESTPREIS", item.getName() + item.getPrice());
                }}

                itemsWithPrice = new ArrayList<Item>();
                for (Item item: gekaufteItems) {
                    if (item.getPrice() != 0) {
                        itemsWithPrice.add(item);
                        Log.i("TESTGRÖßE", "Preisliste: " + itemsWithPrice.size());
                    }
                }
                /** TODO Items (itemsWithPrice) aus Einkaufsliste-Datenbank löschen und in Bilanz einfügen**/

                for(Item tempItem : itemsWithPrice){
                    List<Mate> mateList = tempItem.getMates();
                    float partialPrice = tempItem.getPrice() / mateList.size();
                    partialPrice = (float)Math.round(partialPrice*100f)/100f;
                    for(Mate tempMate : mateList){
                        Log.i("TEST", "mateID: "+ tempMate.getId());
                        if(tempMate.getId()!= 1){
                            for(Mate tempMate02 : changedMates){
                                if(tempMate02.getId()==tempMate.getId()){
                                    tempMate = tempMate02;
                                }
                            }
                            Log.i("TEST","getBalance()"+tempMate.getBalance());
                            Log.i("TEST", "Welcher Mate? "+tempMate);
                            float balance = tempMate.getBalance() + partialPrice;
                            tempMate.setBalance(balance);
                            if(!changedMates.contains(tempMate)){
                                changedMates.add(tempMate);
                            }
                        }
                    }
                }
                Mate[] updateMates = new Mate[changedMates.size()];
                updateMates = changedMates.toArray(updateMates);
                mMateViewModel.updateMate(updateMates);


                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                if(itemsWithPrice.size() == gekaufteItems.size()) {
                    startActivity(intent);
                } else {
                    Toast.makeText(PreiseingabeActivity.this, "Bitte bei allen Produkten den Preis angeben!", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}
