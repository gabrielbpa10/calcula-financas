package br.com.calculafinancas

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView

class LoginActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var criarNovoCadastro: TextView
    private lateinit var acessarPerfil: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        criarNovoCadastro = findViewById(R.id.activity_main_novo_cadastro)
        acessarPerfil = findViewById(R.id.activity_main_button_acessar)

        criarNovoCadastro.setOnClickListener(this)
        acessarPerfil.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when(view?.id){
            R.id.activity_main_novo_cadastro -> {
                var intent = Intent(this, FormularioLoginActivity::class.java)
                startActivity(intent)
            }

            R.id.activity_main_button_acessar -> {

            }
        }
    }
}