package zexal.org.galerihewan;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    private DatabaseReference mDatabase;
    private AdapterHewan mAdapterHewan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.keepSynced(true);
        mAdapterHewan = new AdapterHewan(this,mDatabase.child("daftar"));
        ListView listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(mAdapterHewan);
        Button button = (Button) findViewById(R.id.submit);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SimpanData();
            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                mDatabase.child("daftar").child(mAdapterHewan.getIdkey().get(i)).removeValue(new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                        return;
                    }
                });
                return true;
            }
        });

    }

    public void SimpanData(){
        EditText nama = (EditText) findViewById(R.id.name);
        String hewan = nama.getText().toString();

        String key = mDatabase.child("daftar").push().getKey();
        mDatabase.child("daftar").child(key).setValue(new Hewan(hewan));
    }

}