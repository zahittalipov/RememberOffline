package zahittalipov.com.remember.fragments;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import io.realm.RealmResults;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;
import zahittalipov.com.remember.AppDelegate;
import zahittalipov.com.remember.GameActivity;
import zahittalipov.com.remember.R;
import zahittalipov.com.remember.adapter.WordAdapter;
import zahittalipov.com.remember.database.DictionaryDBInterface;
import zahittalipov.com.remember.entity.Word;
import zahittalipov.com.remember.entity.WordReq;
import zahittalipov.com.remember.service.ApiFactory;

/**
 * Created by Zahit Talipov on 19.12.2015.
 */
public class DictionarySingle extends Fragment implements View.OnClickListener {
    TextView textViewName;
    TextView textViewDesc;
    ProgressBar progressBar;
    ListView listView;
    Button buttonStart;
    Button buttonAdd;
    int progressState = View.VISIBLE;
    Context context;
    WordAdapter wordAdapter;
    private int id;
    private String name;
    private String desc;

    public DictionarySingle(Context context) {
        this.context = context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        id = getArguments().getInt("id");
        name = getArguments().getString("name");
        desc = getArguments().getString("desc");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.select_dictionary, null, false);
        textViewName = (TextView) view.findViewById(R.id.singlDescName);
        textViewDesc = (TextView) view.findViewById(R.id.singlDescDisc);
        progressBar = (ProgressBar) view.findViewById(R.id.progressBarDictionary);
        listView = (ListView) view.findViewById(R.id.listViewWord);
        textViewName.setText(name);
        textViewDesc.setText(desc);
        buttonAdd = (Button) view.findViewById(R.id.buttonAddWord);
        buttonStart = (Button) view.findViewById(R.id.buttonStart);
        buttonStart.setOnClickListener(this);
        buttonAdd.setOnClickListener(this);
        progressBar.setVisibility(progressState);
        RealmResults<Word> words = DictionaryDBInterface.getInstance(context).findAllWords(id);
        wordAdapter = new WordAdapter(context, words);
        listView.setAdapter(wordAdapter);
        listView.addHeaderView(inflater.inflate(R.layout.headerlv, null));
        registerForContextMenu(listView);
        update();
        return view;
    }

    private void update() {
        ApiFactory.getRememberService().getDictionaryWords(id, AppDelegate.getCurrentUser().getAccessToken()).enqueue(new Callback<List<WordReq>>() {
            @Override
            public void onResponse(Response<List<WordReq>> response, Retrofit retrofit) {
                Log.d("code", response.code() + "");
                if (response.code() == 200) {
                    DictionaryDBInterface dbInterface = DictionaryDBInterface.getInstance(context);
                    for (WordReq req : response.body()) {
                        dbInterface.addWord(id, req);
                    }
                } else {
                    Toast.makeText(context, "Ошибка сервера", Toast.LENGTH_LONG).show();
                }
                progressBar.setVisibility(View.GONE);
                progressState = View.GONE;
            }

            @Override
            public void onFailure(Throwable t) {

                Toast.makeText(context, "Ошибка сервера", Toast.LENGTH_LONG).show();

                progressBar.setVisibility(View.GONE);
                progressState = View.GONE;
            }
        });
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(0, 0, 0, "Удалить слово");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if (item.getItemId() == 0) {
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
            final Word word = wordAdapter.getItem(info.position - 1);
            ApiFactory.getRememberService().deleteWord(id, word.getId(), AppDelegate.getCurrentUser().getAccessToken()).enqueue(new Callback<WordReq>() {
                @Override
                public void onResponse(Response<WordReq> response, Retrofit retrofit) {
                    Log.d("code", response.code() + "");
                    if (response.code() == 200)
                        DictionaryDBInterface.getInstance(context).deleteWord(word.getId());
                }

                @Override
                public void onFailure(Throwable t) {

                }
            });
        }
        return super.onContextItemSelected(item);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.buttonAddWord: {
                AddWordFragment addWordFragment = new AddWordFragment(id, context);
                addWordFragment.show(getFragmentManager(), null);
                break;
            }
            case R.id.buttonStart: {
                Intent intent = new Intent(context, GameActivity.class);
                intent.putExtra("id", id);
                startActivity(intent);
                break;
            }
        }
    }
}
