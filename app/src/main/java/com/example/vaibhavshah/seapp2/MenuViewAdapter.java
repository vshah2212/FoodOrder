package com.example.vaibhavshah.seapp2;

import java.util.List;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class MenuViewAdapter extends BaseAdapter
{
    private Context context = null;
    private List<Menu> MenuList = null;

    public MenuViewAdapter(Context context, List<Menu> MenuList)
    {
        this.context = context;
        this.MenuList = MenuList;
    }

    public int getCount()
    {
        return this.MenuList != null ? this.MenuList.size() : 0;
    }

    public Object getItem(int position)
    {
        return this.MenuList != null ? this.MenuList.get(position) : null;
    }

    public long getItemId(int position)
    {
        if (this.MenuList != null)
        {
            Menu p = this.MenuList.get(position);
            return p.getId();
        }
        return 0;
    }

    public View getView(int position, View convertView, ViewGroup parent)
    {
        MenuView MenuView;

        if (convertView == null)
        {
            MenuView = new MenuView(this.context, this.MenuList.get(position));
        } else
        {
            MenuView = (MenuView) convertView;
            MenuView.setMenu(this.MenuList.get(position));
        }
        return MenuView;
    }

    public List<Menu> getProductList()
    {
        return this.MenuList;
    }
}
