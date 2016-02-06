package zahittalipov.com.remember.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import zahittalipov.com.remember.AppDelegate;
import zahittalipov.com.remember.R;

/**
 * Created by Zahit Talipov on 17.12.2015.
 */
public class ProfileFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.profile_layout, null);
        TextView textView = (TextView) view.findViewById(R.id.profileLastName);
        textView.setText(String.format("%s %s", AppDelegate.getCurrentUser().getLastName(),
                AppDelegate.getCurrentUser().getFirstName()));
        getActivity().setTitle("Профиль");
        return view;
    }

}
