package com.natodriod.timecontrol.fragments;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.natodriod.timecontrol.R;
import com.natodriod.timecontrol.activities.ImDoingActivity;
import com.natodriod.timecontrol.adapter.DoingListAdapter;
import com.natodriod.timecontrol.common.PrefManager;
import com.natodriod.timecontrol.data.DoingListData;
import com.natodriod.timecontrol.model.DoingList;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by natiqmustafa on 04.04.2017.
 */

public class DoingListFragment extends Fragment {

    private static final String TAG = "DoingListFragment";
    private DoingList updateChildren;
    private ListView lvDoingList;
    private List<DoingList> doingLists;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (new PrefManager(getContext()).isStartTransaction())
            selectActivity(null);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_doing_list, container, false);
        lvDoingList = (ListView) view.findViewById(R.id.lv_doing_list);
        registerForContextMenu(lvDoingList);

        listener(view);
        loadListData();
        return view;
    }

    private void loadListData() {
        doingLists = new ArrayList<>();
        DoingListAdapter adapter = new DoingListAdapter(getContext(),
                android.R.layout.simple_list_item_activated_2,
                new DoingListData(getContext()).getDoingList(0));
        lvDoingList.setAdapter(adapter);

    }

    private void listener(View view) {
        view.findViewById(R.id.btn_add_doing).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(true);
            }
        });

        lvDoingList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                //adapterView.getItemAtPosition(pos);
                DoingList list = (DoingList) adapterView.getItemAtPosition(pos);
                selectActivity(list);
            }
        });

        lvDoingList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int pos, long l) {
                try {
                    updateChildren = (DoingList) adapterView.getItemAtPosition(pos);
                    getActivity().openContextMenu(lvDoingList);
                } catch (Exception e) {
                    Toast.makeText(getContext(), getString(R.string.general_error), Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
                return true;
            }
        });
    }

    private void selectActivity(DoingList selected) {

        Intent intent = new Intent(getContext(), ImDoingActivity.class);
        intent.putExtra("IM_DOING", selected);
        startActivity(intent);
    }


    private void showDialog(final boolean isAdd){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        String title = isAdd ? getString(R.string.str_title_of_add_dialog) : getString(R.string.str_title_of_change_dialog);
        String buttonText = isAdd ? getString(R.string.str_add_doing) : getString(R.string.str_change);
        float remaining = 0;
        String text = "";
        if (!isAdd){
            text = updateChildren.getText();
            remaining = updateChildren.getRemaing();
        }
        builder.setTitle(title);

        LinearLayout layout = new LinearLayout(getContext());
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(25, 25, 25, 25);

        final EditText inputText = new EditText(getContext());
        inputText.setInputType(InputType.TYPE_CLASS_TEXT);
        inputText.setText(text);
        inputText.setHint(getString(R.string.str_doing_text));
        layout.addView(inputText);

        final EditText inputKod = new EditText(getContext());
        inputKod.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
        inputKod.setText(String.valueOf(remaining));
        inputKod.setHint(getString(R.string.str_doing_remaining));
        layout.addView(inputKod);

        builder.setView(layout);


        // Set up the buttons
        builder.setPositiveButton(buttonText, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (updateChildren == null)
                    updateChildren = new DoingList();

                updateChildren.setText(inputText.getText().toString().trim());
                updateChildren.setRemaing(inputKod.getText().toString().trim().equals("") ? 0 : Float.parseFloat(inputKod.getText().toString().trim()));
                if (isAdd)
                    submit(updateChildren);
                else
                    modifyData(updateChildren);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

    private void modifyData(DoingList doingList) {
        new DoingListData(getContext()).updateDoingList(doingList);
        loadListData();
    }

    private void submit(DoingList doingList) {
        new DoingListData(getContext()).saveDoingList(doingList);
        loadListData();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        if (v.getId()==R.id.lv_doing_list) {
            MenuInflater inflater = getActivity().getMenuInflater();
            inflater.inflate(R.menu.context_menu_doing_list, menu);
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.ctx_menu_change:
                showDialog(false);
                return true;
            case R.id.ctx_menu_delete:
                removeListItem();
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    private void removeListItem() {
        new DoingListData(getContext()).deleteDoingList(String.valueOf(updateChildren.getDoingListId()));
        loadListData();

    }
}
