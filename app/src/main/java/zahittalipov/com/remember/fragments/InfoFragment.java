package zahittalipov.com.remember.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import zahittalipov.com.remember.R;

/**
 * Created by Zahit Talipov on 17.12.2015.
 */
public class InfoFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getActivity().setTitle("О программе");
        return inflater.inflate(R.layout.info_layout,null);
    }
}
