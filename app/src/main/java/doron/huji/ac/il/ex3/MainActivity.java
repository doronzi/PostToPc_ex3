package doron.huji.ac.il.ex3;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    ArrayList<Item> items = new ArrayList<Item>();
    ListView listTasks;
    ItemAdapter itemAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        listTasks = (ListView)findViewById(R.id.listTasks);

        itemAdapter = new ItemAdapter(this, items);


        listTasks.setLongClickable(true);
        listTasks.setAdapter(itemAdapter);

        listTasks.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            public boolean onItemLongClick(AdapterView<?> arg0, View view, final int index, long arg3) {
                showDeleteTaskPopUp(view, index);
                return false;
            }

        });


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showNewTaskPopUp(view);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void showNewTaskPopUp(View view) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("New Item");
        final View viewInflated = LayoutInflater.from(this).inflate(R.layout.dialog_add_new_task, (ViewGroup) findViewById(android.R.id.content), false);
        final View outerView = view;
        final EditText inputText = (EditText) viewInflated.findViewById(R.id.input);
        final DatePicker inputDate = (DatePicker) viewInflated.findViewById(R.id.inputDate);
        builder.setView(viewInflated);

        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                String text = inputText.getText().toString();
                Date date = new Date(inputDate.getYear(), inputDate.getMonth(), inputDate.getDayOfMonth());

                if (!text.trim().equals("")) {
                    itemAdapter.add(new Item(text, date));

                } else {
                    Snackbar.make(outerView, "Item should not be empty", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                }
            }
        });
        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();

    }

    public void showDeleteTaskPopUp(View view, int index) {

        final View viewInflated = LayoutInflater.from(this).inflate(R.layout.dialog_delete_task, (ViewGroup) findViewById(android.R.id.content), false);
        final View outerView = view;
        final TextView taskText = (TextView) viewInflated.findViewById(R.id.taskText);
        taskText.setText(items.get(index).toString());
        final int indexToDelete = index;

        boolean isCall = items.get(index).toString().matches("#call\\d*");
        Toast.makeText(getApplicationContext(), items.get(index).toString() + "", Toast.LENGTH_SHORT).show();

        if (!isCall) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Delete Item?");
            builder.setView(viewInflated);

            builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    String ass = items.get(indexToDelete).getDueDate().toString();
                    items.remove(indexToDelete);
                    itemAdapter.notifyDataSetChanged();
                    Snackbar.make(outerView, "Item deleted" + ass, Snackbar.LENGTH_LONG).setAction("Action", null).show();

                }
            });
            builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });

            builder.show();
        }
        else {
            String callNumber =  items.get(index).toString().replace("#call", "");
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:" + callNumber));
            startActivity(intent);
        }

    }
}
