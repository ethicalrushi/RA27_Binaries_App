package binaries.app.codeutsava.restapi.fragments;


import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;

import binaries.app.codeutsava.R;
import binaries.app.codeutsava.restapi.adapters.AdapterActiveBid;
import binaries.app.codeutsava.restapi.adapters.AdapterProduce;
import binaries.app.codeutsava.restapi.model.farmer.FarmerActiveBidListResponse;
import binaries.app.codeutsava.restapi.model.farmer.FarmerProduceResponse;
import binaries.app.codeutsava.restapi.restapi.APIServices;
import binaries.app.codeutsava.restapi.restapi.AppClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentFarmerProduce extends DialogFragment {
    RecyclerView recyclerView;
    AdapterProduce mAdapter;

    public FragmentFarmerProduce() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Light_NoTitleBar);
    }

    @Override
    public void onStart() {
        super.onStart();

        Dialog dialog = getDialog();

        if(dialog != null){
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;

            dialog.getWindow().setLayout(width, height);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_farmer_produce, container, false);
        recyclerView = view.findViewById(R.id.farmerProduceListRecyclerView);

        getFarmerActiveBids();


        return view;
    }

    public void getFarmerActiveBids() {
        APIServices apiServices = AppClient.getInstance().createService(APIServices.class);
        Call<List<FarmerProduceResponse>> call = apiServices.getFarmerProduceList();

        call.enqueue(new Callback<List<FarmerProduceResponse>>() {
            @Override
            public void onResponse(Call<List<FarmerProduceResponse>> call, Response<List<FarmerProduceResponse>> response) {
                mAdapter = new AdapterProduce(response.body(), getActivity());
                mAdapter.setFragManager(getFragmentManager());
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                recyclerView.setAdapter(mAdapter);
                mAdapter.notifyDataSetChanged();

//                Log.v("response: ",response.body());
//                Toast.makeText(getContext(), response.body(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<List<FarmerProduceResponse>> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage().toString(), Toast.LENGTH_LONG).show();
            }
        });
    }

}