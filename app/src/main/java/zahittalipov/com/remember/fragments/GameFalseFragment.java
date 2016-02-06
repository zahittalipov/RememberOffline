package zahittalipov.com.remember.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import zahittalipov.com.remember.GameActivity;
import zahittalipov.com.remember.GameInterface;
import zahittalipov.com.remember.R;

/**
 * Created by Zahit Talipov on 19.12.2015.
 */
public class GameFalseFragment extends Fragment {
    GameInterface gameInterface;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.game_false, null, false);
        gameInterface = (GameInterface) getActivity();
        TextView textView = (TextView) view.findViewById(R.id.falseName);
        textView.setText(GameActivity.currentWord.getName());
        TextView textView1 = (TextView) view.findViewById(R.id.falseValue);
        textView1.setText(GameActivity.currentWord.getValue());
        Button buttonClose = (Button) view.findViewById(R.id.buttonFalseFinish);
        buttonClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gameInterface.exit();
            }
        });
        Button buttonNext = (Button) view.findViewById(R.id.buttonFalseNext);
        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gameInterface.next();
            }
        });
        return view;
    }
}
