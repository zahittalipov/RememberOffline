package zahittalipov.com.remember.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import zahittalipov.com.remember.GameActivity;
import zahittalipov.com.remember.GameInterface;
import zahittalipov.com.remember.R;

/**
 * Created by Zahit Talipov on 19.12.2015.
 */
public class GameFragment extends Fragment {
    GameInterface gameInterface;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.game, null, false);
        gameInterface = (GameInterface) getActivity();
        TextView textView = (TextView) view.findViewById(R.id.tvAnswer);
        textView.setText(GameActivity.currentWord.getName());
        final EditText editText = (EditText) view.findViewById(R.id.editTextAnswer);
        Button buttonClose = (Button) view.findViewById(R.id.buttonExitGame);
        buttonClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gameInterface.exit();
            }
        });
        Button buttonNext = (Button) view.findViewById(R.id.buttonAns);
        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gameInterface.answer(editText.getText().toString());
            }
        });
        return view;
    }
}
