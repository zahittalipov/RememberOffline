package zahittalipov.com.remember.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.List;

import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;
import zahittalipov.com.remember.AppDelegate;
import zahittalipov.com.remember.R;
import zahittalipov.com.remember.adapter.DictionaryAdapter;
import zahittalipov.com.remember.database.DictionaryDBInterface;
import zahittalipov.com.remember.entity.Dict;
import zahittalipov.com.remember.entity.Dictionary;
import zahittalipov.com.remember.service.ApiFactory;

/**
 * Created by Zahit Talipov on 18.12.2015.
 */
public class DictionariesFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemClickListener {
    DictionaryAdapter dictionaryAdapter;
    DictionaryDBInterface dbInterface;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dictionaries_layout, null);
        FloatingActionButton actionButton = (FloatingActionButton) view.findViewById(R.id.addDictionary);
        actionButton.setOnClickListener(this);
        getActivity().setTitle("Мои словари");
        dbInterface = DictionaryDBInterface.getInstance(getActivity().getApplicationContext());
        dictionaryAdapter = new DictionaryAdapter(getActivity().getApplicationContext(), dbInterface.findAll());
        ListView listView = (ListView) view.findViewById(R.id.listDict);
        listView.setAdapter(dictionaryAdapter);
        listView.setOnItemClickListener(this);
        if (getArguments().getBoolean("update")) {
            update();
        }
        registerForContextMenu(listView);
        return view;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(0, 0, 0, "Удалить словарь");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if (item.getItemId() == 0) {
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
            final Dictionary dictionary = dictionaryAdapter.getItem(info.position);
            ApiFactory.getRememberService().deleteDictionary(dictionary.getId(), AppDelegate.getCurrentUser().getAccessToken()).enqueue(new Callback<Dict>() {
                @Override
                public void onResponse(Response<Dict> response, Retrofit retrofit) {
                    Log.d("code", response.code() + "");
                    if(response.code()==200)
                        dbInterface.deleteDictionary(dictionary.getId());
                }

                @Override
                public void onFailure(Throwable t) {

                }
            });
        }
        return super.onContextItemSelected(item);
    }

    private void update() {
        ApiFactory.getRememberService().getAllDictionaries(AppDelegate.getCurrentUser().getAccessToken()).enqueue(new Callback<List<Dict>>() {
            @Override
            public void onResponse(Response<List<Dict>> response, Retrofit retrofit) {
                Log.d("code", response.code() + "");
                if (response.code() == 200) {
                    DictionaryDBInterface dbInterface = DictionaryDBInterface.getInstance(getActivity().getApplicationContext());
                    for (Dict dict : response.body()) {
                        dbInterface.add(dict);
                    }
                }
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        AddDictionaryFragment fragment = new AddDictionaryFragment(getActivity().getApplicationContext());
        fragment.show(getFragmentManager(), "addDict");
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        DictionarySingle dictionarySingle = new DictionarySingle(getActivity().getApplicationContext());
        dictionarySingle.setRetainInstance(true);
        Bundle bundle = new Bundle();
        Dictionary dictionary = dictionaryAdapter.getItem(position);
        bundle.putInt("id", dictionary.getId());
        bundle.putString("name", dictionary.getName());
        bundle.putString("desc", dictionary.getDescription());
        Log.d("sef", dictionary.getId() + "");
        dictionarySingle.setArguments(bundle);
        getFragmentManager().beginTransaction().replace(R.id.frameLayoutMy, dictionarySingle, "single").addToBackStack(null).commit();
    }
}
