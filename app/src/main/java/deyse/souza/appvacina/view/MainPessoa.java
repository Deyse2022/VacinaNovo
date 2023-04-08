package deyse.souza.appvacina.view;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import androidx.appcompat.widget.Toolbar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import deyse.souza.appvacina.R;

public class MainPessoa extends AppCompatActivity {

    private DatabaseReference referencia = FirebaseDatabase.getInstance().getReference();

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Vacina+");
        setSupportActionBar(toolbar);

        DatabaseReference usuarios = referencia.child( "usuarios" );

        usuarios.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.i("FIREBASE", dataSnapshot.getValue().toString() );
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }


    public boolean onCreateOptionsMenu(Menu menu){

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_pessoa,menu);

        return super.onCreateOptionsMenu(menu);
    }

}