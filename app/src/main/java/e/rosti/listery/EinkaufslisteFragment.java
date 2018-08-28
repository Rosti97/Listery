package e.rosti.listery;

        import android.content.DialogInterface;
        import android.os.Bundle;
        import android.support.annotation.Nullable;
        import android.support.v4.app.Fragment;
        import android.util.Log;
        import android.view.LayoutInflater;
        import android.view.Menu;
        import android.view.MenuInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.Button;
        import android.widget.ListView;
        import android.widget.Toast;

        import java.util.List;

        import static android.content.ContentValues.TAG;

public class EinkaufslisteFragment extends Fragment {

    private View inflatedView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        setHasOptionsMenu(true);

        View v = inflater.inflate(R.layout.fragment_einkaufsliste, container, false);

        Button b = (Button) v.findViewById(R.id.button_einkauffertig);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "Button gedrückt");
                Toast.makeText(getActivity().getApplicationContext(), "Button geklickt", Toast.LENGTH_LONG).show();
            }
        });

        return v;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Einkaufsliste");

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_einkaufsliste, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }
}