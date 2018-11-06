package com.meso.ldrl96.segundoparcial;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentInclinacion.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentInclinacion#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentInclinacion extends Fragment implements SensorEventListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    DisplayMetrics display = new DisplayMetrics();
    ShapeDrawable mDrawable = new ShapeDrawable();
    int x = 0, y = 0, z = 0, modulov = 0, amaxv = 0;
    double gravedadv = SensorManager.STANDARD_GRAVITY;
    private int width, height;
    private Lienzo fondo;
    boolean bOrientacion = false;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public FragmentInclinacion() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentInclinacion.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentInclinacion newInstance(String param1, String param2) {
        FragmentInclinacion fragment = new FragmentInclinacion();
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

        View rootview = inflater.inflate(R.layout.fragment_fragment_inclinacion, container, false);


        getActivity().getWindowManager().getDefaultDisplay().getMetrics(display);
        width = 25;
        height = 25;

        RelativeLayout layout1 = (RelativeLayout)rootview.findViewById(R.id.layout1);
        fondo = new Lienzo(getActivity());
        layout1.addView(fondo);


        SensorManager sensorManager = (SensorManager)getActivity().getSystemService(Context.SENSOR_SERVICE);
        Sensor acelerometro = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(this, acelerometro, sensorManager.SENSOR_DELAY_FASTEST);
        List<Sensor> sensores = sensorManager.getSensorList(Sensor.TYPE_ALL);
        return rootview;
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

//        getActivity().getWindowManager().getDefaultDisplay().getMetrics(display);

        int margenMaxX = 400;
        int margenMinX = 25, margenMinY = 25;
        int margenMaxY = display.heightPixels/2;
        if (sensorEvent.sensor.getType() != Sensor.TYPE_ACCELEROMETER) {
            return;
        }
        synchronized (this) {
            //For each sensor
            switch (sensorEvent.sensor.getType()) {
                case Sensor.TYPE_MAGNETIC_FIELD:
                    break;
                case Sensor.TYPE_ACCELEROMETER:
                    if (bOrientacion == true) {

                        if (sensorEvent.values[1] > 0) {
                            if (x <= margenMaxX) {
                                x = x + (int) Math.pow(sensorEvent.values[1], 2);
                            }
                        } else {
                            if (x >= margenMinX) {
                                x = x - (int) Math.pow(sensorEvent.values[1], 2);
                            }
                        }
                        if (sensorEvent.values[0] > 0) {
                            if (y <= margenMaxY) {
                                y = y + (int) Math.pow(sensorEvent.values[0], 2);
                            }
                        } else {
                            if (y >= margenMinY) {
                                y = y - (int) Math.pow(sensorEvent.values[0], 2);
                            }
                        }
                    } else {
                        if (sensorEvent.values[0] < 0) {
                            if (x <= margenMaxX) {
                                x = x + (int) Math.pow(sensorEvent.values[0], 2);
                            }
                        } else {
                            if (x >= margenMinX) {
                                x = x - (int) Math.pow(sensorEvent.values[0], 2);
                            }
                        }
                        //Eje Y
                        if (sensorEvent.values[1] > 0) {
                            if (y <= margenMaxY) {
                                y = y + (int) Math.pow(sensorEvent.values[1], 2);
                            }
                        } else {
                            if (y >= margenMinY) {
                                y = y - (int) Math.pow(sensorEvent.values[1], 2);
                            }
                        }
                    }
            }
        }

        float[] mMagneticValues = new float[10];
        float[] mAccelerometerValues = new float[10];
        if (mMagneticValues != null && mAccelerometerValues != null) {
            float[] R = new float[16];
            SensorManager.getRotationMatrix(R, null, mAccelerometerValues, mMagneticValues);
            float[] orientation = new float[3];
            SensorManager.getOrientation(R, orientation);
            //if x have positives values the screen orientation is landscape in other case is portrait

            if (orientation[0] > 0) {
                bOrientacion = true;

                margenMaxX = 400;
                margenMinX = 25;
                margenMaxY = display.heightPixels/2;
                margenMinY = 25;
            } else {//Portrait
                bOrientacion = false;
                margenMaxX = 400;
                margenMinX = 25;
                margenMaxY = display.heightPixels/2;
                margenMinY = 25;
            }
        }
        fondo.invalidate();
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

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


    class Lienzo extends View {
        public Lienzo(Context context)
        {
            super(context);
            mDrawable = new ShapeDrawable(new OvalShape());
            mDrawable.getPaint().setColor(0xff74AC23);
            x=250;
            y=250;
            mDrawable.setBounds(x, y, x + width, y + height);
        }
        protected  void onDraw(Canvas canvas)
        {
            canvas.drawRGB(51, 204, 255);
            Paint pincel1 = new Paint();
            pincel1.setColor(Color.BLACK);
            pincel1.setStrokeWidth(4);
            pincel1.setStyle(Paint.Style.STROKE);


            canvas.drawCircle(x, y,20,pincel1);
//            RectF oval = new RectF(x+z, y+z,x + width,y + height);
            //           canvas.drawOval(oval,pincel1);
        }
    }
}
