package e.rosti.listery;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class PreiseingabeActivity extends AppCompatActivity {

    private PreiseingabeAdapter adapter;
    private ListView listView;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preiseingabe);

        setupView();
        setupButton();
    }


    private void setupView() {
        listView = findViewById(R.id.listview_preiseingabe);

        ArrayList<Einkaufsitem> gekaufteItems = new ArrayList<>();
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();

        if (extras != null) {
            gekaufteItems = extras.getParcelableArrayList("Gekauft");
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
            }
        });
    }
}
