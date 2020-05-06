package clientUi;

import android.os.Bundle;
import androidx.fragment.app.ListFragment;
import android.widget.ArrayAdapter;

public class FragmentMenu extends ListFragment {



    public FragmentMenu() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        populateListView();
    }

    private void populateListView() {
        String[] values = new String[] { "Android", "iPhone", "WindowsMobile",
                "Blackberry", "WebOS", "Ubuntu", "Windows7", "Max OS X",
                "Linux", "OS/2" };
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, values);
        setListAdapter(adapter);
    }



    @Override
    public void onStart() {
        super.onStart();
    }
}
