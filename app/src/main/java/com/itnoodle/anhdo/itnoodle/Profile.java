package com.itnoodle.anhdo.itnoodle;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.arch.lifecycle.ViewModelProvider;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.itnoodle.anhdo.itnoodle.viewmodels.StudentViewModel;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Profile.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Profile#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Profile extends Fragment {
    public final static String LOG_TAG = Profile.class.getSimpleName();
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private EditText mStdCodeView;
    private EditText mTermView;
    private EditText mYearView;
    private TextView tvName;
    private TextView tvBirthday;
    private TextView tvKlass;
    private View mProgressView;
    private View mLoginFormView;

    private OnFragmentInteractionListener mListener;

    public Profile() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Profile.
     */
    // TODO: Rename and change types and number of parameters
    public static Profile newInstance(String param1, String param2) {
        Profile fragment = new Profile();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Button mLoginButton = (Button)getView().findViewById(R.id.btn_login);
        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });
        mStdCodeView = (EditText) getView().findViewById(R.id.std_code);
        mTermView = (EditText) getView().findViewById(R.id.term);
        mYearView = (EditText) getView().findViewById(R.id.year);
        tvBirthday = (TextView) getView().findViewById(R.id.tv_birthday);
        tvKlass = (TextView) getView().findViewById(R.id.tv_klass);
        tvName = (TextView) getView().findViewById(R.id.tv_fullname);
        mLoginFormView = (View)getView().findViewById(R.id.login_form);
        mProgressView = (View)getView().findViewById(R.id.login_progress);

        tvName.setText(MainActivity.studentViewModel.studentInfo().fullname);
        tvKlass.setText(MainActivity.studentViewModel.studentInfo().klass);
        tvBirthday.setText(MainActivity.studentViewModel.studentInfo().birthday);
        mStdCodeView.setText(MainActivity.studentViewModel.studentInfo().code);
        mTermView.setText(MainActivity.studentViewModel.studentInfo().term);
        mYearView.setText(MainActivity.studentViewModel.studentInfo().year);
//        ((MainActivity)getActivity()).updateStudentInfo();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {
        // Reset errors.
        mStdCodeView.setError(null);
        mTermView.setError(null);
        mYearView.setError(null);

        // Store values at the time of the login attempt.
        final String studentCode = mStdCodeView.getText().toString();
        String term = mTermView.getText().toString();
        String year = mYearView.getText().toString();
        Log.i(LOG_TAG, studentCode);
        Log.i(LOG_TAG, term);
        Log.i(LOG_TAG, year);

        boolean cancel = false;
        View focusView = null;

        // Check for a valid student code.
        if (!isStudentCodeValid(studentCode)) {
            mStdCodeView.setError(getString(R.string.error_invalid_student_code));
            focusView = mStdCodeView;
            cancel = true;
        }

        // Check for a valid term if student code entered one.
        if (!cancel && !isTermValid(term)) {
            mTermView.setError(getString(R.string.error_invalid_term));
            focusView = mTermView;
            cancel = true;
        }

        // Check for a valid year if student code entered one and term also entered one.
        if (!cancel && !isYearValid(year)) {
            mYearView.setError(getString(R.string.error_invalid_year));
            focusView = mYearView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            MainActivity.studentViewModel.init(studentCode, term, year);
        }
    }

    private boolean isTermValid(String term) {
        return term.equals("1") || term.equals("2");
    }

    private boolean isYearValid(String year) {
        return year.equals("2017-2018") || year.equals("2016-2017") || year.equals("2018-2019");
    }

    private boolean isStudentCodeValid(String studentCode) {
        return studentCode.length() == 8;
    }
    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }
}
