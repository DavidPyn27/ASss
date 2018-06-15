package com.davidpopayan.sena.guper.Fragments;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.davidpopayan.sena.guper.Controllers.Login;
import com.davidpopayan.sena.guper.R;
import com.davidpopayan.sena.guper.models.Persona;
import com.davidpopayan.sena.guper.models.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentPerfil extends Fragment implements View.OnClickListener{

    ImageView ImgPerfil;
    Button btnCambiarP,btnGuardar;
    Persona personaP= new Persona();
    User userP= new User() ;
    TextView txtnombre, txtapellidos, txttelefono,txtdocumento, txtemail, txtpassword;

    public FragmentPerfil() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_fragment_perfil, container, false);

        ImgPerfil = view.findViewById(R.id.ImgPerfil);
        btnCambiarP = view.findViewById(R.id.btnCambiarI);
        btnGuardar = view.findViewById(R.id.btnGuardar);
        inizialite(view);
        cargarPerfil();
        cargarUsuario();



        btnGuardar.setOnClickListener(this);


        btnCambiarP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                cargarImagen();

            }
        });

        return view;
    }


    @Override
    public void onStart() {
        super.onStart();
        cargarPerfil();
        cargarUsuario();
    }


    @Override
    public void onResume() {
        super.onResume();
        cargarPerfil();
        cargarUsuario();
    }

    public void inizialite(View v){

        txtnombre = v.findViewById(R.id.txtNombrePerfil);
        txtapellidos = v.findViewById(R.id.txtApellidosPerfil);
        txttelefono = v.findViewById(R.id.txttelefonoPerfil);
        txtdocumento = v.findViewById(R.id.txtdocumentoPerfil);
        txtemail = v.findViewById(R.id.txtCorreoPerfil);
        txtpassword = v.findViewById(R.id.txtpasswordPerfil);



    }
    private void cargarImagen() {

        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/");
        startActivityForResult(intent.createChooser(intent, "Selecciona la app"), 10);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            Uri path = data.getData();
            ImgPerfil.setImageURI(path);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.btnGuardar:
                Snackbar.make(v,"Se ha guardado correctamente",Snackbar.LENGTH_SHORT).show();
                break;
        }
    }

    public void cargarPerfil(){
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        String url = Login.personaUrl;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();

                personaP = gson.fromJson(response, Persona.class);




            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });


        requestQueue.add(stringRequest);

    }

    public void cargarUsuario(){
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        String url = Login.userUrl;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();

                userP = gson.fromJson(response, User.class);
                mostrarPerfil();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        requestQueue.add(stringRequest);
    }


    private void mostrarPerfil() {

        txtnombre.setText(personaP.getNombres());
        txtapellidos.setText(personaP.getApellidos());
        txttelefono.setText(personaP.getTelefono());
        txtdocumento.setText(personaP.getDocumentoIdentidad());
        txtemail.setText(userP.getEmail());
        txtpassword.setText(userP.getPassword());

    }



}
