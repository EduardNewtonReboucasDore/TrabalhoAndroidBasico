package com.studio.trabalho;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    ListView lvClientes;
    Button botaoAdd;

    BancoDados db = new BancoDados(this);

    List<Cliente> lista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvClientes = (ListView)findViewById(R.id.lvClientes);
        botaoAdd = (Button)findViewById(R.id.buttonAdd);

        lista = db.listatodosClientes();
        final ClienteAdapterList clienteAdapterList = new ClienteAdapterList(MainActivity.this, lista);
        lvClientes.setAdapter(clienteAdapterList);

        lvClientes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Cliente cliente = clienteAdapterList.getItem(i);

                Intent intent = new Intent(MainActivity.this, FormularioCliente.class);
                intent.putExtra("codigo", Integer.toString(cliente.getId()));
                startActivity(intent);

            }
        });
    }

    public void add(View view){

        Intent intent = new Intent(MainActivity.this, FormularioCliente.class);
        intent.putExtra("codigo", "");
        startActivity(intent);
    }
}
