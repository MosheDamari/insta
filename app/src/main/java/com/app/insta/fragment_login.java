package com.app.insta;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.app.insta.Model.Model;
import com.app.insta.Model.Profile;
import com.app.insta.Model.ProfileViewMdoel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link fragment_login.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link fragment_login#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fragment_login extends Fragment {
    TextView email, password, name;
    Button login, register;
    ProfileViewMdoel model;
    Profile currentUserProfile;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public fragment_login() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment fragment_login.
     */
    // TODO: Rename and change types and number of parameters
    public static fragment_login newInstance(String param1, String param2) {
        fragment_login fragment = new fragment_login();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        model = new ProfileViewMdoel();
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    private void updateUI() {
        Navigation.findNavController(getView()).navigate(R.id.feed);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_login, container, false);
        email = v.findViewById(R.id.login_email);
        password = v.findViewById(R.id.login_password);
        name = v.findViewById(R.id.login_user_name);
        login = v.findViewById(R.id.login_login_button);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(model.getCurrentUser() != null) {
                    model.getProfile(model.getmAuth().getUid(), new Model.GetProfileListener() {
                        @Override
                        public void onComplete(Profile profile) {
                            MyApplication.currentProfile = profile;
                            updateUI();
                        }
                    });

                }else{
                    Login();
                }
            }
        });
        register =  v.findViewById(R.id.login_register_button);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(model.getCurrentUser() != null) {
                    model.getProfile(model.getmAuth().getUid(), new Model.GetProfileListener() {
                        @Override
                        public void onComplete(Profile profile) {
                            MyApplication.currentProfile = profile;
                            updateUI();
                        }
                    });

                }else{
                    Register();
                }
            }
        });
        return v;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(model != null && model.getCurrentUser() != null){
            updateUI();
        }
    }

    public void Register(){
        model.getmAuth().createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Profile currentProfile = new Profile(model.getmAuth().getUid(),name.getText().toString(), email.getText().toString());
                            MyApplication.currentProfile = currentProfile;
                            model.addProfile(currentProfile, new Model.AddProfileListener() {
                                @Override
                                public void onComplete(boolean success) {

                                }
                            });
                            updateUI();
                        } else {
                            Toast.makeText(MyApplication.getContext(), "Registry failed.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    public void Login() {
        model.getmAuth().signInWithEmailAndPassword(email.getText().toString(), password.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    model.getProfile(model.getmAuth().getUid(), new Model.GetProfileListener() {
                        @Override
                        public void onComplete(Profile profile) {
                            MyApplication.currentProfile = profile;
                            updateUI();
                        }
                    });
                }else {
                    Toast.makeText(MyApplication.getContext(), "Authentication failed.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
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
}
