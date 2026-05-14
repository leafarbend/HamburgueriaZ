package com.example.hamburgueriaz;

import android.content.Intent;
import android.net.Uri;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    int quantidade = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    // 👉 BOTÃO ENVIAR PEDIDO
    public void enviarPedido(View view) {

        EditText nomeInput = findViewById(R.id.etNome);
        CheckBox bacon = findViewById(R.id.checkBoxBacon);
        CheckBox queijo = findViewById(R.id.checkBoxQueijo);
        CheckBox onion = findViewById(R.id.checkBoxOnion);
        TextView resumo = findViewById(R.id.txtResumo);

        String nome = nomeInput.getText().toString();

        boolean temBacon = bacon.isChecked();
        boolean temQueijo = queijo.isChecked();
        boolean temOnion = onion.isChecked();

        int preco = calcularPreco(quantidade, temBacon, temQueijo, temOnion);

        String mensagem =
                "Nome do cliente: " + nome +
                        "\nTem Bacon? " + (temBacon ? "Sim" : "Não") +
                        "\nTem Queijo? " + (temQueijo ? "Sim" : "Não") +
                        "\nTem Onion Rings? " + (temOnion ? "Sim" : "Não") +
                        "\nQuantidade: " + quantidade +
                        "\nPreço final: R$ " + preco;

        resumo.setText(mensagem);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // só apps de email

        intent.putExtra(Intent.EXTRA_SUBJECT, "Pedido de " + nome);
        intent.putExtra(Intent.EXTRA_TEXT, mensagem);

        startActivity(intent);
    }

    // 👉 CALCULAR PREÇO
    private int calcularPreco(int quantidade, boolean bacon, boolean queijo, boolean onion) {
        int preco = 20;

        if (bacon) preco += 2;
        if (queijo) preco += 2;
        if (onion) preco += 3;

        return preco * quantidade;
    }

    // 👉 BOTÃO +
    public void aumentar(View view) {
        quantidade++;
        TextView qtd = findViewById(R.id.txtQuantidade);
        qtd.setText(String.valueOf(quantidade));
    }

    // 👉 BOTÃO -
    public void diminuir(View view) {
        if (quantidade > 0) quantidade--;
        TextView qtd = findViewById(R.id.txtQuantidade);
        qtd.setText(String.valueOf(quantidade));
    }
}
