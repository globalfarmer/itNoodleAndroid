package com.itnoodle.anhdo.itnoodle;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.itnoodle.anhdo.itnoodle.dummy.CourseContent;
import com.itnoodle.anhdo.itnoodle.utilities.ApiUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Profile.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Profile#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Profile extends Fragment {
    public final static String LOG_TAG = "PROFILE_FRAG";
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private EditText mStdCodeView;
    private EditText mTermView;
    private EditText mYearView;
    private RequestQueue queue;
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
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(Student.hasInfo)
            setStudentInfo();
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
            showProgress(true);
            queue = Volley.newRequestQueue(getContext());
            queue.add(new StringRequest(Request.Method.GET, ApiUtils.getStudentUrl(studentCode, term, year.substring(0,4)),
                    new Response.Listener<String>(){
                        @Override
                        public void onResponse(String response) {
                            showProgress(false);
                            try {
                                JSONObject student = new JSONObject(response);
                                Student.setCode(student.optString(Student.KEY_CODE));
                                Student.fullname = student.optString(Student.KEY_FULLNAME);
                                Student.birthday = student.optString(Student.KEY_BIRTHDAY);
                                Student.klass = student.optString(Student.KEY_KLASS);
                                Student.hasInfo = true;
                                setStudentInfo();
                                JSONObject slots = student.optJSONObject(Student.KEY_SLOTS);
                                Log.i(LOG_TAG, slots.toString());
                                Student.ITEMS.clear();
                                Student.ITEM_MAP.clear();
                                JSONObject course;
                                for(Iterator<String> iKey=slots.keys();iKey.hasNext();) {
                                    course = slots.optJSONObject(iKey.next().toString());
                                    Student.addCourse(
                                            new CourseContent.CourseItem(
                                                    course.optString(CourseContent.CourseItem.CODE),
                                                    course.optString(CourseContent.CourseItem.NAME),
                                                    course.optString(CourseContent.CourseItem.CREDIT),
                                                    course.optString(CourseContent.CourseItem.GROUP),
                                                    course.optString(CourseContent.CourseItem.NOTE)
                                            )
                                    );
                                }
                            } catch(final JSONException e) {
                                Log.e(LOG_TAG, "Parse JSON failed");
                                Log.e(LOG_TAG, e.getMessage());
                            }

                            Log.i(LOG_TAG, response);
                        }
                    }, new Response.ErrorListener(){
                @Override
                public void onErrorResponse(VolleyError error) {
                    showProgress(false);
                    Log.e(LOG_TAG, "request get student failed");
                }
            }));
        }
    }
    private void setStudentInfo() {
        tvName.setText(Student.fullname);
        tvKlass.setText(Student.klass);
        tvBirthday.setText(Student.birthday);
        ((MainActivity)getActivity()).updateStudentInfo();
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
    static class Student {
        public static final List<CourseContent.CourseItem> ITEMS = new ArrayList<CourseContent.CourseItem>();
        public static final Map<String, CourseContent.CourseItem> ITEM_MAP = new HashMap<String, CourseContent.CourseItem>();
        public static final String KEY_CODE = "code";
        public static final String KEY_FULLNAME = "fullname";
        public static final String KEY_KLASS = "klass";
        public static final String KEY_BIRTHDAY = "birthday";
        public static final String KEY_SLOTS = "slots";
        public static String code;
        public static String fullname;
        public static String klass;
        public static String birthday;
        public static String email;
        public static boolean hasInfo = false;
        static public void addCourse(CourseContent.CourseItem courseItem) {
            ITEMS.add(courseItem);
            ITEM_MAP.put(courseItem.code, courseItem);
        }
        public static void setCode(String code) {
            Student.code = code;
            Student.email = code + "@vnu.edu.vn";
        }
        public static String getEmail() {
            return email;
        }
    }
}
