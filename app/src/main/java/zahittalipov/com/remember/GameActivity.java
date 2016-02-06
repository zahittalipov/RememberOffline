package zahittalipov.com.remember;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.Iterator;

import io.realm.RealmResults;
import zahittalipov.com.remember.database.DictionaryDBInterface;
import zahittalipov.com.remember.entity.Word;
import zahittalipov.com.remember.fragments.GameFalseFragment;
import zahittalipov.com.remember.fragments.GameFinish;
import zahittalipov.com.remember.fragments.GameFragment;
import zahittalipov.com.remember.fragments.GameTrueFragment;

/**
 * Created by Zahit Talipov on 19.12.2015.
 */
public class GameActivity extends AppCompatActivity implements GameInterface {

    public static Word currentWord;
    public static int count = 0;
    public static int fin;
    int id;
    Iterator<Word> wordIterator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_main);
        count = 0;
        id = getIntent().getIntExtra("id", 0);
        DictionaryDBInterface dbInterface = DictionaryDBInterface.getInstance(getApplicationContext());
        RealmResults<Word> words = dbInterface.findAllWords(id);
        wordIterator = words.iterator();
        fin = words.toArray().length;
        next();
    }

    @Override
    public void answer(String s) {
        if (s.toLowerCase().replace(" ", "").contains(currentWord.getValue().toLowerCase().replace(" ", ""))) {
            count++;
            getFragmentManager().beginTransaction().replace(R.id.frameLayoutGame, new GameTrueFragment()).commit();
        } else {
            getFragmentManager().beginTransaction().replace(R.id.frameLayoutGame, new GameFalseFragment()).commit();
        }
    }

    @Override
    public void next() {
        if (wordIterator.hasNext()) {
            currentWord = wordIterator.next();
            getFragmentManager().beginTransaction().replace(R.id.frameLayoutGame, new GameFragment()).commit();
        } else
            getFragmentManager().beginTransaction().replace(R.id.frameLayoutGame, new GameFinish()).commit();
    }

    @Override
    public void exit() {
        count = 0;
        fin = 0;
        onBackPressed();
    }
}
