package com.example.adhit.bikubiku.ui.home.akun;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.adhit.bikubiku.R;
import com.example.adhit.bikubiku.data.model.Home;
import com.example.adhit.bikubiku.ui.home.home.HomeAdapter;
import com.example.adhit.bikubiku.ui.home.home.HomePresenter;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class AkunFragment extends Fragment implements AkunView {

    private RecyclerView rvMenu;
    private AkunAdapter adapter;
    private AkunPresenter presenter;

    public AkunFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_akun, container, false);
        rvMenu = view.findViewById(R.id.rv_akun_menu);
        initView();
        return  view;
    }

    public void initView(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        adapter = new AkunAdapter(getContext());
        rvMenu.setAdapter(adapter);
        rvMenu.setHasFixedSize(true);
        rvMenu.setLayoutManager(linearLayoutManager);
        presenter = new AkunPresenter(this);
        presenter.showListAkunMenu();
    }

    @Override
    public void showData(ArrayList<Home> akunArrayList) {
        adapter.setData(akunArrayList);
    }
}
