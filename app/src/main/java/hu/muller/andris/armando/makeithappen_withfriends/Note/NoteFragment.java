package hu.muller.andris.armando.makeithappen_withfriends.Note;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import hu.muller.andris.armando.makeithappen_withfriends.R;
import hu.muller.andris.armando.makeithappen_withfriends.Todo.TodoFragment;
import hu.muller.andris.armando.makeithappen_withfriends.database.DBHelper;
import hu.muller.andris.armando.makeithappen_withfriends.model.Note;

public class NoteFragment extends Fragment {
    private static final String TAG = "TodoFragment";

    private EditText newNoteET;
    private ImageButton newNoteButton;
    private ListView noteListView;
    private NoteListAdapter adapter;

    public static NoteFragment newInstance() {
        NoteFragment fragment = new NoteFragment();
        return fragment;
    }

    public NoteFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new NoteListAdapter(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_note, container, false);

        newNoteET = view.findViewById(R.id.note_fragment_edittext);
        newNoteButton = view.findViewById(R.id.note_fragment_imageview);
        newNoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Note note = new Note();
                note.setNote(newNoteET.getText().toString());
                note.setTimeCreated(System.currentTimeMillis());

                DBHelper dbHelper = new DBHelper(getContext());
                note.setId(dbHelper.createNote(note));
                adapter.addNote(note);

                newNoteET.setText("");
            }
        });

        noteListView = view.findViewById(R.id.note_fragment_listview);
        noteListView.setAdapter(adapter);

        return view;
    }

}
