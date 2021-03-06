package com.example.adhit.bikubiku.ui.home.home;


import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.example.adhit.bikubiku.R;
import com.example.adhit.bikubiku.adapter.HomeAdapter;
import com.example.adhit.bikubiku.data.model.Home;
import com.example.adhit.bikubiku.presenter.HomePresenter;
import com.example.adhit.bikubiku.ui.psychologychatting.ChattingPsychologyFragment;
import com.example.adhit.bikubiku.ui.listpsychologistchattinghistory.ListChattingPsychologistHistoryFragment;
import com.example.adhit.bikubiku.ui.listpsychologist.ListPsychologistFragment;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements HomeView, HomeAdapter.OnDetailListener {

    private SliderLayout sliderLayout;
    private RecyclerView rvMenu;
    private HomeAdapter adapter;
    private HomePresenter presenter;
    private TextView tvSaldo;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        sliderLayout = view.findViewById(R.id.slider);
        rvMenu = view.findViewById(R.id.rv_menu);
        tvSaldo = view.findViewById(R.id.tv_saldo);
        init();
        initView();
        return view;
    }

    public void initView(){
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 4);
        adapter = new HomeAdapter(getActivity());
        adapter.setOnClickDetailListener(this);
        rvMenu.setAdapter(adapter);
        rvMenu.setHasFixedSize(true);
        rvMenu.setLayoutManager(gridLayoutManager);
            presenter = new HomePresenter(this);
            presenter.showListHome();
            presenter.showSaldo();
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.showSaldo();
    }

    public void init(){
        ArrayList<Integer> gambar = new ArrayList<>();
        gambar.add(R.drawable.a1);
        gambar.add(R.drawable.a2);
        gambar.add(R.drawable.a2);
        for(int i=0; i<gambar.size(); i++){
            TextSliderView textSliderView = new TextSliderView(getActivity());
            // initialize a SliderLayout
            textSliderView
                    .image(gambar.get(i))
                    .setScaleType(BaseSliderView.ScaleType.Fit);
            sliderLayout.addSlider(textSliderView);

        }

        sliderLayout.setPresetTransformer(SliderLayout.Transformer.Accordion);
        sliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
    }

    @Override
    public void showData(ArrayList<Home> homeArrayList) {
        adapter.setData(homeArrayList);
    }

    @Override
    public void showSaldo(String balance) {
        tvSaldo.setText(balance);
    }

    @Override
    public void onItemDetailClicked(String menu) {
        if(menu.equals("Konsultasi Psikologi")){
            getFragmentManager().beginTransaction().
                    replace(R.id.frame_container,
                            new ListPsychologistFragment(),
                            ListPsychologistFragment.class.getSimpleName())
                    .addToBackStack(ListPsychologistFragment.class.getSimpleName())
                    .commit();
        }
        if(menu.equals("History Konsultasi")){
            getFragmentManager().beginTransaction().
                    replace(R.id.frame_container,
                            new ListChattingPsychologistHistoryFragment(),
                            ChattingPsychologyFragment.class.getSimpleName())
                    .addToBackStack(ListChattingPsychologistHistoryFragment.class.getSimpleName())
                    .commit();
        }
    }
}
