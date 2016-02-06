package zahittalipov.com.remember.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.TextView;

import io.realm.RealmBaseAdapter;
import io.realm.RealmResults;
import zahittalipov.com.remember.R;
import zahittalipov.com.remember.entity.Word;

/**
 * Created by Zahit Talipov on 19.12.2015.
 */
public class WordAdapter extends RealmBaseAdapter<Word> implements ListAdapter {
    LayoutInflater inflater;

    public WordAdapter(Context context, RealmResults<Word> realmResults) {
        super(context, realmResults, true);
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null)
            convertView = inflater.inflate(R.layout.item_word, parent, false);
        TextView textViewName = (TextView) convertView.findViewById(R.id.itemWordName);
        textViewName.setText(realmResults.get(position).getName());

        TextView textViewValue = (TextView) convertView.findViewById(R.id.itemWordValue);
        textViewValue.setText(realmResults.get(position).getValue());

        return convertView;
    }
}
