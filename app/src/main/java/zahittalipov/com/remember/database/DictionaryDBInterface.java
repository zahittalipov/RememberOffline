package zahittalipov.com.remember.database;

import android.content.Context;
import android.util.Log;

import io.realm.DynamicRealm;
import io.realm.FieldAttribute;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmMigration;
import io.realm.RealmObjectSchema;
import io.realm.RealmResults;
import io.realm.RealmSchema;
import zahittalipov.com.remember.entity.Dict;
import zahittalipov.com.remember.entity.Dictionary;
import zahittalipov.com.remember.entity.Word;
import zahittalipov.com.remember.entity.WordReq;

/**
 * Created by Zahit Talipov on 18.12.2015.
 */
public class DictionaryDBInterface {
    static DictionaryDBInterface dbInterface;
    public Realm realm;

    private DictionaryDBInterface(Context context) {
        this.realm = Realm.getInstance(configuringRealm(context));
    }

    public static DictionaryDBInterface getInstance(Context context) {
        if (dbInterface != null) {
            return dbInterface;
        } else {
            dbInterface = new DictionaryDBInterface(context);
            return dbInterface;
        }
    }

    public RealmConfiguration configuringRealm(Context context) {
        RealmConfiguration config = new RealmConfiguration.Builder(context)
                .schemaVersion(1)
                .migration(new Migration())
                .build();

        return config;
    }

    public void add(Dict dictionary) {
        Log.d("create", "add");
        realm.beginTransaction();
        if (realm.where(Dictionary.class).equalTo("id", dictionary.getId()).count() == 0) {
            Dictionary dict = realm.createObject(Dictionary.class);
            dict.setId(dictionary.getId());
            dict.setAccess(dictionary.getAccess());
            dict.setDescription(dictionary.getDescription());
            dict.setName(dictionary.getName());
        }
        realm.commitTransaction();
    }

    public void deleteDictionary(int id) {
        realm.beginTransaction();
        realm.where(Dictionary.class).equalTo("id", id).findAll().removeLast();
        realm.commitTransaction();
    }

    public void deleteWord(int id){
        realm.beginTransaction();
        realm.where(Word.class).equalTo("id", id).findAll().removeLast();
        realm.commitTransaction();
    }

    public RealmResults<Dictionary> findAll() {
        return realm.where(Dictionary.class).findAllSorted("name");
    }

    public RealmResults<Word> findAllWords(int id) {
        return realm.where(Word.class).equalTo("idDict", id).findAll();
    }

    public void addWord(int id, WordReq req) {
        Dictionary dictionary = realm.where(Dictionary.class).equalTo("id", id).findFirst();
        realm.beginTransaction();
        if (realm.where(Word.class).equalTo("id", req.getId()).count() == 0) {
            Word word = realm.createObject(Word.class);
            word.setName(req.getName());
            word.setValue(req.getValue());
            word.setId(req.getId());
            word.setIdDict(id);
            dictionary.getWords().add(word);
        }
        realm.commitTransaction();
    }

    public void deleteDB() {
        realm.beginTransaction();
        realm.clear(Word.class);
        realm.clear(Dictionary.class);
        realm.commitTransaction();
    }

    class Migration implements RealmMigration {

        @Override
        public void migrate(DynamicRealm dynamicRealm, long l, long l1) {
            Log.d("vers", "vers");
            RealmSchema schema = dynamicRealm.getSchema();
            if (l == 0) {
                RealmObjectSchema realmObjectSchema = schema.get("Word");
                realmObjectSchema.addField("idDict", int.class, FieldAttribute.REQUIRED);
                l++;
            }
        }
    }

}
