package com.example.adhit.bikubiku.ui.home.akun;


import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.adhit.bikubiku.R;
import com.example.adhit.bikubiku.adapter.AkunAdapter;
import com.example.adhit.bikubiku.data.model.Home;
import com.example.adhit.bikubiku.data.model.User;
import com.example.adhit.bikubiku.presenter.AkunPresenter;
import com.example.adhit.bikubiku.ui.detailakun.DetailAkunActivity;
import com.example.adhit.bikubiku.ui.login.LoginActivity;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class AkunFragment extends Fragment implements View.OnClickListener, AkunView, AkunAdapter.OnCardViewClickListener {

    private RecyclerView rvMenu;
    private AkunAdapter adapter;
    private AkunPresenter presenter;
    private TextView tvName, tvEditProfil;
    private ImageView cimgPhotoProfile;

    public AkunFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_akun, container, false);
        rvMenu = view.findViewById(R.id.rv_akun_menu);
        tvName = view.findViewById(R.id.tv_name);
        tvEditProfil = view.findViewById(R.id.tv_edit_profil);
        cimgPhotoProfile = view.findViewById(R.id.cimg_profile_photo);
        initView();
        return  view;
    }

    public void initView(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        tvEditProfil.setOnClickListener(this);
        adapter = new AkunAdapter(getActivity());
        adapter.setOnClickDetailListener(this);
        rvMenu.setAdapter(adapter);
        rvMenu.setHasFixedSize(true);
        rvMenu.setLayoutManager(linearLayoutManager);
        presenter = new AkunPresenter(this);
        presenter.showListAkunMenu();
        presenter.showDataUser();
    }

    @Override
    public void showData(ArrayList<Home> akunArrayList) {
        adapter.setData(akunArrayList);
    }

    @Override
    public void showUserData(User user) {
        tvName.setText(user.getNama());

    }

    @SuppressLint("WrongConstant")
    @Override
    public void onMenuClicked(String string) {
        if(string.equals("Log Out")){
            presenter.userLogOut();
            startActivity(new Intent(getActivity(), LoginActivity.class));
            getActivity().finish();
        }else{
            Intent intent = new Intent(getActivity(), DetailAkunActivity.class);
            intent.putExtra("detail_akun", string);
            startActivity(intent);
        }
    }

    @Override
    public void onClick(View view) {
        if(view.getId()== R.id.tv_edit_profil){
            Intent intent = new Intent(getActivity(), DetailAkunActivity.class);
            intent.putExtra("detail_akun", "Profil");
            startActivity(intent);
        }
    }
}
