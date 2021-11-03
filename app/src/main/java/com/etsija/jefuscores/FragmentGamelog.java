package com.etsija.jefuscores;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.etsija.jefuscores.db.Gamelog;
import com.etsija.jefuscores.ui.main.GamelogAdapter;
import com.etsija.jefuscores.ui.main.GamelogViewModel;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class FragmentGamelog extends Fragment {
    private static final String TAG = "FragmentGamelog";
    private GamelogViewModel gamelogViewModel;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.fragment_gamelog, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView recyclerView = view.findViewById(R.id.rvGamelog);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);

        final GamelogAdapter adapter = new GamelogAdapter();
        recyclerView.setAdapter(adapter);

        // getActivity() -> requireActivity()
        gamelogViewModel = new ViewModelProvider(requireActivity()).get(GamelogViewModel.class);
        gamelogViewModel.getAllGamelogs().observe(getViewLifecycleOwner(), new Observer<List<Gamelog>>() {
            @Override
            public void onChanged(List<Gamelog> gamelogs) {
                adapter.setGamelogs(gamelogs);
            }
        });

        // Swipe to delete gamelog
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

                // Ask the user before deleting a gamelog
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Poista")
                        .setMessage("Poistetaanko tämä peli?")
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton("KYLLÄ", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                // Yes button clicked -> delete this gamelog from the database
                                gamelogViewModel.delete(adapter.getGamelogAt(viewHolder.getAdapterPosition()));
                                Toast.makeText(getContext(), "Peli poistettu", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("EI", null)
                        .show();
            }
        }).attachToRecyclerView(recyclerView);
    }
}
