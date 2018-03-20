package br.edu.iff.pooa20172.Trabalho_P2_2018_web_service;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class MarcaActivity extends AppCompatActivity implements ClickRecyclerViewListener {


    private List<Marca> resultadoMarca = new ArrayList<Marca>();
    private String durl = "http://fipeapi.appspot.com/api/1/carros/marcas.json";
    String TAG = this.getClass().getSimpleName();
    RecyclerView recyclerView;
    MarcaAdapter marcaAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marca);

        BuscaMarcaTask busca = new BuscaMarcaTask(durl,null, resultadoMarca);
        busca.execute();

        recyclerView = (RecyclerView) findViewById(R.id.rv_marca);
        marcaAdapter.setClickRecyclerViewListener(this);
    }

    @Override
    public void onClick(Object object) {
        Marca marca = (Marca) object;
        Toast.makeText(getContext(), marca.getNome() + " " + marca.getId(), Toast.LENGTH_SHORT).show();
    }

    public Context getContext() {
        return this;
    }


    public class BuscaMarcaTask extends AsyncTask<String, Void, List<Marca>> {

        ProgressDialog dialog;
        String url;
        Object o;
        List<Marca> resultado;

        public BuscaMarcaTask(String url, Object o, List<Marca> resultado) {
            this.url = url;
            this.o = o;
            this.resultado = resultado;
        }

        @Override
        protected void onPreExecute() {

            dialog = ProgressDialog.show(MarcaActivity.this, "Aguarde", "Fazendo download do JSON");
            dialog.show();
        }

        @Override
        protected void onPostExecute(List<Marca> result) {

            super.onPostExecute(result);
            Log.w(TAG, ".onPostExecute..................." + result.size());
            dialog.dismiss();

            if (result.size() <= 0) {
                AlertDialog.Builder builder = new AlertDialog.Builder(
                        MarcaActivity.this).setTitle("Erro").setMessage("Não foi possível acessar as informações!!").setPositiveButton("OK", null);
                builder.create().show();
            }

            resultadoMarca = result;
            marcaAdapter = new MarcaAdapter(resultadoMarca, getContext());

            RecyclerView.LayoutManager layout = new LinearLayoutManager(getContext(),
                    LinearLayoutManager.VERTICAL, false);
            recyclerView.setAdapter(marcaAdapter);
            recyclerView.setLayoutManager(layout);
            Log.w(TAG, "Resultado Marca >>>>>>>>>>> " + resultadoMarca.size());

        }

        @Override
        protected List<Marca> doInBackground(String... params) {
            RestFullHelper http = new RestFullHelper();

            return getMarcas(http.doGet(durl));
        }

        private List<Marca> getMarcas(JSONArray marcas) {

            List<Marca> marcasList = new ArrayList<Marca>();
            for (int i = 0; i < marcas.length(); i++) {
                try {
                    JSONObject jMarca = marcas.getJSONObject(i);
                    Marca marca = new Marca(jMarca.getString("nome"), jMarca.getString("fipe_name"), Integer.parseInt(jMarca.getString("order")), jMarca.getString("key"), Integer.parseInt(jMarca.getString("id")));
                    marcasList.add(marca);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            Log.w(TAG, "...................> " + marcasList.size());
            return marcasList;
        }

    }
}
