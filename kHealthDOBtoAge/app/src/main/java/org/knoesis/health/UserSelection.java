package org.knoesis.health;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import org.knoesis.health.adapters.UserListAdapter;
import org.knoesis.health.dataHolders.UserDataHolder;

import java.util.List;

public class UserSelection extends KHealthAsthmaAdminActivity {

    private List<UserDataHolder> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_selection);

        data = db.getAllUsers();
        if(data.size() <= 0){
            Toast.makeText(this, "There are no users. Please create a user.",
                    Toast.LENGTH_SHORT).show();
            finish();
        }
        ListView users = (ListView) findViewById(R.id.userList);

        UserListAdapter listAdapter = new UserListAdapter(data, this,
                ((AsthmaApplication) getApplication()));
        users.setAdapter(listAdapter);
        users.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                UserDataHolder user = data.get(position);
                ((AsthmaApplication) getApplication()).setCurrentLoggedInUser(user.getName());
                Intent i = new Intent(UserSelection.this, ActivitySummaryAdmin.class);
                startActivity(i);
            }
        });
        System.out.println("end");
    }

}
