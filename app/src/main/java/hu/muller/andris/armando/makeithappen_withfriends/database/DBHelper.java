package hu.muller.andris.armando.makeithappen_withfriends.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import hu.muller.andris.armando.makeithappen_withfriends.model.Alarm;
import hu.muller.andris.armando.makeithappen_withfriends.model.Note;
import hu.muller.andris.armando.makeithappen_withfriends.model.Todo;

/**
 * Created by Muller Andras on 9/18/2017.
 */

public class DBHelper extends SQLiteOpenHelper {
    private static final String TAG = "SQLiteOpenHelper";

    private static final String DATABASE_NAME = "MakeItHappen.db";
    private static final int DATABASE_VERSION = 2;
    private static final String ROWID = "rowid";

    private static final String TABLE_ALARMS = "alarms";
    private static final String ALARMS_COLUMN_NOTE = "note";
    private static final String ALARMS_COLUMN_TIME = "time";
    private static final String ALARMS_COLUMN_REPEATTYPE = "repeattype";

    private static final String TABLE_TODOS = "todos";
    private static final String TODOS_COLUMN_DESCRIPTION = "description";
    private static final String TODOS_COLUMN_TITLE = "title";
    private static final String TODOS_COLUMN_CREATED_AT = "created_at";
    private static final String TODOS_COLUMN_REQUIRED = "required";
    private static final String TODOS_COLUMN_ALARM_ID = "alarm_id";
    private static final String TODOS_COLUMN_PRIORITY = "priority";
    private static final String TODOS_COLUMN_CATEGORY = "category";
    private static final String TODOS_COLUMN_IS_DONE = "is_done";

    private static final String TABLE_NOTES = "notes";
    private static final String NOTES_COLUMN_NOTE = "note";
    private static final String NOTES_COLUMN_TIME_CREATED = "time_created";

    private static final String CREATE_ALARMS_TABLE =
            "create table " + TABLE_ALARMS +
            "(" + ALARMS_COLUMN_NOTE + " text, " +
                ALARMS_COLUMN_TIME + " integer, " +
                ALARMS_COLUMN_REPEATTYPE + " text" + ")";

    private static final String CREATE_TODOS_TABLE =
            "create table " + TABLE_TODOS +
                    "(" + TODOS_COLUMN_CREATED_AT + " integer, " +
                    TODOS_COLUMN_TITLE + " text, " +
                    TODOS_COLUMN_PRIORITY + " text," +
                    TODOS_COLUMN_REQUIRED + " text, " +
                    TODOS_COLUMN_CATEGORY + " text, " +
                    TODOS_COLUMN_DESCRIPTION + " text, " +
                    TODOS_COLUMN_IS_DONE + " integer, " +
                    TODOS_COLUMN_ALARM_ID + " integer" +")";

    private static final String CREATE_NOTES_TABLE =
            "create table " + TABLE_NOTES +
                    "(" + NOTES_COLUMN_NOTE + " text, " +
                    NOTES_COLUMN_TIME_CREATED + " integer " + ")";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, 4);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_ALARMS_TABLE);
        sqLiteDatabase.execSQL(CREATE_TODOS_TABLE);
        sqLiteDatabase.execSQL(CREATE_NOTES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_ALARMS);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_TODOS);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTES);
        onCreate(sqLiteDatabase);
    }

/**********************************************************************************************************/

    public long createAlarm(Alarm alarm) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(ALARMS_COLUMN_NOTE, alarm.getNote());
        values.put(ALARMS_COLUMN_TIME, alarm.getTime());
        values.put(ALARMS_COLUMN_REPEATTYPE, alarm.getRepeatType());

        return db.insert(TABLE_ALARMS, null, values);
    }

    public Alarm getAlarm(long alarm_id) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT * FROM " + TABLE_ALARMS + " WHERE " +
                ROWID + " = " + alarm_id;

        Log.e(TAG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null)
            c.moveToFirst();

        Alarm alarm = new Alarm();
        alarm.setId(alarm_id);
        alarm.setNote((c.getString(c.getColumnIndex(ALARMS_COLUMN_NOTE))));
        alarm.setTime(c.getLong(c.getColumnIndex(ALARMS_COLUMN_TIME)));

        return alarm;
    }

    public List<Alarm> getAllAlarm() {
        List<Alarm> alarms = new ArrayList<>();
        String selectQuery = "SELECT "+ ROWID + "," +
                ALARMS_COLUMN_TIME + "," +
                ALARMS_COLUMN_NOTE + "," +
                ALARMS_COLUMN_REPEATTYPE +
                " FROM " + TABLE_ALARMS;

        Log.e(TAG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        if (c.moveToFirst()) {
            do {
                Alarm alarm = new Alarm();
                alarm.setId(c.getLong((c.getColumnIndex(ROWID))));
                alarm.setNote((c.getString(c.getColumnIndex(ALARMS_COLUMN_NOTE))));
                alarm.setTime(c.getLong(c.getColumnIndex(ALARMS_COLUMN_TIME)));

                alarms.add(alarm);
            } while (c.moveToNext());
        }

        return alarms;
    }

    public int getAlarmCount() {
        String countQuery = "SELECT  * FROM " + TABLE_ALARMS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();
        cursor.close();

        return count;
    }

    public int updateAlarm(Alarm alarm) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(ALARMS_COLUMN_NOTE, alarm.getNote());
        values.put(ALARMS_COLUMN_TIME, alarm.getTime());
        values.put(ALARMS_COLUMN_REPEATTYPE, alarm.getRepeatType());

        return db.update(TABLE_ALARMS, values, ROWID + " = ?",
                new String[] { String.valueOf(alarm.getId()) });
    }

    public void deleteAlarm(long alarmID) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_ALARMS, ROWID + " = ?",
                new String[] { String.valueOf(alarmID) });
    }

///*********** TODOS *********************************************************************************/

public long createTodo(Todo todo) {
    SQLiteDatabase db = this.getWritableDatabase();

    ContentValues values = new ContentValues();
    values.put(TODOS_COLUMN_CREATED_AT, todo.getCreatedDate());
    values.put(TODOS_COLUMN_TITLE, todo.getTitle());
    values.put(TODOS_COLUMN_PRIORITY, todo.getPriority());
    values.put(TODOS_COLUMN_REQUIRED, todo.getRequiredInformation());
    values.put(TODOS_COLUMN_CATEGORY, todo.getTodoCategory());
    values.put(TODOS_COLUMN_DESCRIPTION, todo.getDescription());
    values.put(TODOS_COLUMN_IS_DONE, todo.isDone() ? 1 : 0);
    values.put(TODOS_COLUMN_ALARM_ID, todo.getAlarm_id());

    return db.insert(TABLE_TODOS, null, values);
}

    public Todo getTodo(long todo_id) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT  * FROM " + TABLE_TODOS + " WHERE " +
                ROWID + " = " + todo_id;

        Log.e(TAG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null)
            c.moveToFirst();

        Todo todo = new Todo();
        todo.setId(todo_id);
        todo.setCreatedDate((c.getLong(c.getColumnIndex(TODOS_COLUMN_CREATED_AT))));
        todo.setTitle(c.getString(c.getColumnIndex(TODOS_COLUMN_TITLE)));
        todo.setPriority(c.getString(c.getColumnIndex(TODOS_COLUMN_PRIORITY)));
        todo.setRequiredInformation(c.getString(c.getColumnIndex(TODOS_COLUMN_REQUIRED)));
        todo.setDescription(c.getString(c.getColumnIndex(TODOS_COLUMN_DESCRIPTION)));
        todo.setDone(c.getInt(c.getColumnIndex(TODOS_COLUMN_IS_DONE)) == 1);
        todo.setTodoCategory(c.getString(c.getColumnIndex(TODOS_COLUMN_TITLE)));
        todo.setAlarm_id(c.getLong(c.getColumnIndex(TODOS_COLUMN_ALARM_ID)));

        return todo;
    }

    public List<Todo> getAllTodo() {
        List<Todo> todos = new ArrayList<>();
        String selectQuery = "SELECT "+ TODOS_COLUMN_PRIORITY + "," +
                TODOS_COLUMN_TITLE + "," +
                TODOS_COLUMN_CATEGORY + "," +
                TODOS_COLUMN_REQUIRED + "," +
                TODOS_COLUMN_DESCRIPTION + "," +
                TODOS_COLUMN_ALARM_ID + "," +
                TODOS_COLUMN_IS_DONE + "," +
                ROWID + "," +
                TODOS_COLUMN_CREATED_AT +
                " FROM " + TABLE_TODOS;

        Log.e(TAG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        if (c.moveToFirst()) {
            do {
                Todo todo = new Todo();
                todo.setId(c.getLong((c.getColumnIndex(ROWID))));
                todo.setDescription((c.getString(c.getColumnIndex(TODOS_COLUMN_DESCRIPTION))));
                todo.setTitle((c.getString(c.getColumnIndex(TODOS_COLUMN_TITLE))));
                todo.setRequiredInformation((c.getString(c.getColumnIndex(TODOS_COLUMN_REQUIRED))));
                todo.setPriority(c.getString(c.getColumnIndex(TODOS_COLUMN_PRIORITY)));
                todo.setTodoCategory(c.getString(c.getColumnIndex(TODOS_COLUMN_CATEGORY)));
                todo.setDone(c.getInt(c.getColumnIndex(TODOS_COLUMN_IS_DONE)) == 1);
                todo.setAlarm_id((c.getLong(c.getColumnIndex(TODOS_COLUMN_ALARM_ID))));
                todo.setCreatedDate(c.getLong(c.getColumnIndex(TODOS_COLUMN_CREATED_AT)));

                todos.add(todo);
            } while (c.moveToNext());
        }

        return todos;
    }

    public int getTodoCount() {
        String countQuery = "SELECT  * FROM " + TABLE_TODOS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();
        cursor.close();

        return count;
    }

    public int updateTodo(Todo todo) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(TODOS_COLUMN_CREATED_AT, todo.getCreatedDate());
        values.put(TODOS_COLUMN_TITLE, todo.getTitle());
        values.put(TODOS_COLUMN_PRIORITY, todo.getPriority());
        values.put(TODOS_COLUMN_REQUIRED, todo.getRequiredInformation());
        values.put(TODOS_COLUMN_CATEGORY, todo.getTodoCategory());
        values.put(TODOS_COLUMN_IS_DONE, todo.isDone() ? 1 : 0);
        values.put(TODOS_COLUMN_DESCRIPTION, todo.getDescription());
        values.put(TODOS_COLUMN_ALARM_ID, todo.getAlarm_id());

        return db.update(TABLE_TODOS, values, ROWID + " = ?",
                new String[] { String.valueOf(todo.getId()) });
    }

    public void deleteTodo(long todoID) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_TODOS, ROWID + " = ?",
                new String[] { String.valueOf(todoID) });
    }

/**************     NOTES        ****************************************************************************************/


    public long createNote(Note note) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(NOTES_COLUMN_NOTE, note.getNote());
        values.put(NOTES_COLUMN_TIME_CREATED, note.getTimeCreated());

        return db.insert(TABLE_NOTES, null, values);
    }

    public Note getNote(long note_id) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT * FROM " + TABLE_NOTES + " WHERE " +
                ROWID + " = " + note_id;

        Log.e(TAG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null)
            c.moveToFirst();

        Note note = new Note();
        note.setId(note_id);
        note.setNote((c.getString(c.getColumnIndex(NOTES_COLUMN_NOTE))));
        note.setTimeCreated(c.getLong(c.getColumnIndex(NOTES_COLUMN_TIME_CREATED)));

        return note;
    }

    public List<Note> getAllNote() {
        List<Note> notes = new ArrayList<>();
        String selectQuery = "SELECT "+ ROWID + "," +
                NOTES_COLUMN_TIME_CREATED + "," +
                NOTES_COLUMN_NOTE +
                " FROM " + TABLE_NOTES;

        Log.e(TAG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        if (c.moveToFirst()) {
            do {
                Note note = new Note();
                note.setId(c.getLong((c.getColumnIndex(ROWID))));
                note.setNote((c.getString(c.getColumnIndex(NOTES_COLUMN_NOTE))));
                note.setTimeCreated(c.getLong(c.getColumnIndex(NOTES_COLUMN_TIME_CREATED)));

                notes.add(note);
            } while (c.moveToNext());
        }

        return notes;
    }

    public int getNoteCount() {
        String countQuery = "SELECT  * FROM " + TABLE_NOTES;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();
        cursor.close();

        return count;
    }

    public int updateNote(Note note) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(NOTES_COLUMN_NOTE, note.getNote());
        values.put(NOTES_COLUMN_TIME_CREATED, note.getTimeCreated());

        return db.update(TABLE_NOTES, values, ROWID + " = ?",
                new String[] { String.valueOf(note.getId()) });
    }

    public void deleteNote(long noteID) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NOTES, ROWID + " = ?",
                new String[] { String.valueOf(noteID) });
    }
}
