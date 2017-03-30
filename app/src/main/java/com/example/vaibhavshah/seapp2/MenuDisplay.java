package com.example.vaibhavshah.seapp2;

import java.util.ArrayList;
import java.util.List;

import android.view.View;
import android.widget.Button;
import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;
import android.content.Intent;


public class MenuDisplay extends Activity
{
    Button b1;
    TextView tv;

    @Override
    public void onBackPressed() {

    }

    public ListView listViewMenus = null;
    public List<Menu> Menus = null;

    public void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_display);

        final String userId=getIntent().getStringExtra("UserId");

        this.listViewMenus = (ListView) this.findViewById(R.id.lv_Menus);

        this.Menus = new ArrayList<Menu>();
        Menu Menu = null;

        Menu = new Menu();
        Menu.setId(1);
        Menu.setName("Pizza");
        this.Menus.add(Menu);

        Menu = new Menu();
        Menu.setId(2);
        Menu.setName("Burger");
        this.Menus.add(Menu);

        Menu = new Menu();
        Menu.setId(3);
        Menu.setName("French Fries");
        this.Menus.add(Menu);

        Menu = new Menu();
        Menu.setId(4);
        Menu.setName("Pasta");
        this.Menus.add(Menu);

        MenuViewAdapter adapter = new MenuViewAdapter(this, this.Menus);
        this.listViewMenus.setAdapter(adapter);

        b1= (Button) findViewById(R.id.button);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MenuDisplay.this,OrderDisplay.class);
                Bundle extras= new Bundle();
                List<Menu> selectedMenu = new ArrayList<Menu>();
                int i=1;
                for(Menu s: Menus)
                {
                    if(s.isSelected()) {
                        selectedMenu.add(s); // Here you will get your selected skill in this list.
                        extras.putString("Item"+i+"",s.getName());
                        i++;
                    }
                }
                intent.putExtra("UserId",userId);
                intent.putExtras(extras);
                startActivity(intent);
            }
        });


    }
}

