package controllers.ui.summary;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.ristodroid.R;

import controllers.Dashboard;
import controllers.MainActivity;

public class SummaryFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_summary, container, false);
        TextView textView = root.findViewById(R.id.text_dashboard);
        String dishes=" ";
        boolean orderNull = MainActivity.getOrder()!=null && MainActivity.getOrder().getOrderDetails().size()>0;

        if(orderNull) {
               for (int i = 0; i < MainActivity.getOrder().getOrderDetails().size(); i++) {
                   dishes += MainActivity.getOrder().getOrderDetails().get(i).getDish().getName() + ", ";
           }
        }

        textView.setText(R.string.emptySummary);
        return root;
    }

}