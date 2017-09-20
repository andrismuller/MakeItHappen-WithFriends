package hu.muller.andris.armando.makeithappen_withfriends.Todo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import hu.muller.andris.armando.makeithappen_withfriends.R;
import hu.muller.andris.armando.makeithappen_withfriends.database.DBHelper;
import hu.muller.andris.armando.makeithappen_withfriends.model.Todo;

/**
 * Created by Muller Andras on 9/19/2017.
 */

public class TodoListAdapter extends BaseAdapter{
    private static List<Todo> todos = new ArrayList<>();
    private Context context;
    private LayoutInflater layoutInflater;
    private TextView titleTextView;
    private TextView requiredTextView;
    private TextView descriptionTextView;
    private ImageButton deleteImgButton;
    private ImageButton editImgButton;
    private CheckBox doneCheckBox;

    public TodoListAdapter(Context applicationContext) {
        this.context = applicationContext;
        layoutInflater = (LayoutInflater.from(applicationContext));
        DBHelper dbHelper = new DBHelper(context);
        todos = dbHelper.getAllTodo();
    }
    
    @Override
    public int getCount() {
        return todos.size();
    }

    @Override
    public Object getItem(int i) {
        return todos.get(i);
    }

    @Override
    public long getItemId(int i) {
        return todos.get(i).hashCode();
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        view = layoutInflater.inflate(R.layout.todo_listview_item, null);

        titleTextView = view.findViewById(R.id.todoItem_title_textView);
        titleTextView.setText(todos.get(i).getTitle());
        descriptionTextView = view.findViewById(R.id.todoItem_description_textView);
        descriptionTextView.setText(todos.get(i).getDescription());
        requiredTextView = view.findViewById(R.id.todoItem_required_textView);
        requiredTextView.setText(todos.get(i).getRequiredInformation());

        doneCheckBox = view.findViewById(R.id.todo_checkbox);
        doneCheckBox.setChecked(todos.get(i).isDone());

        deleteImgButton = view.findViewById(R.id.todoItem_delete_imgButton);
        deleteImgButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DBHelper dbHelper = new DBHelper(context);
                dbHelper.deleteTodo(todos.get(i).getId());

                todos.remove(i);
                notifyDataSetChanged();
            }
        });
        editImgButton = view.findViewById(R.id.todoItem_edit_imgButton);

        return view;
    }

    public void onTodoAdded(){
        DBHelper dbHelper = new DBHelper(context);
        todos = dbHelper.getAllTodo();
        notifyDataSetChanged();
    }
}
