package com.example.vaibhavshah.seapp2;

        import android.content.Context;
        import android.graphics.Color;
        import android.graphics.Typeface;
        import android.view.Gravity;
        import android.view.View;
        import android.widget.CheckBox;
        import android.widget.LinearLayout;
        import android.widget.TextView;

public class MenuView extends LinearLayout
{
    // To store Menu information
    private Menu menu;
    // For UI
    private Context context;
    private CheckBox chkSelect;
    private TextView tvMenuName;

    public MenuView(Context context, Menu menu)
    {
        super(context);
        this.context = context;
        this.menu = menu;

        /* Initialize UI components */
        this.chkSelect = new CheckBox(this.context);
        this.chkSelect.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View view)
            {
                MenuView.this.menu.setSelected(((CheckBox) view).isChecked());
            }
        });

        this.tvMenuName = new TextView(this.context);
        this.tvMenuName.setTextSize(15);
        this.tvMenuName
                .setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        this.tvMenuName.setText(" " + this.menu.getName());

        // Add components to main layout
        this.setOrientation(HORIZONTAL);
        this.addView(this.chkSelect, new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        this.addView(this.tvMenuName, new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));

    }

    public Menu getMenu()
    {
        return menu;
    }

    public void setMenu(Menu menu)
    {
        this.menu = menu;
    }
}
