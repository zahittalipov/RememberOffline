package zahittalipov.com.remember.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.TextView;

import io.realm.RealmBaseAdapter;
import io.realm.RealmResults;
import zahittalipov.com.remember.R;
import zahittalipov.com.remember.entity.Dictionary;

/**
 * Created by Zahit Talipov on 18.12.2015.
 */
public class DictionaryAdapter extends RealmBaseAdapter<Dictionary> implements ListAdapter {
    LayoutInflater inflater;

    public DictionaryAdapter(Context context, RealmResults<Dictionary> realmResults) {
        super(context, realmResults, true);
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Log.d("update","view");
        if (convertView == null)
            convertView = inflater.inflate(R.layout.item_dictionary, parent, false);
        TextView textViewName = (TextView) convertView.findViewById(R.id.nameDictTV);
        textViewName.setText(realmResults.get(position).getName());
        TextView textViewID = (TextView) convertView.findViewById(R.id.textViewDescription);
        textViewID.setText(realmResults.get(position).getDescription());
        return convertView;
    }

    @Override
    public Dictionary getItem(int i) {
        return realmResults.get(i);
    }
}
