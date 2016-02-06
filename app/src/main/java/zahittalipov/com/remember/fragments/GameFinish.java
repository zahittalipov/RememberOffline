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
public class GameFinish extends Fragment {
    GameInterface gameInterface;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        gameInterface= (GameInterface) getActivity();
        View view=inflater.inflate(R.layout.game_finish,null,false);
        TextView textView=(TextView)view.findViewById(R.id.result);
        textView.setText(String.valueOf((Integer)(100 / GameActivity.fin)*GameActivity.count)+"%");
        Button button=(Button)view.findViewById(R.id.buttonResult);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gameInterface.exit();
            }
        });
        return view;
    }
}
