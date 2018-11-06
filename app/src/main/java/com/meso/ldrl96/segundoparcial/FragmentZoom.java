package com.meso.ldrl96.segundoparcial;

import android.content.Context;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentZoom.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentZoom#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentZoom extends Fragment implements SensorEventListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    TextView texto;
    LinearLayout Linear;
    SensorManager sm;
    Sensor sensorZoom;
    private OnFragmentInteractionListener mListener;

    public FragmentZoom() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentZoom.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentZoom newInstance(String param1, String param2) {
        FragmentZoom fragment = new FragmentZoom();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootview = inflater.inflate(R.layout.fragment_fragment_zoom, container, false);
        texto = (TextView)rootview.findViewById(R.id.TextoZom);
        Linear = (LinearLayout)rootview.findViewById(R.id.layoutzoom);



        sm = (SensorManager)getActivity().getSystemService(Context.SENSOR_SERVICE);
        sensorZoom = sm.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        sm.registerListener(this, sensorZoom, SensorManager.SENSOR_DELAY_FASTEST);

        return  rootview;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {

        String txt = String.valueOf(sensorEvent.values[0]);
        //texto.setText(txt);
        float valor = Float.parseFloat(txt);


        if(valor ==0)
        {
            texto.setTextSize(15);
         //   Linear.setBackgroundColor(Color.WHITE);
        }
        else
        {
            texto.setTextSize(25);
        //    Linear.setBackgroundColor(Color.RED);
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    @Override
    public void onResume() {
        super.onResume();
        sm.registerListener(this,sm.getDefaultSensor(Sensor.TYPE_PROXIMITY),SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onPause() {
        super.onStop();
        sm.unregisterListener(this);
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
