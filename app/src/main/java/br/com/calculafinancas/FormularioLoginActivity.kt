package br.com.calculafinancas

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class FormularioLoginActivity : AppCompatActivity() {

    private val NOME_BAR_TELA: String = "Cadastro"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_formulario_login)
        setTitle(NOME_BAR_TELA)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}