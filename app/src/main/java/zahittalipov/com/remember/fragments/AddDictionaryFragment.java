package zahittalipov.com.remember.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;
import zahittalipov.com.remember.AppDelegate;
import zahittalipov.com.remember.R;
import zahittalipov.com.remember.database.DictionaryDBInterface;
import zahittalipov.com.remember.entity.Dict;
import zahittalipov.com.remember.service.ApiFactory;

/**
 * Created by Zahit Talipov on 18.12.2015.
 */
public class AddDictionaryFragment extends DialogFragment implements DialogInterface.OnClickListener {
    EditText editTextName = null;
    EditText editTextDesc = null;
    CheckBox checkBox;
    Dialog dialog;
    AlertDialog.Builder builder;
    boolean cancel = false;
    Context context;
    private View view;

    public AddDictionaryFragment(Context context) {
        this.context = context;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        builder.setNegativeButton("Отмена", this);
        builder.setPositiveButton("Добавить словарь", this);
        builder.setTitle("Добавление словаря");
        builder.setCancelable(true);
        view = inflater.inflate(R.layout.add_dictionary, null);
        editTextDesc = (EditText) view.findViewById(R.id.editTextDesc);
        editTextName = (EditText) view.findViewById(R.id.editTextName);
        checkBox = (CheckBox) view.findViewById(R.id.checkBoxPrivate);
        builder.setView(view);
        dialog = builder.create();
        return dialog;
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        switch (which) {
            case Dialog.BUTTON_POSITIVE: {
                String name = editTextName.getText().toString();
                String desc = editTextDesc.getText().toString();
                Boolean privateDict = checkBox.isChecked();
                Dict dictionary = new Dict(name, desc, privateDict);
                ApiFactory.getRememberService().addDictionary(dictionary, AppDelegate.getCurrentUser().getAccessToken()).enqueue(new Callback<Dict>() {
                    @Override
                    public void onResponse(Response<Dict> response, Retrofit retrofit) {
                        Log.d("error", response.code() + "");
                        if (response.code() == 200) {
                            if (response.body().getName() != null) {
                                Log.d("dict", response.body().getName());
                                DictionaryDBInterface dbInterface = DictionaryDBInterface.getInstance(context);
                                dbInterface.add(response.body());
                            } else {
                                Toast.makeText(context, "Не все поля были заполнены!", Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                    @Override
                    public void onFailure(Throwable t) {
                        Log.d("error", "error");
                    }
                });
                break;
            }
            case Dialog.BUTTON_NEGATIVE: {
                cancel = true;
                break;
            }
        }

    }

    @Override
    public void onCancel(DialogInterface dialog) {
        if (cancel)
            super.onCancel(dialog);
    }
}
