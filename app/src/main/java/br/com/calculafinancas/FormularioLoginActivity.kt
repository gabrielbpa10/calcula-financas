package br.com.calculafinancas

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class FormularioLoginActivity : AppCompatActivity() {

    private val NOME_BAR_TELA: String = "Cadastro"
    private var nome: EditText? = null
    private var sobrenome: EditText? = null
    private var telefone: EditText? = null
    private var email: EditText? = null
    private var senha: EditText? = null
    private var botaoSalvar: Button? = null
    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_formulario_login)
        setTitle(NOME_BAR_TELA)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        nome = findViewById(R.id.activity_formulario_edit_text_nome)
        sobrenome = findViewById(R.id.activity_formulario_edit_text_sobrenome)
        telefone = findViewById(R.id.activity_formulario_edit_text_telefone)
        email = findViewById(R.id.activity_formulario_edit_text_email)
        senha = findViewById(R.id.activity_formulario_edit_text_senha)
        botaoSalvar = findViewById(R.id.activity_formulario_button_salvar)

        botaoSalvar?.setOnClickListener{
            if(nome?.text.toString() != "" &&
                    sobrenome?.text.toString() != "" &&
                    telefone?.text.toString() != "" &&
                    email?.text.toString() != "" &&
                    senha?.text.toString() != ""){

                val handler = Handler(Looper.getMainLooper())
                mAuth = FirebaseAuth.getInstance()
                mAuth.createUserWithEmailAndPassword(email?.text.toString(), senha?.text.toString())
                    .addOnCompleteListener{
                        if(it.isSuccessful){
                            handler.post{
                                Toast.makeText(applicationContext,
                                "O usuário ${nome?.text.toString()} foi cadastrado com sucesso!",
                                Toast.LENGTH_SHORT).show()
                                finish()
                            }
                        } else{
                            handler.post{
                                Toast.makeText(
                                    applicationContext,
                                    "Usuário não cadastrado, por favor tente mais tarde.",
                                    //it.exception?.message,
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        }
                    }
            } else {
                Toast.makeText(this,"Há algum campo em branco, por favor, verifique.",Toast.LENGTH_SHORT).show()
            }
        }
    }
}