package doron.huji.ac.il.ex3;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.ArrayList;


public class ItemAdapter extends ArrayAdapter<Item> {

    public ItemAdapter(Context context, ArrayList<Item> items) {
        super(context, 0, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.task, parent, false);
        }

        convertView.setBackgroundColor((position % 2 == 0) ? Color.BLUE : Color.RED);

        Item item = getItem(position);

        TextView taskText = (TextView) convertView.findViewById(R.id.task_text);

        taskText.setText(item.toString());

        return convertView;
    }
}